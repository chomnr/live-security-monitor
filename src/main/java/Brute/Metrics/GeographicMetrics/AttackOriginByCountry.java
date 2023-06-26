package Brute.Metrics.GeographicMetrics;

import Brute.BruteException;
import Brute.Constants;
import Brute.Exceptions.InvalidIpAddress;
import com.maxmind.db.Reader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class AttackOriginByCountry {

    private HashMap<String, Integer> countries = new HashMap<>();
    private transient Path ipToCountryFile;
    private transient Reader ipReader;

    public AttackOriginByCountry() {
        //TODO: replace c:// soon just hacky solution..
        try {
            URI uri = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(Constants.COUNTRY_FILE_LOCATION)).toURI();
            ipToCountryFile = Paths.get(uri);
            ipReader = new Reader(ipToCountryFile.toFile(), Reader.FileMode.MEMORY_MAPPED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAttacker(String ip, int amount) {
        addAttempt(ip, amount);
    }

    private void addAttempt(String ip, int amount) {
        try {
            if (!isValidIpAddress(ip)) {
                throw new InvalidIpAddress();
            }
            String country = getCountryByIp(ip);
            if (!countries.containsKey(country)) {
                countries.put(country, amount);
            } else {
                countries.put(country, getAttempts(country)+amount);
            }
        } catch (BruteException e) {
            e.printStackTrace();
        }
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
            return "US"; // ? why does this work hmm
        }
    }

    private InetAddress getInetAddress(String ip) throws IOException {
       return InetAddress.getByName(ip);
    }

    /*
        Snippet taken from:
        https://stackoverflow.com/questions/4581877/validating-ipv4-string-in-java
    */
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
