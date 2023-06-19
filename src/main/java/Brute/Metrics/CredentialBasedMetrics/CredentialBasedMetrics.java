package Brute.Metrics.CredentialBasedMetrics;

public class CredentialBasedMetrics {

    private CommonlyTargetedByCredential commonlyTargetedByCredential;
    private CommonlyTargetedByCombination commonlyTargetedByCombination;

    public enum CredentialBasedType {
        USERNAME, PASSWORD, COMBINATION
    }

    public CredentialBasedMetrics () {
        commonlyTargetedByCredential = new CommonlyTargetedByCredential();
        commonlyTargetedByCombination = new CommonlyTargetedByCombination();
    }


    public CommonlyTargetedByCredential getCommonlyTargetedByCredential() {
        return commonlyTargetedByCredential;
    }

    public CommonlyTargetedByCombination getCommonlyTargetedByCombination() {
        return commonlyTargetedByCombination;
    }

    public void populate(String username, String password) {
        getCommonlyTargetedByCredential().insert(username, password, 1);
    }

    public void populate(String username, String password, int amount) {
        getCommonlyTargetedByCredential().insert(username, password, amount);
    }

    public void populate(CredentialBasedType type, String field, int amount) {
        getCommonlyTargetedByCredential().insert(type, field, amount);
    }
}
