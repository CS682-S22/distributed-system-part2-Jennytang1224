import com.google.protobuf.InvalidProtocolBufferException;
import dsd.pubsub.protos.PeerInfo;
import java.io.*;
import java.util.*;
/**
 * broker class
 */
public class DistributedBroker {
    private String hostName;
    private int port;
    private static volatile boolean running = true;
    private static Server server;
    private static String peerHostName;
    private static int peerPort;
    private static int messageCounter = 0;
    private static List<HashMap<String,HashMap<Integer, List<byte[]>>>> topicMapList = new ArrayList<>();
    private static HashMap<String, HashMap<Integer, List<byte[]>>> topicMap;
    private static String brokerConfig;
    static boolean firstTime;
    static int count = 0;

    public DistributedBroker(String hostName, int port, String brokerConfig) {
        this.hostName = hostName;
        this.port = port;
        this.topicMap = new HashMap<>();
        this.brokerConfig = brokerConfig;
    }


    // broker needs to constantly listen and
    // unpack proto buffer see if its producer or consumer connection, peerinfo
    /**
     * use threads to start the connections, receive and send data concurrently
     */
    public void run() throws IOException {
        Thread serverListener = new Thread(() -> {
            boolean running = true;
            try {
                this.server = new Server(this.port);
                System.out.println("A broker start listening on port: " + this.port + "...");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (running) {
                Connection connection = this.server.nextConnection(); // calls accept on server socket to block
                Thread serverReceiver = new Thread(new DistributedBroker.Receiver(this.hostName, this.port, connection));
                serverReceiver.start();
            }
        });
        serverListener.start(); // start listening ...

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        int brokerID;

        public Receiver(String name, int port, Connection conn) {
            this.name = name;
            this.port = port;
            this.conn = conn;
            brokerID = Utilities.getBrokerIDFromFile(name, String.valueOf(port), brokerConfig);
        }

        @Override
        public void run() {
            PeerInfo.Peer p = null;
            while (receiving) {
                byte[] buffer = conn.receive();
                if (buffer == null || buffer.length == 0) {
                    // System.out.println("nothing received/ finished receiving");
                }

                if(counter == 0) { // first mesg is peerinfo
                    try {
                        p = PeerInfo.Peer.parseFrom(buffer);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }

                    type = p.getType(); // consumer or producer
                    System.out.println("\n*** Broker " + port + ": New Connection coming in ***");
                    peerHostName = p.getHostName();
                    peerPort = p.getPortNumber();

                    if (type.equals("consumer pull") || type.equals("consumer push")) {
                        System.out.println("this broker NOW has connected to consumer: " + peerHostName + " port: " + peerPort + "\n");
                        counter++;

                    } else {
                        // get the messageInfo though socket
                        type = "producer"; // producer data send from load balancer directly, so no peerinfo
                        System.out.println(">> this Broker now has connected to producer ");
                        firstTime = true;
                        Thread th = new Thread(new ReceiveProducerData(buffer, topicMapList, brokerID, firstTime, count++));
                        System.out.println(" -> size of topic map: " + count);

                        firstTime = false;
                        th.start();
                        try {
                            th.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        counter++;
                        messageCounter++;
                    }

                }
                else{ // when receiving data
                    if (type.equals("producer")) {
                        Thread th = new Thread(new ReceiveProducerData(buffer, topicMapList, brokerID, firstTime, count++));
                        System.out.println(" -> size of topic map: " + count);

                        th.start();
                        try {
                            th.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        counter++;
                        messageCounter++;
                    } else if (type.equals("consumer pull")) {
                        Thread th = new Thread(new SendConsumerDataPullBased(conn, buffer, topicMapList, LoadBalancer.connMap, brokerID));
                        th.start();
                        try {
                            th.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        counter++;
                    }
                    else if (type.equals("consumer push")) {
                        Thread th = new Thread(new SendConsumerDataPushBased(conn, buffer, topicMapList, LoadBalancer.connMap, brokerID));
                        th.start();
                        try {
                            th.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        counter++;
                    }else {
                        System.out.println("invalid type, should be either producer or consumer");
                    }
                }
            }
        }
    }

    public int receiveMessageCounter(){
        return messageCounter;
    }

}
