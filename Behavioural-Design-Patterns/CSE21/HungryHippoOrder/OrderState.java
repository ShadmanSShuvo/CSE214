public interface OrderState {
    String getStateName();
    void confirm(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order, String reason);
}
