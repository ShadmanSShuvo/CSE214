class OrderItem implements FoodItem {
    private String name;
    private double price;

    // public void add() {
    //     // Implementation for adding an item (if needed)
    // }

    public OrderItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void print(String prefix) {
        System.out.printf("%s%s (£%.2f)%n", prefix, name, price);
    }
}
