public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   FINANCIAL DASHBOARD (OBSERVER PATTERN)                 ");
        System.out.println("==========================================================");

        // Core data feed (Subject)
        StockDataFeed feed = new StockDataFeed();

        // Distinct dashboard widgets (Observers)
        DashboardWidget tickerTape = new TickerTapeWidget("Top-TickerTape");
        DashboardWidget graphWidget = new GraphWidget("Candlestick-Graph");
        DashboardWidget bot = new BuySellBot("HighFreq-Bot", "AAPL", 145.0, 180.0);

        // 1. Attach widgets
        System.out.println("\n--- Step 1: Attaching Dashboard Widgets ---");
        feed.attach(tickerTape);
        feed.attach(graphWidget);
        feed.attach(bot);

        // 2. Normal price update
        feed.setPrice("AAPL", 160.00);

        // 3. Price drops below buy threshold -> triggers automated buy
        feed.setPrice("AAPL", 142.50);

        // 4. Detach a widget (e.g. user closes Graph Widget)
        System.out.println("\n--- Step 4: Detaching Graph Widget at Runtime ---");
        feed.detach(graphWidget);

        // 5. Price rises above sell threshold -> triggers automated sell, graph no
        // longer updates
        feed.setPrice("AAPL", 185.00);

        System.out.println("\n==========================================================");
        System.out.println("   Financial Dashboard demonstration completed.           ");
        System.out.println("==========================================================");
    }
}
