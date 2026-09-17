public class DeliveredState implements ReturnState {
    @Override
    public String getStateName() {
        return "Delivered";
    }

    @Override
    public void updateReason(ReturnRequest request, String reason) {
        System.out.println("[Invalid Operation] Return reason cannot be modified after item has been delivered.");
    }

    @Override
    public void approve(ReturnRequest request) {
        System.out.println("[Invalid Operation] Request was already approved and delivered.");
    }

    @Override
    public void reject(ReturnRequest request) {
        System.out.println("[Invalid Operation] Use inspect(false) to reject following physical examination.");
    }

    @Override
    public void cancel(ReturnRequest request) {
        System.out.println(
                "[Invalid Operation] Return request cannot be cancelled once the item has been delivered to the facility.");
    }

    @Override
    public void itemDelivered(ReturnRequest request) {
        System.out.println("[Warning] Item is already marked as delivered.");
    }

    @Override
    public void inspect(ReturnRequest request, boolean eligible) {
        if (eligible) {
            System.out.println(
                    "[State Transition] Item inspection PASSED! Return policy satisfied. Transitioning to Processing Refund.");
            request.setState(new ProcessingRefundState());
        } else {
            System.out.println(
                    "[State Transition] Item inspection FAILED! Product damaged/ineligible. Return request Rejected.");
            request.setState(new RejectedState("Failed warehouse inspection (policy violation)"));
        }
    }

    @Override
    public void refundSuccessful(ReturnRequest request) {
        System.out.println("[Invalid Operation] Inspection must be completed before processing refund.");
    }

    @Override
    public void refundFailed(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot fail refund before inspection and refund initiation.");
    }
}
