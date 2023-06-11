package Brute.Metrics.TimeBasedMetrics;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;

public class AttackFrequencyByDayOfWeek {

    NavigableMap<String, Integer> days = new TreeMap<>();

    public AttackFrequencyByDayOfWeek() {}

    public void insert(int value) {
        ZonedDateTime now = LocalDate.now().atStartOfDay().atZone(TimeBasedMetrics.ZONE_OFFSET);
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
