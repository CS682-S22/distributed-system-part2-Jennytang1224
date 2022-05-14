import com.google.protobuf.InvalidProtocolBufferException;
import dsd.pubsub.protos.MessageInfo;
import dsd.pubsub.protos.PeerInfo;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * consumer class
 */
public class Consumer implements Runnable{
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
    static AtomicInteger totalSaved = new AtomicInteger(0);
    static long startTime;
    static long endTime;
    static long duration;
    int max = 0;

    public Consumer(String topic, int startingPosition, String method) {
        // this.brokerLocation = brokerLocation;
        this.topic = topic;
        this.startingPosition = startingPosition;
//        this.brokerHostName = brokerLocation.split(":")[0];
//        this.brokerPort = Integer.parseInt(brokerLocation.split(":")[1]);
        this.socket = null;
        this.method = method;
    }

    @Override
    public void run() {

        List<Object> maps = Utilities.readBrokerConfig();
        IPMap ipMap = (IPMap) maps.get(0);
        PortMap portMap = (PortMap) maps.get(1);
        int requestCounter = 0;
        int start = 0;

        AtomicInteger receiveCounter = new AtomicInteger(0);
        int lastReceivedCounter = 0;

        //connect to each broker and ask for data
      //  while(true) {
            for (int i = 1; i <= Utilities.numOfBrokersInSys; i++) { // loop though num of brokers
                // Connect to the consumer
                //                System.out.println("\nStarting position: " + startingPosition);
                String brokerHostName = ipMap.getIpById(String.valueOf(i));
                int brokerPort = Integer.parseInt(portMap.getPortById(String.valueOf(i)));
                String brokerLocation = brokerHostName + ":" + brokerPort;

                //connect to each broker
                try {
                    this.socket = new Socket(brokerHostName, brokerPort);
                    this.connection = new Connection(this.socket);
                    this.input = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
                    this.output = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                System.out.println("\n*** this consumer is connecting to broker " + brokerLocation + " ***");

                List<Object> maps1 = Utilities.readConfig();
                IPMap ipMap1 = (IPMap) maps1.get(0);
                PortMap portMap1 = (PortMap) maps1.get(1);
                String peerHostName = Utilities.getHostName();
                // int peerPort = Integer.parseInt(portMap1.getPortById(ipMap1.getIdByIP(peerHostName)));
                int peerPort = 1413;
                // send peer info to each broker
                String type = "consumer " + method;
                PeerInfo.Peer peerInfo = PeerInfo.Peer.newBuilder()
                        .setType(type)
                        .setHostName(peerHostName)
                        .setPortNumber(peerPort)
                        .build();
                if (connection == null) {
                    System.out.println("(This broker is NOT in use)");
                    return;
                }
                newReceiver = new Receiver(peerHostName, peerPort, this.connection);
                Thread serverReceiver = new Thread(newReceiver);
                serverReceiver.start();
                this.connection.send(peerInfo.toByteArray());
                //save consumer info to filename
                outputPath = "files/" + type + "_" + peerHostName + "_" + peerPort + "_output";
                System.out.println("consumer sends first msg to broker with its identity...\n");

                try { // every 2 sec request new data
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (method.equals("pull")) {
                    if (requestCounter == 0) {//first time
                        //receiveCounter = consumer.getReceiverCounter() + startingPosition - 1;
                        receiveCounter.set(getReceiverCounter()  + startingPosition - 1);
                        startTime = System.currentTimeMillis();
                        System.out.println("start time: " + startTime);
                        requestCounter++;
                    } else { // not first time
                        // receiveCounter += (consumer.getReceiverCounter() - lastReceivedCounter);
                        int tmp = receiveCounter.intValue() + getReceiverCounter() - lastReceivedCounter;
                        receiveCounter.set(tmp);
                    }

                    if (getMaxPosition() >= max) {
                        max = getMaxPosition();
                    }
                    System.out.println("max: " + max + ", totalSaved : " + (totalSaved));
                    if (max - start == getReceiverCounter()) { // get through all brokers
                        if (requestCounter != 0) { // not first time
                            startingPosition = max + 1;

                        } // else if first time, wilsl use input starting position
                        if(max != 0) {
                           // break;
                            endTime = System.currentTimeMillis();
                            System.out.println("end time: " + endTime);

                            duration = (endTime - startTime); // millisec
                            System.out.println("**************Execution time in milliseconds: " + (duration));
                            System.exit(0);
                        }
                    }
                    subscribe(topic, startingPosition);
                    lastReceivedCounter = getReceiverCounter();

                } else if (method.equals("push")) {
                    if (requestCounter == 0) {//first time
                        receiveCounter.set(getReceiverCounter() + startingPosition - 1);
                        startTime = System.currentTimeMillis();
                        System.out.println("start time: " + startTime);
                        requestCounter++;
                    } else { // not first time
                        // receiveCounter += (consumer.getReceiverCounter() - lastReceivedCounter);
                        int tmp = receiveCounter.intValue() + totalSaved.intValue() - lastReceivedCounter;
                        receiveCounter.set(tmp);
                    }

                    if (getMaxPosition() >= max) {
                        max = maxPosition;
                    }
                    System.out.println("max: " + max + ", totalsaved : " + (totalSaved));
                    if (max == totalSaved.intValue()) { // get through all brokers
//                        if (requestCounter != 0) { // not first time
//                            startingPosition = max + 1;
//
//                        } // else if first time, will use input starting position
                        if(max != 0) {
                            // break;
                            endTime = System.currentTimeMillis();
                            System.out.println("end time: " + endTime);

                            duration = (endTime - startTime); // millisec
                            System.out.println("**************Execution time in milliseconds: " + (duration));
                            System.exit(0);
                        }
                    }
                    subscribe(topic, startingPosition);

                }

               try { // every 2 sec request new data
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


//            if (method.equals("pull")) {
//                requestCounter++;
//
//                System.out.println("outside for loop: " + "max: " + max + ", receiverCounter: " + receiveCounter);
//                if (max - start != receiveCounter.intValue()) {
//                    // miss brokers -> no increment on starting position
//                } else {
//                    if (requestCounter != 0) { // not first time
//                        startingPosition = max + 1;
//
//                    } // else if first time, will use input starting position
//                    break;
//                }
//
//            }
//            else if (method.equals("push")) {
//                try { // every 2 sec request new data
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
    //    }


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
        return newReceiver.receiverCounter.intValue();
    }

    public void setReceiverCounter(int newCounter) {
        newReceiver.receiverCounter = new AtomicInteger(newCounter);
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
        static AtomicInteger receiverCounter = new AtomicInteger(0);

        public Receiver(String name, int port, Connection conn) {
            this.name = name;
            this.port = port;
            this.conn = conn;
            this.bq = new CS601BlockingQueue<>(500000);
            this.executor = Executors.newSingleThreadExecutor();
            this.positionCounter = 0;
        }

        public int getPositionCounter(){
            return positionCounter;
        }

        public int receiverCounter(){
            return receiverCounter.intValue();
        }

        @Override
        public void run() {
            MessageInfo.Message m = null;
            Runnable add = () -> {
                byte[] result = conn.receive();
                if (result != null) {
                    try {
//                        if(totalSaved.intValue() == 0){
//                            startTime = System.currentTimeMillis();
//                        }
                        bq.put(MessageInfo.Message.parseFrom(result));
                        System.out.println("num of data: " + totalSaved.incrementAndGet());
                        receiverCounter.incrementAndGet();
                        int id = MessageInfo.Message.parseFrom(result).getOffset();
                        if(id >= maxPosition){
                            maxPosition = id;
                        }
                        System.out.println(" ---> Consumer added a record to the blocking queue...");
                    } catch (InvalidProtocolBufferException e) {
                       // e.printStackTrace();
                    }
                }
                else{
//                    System.out.println("received result is null");
                }
            };

            //application poll from bq
            while (receiving) {
                executor.execute(add);
                m = bq.poll(3);
                if (m != null) { // received within timeout
                    //save to file
                    byte[] arr = m.getValue().toByteArray();
                    try {
                        writeBytesToFile(outputPath, arr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{ //nothing receives
                 //   System.out.println("m == null");
//                    if(totalSaved.intValue() != 0) {
//                        break;
//                    }
                }
            }
//            endTime = System.currentTimeMillis();
//            duration = (endTime - startTime); // millisec
//            System.out.println("**************Execution time in seconds: " + duration);

        }
    }





    /**
     * write bytes to files
     */
    private static void writeBytesToFile(String fileOutput, byte[] buf)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileOutput, true)) {
         //   System.out.println("Application is storing data to the file...");
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