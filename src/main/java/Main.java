import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.Constants;
import Brute.Logger.BruteLogger;
import Brute.Metrics.BruteMetrics;
import Brute.WebSocket.BruteServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws BruteException, IOException {
        // Creates a TRACKER_FILE, LOG_FILE and METRIC_FILE_LOCATION.
        // If they do not exist.
        loadPrerequisites();

        // The websocket server.
        BruteServer a = new BruteServer(Constants.WEBSOCKET_PORT);

        // Handles the data inside METRIC_FILE_LOCATION.
        BruteMetrics b = new BruteMetrics(Constants.METRIC_FILE_LOCATION);

        // Stores the actual 'metrics' data. ^ above is actually analytics but... yeah.
        BruteLogger c = new BruteLogger(Constants.LOG_FILE_LOCATION);

        // Listens to any changes to TRACKER_FILE.
        BruteFileListener d = new BruteFileListener(Constants.TRACKER_FILE_DIRECTORY, Constants.TRACKER_FILE, b, c);

        // Starts the websocket server.
        a.start();

        // Starts listening to TRACKER_FILE.
        d.listen(a);
    }


    private static void loadPrerequisites() throws IOException {
        createTrackerFile();
        createMetricsFile();
        createLogFile();
    }

    private static boolean createMetricsFile() throws IOException {
        File metricFile = new File(Constants.METRIC_FILE_LOCATION);
        if (!metricFile.exists()) {
            boolean createFile = metricFile.createNewFile();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(Constants.METRIC_FILE_LOCATION);
            writer.write(gson.toJson(new BruteMetrics()));
            writer.close();
            return true;
        }
        return false;
    }

    private static boolean createTrackerFile() throws IOException {
        File logFile = new File(Constants.TRACKER_FILE_LOCATION);
        if (!logFile.exists()) {
            boolean createFile = logFile.createNewFile();
            return true;
        }
        return false;
    }

    private static boolean createLogFile() throws IOException {
        File logFile = new File(Constants.LOG_FILE_LOCATION);
        if (!logFile.exists()) {
            boolean createFile = logFile.createNewFile();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(Constants.LOG_FILE_LOCATION);
            writer.write(gson.toJson(new BruteLogger()));
            writer.close();
            return true;
        }
        return false;
    }

}
