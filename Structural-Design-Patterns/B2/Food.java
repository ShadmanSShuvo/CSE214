public class Food implements OrderItem {
    private String name;
    private double price;

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void print(String indent) {
        System.out.printf("%sFood: %s (£%.2f)%n", indent, name, price);
    }
}
