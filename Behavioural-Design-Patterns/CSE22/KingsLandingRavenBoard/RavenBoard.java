import java.util.ArrayList;
import java.util.List;

public class RavenBoard {
    private final List<KingdomObserver> observers;

    public RavenBoard() {
        this.observers = new ArrayList<>();
    }

    public void subscribe(KingdomObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println(
                    "[Board Log] " + observer.getName() + " entered the war room and subscribed to the Raven Board.");
        }
    }

    public void unsubscribe(KingdomObserver observer) {
        if (observers.remove(observer)) {
            System.out.println(
                    "[Board Log] " + observer.getName() + " left the war room and unsubscribed from the Raven Board.");
        }
    }

    public void deliverScroll(String scroll) {
        System.out.println("\n--------------------------------------------------");
        System.out.println(">>> [RAVEN ARRIVES] New Scroll Delivered: \"" + scroll + "\" <<<");
        System.out.println("--------------------------------------------------");
        if (observers.isEmpty()) {
            System.out.println("[Board Log] No groups are currently in the war room to read the scroll.");
            return;
        }

        for (KingdomObserver observer : observers) {
            observer.onScrollDelivered(scroll);
        }
    }
}
