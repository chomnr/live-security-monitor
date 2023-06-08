package Brute.Metrics.TimeBasedMetrics;

import Brute.BruteException;
import Brute.Exceptions.MetricTypeNotCompatible;
import Brute.Metrics.BruteMetricData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private NavigableMap<String, Integer> hourly = new TreeMap<>();
    private NavigableMap<String, Integer> daily = new TreeMap<>();
    private NavigableMap<String, Integer> weekly = new TreeMap<>();


    public void insert(TimeBasedType type, int value) {
        updateValue(type, value);
    }

    private void updateValue(TimeBasedType type, int value) {
        NavigableMap<String, Integer> currentMap = getMapByType(type);

        long elaspedTime = 0;
        if (currentMap.size() != 0) {
            elaspedTime = System.currentTimeMillis() - getExactTime(type, currentMap.lastKey());
        }

        if ( type == TimeBasedType.HOURLY) {
            if (currentMap.size() != 0 ) {
                if (elaspedTime > (60 * 60 * 1000)) {
                    hourly.put(getFormattedTime(type), value);
                } else {
                    addAttempts(type, currentMap.lastKey(), value);
                }
            } else {
                hourly.put(getFormattedTime(type), value);
            }
        }

        if ( type == TimeBasedType.DAILY) {
            if (currentMap.size() != 0 ) {
                if (elaspedTime > (24 * 60 * 60 * 1000)) {
                    daily.put(getFormattedTime(type), value);
                } else {
                    addAttempts(type, currentMap.lastKey(), value);
                }
            } else {
                daily.put(getFormattedTime(type), value);
            }
        }

        if ( type == TimeBasedType.WEEKLY) {
            if (currentMap.size() != 0 ) {
                if (elaspedTime > (24 * 7 * 60 * 60 * 1000)) {
                    weekly.put(getFormattedTime(type), value);
                } else {
                    addAttempts(type, currentMap.lastKey(), value);
                }
            } else {
                weekly.put(getFormattedTime(type), value);
            }
        }
    }

    private void addAttempts(TimeBasedType type, String key, int value) {
        Integer attempts = null;
        try {
            if (type == TimeBasedType.HOURLY) {
                attempts = getAttempts(type, key);
                hourly.put(key, attempts+value);
            }
            if (type == TimeBasedType.DAILY) {
                attempts = getAttempts(type, key);
                daily.put(key, attempts+value);
            }
            if (type == TimeBasedType.WEEKLY) {
                attempts = getAttempts(type, key);
                weekly.put(key, attempts+value);
            }
        } catch (BruteException e) {
            e.printStackTrace();
        }
    }

    private Integer getAttempts(TimeBasedType type, String key) throws BruteException {
        if (type == TimeBasedType.HOURLY) { return hourly.get(key); }
        if (type == TimeBasedType.DAILY) { return daily.get(key); }
        if (type == TimeBasedType.WEEKLY) { return weekly.get(key); }
        throw new MetricTypeNotCompatible();
    }

    private long getExactTime(TimeBasedType type, String formattedTime){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");

        if (type == TimeBasedType.DAILY || type == TimeBasedType.WEEKLY) {
            sdf = new SimpleDateFormat("MM-dd-yyyy");
        }
        try {
            Date date = sdf.parse(formattedTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private NavigableMap<String, Integer> getMapByType(TimeBasedType type) {
        if (type == TimeBasedType.HOURLY) {
            return hourly;
        }
        if (type == TimeBasedType.DAILY) {
            return daily;
        }
        return weekly;
    }

    private Map.Entry<String, Integer> getLastEntry(TimeBasedType type) {
        if (type == TimeBasedType.HOURLY) {
            return hourly.lastEntry();
        }
        if (type == TimeBasedType.DAILY) {
            return daily.lastEntry();
        }
        return weekly.lastEntry();
    }

    public String getFormattedTime(TimeBasedType type) {
        LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        if (type == TimeBasedType.DAILY) {
            formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        }
        if (type == TimeBasedType.WEEKLY) {
            formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        }
        return date.format(formatter);
    }
}

/*
        LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        NavigableMap<String, Integer> currentMap = hourly;

        if (type == TimeBasedType.DAILY) {
            formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            currentMap = daily;
        }

        if (type == TimeBasedType.WEEKLY) {
            formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            currentMap = weekly;
        }
         */

 /*


        try {
            Integer attempts = getAttempts(type, key);
            long elaspedTime = System.currentTimeMillis() - getTime(key);

            if ( type == TimeBasedType.HOURLY) {
                if (elaspedTime > (60 * 60 * 1000)) {

                }
            }
        } catch (BruteException e) {
            e.printStackTrace();
        }*/

   /*
    private Integer getAttempts(String key) {
        //LocalDateTime dateTime = LocalDateTime.parse(key, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        DateTimeFormatter hf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy HH");
        DateTimeFormatter wf = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    }*/
