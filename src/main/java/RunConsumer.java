import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;
/**
 * run consumer
 */
public class RunConsumer {
    public static void main(String[] args) throws IOException {
        //usage: topic startingPosition brokerConfig push/pull
        if(!Utilities.validateArgsConsumer(args)){
            System.exit(-1);
        }

        long duration = 0;
    //    for(int k  = 1; k < 11; k++){
//            System.out.println("\n\n~~~~~~~~~~~~~~~~ ROUND " + k + "\n");
            String topic = args[0];
            int startingPosition = Integer.parseInt(args[1]);
            String method = args[2]; // pull or push

            List<Object> maps = Utilities.readBrokerConfig();
            IPMap ipMap = (IPMap) maps.get(0);
            PortMap portMap = (PortMap) maps.get(1);
            Consumer consumer = null;
            int requestCounter = 0;
            int start = 0;
            int max = 0;
            int receiveCounter = 0;
            int lastReceivedCounter = 0;



            while(true) {
                for (int i = 1; i <= Utilities.numOfBrokersInSys; i++) { // loop though num of brokers
                    // Connect to the consumer
                    //                System.out.println("\nStarting position: " + startingPosition);
                    String brokerHostName = ipMap.getIpById(String.valueOf(i));
                    int brokerPort = Integer.parseInt(portMap.getPortById(String.valueOf(i)));
                    String brokerLocation = brokerHostName + ":" + brokerPort;

                    consumer = new Consumer(brokerLocation, topic, startingPosition, method);
//                    consumer.setReceiverCounter(lastReceivedCounter);
//                    consumer.setMaxPosition(max);

                    if (method.equals("pull")) {
                        if (requestCounter == 0) {
                            receiveCounter = consumer.getReceiverCounter() + startingPosition - 1;
                        } else {
                            receiveCounter += (consumer.getReceiverCounter() - lastReceivedCounter);
                        }

                        if (consumer.getMaxPosition() >= max) {
                            max = consumer.getMaxPosition();
                        }
                        System.out.println("max: " + max + ", receiverCounter: " + receiveCounter);
                        if (max - start == receiveCounter) { // get through all brokers
                            if (requestCounter != 0) { // not first time
                                startingPosition = max + 1;
                            } // else if first time, will use input starting position
                        }
                        consumer.subscribe(topic, startingPosition);
                        lastReceivedCounter = consumer.getReceiverCounter();
                        try { // every 1 sec request new data
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else if (method.equals("push")) {
                        consumer.subscribe(topic, startingPosition);
                    }

                }

                if (method.equals("pull")) {
                    requestCounter++;

                    System.out.println("outside for loop: " + "max: " + max + ", receiverCounter: " + receiveCounter);
                    if (max - start != receiveCounter) {
                        // miss brokers -> no increment on starting position
                    } else {
                        if (requestCounter != 0) { // not first time
                            startingPosition = max + 1;

                        } // else if first time, will use input starting position
                        break;
                    }

                } else if (method.equals("push")) {
                    break;
                }




            }
      //      duration += consumer.getDuration();
      //  }
      //  System.out.println("************************************ AVERGAE TIME: " + duration/10);
    }
}