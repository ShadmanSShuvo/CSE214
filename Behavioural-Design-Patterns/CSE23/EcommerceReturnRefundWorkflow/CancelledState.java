public class CancelledState implements ReturnState {
    private final String cancellationReason;

    public CancelledState(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    @Override
    public String getStateName() {
        return "Cancelled (" + cancellationReason + ")";
    }

    @Override
    public void updateReason(ReturnRequest request, String reason) {
        System.out.println("[Terminal State] Cannot modify return reason. Request has been CANCELLED.");
    }

    @Override
    public void approve(ReturnRequest request) {
        System.out.println("[Terminal State] Cannot approve a CANCELLED return request.");
    }

    @Override
    public void reject(ReturnRequest request) {
        System.out.println("[Terminal State] Request is already CANCELLED.");
    }

    @Override
    public void cancel(ReturnRequest request) {
        System.out.println("[Terminal State] Request is already CANCELLED.");
    }

    @Override
    public void itemDelivered(ReturnRequest request) {
        System.out.println("[Terminal State] Request is already closed as CANCELLED.");
    }

    @Override
    public void inspect(ReturnRequest request, boolean eligible) {
        System.out.println("[Terminal State] Request is already closed as CANCELLED.");
    }

    @Override
    public void refundSuccessful(ReturnRequest request) {
        System.out.println("[Terminal State] No refund possible for a CANCELLED request.");
    }

    @Override
    public void refundFailed(ReturnRequest request) {
        System.out.println("[Terminal State] Request is already closed as CANCELLED.");
    }
}
