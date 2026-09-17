public class ProcessingRefundState implements ReturnState {
    private int attemptCount = 0;

    @Override
    public String getStateName() {
        return "Processing Refund";
    }

    @Override
    public void updateReason(ReturnRequest request, String reason) {
        System.out.println("[Invalid Operation] Return reason cannot be modified while refund is being processed.");
    }

    @Override
    public void approve(ReturnRequest request) {
        System.out.println("[Invalid Operation] Request is already approved and refund is currently in progress.");
    }

    @Override
    public void reject(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot reject return once approved and inspection has passed.");
    }

    @Override
    public void cancel(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot cancel return request while refund payment is in progress.");
    }

    @Override
    public void itemDelivered(ReturnRequest request) {
        System.out.println("[Warning] Item has already been delivered and processed.");
    }

    @Override
    public void inspect(ReturnRequest request, boolean eligible) {
        System.out.println("[Invalid Operation] Inspection has already been finalized.");
    }

    @Override
    public void refundSuccessful(ReturnRequest request) {
        System.out.println("[State Transition] Refund payout of $" + String.format("%.2f", request.getAmount())
                + " succeeded! Request marked as REFUNDED.");
        request.setState(new RefundedState());
    }

    @Override
    public void refundFailed(ReturnRequest request) {
        attemptCount++;
        System.out.println("[Refund Alert] Refund payout attempt #" + attemptCount
                + " failed (e.g. gateway error). The request remains in Processing Refund and will be retried.");
    }
}
