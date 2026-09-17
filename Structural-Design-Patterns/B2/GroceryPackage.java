import java.util.ArrayList;
import java.util.List;

public class GroceryPackage implements OrderItem {

    private String name;
    private List<OrderItem> items = new ArrayList<>();

    public GroceryPackage(String name) {
        this.name = name;
    }

    public void add(OrderItem item) {
        items.add(item);
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Package: " + name);
        for (OrderItem item : items) {
            item.print(indent + " ");
        }
    }
}
