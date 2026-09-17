public class ShippedState implements OrderState {
    @Override
    public String getStateName() {
        return "Shipped";
    }

    @Override
    public void confirm(Order order) {
        System.out.println("[Invalid Action] Cannot revert order #" + order.getOrderId() + " back to Confirmed once it has been shipped.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[Warning] Order #" + order.getOrderId() + " is already in transit (Shipped).");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[State Transition] Order #" + order.getOrderId() + " has been successfully Delivered to customer.");
        order.setState(new DeliveredState());
    }

    @Override
    public void cancel(Order order, String reason) {
        System.out.println("[Invalid Action] Cannot cancel order #" + order.getOrderId() + " after it has been shipped! Courier is already en route.");
    }
}
