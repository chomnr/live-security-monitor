package Brute.Metrics.TimeBasedMetrics;

import Brute.Metrics.AutoPopulate;
import Brute.Metrics.BruteMetricData;
import Brute.Metrics.BruteMetrics;

import java.time.ZoneOffset;

public class TimeBasedMetrics implements AutoPopulate {

    public final static ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
    private final static int DEFAULT_INCREMENT_VALUE = 1;

    private NumberOfAttemptsOverTime numberOfAttemptsOverTime;
    private AttackFrequencyByDayOfWeek attackFrequencyByDayOfWeek;

    public enum TimeBasedType {
        HOURLY, DAILY, WEEKLY
    }

    public enum TimeBasedTypeExtra {
        ATTACKFREQUENCYBYDAYOFWEEK, ATTACKFREQUENCYBYTIMEOFDAY
    }

    public TimeBasedMetrics() {
        numberOfAttemptsOverTime = new NumberOfAttemptsOverTime();
        attackFrequencyByDayOfWeek = new AttackFrequencyByDayOfWeek();
    }

    public NumberOfAttemptsOverTime getNumberOfAttemptsOverTime() {
        return numberOfAttemptsOverTime;
    }
    public AttackFrequencyByDayOfWeek getAttackFrequencyByDayOfWeek() { return attackFrequencyByDayOfWeek; }

    @Override
    public void populate() {
        getNumberOfAttemptsOverTime()
                .insert(DEFAULT_INCREMENT_VALUE);
        getAttackFrequencyByDayOfWeek()
                .insert(DEFAULT_INCREMENT_VALUE);
    }

    @Override
    public void populate(int value) {
        getNumberOfAttemptsOverTime()
                .insert(value);
        getAttackFrequencyByDayOfWeek()
                .insert(value);
    }
}
