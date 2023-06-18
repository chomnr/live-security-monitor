package Brute.Exceptions;

import Brute.BruteException;

public class InvalidIpAddress extends BruteException {
    public InvalidIpAddress() {
        super(700, "INVALID_IP_ADDRESS", "The ip address is invalid.");
    }
}
