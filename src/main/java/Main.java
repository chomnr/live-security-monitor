import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.WebSocket.BruteServer;

import java.io.IOException;

public class Main {
    private static final int WEBSOCKET_PORT = 8080;
    public static void main(String[] args) throws BruteException, IOException, InterruptedException {
        BruteServer s = new BruteServer(WEBSOCKET_PORT);
        BruteFileListener fl = new BruteFileListener("/Users/zeljk/OneDrive/Desktop/Test/", "hello.txt", true);
        s.start();
        fl.listen();
    }
}