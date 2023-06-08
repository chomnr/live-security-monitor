package Brute.Exceptions;

import Brute.BruteException;

public class MetricBadTimeFormat extends BruteException {
    public MetricBadTimeFormat() {
        super(400, "METRIC_BAD_TIME_FORMAT", "The time that was received is not formatted correctly.");
    }
}
