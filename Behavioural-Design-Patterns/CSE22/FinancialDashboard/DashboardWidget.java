public interface DashboardWidget {
    String getWidgetName();
    void onPriceUpdate(String stockSymbol, double price);
}
