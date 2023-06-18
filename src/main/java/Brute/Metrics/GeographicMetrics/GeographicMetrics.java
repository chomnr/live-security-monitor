package Brute.Metrics.GeographicMetrics;

public class GeographicMetrics {

    private AttackOriginByCountry attackOriginByCountry;

    public enum GeographicType {
        COUNTRY, IP
    }

    public GeographicMetrics() {
        attackOriginByCountry = new AttackOriginByCountry();
    }

    public AttackOriginByCountry getAttackOriginByCountry() {
        return attackOriginByCountry;
    }

    public void populate(String ip) {
        getAttackOriginByCountry().addAttacker(ip, 1);
    }

    public void populate(String ip, int amount) {
        getAttackOriginByCountry().addAttacker(ip, amount);
    }
}
