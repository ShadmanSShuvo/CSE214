public class TickerTapeWidget implements DashboardWidget {
    private final String name;

    public TickerTapeWidget(String name) {
        this.name = name;
    }

    @Override
    public String getWidgetName() {
        return name;
    }

    @Override
    public void onPriceUpdate(String stockSymbol, double price) {
        System.out.printf("  [%s] Scrolling on banner: >>> %s: $%.2f <<<\n", name, stockSymbol, price);
    }
}
