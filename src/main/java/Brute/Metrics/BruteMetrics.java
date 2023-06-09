package Brute.Metrics;

public class BruteMetrics {

    private BruteMetricData metrics;

    public BruteMetrics() {
        metrics =  new BruteMetricData();
    }

    public BruteMetricData GetMetrics() {
        return metrics;
    }

    //load metrics data...

}

//          //brutemeterics.get(file).populate().auto();l
//                            // automatically populates all the necessary metrics
//                            // inside the .json or database hmm.
// BruteMetrics<T: MetricType>
