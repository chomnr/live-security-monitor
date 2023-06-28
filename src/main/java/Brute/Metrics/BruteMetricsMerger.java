package Brute.Metrics;

import Brute.Logger.LogEntry;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class BruteMetricsMerger {
    private static final Gson gson = new Gson();

    public static String mergeJson(Object mergeA, Object mergeB) {
        return gson.toJson(new MetricsMerger(mergeA, mergeB));
    }

    public static class MetricsMerger {
        public Object mergeA;
        public Object mergeB;

        public MetricsMerger(Object mergeA, Object mergeB) {
            this.mergeA = mergeA;
            this.mergeB = mergeB;
        }

    }
}
