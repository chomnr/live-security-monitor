import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.Metrics.BruteMetrics;
import Brute.WebSocket.BruteServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws BruteException, IOException {
        BruteServer a = new BruteServer(Constants.WEBSOCKET_PORT);
        BruteMetrics b = new BruteMetrics(Constants.METRIC_FILE_LOCATION);
        BruteFileListener c = new BruteFileListener(Constants.FILE_DIRECTORY, Constants.FILE, true, b.GetMetrics());
        a.start();
        c.listen();
    }
}
