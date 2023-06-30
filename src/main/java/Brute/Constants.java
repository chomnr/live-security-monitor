package Brute;

import java.time.ZoneOffset;

//TODO: SHRINK THIS.
public class Constants {

    public static final int WEBSOCKET_PORT = 8080;
    public static final String COUNTRY_FILE_LOCATION = "country.mmdb";
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    public static String BASE_PATH;

    public static String METRIC_FILE_DIRECTORY; //BASE_PATH
    public static String METRIC_FILE = "brute_metrics.json";
    public static String METRIC_FILE_LOCATION; // METRIC_FILE_DIRECTORY + METRIC_FILE

    public static String TRACKER_FILE_DIRECTORY; // MAKE SURE THE DIRECTORY FOR BE_LOG_FILE ARE THE SAME. windows variant
    public static String TRACKER_FILE =  "brute_tracker.txt";
    public static String TRACKER_FILE_LOCATION; //TRACKER_FILE_DIRECTORY + TRACKER_FILE;

    public static String LOG_FILE_DIRECTORY;
    public static String LOG_FILE = "brute_log.json";
    public static String LOG_FILE_LOCATION; //LOG_FILE_DIRECTORY + LOG_FILE;
    public static final int LOG_RETRIEVAL_LIMIT = 50;

    public static boolean autoSetBasePath() {
        String os_name = System.getProperty("os.name");
        if (os_name.toLowerCase().contains("linux")) {
            BASE_PATH = "/var/log/";
        }
        if (os_name.toLowerCase().contains("windows")) {
            String user = System.getProperty("user.name");
            System.out.println(user);
            BASE_PATH = "/Users/" + user + "/OneDrive/Desktop/Test/";
        }

        if (!BASE_PATH.isEmpty()) {
            METRIC_FILE_DIRECTORY = BASE_PATH;
            TRACKER_FILE_DIRECTORY = BASE_PATH;
            LOG_FILE_DIRECTORY = BASE_PATH;

            METRIC_FILE_LOCATION = METRIC_FILE_DIRECTORY + METRIC_FILE;
            TRACKER_FILE_LOCATION = TRACKER_FILE_DIRECTORY + TRACKER_FILE;
            LOG_FILE_LOCATION = LOG_FILE_DIRECTORY + LOG_FILE;
            return true;
        }

        return false;
    }

    // Metrics File
    //public static final String METRIC_FILE_LOCATION = "/Users/zeljk/OneDrive/Desktop/Test/brute_metrics.json";
    /*
    public static String METRIC_FILE_DIRECTORY = BASE_PATH;
    public static String METRIC_FILE = "brute_metrics.json";
    public static String METRIC_FILE_LOCATION = METRIC_FILE_DIRECTORY + METRIC_FILE;

    // Tracker File
    public static String TRACKER_FILE_DIRECTORY = BASE_PATH; // MAKE SURE THE DIRECTORY FOR BE_LOG_FILE ARE THE SAME. windows variant
    public static String TRACKER_FILE = "brute_tracker.txt"; // MAKE SURE THE FILE FOR BE_LOG_FILE ARE THE SAME.
    public static String TRACKER_FILE_LOCATION = TRACKER_FILE_DIRECTORY + TRACKER_FILE;

    // Log File
    public static String LOG_FILE_DIRECTORY = BASE_PATH;
    public static String LOG_FILE = "brute_log.json";
    public static String LOG_FILE_LOCATION = LOG_FILE_DIRECTORY + LOG_FILE;
    public static final int LOG_RETRIEVAL_LIMIT = 50;
     */
}
