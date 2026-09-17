public class RejectedState implements ReturnState {
    private final String rejectionReason;

    public RejectedState(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    @Override
    public String getStateName() {
        return "Rejected (" + rejectionReason + ")";
    }

    @Override
    public void updateReason(ReturnRequest request, String reason) {
        System.out.println("[Terminal State] Cannot modify return reason. Request has been REJECTED.");
    }

    @Override
    public void approve(ReturnRequest request) {
        System.out.println("[Terminal State] Cannot approve a REJECTED return request.");
    }

    @Override
    public void reject(ReturnRequest request) {
        System.out.println("[Terminal State] Request is already REJECTED.");
    }

    @Override
    public void cancel(ReturnRequest request) {
        System.out.println("[Terminal State] Cannot cancel a REJECTED return request.");
    }

    @Override
    public void itemDelivered(ReturnRequest request) {
        System.out.println("[Terminal State] Request is already closed as REJECTED.");
    }

    @Override
    public void inspect(ReturnRequest request, boolean eligible) {
        System.out.println("[Terminal State] Request is already closed as REJECTED.");
    }

    @Override
    public void refundSuccessful(ReturnRequest request) {
        System.out.println("[Terminal State] No refund possible for a REJECTED request.");
    }

    @Override
    public void refundFailed(ReturnRequest request) {
        System.out.println("[Terminal State] Request is already closed as REJECTED.");
    }
}
