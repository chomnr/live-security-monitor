package Brute.Metrics.TimeBasedMetrics;

public class TimeBasedMetrics {

    private final static int DEFAULT_INCREMENT_VALUE = 1; // no reason why this should not be 1.

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

    public void populate() {
        getNumberOfAttemptsOverTime()
                .insert(DEFAULT_INCREMENT_VALUE);
        getAttackTotalByDayOfWeek()
                .insert(DEFAULT_INCREMENT_VALUE);
    }

    public void populate(int value) {
        getNumberOfAttemptsOverTime()
                .insert(value);
        getAttackTotalByDayOfWeek()
                .insert(value);
    }
}
