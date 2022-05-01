import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import dsd.pubsub.protos.MessageInfo;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * load balancer save intermediate info file and send each record to broker
 */
public class ReceiveProducerMessage implements Runnable{
    private byte[] recordBytes;
    private String offsetOutputPath = Utilities.offsetFilePath;
    private String infoOutputPath = Utilities.InfoFileName;
    private int messageCounter;
    private int offsetInMem;
    private int numOfBrokers;
    private int numOfPartitions;
    private HashMap<Integer, Connection> connMap;
    private HashMap<String, Integer> counterMap;
    private static HashMap<String, HashMap<Integer, CopyOnWriteArrayList<byte[]>>> topicMap;

    public ReceiveProducerMessage( byte[] recordBytes, int messageCounter,
                                   int offsetInMem, int numOfBrokers, int numOfPartitions, HashMap<Integer, Connection> connMap, HashMap<String, Integer> counterMap) {
        this.recordBytes = recordBytes;
        this.messageCounter = messageCounter;
        this.offsetInMem = offsetInMem;
        this.numOfBrokers = numOfBrokers;
        this.numOfPartitions = numOfPartitions;
        this.connMap = connMap;
        this.counterMap = counterMap;
        topicMap = new HashMap<>();
    }

    @Override
    public void run(){
        MessageInfo.Message d = null;
        int count;
        try {
            d = MessageInfo.Message.parseFrom(recordBytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        // create counterMap: continuous messageId per topic
        String topic = d.getTopic();
        if(counterMap.containsKey(topic)){
            count = counterMap.get(topic);
            count++;
            counterMap.put(topic, count);
        }else{
            count = 1;
            counterMap.put(topic, 1);
        }
        String key = d.getKey();
        ByteString data = d.getValue();

        // calculate partitionID ba & brokerID sed on key
        int partitionID = Utilities.CalculatePartition(key, numOfPartitions);
        int brokerID = Utilities.CalculateBroker(partitionID, numOfBrokers);

        // save intermediate file:  msgID, key, topic, partitionID, BrokerID
        String line;
        line = count + "," + key + "," + topic + "," + partitionID + "," + brokerID;
        byte[] arr = line.getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println(line);
            writeBytesToFile(infoOutputPath, arr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // send protobuf with partition ID via assigned broker connection
        MessageInfo.Message record = MessageInfo.Message.newBuilder()
                .setTopic(topic)
                .setKey(key)
                .setValue(data)
                .setPartition(partitionID)
                .setOffset(count) // use msgid as offset
                .build();

        Connection connection = connMap.get(brokerID);
        connection.send(record.toByteArray());
        System.out.println("Message has been sent to the assigned BROKER: " + brokerID + ", PARTITION: " + partitionID);

        // save intermediate data msg id, offset of bytes
        String line1;
        if(this.messageCounter == 0){
            line1 = this.messageCounter + "," + 0;
        } else {
            offsetInMem += d.getOffset();
            line1 = this.messageCounter + "," + offsetInMem;
        }
        this.messageCounter++;
        byte[] arr1 = line1.getBytes(StandardCharsets.UTF_8);
        try {
            writeBytesToFile(offsetOutputPath, arr1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write bytes to files
     */
    private static void writeBytesToFile(String fileOutput, byte[] buf)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileOutput, true)) {
            System.out.println("data saved to info file");
            fos.write(buf);
            fos.write(10); //newline
            fos.flush();
        }
        catch(IOException e){
            System.out.println("file writing error :(");
        }
    }
}

