import com.google.protobuf.InvalidProtocolBufferException;
import dsd.pubsub.protos.MessageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * broker send consumer data
 */
public class SendConsumerDataPushBased implements Runnable{
    private Connection consumerConnection;
    private byte[] recordBytes;
    private static List<byte[]> topicList;
    private HashMap<Integer, List<byte[]>> partitionMap;
    private HashMap<String, HashMap<Integer, List<byte[]>>> topicMap;
    private int startingPosition;
    private String topic;
    private HashMap<Integer, Connection> connMap;
    private List<HashMap<String, HashMap<Integer, List<byte[]>>>> topicMapList;
    private int brokerID;
    private boolean sending = true;
    int counter = 0;
  //  int numMessageSent = 0;
    int lastSentMessageID = 0;
    int currentSize = 0;
    int previousSize = 0;
    int msgCounter = 0;


    public SendConsumerDataPushBased(Connection consumerConnection, byte[] recordBytes, List<HashMap<String, HashMap<Integer,
            List<byte[]>>>> topicMapList, HashMap<Integer, Connection> connMap, int brokerID){
        this.consumerConnection = consumerConnection;
        this.recordBytes = recordBytes;
        this.connMap = connMap;
        this.topicMapList = topicMapList;
        this.brokerID = brokerID;

    }

    @Override
    public void run() {
        while(sending){

            if(counter == 0){//first time, send consumer all I have in my topic map
                // get correct topicMap by brokerID
                topicMap = topicMapList.get(brokerID - 1);

                MessageInfo.Message d = null;
                if (recordBytes != null) {
                    try {
                        d = MessageInfo.Message.parseFrom(recordBytes);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                    topic = d.getTopic();
                    startingPosition = d.getOffset();
                    System.out.println("Broker: " + brokerID + " -> Consumer subscribed to: " + topic + ", at position: " + startingPosition);

                 //   System.out.println("consumer topic map: " + topicMap);
                    if (!topicMap.containsKey(topic)) {
                        System.out.println("No topic called '" + topic + "' in this broker!");
                    } else {
                        partitionMap = topicMap.get(topic);
                        System.out.println("there are " + partitionMap.size() + " partitions in this consumer with topic: " + topic);
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        for (Map.Entry<Integer, List<byte[]>> entry : partitionMap.entrySet()) {
                            topicList = entry.getValue();
                            // start getting the all record from this topic from starting position
                            for (int j = 0; j < topicList.size(); j++) {
                                byte[] record = topicList.get(j);
                                int id = -1;
                                try {
                                    id = MessageInfo.Message.parseFrom(record).getOffset();
                                } catch (InvalidProtocolBufferException e) {
                                    e.printStackTrace();
                                }
                                previousSize++;
                                if ((id > 0) && (id >= startingPosition)) {
                                    consumerConnection.send(record);
                                   // numMessageSent++;
                                    if(lastSentMessageID < id) {
                                        lastSentMessageID = id;
                                    }
                                    System.out.println("New data in partition: " + entry.getKey() + ", and " + ++msgCounter + " messages has been sent to the consumer \n");
//                                    try {
//                                        Thread.sleep(1000);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
                                }
                               // System.out.println("no new data in partition: " + entry.getKey() + "\n");
                            }
                        }
                    }
                    System.out.println("-------------------- lastSentMessageID: " + lastSentMessageID);

                }
                counter++;
            }
            else {// if theres more data got from producer
                topicMap = topicMapList.get(brokerID - 1);
                if (!topicMap.containsKey(topic)) {
                    //System.out.println("No topic called '" + topic + "' in this broker!");
                } else {
                    partitionMap = topicMap.get(topic);
                    System.out.println("there are " + partitionMap.size() + " partitions in this consumer with topic: " + topic);
                    //get total number of messages in partition map now
                    for (Map.Entry<Integer, List<byte[]>> entry : partitionMap.entrySet()) {
                        topicList = entry.getValue();
                        for (int j = 0; j < topicList.size(); j++) {
                            currentSize++;
                        }
                    }
                    System.out.println("---------------------- current size: " + currentSize);
                    System.out.println("---------------------- previous size : " + previousSize);

                    //there's more data in the topicmap now, send additional data to consumer
                    if (currentSize > previousSize) {
                        System.out.println("NEW DATA COMES IN!!!!!!!!!!!");
                        startingPosition = lastSentMessageID + 1;
                        previousSize = 0;

                        for (Map.Entry<Integer, List<byte[]>> entry : partitionMap.entrySet()) {
                            topicList = entry.getValue();
                            // start getting the all record from this topic from starting position
                            for (int j = 0; j < topicList.size(); j++) {
                                byte[] record = topicList.get(j);
                                int id = -1;
                                try {
                                    id = MessageInfo.Message.parseFrom(record).getOffset();
                                } catch (InvalidProtocolBufferException e) {
                                    e.printStackTrace();
                                }
                                previousSize++;
                                if ((id > 0) && (id >= startingPosition)) { // in each list if there's item id > last item id, send item
                                    consumerConnection.send(record);

                                    if(lastSentMessageID < id) {
                                        lastSentMessageID = id;
                                    }
                                   // numMessageSent++;
                                    System.out.println(entry.getKey() + ++msgCounter + " of messages has been sent to the consumer \n");
                                }

                                //System.out.println("no new data in partition: " + entry.getKey() + "\n");
                            }
                        }
                    }else{
                        System.out.println("NO NEW DATA YET!!!!!!!!!!!!!!!!!!!!");
                      //  System.exit(0);

                    }
                }
                counter++;
                currentSize = 0;
            }
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
