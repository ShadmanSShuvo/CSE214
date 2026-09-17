public class PlacedState implements OrderState {
    @Override
    public String getStateName() {
        return "Placed";
    }

    @Override
    public void confirm(Order order) {
        System.out.println("[State Transition] Order #" + order.getOrderId() + " has been confirmed by restaurant.");
        order.setState(new ConfirmedState());
    }

    @Override
    public void ship(Order order) {
        System.out.println("[Invalid Action] Cannot ship order #" + order.getOrderId() + " directly from Placed state. It must be confirmed first.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[Invalid Action] Cannot deliver order #" + order.getOrderId() + " directly from Placed state. Order journey cannot skip steps.");
    }

    @Override
    public void cancel(Order order, String reason) {
        System.out.println("[State Transition] Order #" + order.getOrderId() + " cancelled before confirmation. Reason: " + reason);
        order.setState(new CancelledState(reason));
    }
}
