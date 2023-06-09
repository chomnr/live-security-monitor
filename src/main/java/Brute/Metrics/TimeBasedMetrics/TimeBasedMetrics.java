package Brute.Metrics.TimeBasedMetrics;

public class TimeBasedMetrics {
    private NumberOfAttemptsOverTime numberOfAttemptsOverTime;
    private AttackFrequencyByDayOfWeek attackFrequencyByDayOfWeek;
    private AttackFrequencyByTimeOfDay attackFrequencyByTimeOfDay;

    public enum TimeBasedType {
        HOURLY, DAILY, WEEKLY
    }

    public enum TimeBasedTypeExtra {
        ATTACKFREQUENCYBYDAYOFWEEK, ATTACKFREQUENCYBYTIMEOFDAY
    }

    public TimeBasedMetrics() {
        numberOfAttemptsOverTime = new NumberOfAttemptsOverTime();
        attackFrequencyByDayOfWeek = new AttackFrequencyByDayOfWeek();
        attackFrequencyByTimeOfDay = new AttackFrequencyByTimeOfDay();
    }

    public NumberOfAttemptsOverTime getNumberOfAttemptsOverTime() {
        return numberOfAttemptsOverTime;
    }
}
