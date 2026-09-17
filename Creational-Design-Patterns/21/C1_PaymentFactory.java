// C1: E-commerce Payment System - Factory Method Pattern
// Task: User selects a payment method; system instantiates and uses the
// appropriate PaymentMethod without modifying existing code to add new ones.

// ---------- Product Interface ----------
interface PaymentMethod {
    void processPayment(double amount);
}

// ---------- Concrete Products ----------
class CreditCardPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Credit Card payment of $" + amount);
        System.out.println("Payment successful via Credit Card!");
    }
}

class PayPalPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        System.out.println("Payment successful via PayPal!");
    }
}

class BitcoinPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Bitcoin payment of $" + amount);
        System.out.println("Payment successful via Bitcoin!");
    }
}

// ---------- Creator (Factory) ----------
class PaymentMethodFactory {
    public static PaymentMethod createPaymentMethod(String type) {
        switch (type) {
            case "CreditCard":
                return new CreditCardPayment();
            case "PayPal":
                return new PayPalPayment();
            case "Bitcoin":
                return new BitcoinPayment();
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + type);
        }
    }
}

// ---------- Client ----------
public class C1_PaymentFactory {
    public static void main(String[] args) {
        PaymentMethod payment1 = PaymentMethodFactory.createPaymentMethod("CreditCard");
        payment1.processPayment(150.0);

        PaymentMethod payment2 = PaymentMethodFactory.createPaymentMethod("PayPal");
        payment2.processPayment(75.5);

        PaymentMethod payment3 = PaymentMethodFactory.createPaymentMethod("Bitcoin");
        payment3.processPayment(0.005);
    }
}
