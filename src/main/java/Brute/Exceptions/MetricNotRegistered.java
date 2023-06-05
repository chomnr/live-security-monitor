package Brute.Exceptions;

import Brute.BruteException;

public class MetricNotRegistered extends BruteException {
    public MetricNotRegistered() {
        super(400, "METRIC_NOT_REGISTERED", "You attempted to access a metric that exists but is not registered.");
    }
}
