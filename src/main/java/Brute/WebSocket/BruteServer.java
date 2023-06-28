package Brute.WebSocket;

import Brute.BruteException;
import Brute.BruteUtilities;
import Brute.Constants;
import Brute.Logger.BruteLogger;
import Brute.Logger.LogEntry;
import Brute.Metrics.BruteMetrics;
import Brute.Metrics.BruteMetricsMerger;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BruteServer extends WebSocketServer {

    private BruteMetrics metrics;

    public BruteServer(int port, BruteMetrics metrics) throws BruteException {
        super(BruteServerHelper.analyze(port));
        this.metrics = metrics;
    }

    /*
    public BruteServer(InetSocketAddress address) {
        super(address);
    }

    public BruteServer(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }*/


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        Gson gson = new Gson();
        String host = webSocket.getRemoteSocketAddress().getAddress().getHostAddress();
        try {
            List<LogEntry> logs = BruteLogger.limitLogs(new BruteLogger(Constants.LOG_FILE_LOCATION).getLogs(),
                    Constants.LOG_RETRIEVAL_LIMIT);
            Collections.reverse(logs);

            Map<String, Integer> topAttacking = metrics.getMetrics().getGeographicMetrics()
                    .getAttackOriginByCountry()
                    .getTopAttackers(10);

            webSocket.send(String.valueOf(gson.toJsonTree(BruteMetricsMerger.mergeJson(logs, topAttacking))));
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
        // wiuthout ssl
        BruteUtilities.print("Listening on ws://" + this.getAddress().getHostName() + ":" + this.getPort() + ".");
    }
}
