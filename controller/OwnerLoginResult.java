package controller;

public class OwnerLoginResult {
    private final String ownerId;
    private final String approvalStatus;

    public OwnerLoginResult(String ownerId, String approvalStatus) {
        this.ownerId = ownerId;
        this.approvalStatus = approvalStatus;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }
}
