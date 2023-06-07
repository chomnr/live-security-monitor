package Brute.Metrics.TimeBasedMetrics;

import Brute.Metrics.BruteMetricData;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import Brute.Metrics.TimeBasedMetrics.TimeBasedMetrics.TimeBasedType;
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
public class NumberOfAttemptsOverTime {

    private Map<String, Integer> hourly = new HashMap<>();
    private Map<String, Integer> daily = new HashMap<>();
    private Map<String, Integer> weekly = new HashMap<>();


    public void add(TimeBasedType type, int value){
        LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        if (type == TimeBasedType.DAILY || type == TimeBasedType.WEEKLY) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }

        if (type == TimeBasedType.HOURLY) { hourly.put(date.format(formatter), value); }
        if (type == TimeBasedType.DAILY) { daily.put(date.format(formatter), value); }
        if (type == TimeBasedType.WEEKLY) { weekly.put(date.format(formatter), value); }
    }
}
