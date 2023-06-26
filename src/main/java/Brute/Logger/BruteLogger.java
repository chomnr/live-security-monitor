package Brute.Logger;

import Brute.Constants;
import Brute.Metrics.BruteMetricData;
import Brute.Metrics.BruteMetrics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BruteLogger {
    private ArrayList<LogEntry> logs = new ArrayList<>();

    public BruteLogger() {}

    public BruteLogger(String fileLocation) throws IOException {
        JsonReader jr = new JsonReader(new FileReader(fileLocation));

        Gson gson = new Gson();
        BruteLogger data = gson.fromJson(jr, BruteLogger.class);
        logs = data.getLogs();
    }

    public void addLog(LogEntry entry) {
        logs.add(entry);
    }

    public void saveLogs() throws FileNotFoundException {
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter(Constants.LOG_FILE_LOCATION));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonWriter.jsonValue(gson.toJson(this));
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LogEntry> getLogs() {
        return logs;
    }
}
