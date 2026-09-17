import java.util.Collections;
import java.util.List;

public class Order {
    private final String orderId;
    private final String customerName;
    private final List<String> items;
    private OrderState state;

    public Order(String orderId, String customerName, List<String> items) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
        this.state = new PlacedState();
        System.out.println("[Order Placed] Order #" + orderId + " placed by " + customerName + " with items: " + items);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void confirm() {
        state.confirm(this);
    }

    public void ship() {
        state.ship(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel(String reason) {
        state.cancel(this, reason);
    }

    public void printCurrentStatus() {
        System.out.println("[Order Status] Order #" + orderId + " is currently in state: " + state.getStateName());
    }
}
