package Brute.Exceptions;

import Brute.BruteException;

public class InvalidPort extends BruteException {

    public InvalidPort() {
        super(200, "BRUTE_INVALID_PORT", "Your port must range from 0 to 65535.");
    }
}