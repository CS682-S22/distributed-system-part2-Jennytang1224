//import dsd.pubsub.protos.MessageInfo;
//import java.io.*;
//import com.google.protobuf.InvalidProtocolBufferException;
//
//
//public class Application implements Runnable {
//    private String topic;
//    private int startingPosition;
//    Consumer consumer;
//
//
//    public Application(String topic, int startingPosition, Consumer consumer) {
//        this.topic = topic;
//        this.startingPosition = startingPosition;
//        this.consumer = consumer;
//    }
//
//    @Override
//    public void run(){
//        while(true){
//            String fileOutput = consumer.getOutputPath();
//            byte[] m = null;
//            MessageInfo.Message d = null;
//
//            //  application polls from bq
//            try {
//                m = consumer.poll(30);
//            } catch (NullPointerException e){
//            }
//            if (m != null) { // received within timeout
//                //save to file
//                try {
//                    d = MessageInfo.Message.parseFrom(m);
//                } catch (InvalidProtocolBufferException e) {
//                    e.printStackTrace();
//                }
//                byte[] arr = d.getValue().toByteArray();
//
//                try {
//                    Utilities.writeBytesToFile(fileOutput, arr);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            else{
//                // System.out.println("m == null");
//            }
//        }
//    }
//
//}