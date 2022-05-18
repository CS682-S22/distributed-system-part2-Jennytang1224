import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * run consumer
 */
public class RunConsumer {
    public static void main(String[] args) throws IOException {
        //usage: topic startingPosition brokerConfig push/pull
        if(!Utilities.validateArgsConsumer(args)){
            System.exit(-1);
        }
        String topic = args[0];
        int startingPosition = Integer.parseInt(args[1]);
        String method = args[2]; // pull or push

        Consumer consumer = new Consumer(topic, startingPosition, method);
        consumer.run();
    }


}