import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockDataFeed {
    private final List<DashboardWidget> widgets;
    private final Map<String, Double> prices;

    public StockDataFeed() {
        this.widgets = new ArrayList<>();
        this.prices = new HashMap<>();
    }

    public void attach(DashboardWidget widget) {
        if (!widgets.contains(widget)) {
            widgets.add(widget);
            System.out.println("[Feed Log] Added widget: " + widget.getWidgetName());
        }
    }

    public void detach(DashboardWidget widget) {
        if (widgets.remove(widget)) {
            System.out.println("[Feed Log] Removed widget: " + widget.getWidgetName());
        }
    }

    public void setPrice(String stockSymbol, double price) {
        prices.put(stockSymbol, price);
        System.out.println("\n--------------------------------------------------");
        System.out.printf("[MARKET TICK] %s is now trading at $%.2f\n", stockSymbol, price);
        System.out.println("--------------------------------------------------");
        notifyWidgets(stockSymbol, price);
    }

    private void notifyWidgets(String stockSymbol, double price) {
        for (DashboardWidget widget : widgets) {
            widget.onPriceUpdate(stockSymbol, price);
        }
    }
}
