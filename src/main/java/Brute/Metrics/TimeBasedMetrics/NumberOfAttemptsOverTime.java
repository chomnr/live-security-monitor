package Brute.Metrics.TimeBasedMetrics;

import Brute.BruteException;
import Brute.Exceptions.MetricTypeNotCompatible;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
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

    public NumberOfAttemptsOverTime() {}

    public void insert(int value) {
        updateValue(TimeBasedType.HOURLY, value);
        updateValue(TimeBasedType.DAILY, value);
        updateValue(TimeBasedType.WEEKLY, value);
    }

    public void insert(TimeBasedType type,  int value) {
        updateValue(type, value);
    }

    private void updateValue(TimeBasedType type, int value) {
        NavigableMap<String, Integer> currentMap = getMapByType(type);

        long elaspedTime = 0;

        if (currentMap.size() != 0) {
            elaspedTime = System.currentTimeMillis() - getExactTime(type, currentMap.lastKey());
        }
        // todo: fix hourly something weird is happening ye?
        if (currentMap.isEmpty()) {
            currentMap.put(getFormattedTime(type), value);
            return;
        }

        if (elaspedTime >= getDurationMillis(type)) {
            currentMap.put(getFormattedTime(type), value);
        } else {
            addAttempts(type, currentMap.lastKey(), value);
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

    private long getDurationMillis(TimeBasedType type) {
        switch (type) {
            case HOURLY:
                return 60 * 60 * 1000;
            case DAILY:
                return 24 * 60 * 60 * 1000;
            case WEEKLY:
                return 24 * 7 * 60 * 60 * 1000;
            default:
                return 0;
        }
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

    private String getFormattedTime(TimeBasedType type) {
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
