public class RefundedState implements ReturnState {
    @Override
    public String getStateName() {
        return "Refunded";
    }

    @Override
    public void updateReason(ReturnRequest request, String reason) {
        System.out.println("[Terminal State] Cannot modify return reason. Request is already REFUNDED.");
    }

    @Override
    public void approve(ReturnRequest request) {
        System.out.println("[Terminal State] Request is finalized as REFUNDED.");
    }

    @Override
    public void reject(ReturnRequest request) {
        System.out.println("[Terminal State] Request is finalized as REFUNDED.");
    }

    @Override
    public void cancel(ReturnRequest request) {
        System.out.println("[Terminal State] Cannot cancel an already REFUNDED return.");
    }

    @Override
    public void itemDelivered(ReturnRequest request) {
        System.out.println("[Terminal State] Request is finalized as REFUNDED.");
    }

    @Override
    public void inspect(ReturnRequest request, boolean eligible) {
        System.out.println("[Terminal State] Request is finalized as REFUNDED.");
    }

    @Override
    public void refundSuccessful(ReturnRequest request) {
        System.out.println("[Terminal State] Refund has already been processed.");
    }

    @Override
    public void refundFailed(ReturnRequest request) {
        System.out.println("[Terminal State] Refund was already finalized successfully.");
    }
}
