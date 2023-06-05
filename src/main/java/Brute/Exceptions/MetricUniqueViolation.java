package Brute.Exceptions;

import Brute.BruteException;

public class MetricUniqueViolation extends BruteException {
    public MetricUniqueViolation() {
        super(300, "METRIC_UNIQUE_VIOLATION", "Your metric is not unique either the username or the type already exist.");
    }
}
