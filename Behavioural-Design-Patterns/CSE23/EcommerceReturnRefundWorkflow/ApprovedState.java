public class ApprovedState implements ReturnState {
    @Override
    public String getStateName() {
        return "Approved";
    }

    @Override
    public void updateReason(ReturnRequest request, String reason) {
        System.out.println("[Invalid Operation] Return reason cannot be modified once the request has been approved.");
    }

    @Override
    public void approve(ReturnRequest request) {
        System.out.println("[Warning] Return request is already approved. Awaiting item shipment/delivery.");
    }

    @Override
    public void reject(ReturnRequest request) {
        System.out.println(
                "[Invalid Operation] Cannot reject an already approved request. Wait for item delivery and physical inspection.");
    }

    @Override
    public void cancel(ReturnRequest request) {
        System.out.println(
                "[State Transition] Return request #" + request.getId() + " cancelled prior to item delivery.");
        request.setState(new CancelledState("Cancelled by customer prior to delivery"));
    }

    @Override
    public void itemDelivered(ReturnRequest request) {
        System.out.println("[State Transition] Returned item for request #" + request.getId()
                + " has arrived at the warehouse (Delivered).");
        request.setState(new DeliveredState());
    }

    @Override
    public void inspect(ReturnRequest request, boolean eligible) {
        System.out.println(
                "[Invalid Operation] Cannot inspect item in Approved state. Item must be delivered to warehouse first.");
    }

    @Override
    public void refundSuccessful(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot process refund before item is delivered and inspected.");
    }

    @Override
    public void refundFailed(ReturnRequest request) {
        System.out.println("[Invalid Operation] Cannot fail refund before item is delivered and inspected.");
    }
}
