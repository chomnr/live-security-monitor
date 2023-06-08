package Brute.Metrics.TimeBasedMetrics;

public class TimeBasedMetrics {
    private NumberOfAttemptsOverTime numberOfAttemptsOverTime;

    public enum TimeBasedType {
        HOURLY, DAILY, WEEKLY
    }

    public enum TimeBasedTypeExtra {
        ATTACKFREQUENCYBYDAYOFWEEK, ATTACKFREQUENCYBYTIMEOFDAY
    }

    public TimeBasedMetrics() {
        numberOfAttemptsOverTime = new NumberOfAttemptsOverTime();
    }

    public NumberOfAttemptsOverTime getNumberOfAttemptsOverTime() {
        return numberOfAttemptsOverTime;
    }
}
