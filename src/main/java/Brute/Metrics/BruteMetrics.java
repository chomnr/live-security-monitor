package Brute.Metrics;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;

public class BruteMetrics {

    private final BruteMetricData metrics;

    public BruteMetrics() {
        metrics =  new BruteMetricData();
    }

    public BruteMetrics(String directory) throws IOException {
        Gson gson = new Gson();
        JsonReader br = new JsonReader(new FileReader(directory));
        BruteMetrics brute = gson.fromJson(br, BruteMetrics.class);

        this.metrics = brute.GetMetrics();
    }

    public BruteMetricData GetMetrics() {
        return metrics;
    }
}
//          //brutemeterics.get(file).populate().auto();l
//                            // automatically populates all the necessary metrics
//                            // inside the .json or database hmm.
// BruteMetrics<T: MetricType>
