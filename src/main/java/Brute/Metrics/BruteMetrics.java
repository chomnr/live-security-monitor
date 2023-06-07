package Brute.Metrics;

import Brute.Metrics.TimeBasedMetrics.NumberOfAttemptsOverTime;

import java.util.*;

public class BruteMetrics {


    private BruteMetricData metrics;

    //public Map<String, Class<? extends BruteMetricData>> metricsList;
    //private List<Class<? extends BruteMetricData>> metricList;
    //private List<TimeBasedMetric> timeBasedMetricMap;

    public BruteMetrics() {
        metrics =  new BruteMetricData();
    }

    public BruteMetricData GetMetrics() {
        return metrics;
    }





    /*
    public Map<String, BruteMetricType> metricsList;
    private boolean useDefaultMetrics = true;

    public BruteMetrics() {
        metricsList = new HashMap<>();
    }

    public BruteMetrics(boolean useDefaultMetrics) {
        metricsList = new HashMap<>();
        this.useDefaultMetrics = useDefaultMetrics;
    }

    public void track() throws BruteException {
        if (this.useDefaultMetrics) {
            registerDefault();
        }
    }

    public Map<String, BruteMetricType> getMetricsList(){
        return metricsList;
    }

    public NumberOfAttemptsOverTime getNumberOfAttemptsOverTimeMetrics() {
        return (NumberOfAttemptsOverTime) metricsList.get(new NumberOfAttemptsOverTime().getMetricName());
    }

    /*
    public <T extends BruteMetricType<T>> BruteMetricType get(T type) throws MetricNotRegistered {
        if (!metrics.containsKey(type.getMetricName())) {
            throw new MetricNotRegistered();
        }
        return type.getMetricType();
    }

    /*
    public T get(BruteMetricType<T> type) throws BruteException {

        String metricName = type.getMetricName();
        if (!metrics.containsKey(metricName)) {
            throw new MetricNotRegistered();
        }
        return type.getMetricType();
    }
    */


    /*
    public BruteMetricType getType(String metricName) throws MetricNotRegistered {
        if (!metrics.containsKey(metricName)) {
            throw new MetricNotRegistered();
        }
        return metrics.get(metricName).getMetricType();
    }
    */

    /*
    public T get(BruteMetricType<T> type) throws BruteException {

        String metricName = type.getMetricName();
        if (!metrics.containsKey(metricName)) {
            throw new MetricNotRegistered();
        }
        return type.getMetricType();
    }
     */


    /*

    private void add(BruteMetricType type) throws BruteException {
        String metricName = type.getMetricName();
        if (metricsList.containsKey(metricName)) {
            throw new MetricUniqueViolation();
        }
        if (metricsList.containsValue(type)) {
            throw new MetricUniqueViolation();
        }
        metricsList.put(type.getMetricName(), type);
    }

     private void registerDefault() throws BruteException {
        add(new NumberOfAttemptsOverTime());
         metricsList.forEach((k, v) -> {
            System.out.println("BruteMetrics: Loaded metric '" + k + "'");
        });
    }

    /*
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

    public BruteMetricType get(String metricName) throws BruteException {
        if (!metrics.containsKey(metricName)) { throw new MetricNotRegistered(); }
        return metrics.get(metricName);
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

     */
}

//          //brutemeterics.get(file).populate().auto();l
//                            // automatically populates all the necessary metrics
//                            // inside the .json or database hmm.
// BruteMetrics<T: MetricType>
