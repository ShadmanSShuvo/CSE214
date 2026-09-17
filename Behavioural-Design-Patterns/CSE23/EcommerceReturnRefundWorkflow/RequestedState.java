public class RequestedState implements ReturnState {
    @Override
    public String getStateName() {
        return "Requested";
    }

    @Override
    public void updateReason(ReturnRequest request, String reason) {
        System.out.println("[Reason Updated] Updated return reason to: \"" + reason + "\"");
        request.setReason(reason);
    }

    @Override
    public void approve(ReturnRequest request) {
        System.out.println("[State Transition] Return request #" + request.getId() + " approved by customer support.");
        request.setState(new ApprovedState());
    }

    @Override
    public void reject(ReturnRequest request) {
        System.out
                .println("[State Transition] Return request #" + request.getId() + " rejected during initial review.");
        request.setState(new RejectedState("Initial request criteria not met"));
    }

    @Override
    public void cancel(ReturnRequest request) {
        System.out.println("[State Transition] Return request #" + request.getId() + " cancelled by customer.");
        request.setState(new CancelledState("Customer decided to keep product"));
    }

    @Override
    public void itemDelivered(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot mark item as delivered before the request has been approved.");
    }

    @Override
    public void inspect(ReturnRequest request, boolean eligible) {
        System.out.println(
                "[Invalid Operation] Cannot inspect item in Requested state. Item has not been approved or shipped yet.");
    }

    @Override
    public void refundSuccessful(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot process refund in Requested state.");
    }

    @Override
    public void refundFailed(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot fail refund in Requested state.");
    }
}
