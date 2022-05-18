import java.io.IOException;
import java.util.List;
/**
 * run broker
 */
public class RunBroker {
    public static void main(String[] args){
        String brokerConfigFile = Utilities.brokerConfigFile;
        List<Object> maps = Utilities.readBrokerConfig();
        IPMap ipMap = (IPMap) maps.get(0);
        PortMap portMap = (PortMap) maps.get(1);
        String brokerHostName = Utilities.getHostName();
        int brokerPort = Integer.parseInt(portMap.getPortById(ipMap.getIdByIP(brokerHostName)));
        DistributedBroker broker = new DistributedBroker(brokerHostName, brokerPort, brokerConfigFile);
        try {
            broker.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
