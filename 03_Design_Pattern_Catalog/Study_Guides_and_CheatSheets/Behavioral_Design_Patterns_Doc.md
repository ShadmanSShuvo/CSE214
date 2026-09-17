Strategy DP
```java
// Strategy interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete Strategy 1
class CreditCardPayment implements PaymentStrategy {

    private String cardNumber;

    public CreditCardPayment(String card) {
        this.cardNumber = card;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount +
                " via Credit Card " + cardNumber);
    }
}

// Concrete Strategy 2
class PayPalPayment implements PaymentStrategy {

    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount +
                " via PayPal (" + email + ")");
    }
}

// Context - holds a reference to the current strategy
class ShoppingCart {

    private PaymentStrategy strategy;

    // Allows strategy to be changed at runtime
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void checkout(double amount) {
        strategy.pay(amount); // delegates to strategy
    }
}

// Client
public class StrategyDemo {

    public static void main(String[] args) {

        ShoppingCart cart = new ShoppingCart();

        cart.setStrategy(new CreditCardPayment("1234-5678"));
        cart.checkout(100.0);

        // Swap strategy at runtime
        cart.setStrategy(new PayPalPayment("user@email.com"));
        cart.checkout(50.0);
    }
}
```
