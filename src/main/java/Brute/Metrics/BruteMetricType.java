package Brute.Metrics;

public class BruteMetricType {
    private String metricName;

    public BruteMetricType(String name) {
        this.metricName = name;
        System.out.println("BruteMetrics: Loaded metric '" + name + "'");
    }

    public String getMetricName() {
        return metricName;
    }
}
