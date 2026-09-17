public class ConfirmedState implements OrderState {
    @Override
    public String getStateName() {
        return "Confirmed";
    }

    @Override
    public void confirm(Order order) {
        System.out.println("[Warning] Order #" + order.getOrderId() + " is already confirmed.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[State Transition] Order #" + order.getOrderId() + " has been picked up by courier and is now Shipped.");
        order.setState(new ShippedState());
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[Invalid Action] Cannot deliver order #" + order.getOrderId() + " before it is shipped.");
    }

    @Override
    public void cancel(Order order, String reason) {
        System.out.println("[State Transition] Order #" + order.getOrderId() + " cancelled after confirmation but before shipment. Reason: " + reason);
        order.setState(new CancelledState(reason));
    }
}
