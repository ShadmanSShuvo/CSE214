/**
 * Leaf component in the Composite Pattern.
 * Represents an individual gift item sold by the e-commerce company
 * (e.g., chocolates, mugs, perfumes, books, flowers).
 */
public class GiftItem implements GiftComponent {
    private final String name;
    private final double price;

    public GiftItem(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Gift item name cannot be empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.name = name.trim();
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void print(String indent) {
        System.out.printf("%s• Item: %-25s | Price: $%.2f%n", indent, name, price);
    }

    @Override
    public int getIndividualItemCount() {
        return 1;
    }

    @Override
    public String toString() {
        return String.format("%s ($%.2f)", name, price);
    }
}
