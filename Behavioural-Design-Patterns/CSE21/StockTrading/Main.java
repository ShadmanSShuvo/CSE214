public class Main {
    public static void main(String[] args) {
        // Create stocks
        Stock googleStock = new Stock("Google", 1500);
        Stock appleStock = new Stock("Apple", 1200);

        // Create users
        User user1 = new User("Alice");
        User user2 = new User("Bob");

        // Code for following stocks
        // Alice follows both Google and Apple
        user1.followStock(googleStock);
        user1.followStock(appleStock);

        // Bob follows Google
        user2.followStock(googleStock);

        // Simulate price changes
        System.out.println("Updating Google stock price...");
        googleStock.setPrice(1550);

        System.out.println("\nUpdating Apple stock price...");
        appleStock.setPrice(1250);

        // Code for unfollowing stocks
        // Alice unfollows Google
        user1.unfollowStock(googleStock);

        // Simulate price changes again
        System.out.println("\nUpdating Google stock price again...");
        googleStock.setPrice(1600);
    }
}
