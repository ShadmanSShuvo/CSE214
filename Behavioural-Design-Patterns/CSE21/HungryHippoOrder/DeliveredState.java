public class DeliveredState implements OrderState {
    @Override
    public String getStateName() {
        return "Delivered";
    }

    @Override
    public void confirm(Order order) {
        System.out.println("[Invalid Action] Order #" + order.getOrderId() + " is already delivered.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[Invalid Action] Order #" + order.getOrderId() + " has already been delivered.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[Warning] Order #" + order.getOrderId() + " was already marked as Delivered.");
    }

    @Override
    public void cancel(Order order, String reason) {
        System.out.println("[Invalid Action] Cannot cancel order #" + order.getOrderId() + " because it is already delivered.");
    }
}
