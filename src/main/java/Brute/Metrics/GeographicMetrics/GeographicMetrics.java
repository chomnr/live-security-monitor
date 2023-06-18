package Brute.Metrics.GeographicMetrics;

public class GeographicMetrics {

    private AttackOriginByCountry attackOriginByCountry;
    private AttackOriginByIp attackOriginByIp;

    public enum GeographicType {
        COUNTRY, IP
    }

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
        getAttackOriginByCountry().addAttacker(ip, 1);
        getAttackOriginByIp().addAttacker(ip, 1);
    }

    public void populate(String ip, int amount) {
        getAttackOriginByCountry().addAttacker(ip, amount);
        getAttackOriginByIp().addAttacker(ip, amount);
    }
}
