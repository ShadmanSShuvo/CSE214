import java.util.ArrayList;
import java.util.List;

// Subscriber interface
interface Subscriber {
    void update(String event);
}

// Publisher - maintains subscriber list
class Store {

    private List<Subscriber> subscribers = new ArrayList<>();
    private String latestProduct;

    // Subscribe / Unsubscribe
    public void subscribe(Subscriber s) {
        subscribers.add(s);
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    // Notify all subscribers
    private void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update(latestProduct);
        }
    }

    // Business logic - triggers notification
    public void newProductArrived(String product) {
        this.latestProduct = product;
        notifySubscribers();
    }
}

// Concrete Subscriber 1
class EmailSubscriber implements Subscriber {

    private String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String product) {
        System.out.println("Email to " + email +
                ": New product: " + product);
    }
}

// Concrete Subscriber 2
class SMSSubscriber implements Subscriber {

    private String phone;

    public SMSSubscriber(String phone) {
        this.phone = phone;
    }

    @Override
    public void update(String product) {
        System.out.println("SMS to " + phone +
                ": New product: " + product);
    }
}

// Client
public class ObserverDemo {

    public static void main(String[] args) {

        Store store = new Store();

        store.subscribe(new EmailSubscriber("alice@email.com"));
        store.subscribe(new SMSSubscriber("+8801700000000"));

        store.newProductArrived("iPhone 17");
    }
}
