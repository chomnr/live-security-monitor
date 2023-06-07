package Brute.Metrics.TimeBasedMetrics;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
/*
{
   "numberOfAttemptsOverTime":{
      "hourly":{
         "2023-06-04T00:00:00":10
      },
      "daily":{
         "2023-06-04":50
      },
      "weekly":{
         "2023-05-29":200
      }
   }
}
*/
public class NumberOfAttemptsOverTime extends TimeBasedMetric {

    private Map<TBM_TYPE, Map<String, Integer>> numberOfAttemptsOverTime;

    public void insert(TBM_TYPE type, int value) {
        LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        if (type == TBM_TYPE.HOURLY || type == TBM_TYPE.WEEKLY) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }
        
        numberOfAttemptsOverTime.get(type).put(date.format(formatter), value);
    }
}

/*

private Map<String, Integer> hourly = new HashMap<>();
    private Map<String, Integer> daily = new HashMap<>();
    private Map<String, Integer> weekly = new HashMap<>();
    private Map<String, Integer> attackFrequencyByDay = new HashMap<>();
    private Map<String, Integer> attackFrequencyByTime = new HashMap<>();

    public void add(TBM_TYPE type){
        Map<String, Integer> typeMap = null;
        if (type == TBM_TYPE.HOURLY) {
            typeMap = hourly;
        }
        if (type == TBM_TYPE.DAILY) {
            typeMap = daily;
        }
        if (type == TBM_TYPE.WEEKLY) {
            typeMap = weekly;
        }
        if (type == TBM_TYPE.ATTACKFREQUENCYBYDAY) {
            typeMap = attackFrequencyByDay;
        }
        if (type == TBM_TYPE.ATTACKFREQUENCYBYTIME) {
            typeMap = attackFrequencyByTime;
        }
    }
 */
   /*
    private Hourly hourly = new Hourly();

    public NumberOfAttemptsOverTime() {
        super("NumberOfAttemptsOverTime", NumberOfAttemptsOverTime.class);
    }

    public Hourly getHourly() {
        return this.hourly;
    }

    public class Hourly {
        private Map<String, Integer> hourlyMetrics = new HashMap<>();

        public Map<String, Integer> getData(){
            return hourlyMetrics;
        }

        public void addData(String key, int value){
            hourlyMetrics.put(key, value);
        }
    }

    public static class MetricSerializer implements JsonSerializer<NumberOfAttemptsOverTime> {
        @Override
        public JsonElement serialize(NumberOfAttemptsOverTime src, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("Metrics", context.serialize(src));
            return jsonObject;
        }
    }
     */
