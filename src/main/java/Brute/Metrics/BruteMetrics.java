package Brute.Metrics;

import Brute.BruteException;
import Brute.Exceptions.MetricNotRegistered;
import Brute.Exceptions.MetricUniqueViolation;
import Brute.Metrics.Types.NumberOfAttemptsOverTime;

import java.util.*;

public class BruteMetrics {
    private Map<String, BruteMetricType> metrics;
    private boolean useDefaultMetrics = true;

    public BruteMetrics() {
        metrics = new HashMap<>();
    }

    public BruteMetrics(boolean useDefaultMetrics) {
        metrics = new HashMap<>();
        this.useDefaultMetrics = useDefaultMetrics;
    }

    public void track(){
        if (useDefaultMetrics) {
            try {
                registerDefault();
            } catch (BruteException e) {
                e.printStackTrace();
            }
        }
    }

    public BruteMetricType get(BruteMetricType type) throws BruteException {
        String metricName = type.getMetricName();
        if (!metrics.containsKey(metricName)) { throw new MetricNotRegistered(); }
        return metrics.get(type.getMetricName());
    }

    public void add(BruteMetricType type) throws BruteException {
        String metricName = type.getMetricName();
        if (metrics.containsKey(metricName)) { throw new MetricUniqueViolation(); }
        if (metrics.containsValue(type)) { throw new MetricUniqueViolation(); }
        metrics.put(type.getMetricName(), type);
    }

    private void registerDefault() throws BruteException {
        add(new NumberOfAttemptsOverTime());
    }
}

//          //brutemeterics.get(file).populate().auto();l
//                            // automatically populates all the necessary metrics
//                            // inside the .json or database hmm.
// BruteMetrics<T: MetricType>
