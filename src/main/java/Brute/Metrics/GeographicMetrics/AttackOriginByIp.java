package Brute.Metrics.GeographicMetrics;

import Brute.BruteException;
import Brute.Exceptions.InvalidIpAddress;

import java.util.HashMap;

public class AttackOriginByIp {
    private HashMap<String, Integer> ips = new HashMap<>();

    public void addAttacker(String ip, int amount) {
        addAttempt(ip, amount);
    }

    private void addAttempt(String ip, int amount) {
        if (!ips.containsKey(ip)) {
            ips.put(ip, amount);
        } else {
            ips.put(ip, getAttempts(ip)+amount);
        }
    }

    private int getAttempts(String country) {
        return ips.get(country);
    }
}
