package Brute.Metrics.CredentialBasedMetrics;

import Brute.Metrics.CredentialBasedMetrics.CredentialBasedMetrics.CredentialBasedType;


import java.util.HashMap;

public class CommonlyTargetedByCredential {
    private HashMap<String, Integer> usernames = new HashMap<>();
    private HashMap<String, Integer> passwords = new HashMap<>();
    private HashMap<String, Integer> combinations = new HashMap<>();

    public void insert(String username, String password, int amount) {
        addAttempts(CredentialBasedType.USERNAME, username, amount);
        addAttempts(CredentialBasedType.PASSWORD, password, amount);
        addAttempts(CredentialBasedType.COMBINATION, getCombinationKey(username, password), amount);
    }

    public void insert(CredentialBasedType type, String field, int amount) {
        addAttempts(type, field, amount);
        if (type == CredentialBasedType.COMBINATION) {
            String[] ck = field.split(":");
            addAttempts(CredentialBasedType.COMBINATION, getCombinationKey(ck[0], ck[1]), amount);
        }
    }

    private void addAttempts(CredentialBasedType type, String field, int amount) {
        HashMap<String, Integer> currentMap = getCredentialMap(type);

        if (currentMap.containsKey(field)) {
            currentMap.put(field, getAttempts(type, field)+amount);
        } else {
            currentMap.put(field, amount);
        }
    }

    private Integer getAttempts(CredentialBasedType type, String field) {
        return getCredentialMap(type).get(field);
    }

    public HashMap<String, Integer> getCredentialMap(CredentialBasedType type) {
        if (type == CredentialBasedType.USERNAME) {
            return usernames;
        }
        if (type == CredentialBasedType.PASSWORD) {
            return passwords;
        }
        return combinations;
    }

    public String getCombinationKey(String username, String password) {
        String seperator = ":";
        return username + seperator + password;
    }
}
