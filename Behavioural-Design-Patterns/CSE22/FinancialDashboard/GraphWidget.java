public class GraphWidget implements DashboardWidget {
    private final String name;

    public GraphWidget(String name) {
        this.name = name;
    }

    @Override
    public String getWidgetName() {
        return name;
    }

    @Override
    public void onPriceUpdate(String stockSymbol, double price) {
        System.out.printf("  [%s] Plotting real-time data point on chart -> [%s, $%.2f]\n", name, stockSymbol, price);
    }
}
