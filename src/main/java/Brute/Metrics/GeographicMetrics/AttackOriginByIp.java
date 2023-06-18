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
        try {
            if (!isValidIpAddress(ip)) {
                throw new InvalidIpAddress();
            }
            if (!ips.containsKey(ip)) {
                ips.put(ip, amount);
            } else {
                ips.put(ip, getAttempts(ip)+amount);
            }
        } catch (BruteException e) {
            e.printStackTrace();
        }
    }

    private int getAttempts(String country) {
        return ips.get(country);
    }

    private boolean isValidIpAddress(String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            return !ip.endsWith(".");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
