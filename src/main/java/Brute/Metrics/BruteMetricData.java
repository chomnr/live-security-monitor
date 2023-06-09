package Brute.Metrics;

import Brute.Metrics.TimeBasedMetrics.TimeBasedMetrics;

public class BruteMetricData {

    private TimeBasedMetrics timeBasedMetrics;

    public BruteMetricData() {
        timeBasedMetrics = new TimeBasedMetrics();
    }

    public TimeBasedMetrics getTimeBasedMetrics() {
        return timeBasedMetrics;
    }
}
