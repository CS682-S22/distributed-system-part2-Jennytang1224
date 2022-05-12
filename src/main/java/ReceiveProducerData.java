import com.google.protobuf.InvalidProtocolBufferException;
import dsd.pubsub.protos.MessageInfo;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReceiveProducerData implements Runnable{
    byte[] recordBytes;
    private List<HashMap<String, HashMap<Integer, List<byte[]>>>> topicMapList;
   // static List<byte[]> list;
    static HashMap<Integer, List<byte[]>> partitionMap;
    int brokerID;
    HashMap<String, HashMap<Integer, List<byte[]>>> topicMap;
    boolean firstTime;
    List<byte[]> topicList;
    int count;



    public ReceiveProducerData(byte[] recordBytes, List<HashMap<String, HashMap<Integer, List<byte[]>>>> topicMapList,
                               int brokerID, boolean firstTime, int count) {
        this.recordBytes = recordBytes;
        this.topicMapList = topicMapList;
        this.brokerID = brokerID;
        this.firstTime = firstTime;
        this.count = count;

        if(topicMapList.size() == 0){
            for (int i = 0; i < 5; i++) {
                topicMapList.add(i, new HashMap<String, HashMap<Integer, List<byte[]>>>());
            }
        }
    }

    @Override
    public void run(){
        // get correct topicMap by brokerID
        topicMap = topicMapList.get(brokerID - 1);
        MessageInfo.Message d = null;
        try {
            d = MessageInfo.Message.parseFrom(recordBytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        String topic = d.getTopic();
        int partitionID = d.getPartition();

        if(this.topicMap != null && this.topicMap.size() == 0) {
            //topicList = new CopyOnWriteArrayList<>();
            partitionMap = new HashMap<>();
            topicList = Collections.synchronizedList(new ArrayList<>());
            topicList.add(recordBytes);
            count++;
            partitionMap.put(partitionID, topicList);
            this.topicMap.put(topic, partitionMap);
            System.out.println(" ->>> saved first record");

        }else{ // if topic map is null
            if (this.topicMap.containsKey(topic)) { //if topic is in map
                partitionMap = topicMap.get(topic);
                if (partitionMap.containsKey(partitionID)) { // if partitionID is in topic, add
                    partitionMap.get(partitionID).add(recordBytes);
                    count++;
                } else { // if partitionID is not in topic, create a new inner map
                    topicList = Collections.synchronizedList(new ArrayList<>());
                    partitionMap.put(partitionID, topicList);
                    partitionMap.get(partitionID).add(recordBytes);
                }
            } else { //if topic is not in the map, create a new inner hashmap and add first record
                topicList = Collections.synchronizedList(new ArrayList<>());
                partitionMap = new HashMap<>();
                topicList.add(recordBytes);
                count++;
                partitionMap.put(partitionID, topicList);
                this.topicMap.put(topic, partitionMap);
            }
//            System.out.println(" -> size of topic map: " + count);

        }
     //  System.out.println("broker " + brokerID + ": topic map: " + topicMap);
    }

}
