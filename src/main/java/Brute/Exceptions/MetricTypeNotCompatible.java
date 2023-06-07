package Brute.Exceptions;

import Brute.BruteException;

public class MetricTypeNotCompatible extends BruteException {
    public MetricTypeNotCompatible() {
        super(500, "METRIC_TYPE_NOT_COMPATIBLE", "This requested metric type is not compatible with this metric.");
    }
}
