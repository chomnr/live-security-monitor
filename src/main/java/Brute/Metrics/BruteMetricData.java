package Brute.Metrics;

import Brute.Metrics.GeographicMetrics.GeographicMetrics;
import Brute.Metrics.TimeBasedMetrics.TimeBasedMetrics;

public class BruteMetricData {

    private TimeBasedMetrics timeBasedMetrics;
    private GeographicMetrics geographicMetrics;

    public BruteMetricData() {
        timeBasedMetrics = new TimeBasedMetrics();
        geographicMetrics = new GeographicMetrics();
    }

    public TimeBasedMetrics getTimeBasedMetrics() {
        return timeBasedMetrics;
    }
    public GeographicMetrics getGeographicMetrics() { return geographicMetrics; }
}
