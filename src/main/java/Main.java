import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.Constants;
import Brute.Metrics.BruteMetrics;
import Brute.WebSocket.BruteServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws BruteException, IOException, InterruptedException {
        // Creates a LOG_FILE and METRIC_FILE_LOCATION.
        // If they do not exist.
        loadPrerequisites();

        // The websocket server.
        BruteServer a = new BruteServer(Constants.WEBSOCKET_PORT);

        // Handles the data inside METRIC_FILE_LOCATION.
        BruteMetrics b = new BruteMetrics(Constants.METRIC_FILE_LOCATION);

        // Listens to any changes to LOG_FILE.
        BruteFileListener c = new BruteFileListener(Constants.LOG_FILE_DIRECTORY, Constants.LOG_FILE, b);

        // Starts the websocket server.
        a.start();

        // Starts listening to LOG_FILE.
        c.listen();
    }


    private static void loadPrerequisites() throws IOException {
        createLogFile();
        createMetricsFile();
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

    private static boolean createLogFile() throws IOException {
        File logFile = new File(Constants.LOG_FILE_LOCATION);
        if (!logFile.exists()) {
            boolean createFile = logFile.createNewFile();
            return true;
        }
        return false;
    }
}
