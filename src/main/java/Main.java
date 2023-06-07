import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.Metrics.BruteMetrics;
import Brute.Metrics.TimeBasedMetrics.TimeBasedMetrics;
import Brute.WebSocket.BruteServer;
import com.google.gson.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws BruteException, IOException, InterruptedException {
        BruteServer a = new BruteServer(Constants.WEBSOCKET_PORT);
        BruteMetrics b = new BruteMetrics();
        BruteFileListener c = new BruteFileListener(Constants.FILE_DIRECTORY, Constants.FILE, true);

        b.GetMetrics().getTimeBasedMetrics().getNumberOfAttemptsOverTime().add(TimeBasedMetrics.TimeBasedType.DAILY, 123);
        b.GetMetrics().getTimeBasedMetrics().getNumberOfAttemptsOverTime().add(TimeBasedMetrics.TimeBasedType.WEEKLY, 341);
        b.GetMetrics().getTimeBasedMetrics().getNumberOfAttemptsOverTime().add(TimeBasedMetrics.TimeBasedType.HOURLY, 10);
        Gson gson = new Gson();
        System.out.println(gson.toJson(b));


        a.start();
        c.listen();
    }
}