package Brute.WebSocket;

import Brute.BruteException;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collections;

public class BruteServer extends WebSocketServer {

    public BruteServer(int port) throws BruteException {
        super(BruteServerHelper.AnalyzePort(port));
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
        webSocket.send("Welcome to BruteExpose!");
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
    }
}
