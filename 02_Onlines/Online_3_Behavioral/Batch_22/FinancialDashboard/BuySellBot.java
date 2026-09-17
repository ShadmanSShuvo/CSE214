public class BuySellBot implements DashboardWidget {
    private final String name;
    private final String targetSymbol;
    private final double buyThreshold;
    private final double sellThreshold;

    public BuySellBot(String name, String targetSymbol, double buyThreshold, double sellThreshold) {
        this.name = name;
        this.targetSymbol = targetSymbol;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
    }

    @Override
    public String getWidgetName() {
        return name;
    }

    @Override
    public void onPriceUpdate(String stockSymbol, double price) {
        if (!stockSymbol.equalsIgnoreCase(targetSymbol)) {
            return;
        }

        if (price <= buyThreshold) {
            System.out.printf(
                    "  [%s] *** AUTO TRADE TRIGGERED *** Price $%.2f <= Buy Threshold $%.2f -> Executing BUY order for %s!\n",
                    name, price, buyThreshold, stockSymbol);
        } else if (price >= sellThreshold) {
            System.out.printf(
                    "  [%s] *** AUTO TRADE TRIGGERED *** Price $%.2f >= Sell Threshold $%.2f -> Executing SELL order for %s!\n",
                    name, price, sellThreshold, stockSymbol);
        } else {
            System.out.printf("  [%s] Price $%.2f is within hold range ($%.2f - $%.2f). No trade executed.\n",
                    name, price, buyThreshold, sellThreshold);
        }
    }
}
