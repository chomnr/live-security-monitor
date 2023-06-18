package Brute.Metrics.ProtocolBasedMetrics;

public class ProtocolBasedMetrics {

    /*
    * Adding new protocols
    * Step 1: Add the desired protocol to ProtocolType
    * Step 2: Then modify getProtocolByName() accordingly.
    *
    * Nice and simple.
    */

    private DistributionOfAttackProtocols distributionOfAttackProtocols;

    public enum ProtocolBasedType {
        SSH
    }

    public ProtocolBasedMetrics() {
        distributionOfAttackProtocols = new DistributionOfAttackProtocols();
    }

    public DistributionOfAttackProtocols getDistributionOfAttackProtocols() {
        return distributionOfAttackProtocols;
    }

    public void populate(String name, int amount) {
        getDistributionOfAttackProtocols().insert(name, amount);
    }
    public void populate(ProtocolBasedType type, int amount) {
        getDistributionOfAttackProtocols().insert(type, amount);
    }

    public void populate(String type) {
        getDistributionOfAttackProtocols().insert(type, 1);
    }

    public void populate(ProtocolBasedType type) {
        getDistributionOfAttackProtocols().insert(type, 1);
    }
}
