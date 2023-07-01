package Brute.Metrics.GeographicMetrics;

import Brute.BruteException;
import Brute.BruteUtilities;
import Brute.Constants;
import Brute.Exceptions.InvalidIpAddress;
import com.maxmind.db.NodeCache;
import com.maxmind.db.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Path;
import java.util.*;

public class AttackOriginByCountry {

    private HashMap<String, Integer> countries = new HashMap<>();
    private transient Reader ipReader;

    public AttackOriginByCountry() {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.COUNTRY_FILE_LOCATION);
            ipReader = new Reader(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAttacker(String ip, int amount) {
        addAttempt(ip, amount);
    }

    private void addAttempt(String ip, int amount) {
        String country = getCountryByIp(ip);
        if (!countries.containsKey(country)) {
            countries.put(country, amount);
        } else {
            countries.put(country, getAttempts(country)+amount);
        }
    }

    public HashMap<String, Integer> getTopAttackers(int limit) {
        return (HashMap<String, Integer>) BruteUtilities.sortByValueAndLimit((Map<String, Integer>) countries.clone(), limit);
    }

    private int getAttempts(String country) {
        return countries.get(country);
    }

    public String getCountryByIp(String ip) {
        try {
            InetAddress inet = getInetAddress(ip);
            return ipReader.get(inet).get("country").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private InetAddress getInetAddress(String ip) throws IOException {
       return InetAddress.getByName(ip);
    }
}
