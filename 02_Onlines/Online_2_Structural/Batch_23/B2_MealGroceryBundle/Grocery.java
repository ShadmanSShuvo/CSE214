public class Grocery implements OrderItem {
    private String name;
    private double price;

    public Grocery(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void print(String indent) {
        System.out.printf("%sGrocery: %s (£%.2f)%n", indent, name, price);
    }
}
