package Brute.WebSocket;

import Brute.BruteException;
import Brute.Exceptions.InvalidHostName;
import Brute.Exceptions.InvalidPort;

import java.net.InetSocketAddress;

public class BruteServerHelper {
    public static InetSocketAddress AnalyzePort(int port) throws BruteException {
        if (port < 0 || port > 65535 ) throw new InvalidPort();
        return new InetSocketAddress(port);
    }

    public static InetSocketAddress AnalyzeHostName(String address) throws InvalidHostName {
        if (address.isEmpty()) throw new InvalidHostName();
        if (address.length() > 253) throw new InvalidHostName();
        if (!address.matches("[a-zA-Z0-9-]+")) throw new InvalidHostName();
        return new InetSocketAddress(address, 0);
    }
}
