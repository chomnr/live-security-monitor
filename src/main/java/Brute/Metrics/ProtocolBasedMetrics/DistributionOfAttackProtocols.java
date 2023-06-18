package Brute.Metrics.ProtocolBasedMetrics;

import java.util.HashMap;

import Brute.BruteException;
import Brute.Exceptions.MetricTypeNotCompatible;
import Brute.Metrics.ProtocolBasedMetrics.ProtocolBasedMetrics.ProtocolBasedType;


public class DistributionOfAttackProtocols {

    private HashMap<String, Integer> protocols = new HashMap<>();

    public DistributionOfAttackProtocols() {}

    public void insert(String type, int amount) {
        try {
            ProtocolBasedType protocolType = getProtocolByName(type);
            addAttempts(protocolType, amount);
        } catch (BruteException e) {
            e.printStackTrace();
        }
    }
    public void insert(ProtocolBasedType type, int amount) {
        addAttempts(type, amount);
    }

    private void addAttempts(ProtocolBasedType type, int amount) {
        String protocolName = getNameOfProtocol(type);

        if (protocols.get(protocolName) == null) {
            protocols.put(protocolName, amount);
        } else {
            protocols.put(protocolName, getAttempts(type)+amount);
        }
    }

    private Integer getAttempts(ProtocolBasedType type) {
        return protocols.get(getNameOfProtocol(type));
    }

    private ProtocolBasedType getProtocolByName(String protocol) throws MetricTypeNotCompatible {
        if (protocol.equalsIgnoreCase("sshd")) {
            return ProtocolBasedType.SSH;
        }
        throw new MetricTypeNotCompatible();
    }

    private String getNameOfProtocol(ProtocolBasedType type) {
        return type.name();
    }
}
