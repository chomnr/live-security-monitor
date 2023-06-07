import Brute.BruteException;
import Brute.BruteFileListener;
import Brute.Metrics.BruteMetricData;
import Brute.Metrics.BruteMetrics;
import Brute.WebSocket.BruteServer;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws BruteException, IOException, InterruptedException {
        BruteServer a = new BruteServer(Constants.WEBSOCKET_PORT);
        BruteMetrics b = new BruteMetrics();
        BruteFileListener c = new BruteFileListener(Constants.FILE_DIRECTORY, Constants.FILE, true);

        a.start();
        b.track();
        c.listen();
    }
    /*
    public static void main(String[] args) throws BruteException, IOException, InterruptedException {
        BruteServer a = new BruteServer(Constants.WEBSOCKET_PORT);
        BruteMetrics b = new BruteMetrics();
        BruteFileListener c = new BruteFileListener(Constants.FILE_DIRECTORY, Constants.FILE, true);
        NumberOfAttemptsOverTime test = b.getNumberOfAttemptsOverTimeMetrics();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        test.getHourly().addData(dateFormat.format(new Date().getTime()), 1003);
        test.getHourly().addData(dateFormat.format(new Date().getTime() + 1000), 13223);
        Gson gson = new Gson();
        System.out.println(gson.toJson(test));
        a.start();
        b.track();
        c.listen();
    }
     */

    // Custom GSON Serializer
    public static class MyCustomSerializer implements JsonSerializer<Map<String, BruteMetricData>> {
        @Override
        public JsonElement serialize(Map<String, BruteMetricData> stringBruteMetricTypeMap, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            stringBruteMetricTypeMap.forEach((k, v) -> {
                jsonObject.add(k, context.serialize(v));
            });
            return null;
        }
    }

}