package Brute.Metrics;

import Brute.Constants;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;

public class BruteMetrics {

    private BruteMetricData metrics;

    public BruteMetrics() {
        metrics =  new BruteMetricData();
    }

    public BruteMetrics(String fileLocation) throws IOException {
        JsonReader jr = new JsonReader(new FileReader(fileLocation));

        Gson gson = new Gson();
        BruteMetrics data = gson.fromJson(jr, BruteMetrics.class);
        metrics = data.getMetrics();
    }

    public BruteMetricData getMetrics() {
        return metrics;
    }

    public void saveMetrics() throws FileNotFoundException {
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(Constants.METRIC_FILE_LOCATION));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonWriter.jsonValue(gson.toJson(this));
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}