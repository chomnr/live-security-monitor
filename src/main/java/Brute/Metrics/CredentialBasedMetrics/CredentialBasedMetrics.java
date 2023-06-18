package Brute.Metrics.CredentialBasedMetrics;

public class CredentialBasedMetrics {

    private CommonlyTargetedByUsername commonlyTargetedByUsername;
    private CommonlyTargetedByPassword commonlyTargetedByPassword;
    private CommonlyTargetedByCombination commonlyTargetedByCombination;

    public CredentialBasedMetrics () {
        commonlyTargetedByUsername = new CommonlyTargetedByUsername();
        commonlyTargetedByPassword = new CommonlyTargetedByPassword();
        commonlyTargetedByCombination = new CommonlyTargetedByCombination();
    }

    public CommonlyTargetedByUsername getCommonlyTargetedByUsername() {
        return commonlyTargetedByUsername;
    }

    public CommonlyTargetedByPassword getCommonlyTargetedByPassword() {
        return commonlyTargetedByPassword;
    }

    public CommonlyTargetedByCombination getCommonlyTargetedByCombination() {
        return commonlyTargetedByCombination;
    }
}
