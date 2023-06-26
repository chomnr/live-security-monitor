package Brute.WebSocket;

import Brute.BruteException;
import Brute.BruteUtilities;
import Brute.Constants;
import Brute.Logger.BruteLogger;
import Brute.Logger.LogEntry;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Brute.Constants.LOG_FILE_LAST_X;

public class BruteServer extends WebSocketServer {

    public BruteServer(int port) throws BruteException {
        super(BruteServerHelper.analyze(port));
    }

    public BruteServer(InetSocketAddress address) {
        super(address);
    }

    public BruteServer(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        String host = webSocket.getRemoteSocketAddress().getAddress().getHostAddress();
        try {
            List<LogEntry> logs = new BruteLogger(Constants.LOG_FILE_LOCATION).getLogs();
            List<LogEntry> getEndLogs = logs.subList(Math.max(logs.size() - LOG_FILE_LAST_X, 0), logs.size());
            Collections.reverse(getEndLogs);
            Gson gson = new Gson();
            String result = gson.toJson(getEndLogs);
            webSocket.send(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(host + " has connected to BruteExpose!");
    }

    @Override
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        String host = webSocket.getRemoteSocketAddress().getAddress().getHostAddress();
        System.out.println(host + " has left BruteExpose!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {}

    @Override
    public void onError(WebSocket webSocket, Exception e) {}

    @Override
    public void onStart() {
        setConnectionLostTimeout(10);
        BruteUtilities.print("Listening on " + this.getAddress().getHostName() + ":" + this.getPort() + ".");
    }
}
