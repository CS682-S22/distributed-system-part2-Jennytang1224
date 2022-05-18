import java.io.IOException;
import java.util.List;
/**
 * run load balancer
 */
public class RunLoadBalancer {
    public static void main(String[] args){
        int numOfBrokers = Integer.parseInt(args[0]);
        int numOfPartitions = Integer.parseInt(args[1]);
        String brokerConfigFile = Utilities.brokerConfigFile;

        List<Object> maps = Utilities.readConfig();
        IPMap ipMap = (IPMap) maps.get(0);
        PortMap portMap = (PortMap) maps.get(1);
        String LBHostName = Utilities.getHostName();
        int LBPort = Integer.parseInt(portMap.getPortById(ipMap.getIdByIP(LBHostName)));
        LoadBalancer loadBalancer = new LoadBalancer(LBHostName, LBPort, numOfBrokers, numOfPartitions, brokerConfigFile);

        try {
            loadBalancer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
