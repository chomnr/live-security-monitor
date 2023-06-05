import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.Metrics.BruteMetrics;
import Brute.WebSocket.BruteServer;

import java.io.IOException;

public class Main {
    private static final int WEBSOCKET_PORT = 8080;
    public static void main(String[] args) throws BruteException, IOException, InterruptedException {
        BruteServer a = new BruteServer(WEBSOCKET_PORT);
        BruteMetrics b = new BruteMetrics();
        BruteFileListener c = new BruteFileListener("/Users/zeljk/OneDrive/Desktop/Test/", "hello.txt", true);
        a.start();
        b.track();
        c.listen();
    }
}