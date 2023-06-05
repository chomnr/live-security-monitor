import Brute.BruteException;
import Brute.WebSocket.BruteServer;

public class Main {
    private static final int WEBSOCKET_PORT = 8080;
    public static void main(String[] args) throws BruteException {
        BruteServer s = new BruteServer(WEBSOCKET_PORT);
        s.start();
    }
}