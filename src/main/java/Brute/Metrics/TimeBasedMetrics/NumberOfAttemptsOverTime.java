package Brute.Metrics.TimeBasedMetrics;

import Brute.BruteException;
import Brute.Exceptions.MetricTypeNotCompatible;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;



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

    private static ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

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

    private long getExactTime(TimeBasedType type, String formattedTime) {
        DateTimeFormatter formatter = getTimePattern(type);
        LocalDate storedTime = LocalDate.parse(formattedTime, formatter);
        Instant instant;

        if (type != TimeBasedType.HOURLY) {
            instant = storedTime
                    .atStartOfDay()
                    .toInstant(ZONE_OFFSET);
        } else {
            instant = LocalDateTime
                    .parse(formattedTime, formatter)
                    .toInstant(ZONE_OFFSET);
        }

        return instant.toEpochMilli();
    }


    private long getDurationMillis(TimeBasedType type) {
        switch (type) {
            case HOURLY:
                return 60L * 60 * 1000;
            case DAILY:
                return 24L * 60 * 60 * 1000;
            case WEEKLY:
                return 24L * 7 * 60 * 60 * 1000;
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
        DateTimeFormatter formatter;
        if (type == TimeBasedType.DAILY || type == TimeBasedType.WEEKLY) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        LocalDateTime currentTime = LocalDateTime.now(ZONE_OFFSET);

        return formatter.format(currentTime);
    }

    private DateTimeFormatter getTimePattern(TimeBasedType type) {
        DateTimeFormatter formatter;
        if (type == TimeBasedType.DAILY || type == TimeBasedType.WEEKLY) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        return formatter;
    }
}
