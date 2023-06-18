package Brute.Metrics.TimeBasedMetrics;

import Brute.Constants;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.NavigableMap;
import java.util.TreeMap;

public class AttackTotalByDayOfWeek {
    NavigableMap<String, Integer> days = new TreeMap<>();

    public AttackTotalByDayOfWeek() {}

    public void insert(int value) {
        ZonedDateTime now = LocalDate.now().atStartOfDay().atZone(Constants.ZONE_OFFSET);
        String day = now.getDayOfWeek().toString();

        validateDay(day);
        updateAttempt(day, value);
    }

    private void updateAttempt(String day, int value) {
        days.put(day, days.get(day)+value);
    }

    private void validateDay(String day) {
        if (!days.containsKey(day)) {
            days.put(day, 0);
        }
    }
}
