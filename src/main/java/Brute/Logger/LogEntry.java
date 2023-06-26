package Brute.Logger;

public class LogEntry {
    private String username;
    private String password;
    private String hostname;
    private String protocol;
    private String country;

    public LogEntry(String username, String password, String hostname, String protocol) {
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.protocol = protocol;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHostname() {
        return hostname;
    }
    public String getCountry() {
        if (country == null || country.isEmpty() || country.isBlank()) {
            return "NULL";
        }
        return country;
    }
}