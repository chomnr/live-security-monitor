package Brute.Metrics.TimeBasedMetrics;

import Brute.Metrics.AutoPopulate;

import java.time.ZoneOffset;

public class TimeBasedMetrics implements AutoPopulate {

    public final static ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
    private final static int DEFAULT_INCREMENT_VALUE = 1;

    private NumberOfAttemptsOverTime numberOfAttemptsOverTime;
    private AttackTotalByDayOfWeek attackTotalByDayOfWeek;

    public enum TimeBasedType {
        HOURLY, DAILY, WEEKLY
    }

    public enum TimeBasedTypeExtra {
        ATTACKFREQUENCYBYDAYOFWEEK, TOTALATTACKSBYDAYOFWEEK
    }

    public TimeBasedMetrics() {
        numberOfAttemptsOverTime = new NumberOfAttemptsOverTime();
        attackTotalByDayOfWeek = new AttackTotalByDayOfWeek();
    }

    public NumberOfAttemptsOverTime getNumberOfAttemptsOverTime() {
        return numberOfAttemptsOverTime;
    }
    public AttackTotalByDayOfWeek getAttackTotalByDayOfWeek() { return attackTotalByDayOfWeek; }

    @Override
    public void populate() {
        getNumberOfAttemptsOverTime()
                .insert(DEFAULT_INCREMENT_VALUE);
        getAttackTotalByDayOfWeek()
                .insert(DEFAULT_INCREMENT_VALUE);
    }

    @Override
    public void populate(int value) {
        getNumberOfAttemptsOverTime()
                .insert(value);
        getAttackTotalByDayOfWeek()
                .insert(value);
    }
}
