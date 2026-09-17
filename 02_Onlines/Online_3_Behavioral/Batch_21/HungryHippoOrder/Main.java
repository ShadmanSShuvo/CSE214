import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  HUNGRYHIPPO ORDER LIFECYCLE (STATE PATTERN)     ");
        System.out.println("==================================================");

        // Case 1: Complete happy journey (Placed -> Confirmed -> Shipped -> Delivered)
        System.out.println("\n--- Case 1: Complete Successful Order Journey ---");
        Order order1 = new Order("HH-101", "Alice", Arrays.asList("Cheeseburger", "Fries", "Coke"));
        order1.printCurrentStatus();

        order1.confirm();
        order1.printCurrentStatus();

        order1.ship();
        order1.printCurrentStatus();

        order1.deliver();
        order1.printCurrentStatus();

        // Case 2: Attempting invalid transitions on delivered order
        System.out.println("\n--- Case 2: Invalid Operations on Delivered Order ---");
        order1.cancel("Changed mind");
        order1.ship();

        // Case 3: Cancellation while in Placed state
        System.out.println("\n--- Case 3: Cancellation from Placed State ---");
        Order order2 = new Order("HH-102", "Bob", Arrays.asList("Pepperoni Pizza", "Garlic Bread"));
        order2.cancel("Ordered by mistake");
        order2.printCurrentStatus();
        order2.confirm(); // Cannot confirm after cancelled

        // Case 4: Cancellation while in Confirmed state
        System.out.println("\n--- Case 4: Cancellation from Confirmed State ---");
        Order order3 = new Order("HH-103", "Charlie", Arrays.asList("Sushi Platter"));
        order3.confirm();
        order3.cancel("Delivery time too long");
        order3.printCurrentStatus();

        // Case 5: Invalid transition - skipping steps (Placed directly to Delivered)
        System.out.println("\n--- Case 5: Preventing Step-Skipping & Shipped-Cancellation ---");
        Order order4 = new Order("HH-104", "Diana", Arrays.asList("Pasta Carbonara"));
        order4.deliver(); // Should fail: cannot skip Placed -> Delivered
        order4.confirm();
        order4.ship();
        order4.confirm(); // Should fail: cannot revert Shipped -> Confirmed
        order4.cancel("I don't want it anymore"); // Should fail: cannot cancel Shipped order
        order4.deliver(); // Succeeds
        order4.printCurrentStatus();

        System.out.println("\n==================================================");
        System.out.println("  Demonstration completed successfully.           ");
        System.out.println("==================================================");
    }
}
