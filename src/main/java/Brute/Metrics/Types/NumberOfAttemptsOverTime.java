package Brute.Metrics.Types;

import Brute.Metrics.BruteMetricType;

import java.util.HashMap;
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

public class NumberOfAttemptsOverTime extends BruteMetricType {
    private Hourly hourly;
    private Daily daily;
    private Weekly weekly;

    public NumberOfAttemptsOverTime() {
        super("NumberOfAttemptsOverTime");
    }

    public Hourly getHourly() {
        return hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public Weekly getWeekly() {
        return weekly;
    }

    private static class Hourly implements InnerMetricDataType {
        private Map<String, Long> hourly = new HashMap<>();

        public Map<String, Long> getData(){
            return hourly;
        }

        public void addData(String key, Long value){
            hourly.put(key, value);
        }
    }

    private static class Daily implements InnerMetricDataType {
        private Map<String, Long> daily = new HashMap<>();

        public Map<String, Long> getData(){
            return daily;
        }

        public void addData(String key, Long value){
            daily.put(key, value);
        }
    }

    private static class Weekly implements InnerMetricDataType {
        private Map<String, Long> weekly = new HashMap<>();

        public Map<String, Long> getData(){
            return weekly;
        }

        public void addData(String key, Long value){
            weekly.put(key, value);
        }
    }

    private interface InnerMetricDataType {
        public Map<String, Long> getData();
        public void addData(String key, Long value);
    }
}
