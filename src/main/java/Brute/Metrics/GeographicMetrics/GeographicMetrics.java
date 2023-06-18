package Brute.Metrics.GeographicMetrics;

public class GeographicMetrics {

    private final static int DEFAULT_INCREMENT_VALUE = 1;

    private AttackOriginByCountry attackOriginByCountry;
    private AttackOriginByIp attackOriginByIp;

    public GeographicMetrics() {
        attackOriginByCountry = new AttackOriginByCountry();
        attackOriginByIp = new AttackOriginByIp();
    }

    public AttackOriginByCountry getAttackOriginByCountry() {
        return attackOriginByCountry;
    }
    public AttackOriginByIp getAttackOriginByIp() {
        return attackOriginByIp;
    }

    public void populate(String ip) {
        getAttackOriginByCountry().addAttacker(ip, DEFAULT_INCREMENT_VALUE);
        getAttackOriginByIp().addAttacker(ip, DEFAULT_INCREMENT_VALUE);
    }

    public void populate(String ip, int amount) {
        getAttackOriginByCountry().addAttacker(ip, amount);
        getAttackOriginByIp().addAttacker(ip, amount);
    }
}
