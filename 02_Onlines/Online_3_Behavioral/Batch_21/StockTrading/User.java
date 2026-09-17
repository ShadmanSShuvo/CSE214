public class User implements Observer {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(String stockName, double newPrice) {
        System.out.println(name + " has been notified: The price of " + stockName + " is now " + newPrice);
    }

    public void followStock(Stock stock) {
        stock.registerObserver(this);
    }

    public void unfollowStock(Stock stock) {
        stock.removeObserver(this);
    }
}
