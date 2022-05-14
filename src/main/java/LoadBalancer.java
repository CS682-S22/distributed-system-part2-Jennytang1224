import com.google.protobuf.InvalidProtocolBufferException;
import dsd.pubsub.protos.PeerInfo;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * load balancer class for partition
 */
public class LoadBalancer {
    private String hostName;
    private int port;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private static volatile boolean running = true;
    private static HashMap<String, HashMap<Integer, List<byte[]>>> topicMap;
    private static Server server;
    private Connection connection;
    private static String peerHostName;
    private static int peerPort;
    private static int messageCounter = 0;
    private static int offsetInMem = 0;
    private int numOfBrokers;
    private int numOfPartitions;
    private int brokerCounter = 1;
    static HashMap<Integer, Connection> connMap = new HashMap<>();
    private static HashMap<String, Integer> counterMap = new HashMap<>();
    private String brokerConfigFile;
    static long startTime;
    static long endTime;
    static long duration;
    static boolean firstTime = true;



    public LoadBalancer(String hostName, int port, int numOfBrokers, int numOfPartitions, String brokerConfigFile) {
        this.hostName = hostName;
        this.port = port;
        this.topicMap = new HashMap<>();
        this.numOfBrokers = numOfBrokers;
        this.numOfPartitions = numOfPartitions;
        this.brokerConfigFile = brokerConfigFile;

    }



    /**
     * load balancer create broker connections, and listening from producer
     */
    public void run() throws IOException {
        //create broker connection
        while (brokerCounter <= numOfBrokers) {
            List<Object> maps = Utilities.readBrokerConfig();
            IPMap ipMap = (IPMap) maps.get(0);
            PortMap portMap = (PortMap) maps.get(1);
            String brokerHostName = ipMap.getIpById(String.valueOf(brokerCounter));
            int brokerPort =  Integer.parseInt(portMap.getPortById(String.valueOf(brokerCounter)));
            // create a connection to the broker
            try {
                this.socket = new Socket(brokerHostName, brokerPort);
                connection = new Connection(this.socket);
                connMap.put(brokerCounter, connection);
                System.out.println("Connected to broker: " + brokerHostName + ":" + brokerPort);
                this.input = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
                this.output = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            brokerCounter++;
        }

        // start listening
        Thread serverListener = new Thread(() -> {
            boolean running = true;
            try {
                this.server = new Server(this.port);
                System.out.println("\nLoad balancer start listening on port: " + this.port + "...");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (running) {
                Connection connection = this.server.nextConnection(); // calls accept on server socket to block
                Thread serverReceiver = new Thread(new LoadBalancer.Receiver(this.hostName, this.port, connection, this.numOfBrokers, this.numOfPartitions));
                serverReceiver.start();
            }
        });
        serverListener.start(); // start listening ...
    }

    /**
     * inner class Receiver
     */
    static class Receiver implements Runnable {
        private String name;
        private int port;
        private Connection conn;
        boolean receiving = true;
        int counter = 0;
        private String type;
        int numOfBrokers;
        int numOfPartitions;

        public Receiver(String name, int port, Connection conn, int numberOfBrokers, int numOfPartitions) {
            this.name = name;
            this.port = port;
            this.conn = conn;
            this.numOfBrokers = numberOfBrokers;
            this.numOfPartitions = numOfPartitions;
        }

        @Override
        public void run() {
            PeerInfo.Peer p = null;
            while (receiving) {
                byte[] buffer = conn.receive();
                if (buffer == null || buffer.length == 0) {
                    // System.out.println("nothing received/ finished receiving");
                    if(messageCounter != 0){
                        if(firstTime) {
                            endTime = System.currentTimeMillis();
                            duration = (endTime - startTime); // millisec
                            System.out.println("**************Execution time in seconds: " + duration);
                            firstTime = false;
                        }
                   }
                }
                else {
                    if(counter == 0) { // first mesg is peerinfo
                        try {
                            p = PeerInfo.Peer.parseFrom(buffer);
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                        type = p.getType(); // consumer or producer
                        System.out.println("\n *** New Connection coming in -> peer type: " + type + " ***");
                        peerHostName = p.getHostName();
                        peerPort = p.getPortNumber();

                        if (type.equals("producer")) {
                            // get the messageInfo though socket
                            System.out.println("Load Balancer now has connected to producer: " + peerHostName + " port: " + peerPort + "\n");
                            counter++;
                        } else {
                            System.out.println("invalid type, should be either producer or consumer");
                        }

                    }
                    else{ // when receiving data
                        if(type.equals("producer")) {
                            if(messageCounter == 0){
                                startTime = System.currentTimeMillis();
                            }
                            Thread th = new Thread(new ReceiveProducerMessage(buffer, messageCounter,
                                    offsetInMem, numOfBrokers, numOfPartitions, connMap, counterMap));
                            th.start();
                            try {
                                th.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            counter++;
                            messageCounter++;
                        }
                        else{
                            System.out.println("invalid type, should be either producer or consumer");
                        }
                    }
                }
            }
        }
    }
}