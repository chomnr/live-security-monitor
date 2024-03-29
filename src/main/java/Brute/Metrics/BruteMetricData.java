package Brute.Metrics;

import Brute.Metrics.CredentialBasedMetrics.CredentialBasedMetrics;
import Brute.Metrics.GeographicMetrics.GeographicMetrics;
import Brute.Metrics.ProtocolBasedMetrics.ProtocolBasedMetrics;
import Brute.Metrics.TimeBasedMetrics.TimeBasedMetrics;

public class BruteMetricData {

    private TimeBasedMetrics timeBasedMetrics;
    private GeographicMetrics geographicMetrics;
    private ProtocolBasedMetrics protocolBasedMetrics;
    private CredentialBasedMetrics credentialBasedMetrics;

    public BruteMetricData() {
        timeBasedMetrics = new TimeBasedMetrics();
        geographicMetrics = new GeographicMetrics();
        protocolBasedMetrics = new ProtocolBasedMetrics();
        credentialBasedMetrics = new CredentialBasedMetrics();
    }

    public TimeBasedMetrics getTimeBasedMetrics() {
        return timeBasedMetrics;
    }
    public GeographicMetrics getGeographicMetrics() { return geographicMetrics; }
    public ProtocolBasedMetrics getProtocolBasedMetrics() { return protocolBasedMetrics; }
    public CredentialBasedMetrics getCredentialBasedMetrics() { return credentialBasedMetrics; }


}
