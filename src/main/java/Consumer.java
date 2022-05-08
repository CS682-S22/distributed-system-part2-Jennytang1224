import com.google.protobuf.InvalidProtocolBufferException;
import dsd.pubsub.protos.MessageInfo;
import dsd.pubsub.protos.PeerInfo;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * consumer class
 */
public class Consumer {
    private String brokerLocation;
    private String topic;
    private int startingPosition;
    private int brokerPort;
    private String brokerHostName;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Connection connection;
    private static String outputPath;
    private Receiver newReceiver;
    private static int maxPosition = 0;
    private String method;
    static int totalSaved = 0;
    static long startTime;
    static long endTime;
    static long duration;

    public Consumer(String brokerLocation, String topic, int startingPosition, String method) {
        this.brokerLocation = brokerLocation;
        this.topic = topic;
        this.startingPosition = startingPosition;
        this.brokerHostName = brokerLocation.split(":")[0];
        this.brokerPort = Integer.parseInt(brokerLocation.split(":")[1]);
        this.socket = null;
        this.method = method;

        try {
            this.socket = new Socket(this.brokerHostName, this.brokerPort);
            this.connection = new Connection(this.socket);
            this.input = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
            this.output = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
        } catch (IOException e) {
            // e.printStackTrace();
        }

        System.out.println("\n*** this consumer is connecting to broker " + brokerLocation + " ***");
        // draft peerinfo
        String type = "consumer " + method;
        List<Object> maps = Utilities.readConfig();
        IPMap ipMap = (IPMap) maps.get(0);
        PortMap portMap = (PortMap) maps.get(1);
        String peerHostName = Utilities.getHostName();
       // int peerPort = Integer.parseInt(portMap.getPortById(ipMap.getIdByIP(peerHostName)));
        int peerPort = 1413;

        PeerInfo.Peer peerInfo = PeerInfo.Peer.newBuilder()
                .setType(type)
                .setHostName(peerHostName)
                .setPortNumber(peerPort)
                .build();
        if(connection == null) {
            System.out.println("(This broker is NOT in use)");
            return;
        }
        this.connection.send(peerInfo.toByteArray());
        //save consumer info to filename
        outputPath = "files/" + type + "_" + peerHostName + "_" + peerPort + "_output";
        System.out.println("consumer sends first msg to broker with its identity...\n");
        newReceiver = new Receiver(peerHostName, peerPort, this.connection);
        Thread serverReceiver = new Thread(newReceiver);
        serverReceiver.start();
    }


    // send request to broker
    /**
     * send request to broker with topic and starting position
     */
    public void subscribe(String topic, int startingPosition){
        System.out.println("... Requesting topic: " + topic + " starting at position: " + startingPosition + "...");
        MessageInfo.Message request = MessageInfo.Message.newBuilder()
                .setTopic(topic)
                .setOffset(startingPosition)
                .build();
        if(connection == null) {
            return;
        }
        this.connection.send(request.toByteArray());
    }

    /**
     * get position counter
     */
    public int getPositionCounter(){
        return newReceiver.getPositionCounter();
    }

    /**
     * get max position
     */
    public int getMaxPosition(){
        return maxPosition;
    }

    /**
     * get Receiver counter
     */
    public int getReceiverCounter(){
        return newReceiver.receiverCounter;
    }

    public void setReceiverCounter(int newCounter) {
        newReceiver.receiverCounter = newCounter;
    }

    public void setMaxPosition(int newCounter) {
         maxPosition= newCounter;
    }

    /**
     * inner class Receiver
     */
    static class Receiver implements Runnable {
        private String name;
        private int port;
        private Connection conn;
        boolean receiving = true;
        private CS601BlockingQueue<MessageInfo.Message> bq;
        private ExecutorService executor;
        int positionCounter;
        static int receiverCounter = 0;

        public Receiver(String name, int port, Connection conn) {
            this.name = name;
            this.port = port;
            this.conn = conn;
            this.bq = new CS601BlockingQueue<>(100);
            this.executor = Executors.newSingleThreadExecutor();
            this.positionCounter = 0;
        }

        public int getPositionCounter(){
            return positionCounter;
        }

        public int receiverCounter(){
            return receiverCounter;
        }

        @Override
        public void run() {
            MessageInfo.Message m = null;
            Runnable add = () -> {
                byte[] result = conn.receive();
                if (result != null) {
                    try {
                        System.out.println("num of data: " + ++totalSaved);
                        if(totalSaved == 1){
                            startTime = System.nanoTime();
                        }
                        bq.put(MessageInfo.Message.parseFrom(result));
                        receiverCounter++;
                        int id = MessageInfo.Message.parseFrom(result).getOffset();
                        if(id >= maxPosition){
                            maxPosition = id;
                        }
                        System.out.println(" ---> Consumer added a record to the blocking queue...");
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }
                else{
//                    System.out.println("received result is null");
                }
            };

            //application poll from bq
            while (receiving) {
                executor.execute(add);
                m = bq.poll(100);
                if (m != null) { // received within timeout
                    //save to file
                    byte[] arr = m.getValue().toByteArray();
                    try {
                        writeBytesToFile(outputPath, arr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
//                    System.out.println("m == null");
                    break;
                }
            }
            endTime = System.nanoTime();
            duration = (endTime - startTime)/1000000; // sec
            System.out.println("**************Execution time in seconds: " + duration);

        }
    }





    /**
     * write bytes to files
     */
    private static void writeBytesToFile(String fileOutput, byte[] buf)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileOutput, true)) {
            System.out.println("Application is storing data to the file...");
            fos.write(buf);
            fos.write(10);
            fos.flush();
        }
        catch(IOException e){
            System.out.println("file writing error :(");
        }
    }



    public long getDuration(){
        return duration;
    }

}