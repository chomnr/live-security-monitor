import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.BruteUtilities;
import Brute.Constants;
import Brute.Logger.BruteLogger;
import Brute.Metrics.BruteMetrics;
import Brute.WebSocket.BruteServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import static Brute.Constants.autoSetBasePath;

public class Main {

    public static void main(String[] args) throws Exception, BruteException {
        if (!isApplicationNotRunning()) { System.exit(1); }


        if (!autoSetBasePath()) {
            BruteUtilities.print("Unable to set base path.");
            return;
        }
        loadPrerequisites();

        // Stores the actual 'metrics' data. ^ above is actually analytics but... yeah.
        BruteLogger logger = new BruteLogger(Constants.LOG_FILE_LOCATION);

        // Handles the data inside METRIC_FILE_LOCATION.
        BruteMetrics metrics = new BruteMetrics(Constants.METRIC_FILE_LOCATION);

        // The websocket server.
        BruteServer server = new BruteServer(Constants.WEBSOCKET_PORT, metrics);
        // If you need SSL support please refer to these urls
        // https://github.com/TooTallNate/Java-WebSocket/blob/master/src/main/example/SSLServerExample.java
        // https://github.com/TooTallNate/Java-WebSocket/blob/master/src/main/example/SSLServerLetsEncryptExample.java
        // Setting up Cloudflare SSL works with origin too.
        // https://github.com/TooTallNate/Java-WebSocket/issues/916
        // https://docs.oracle.com/cd/E35822_01/server.740/es_admin/src/tadm_ssl_convert_pem_to_jks.html


        // Listens to any changes to TRACKER_FILE.
        BruteFileListener listener = new BruteFileListener(Constants.TRACKER_FILE_DIRECTORY, Constants.TRACKER_FILE, metrics, logger);

        // Starts the websocket server.
        server.start();

        // Starts listening to TRACKER_FILE.
        listener.listen(server);
    }


    private static void loadPrerequisites() throws IOException {
        createFile(Constants.TRACKER_FILE_LOCATION);
        createFile(Constants.METRIC_FILE_LOCATION);
        createFile(Constants.LOG_FILE_LOCATION);
    }

    private static void createFile(String fileLocation) throws IOException {
        File file = new File(fileLocation);
        if (file.exists()) { return; }
        boolean created = file.createNewFile();
        if (fileLocation.contains(".json") && created) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(fileLocation);
            writer.write(gson.toJson(new BruteLogger()));
            writer.close();
        }
    }

    /*
        Check if application is already running.
        https://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
    */
    private static boolean isApplicationNotRunning() {
        Socket s = null;
        try {
            s = new Socket("localhost", Constants.WEBSOCKET_PORT);
            BruteUtilities.print("Already listening on port " + Constants.WEBSOCKET_PORT);
            return false;
        } catch (IOException e) {
            return true;
        } finally {
            if( s != null){
                try {
                    s.close();
                } catch (IOException e) {
                    throw new RuntimeException("You should handle this error." , e);
                }
            }
        }
    }
}
