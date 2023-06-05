package Brute.Exceptions;

import Brute.BruteException;

public class InvalidHostName extends BruteException {
    public InvalidHostName() {
        super(100, "BRUTE_INVALID_HOSTNAME", "Your host name is invalid.");
    }
}