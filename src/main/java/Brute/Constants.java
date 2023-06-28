package Brute;

import java.time.ZoneOffset;

public class Constants {
    public static final int WEBSOCKET_PORT = 8080;

    public static final String COUNTRY_FILE_LOCATION = "country.mmdb";
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static final String METRIC_FILE_LOCATION = "/Users/zeljk/OneDrive/Desktop/Test/brute_metrics.json";

    public static final String TRACKER_FILE_DIRECTORY = "/Users/zeljk/OneDrive/Desktop/Test/"; // MAKE SURE THE DIRECTORY FOR BE_LOG_FILE ARE THE SAME.
    public static final String TRACKER_FILE = "brute_tracker.txt"; // MAKE SURE THE FILE FOR BE_LOG_FILE ARE THE SAME.
    public static final String TRACKER_FILE_LOCATION = TRACKER_FILE_DIRECTORY + TRACKER_FILE;

    public static final String LOG_FILE_DIRECTORY = "/Users/zeljk/OneDrive/Desktop/Test/";
    public static final String LOG_FILE = "brute_log.json";
    public static final String LOG_FILE_LOCATION = LOG_FILE_DIRECTORY + LOG_FILE;
    public static final int LOG_RETRIEVAL_LIMIT = 50;
}
