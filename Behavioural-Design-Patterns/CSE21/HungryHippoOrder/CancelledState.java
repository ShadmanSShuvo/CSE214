public class CancelledState implements OrderState {
    private final String reason;

    public CancelledState(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String getStateName() {
        return "Cancelled (" + reason + ")";
    }

    @Override
    public void confirm(Order order) {
        System.out.println("[Invalid Action] Cannot confirm a cancelled order (#" + order.getOrderId() + ").");
    }

    @Override
    public void ship(Order order) {
        System.out.println("[Invalid Action] Cannot ship a cancelled order (#" + order.getOrderId() + ").");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[Invalid Action] Cannot deliver a cancelled order (#" + order.getOrderId() + ").");
    }

    @Override
    public void cancel(Order order, String reason) {
        System.out.println("[Warning] Order #" + order.getOrderId() + " is already cancelled.");
    }
}
