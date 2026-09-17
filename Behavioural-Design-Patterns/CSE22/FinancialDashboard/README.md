# Problem Statement: Day-Trading Financial Dashboard

**Batch**: CSE22
**Source**: `Behavioral_Online_Questions.pdf` (Subsection: C2)
**Time**: 25 minutes
**Design Pattern**: **Observer Pattern**

---

## 1. Problem Description

You are building a financial dashboard for a day-trading firm. The traders need to see real-time changes in stock prices across various distinct widgets on their screen.

The core of the system is a **StockData feed**. Whenever the price of a specific stock changes, several unrelated components need to update immediately:
1. A **Ticker Tape widget** needs to scroll the new price.
2. A **Graph widget** needs to plot the new data point.
3. A **Buy/Sell Bot** needs to evaluate if the new price triggers an automated trade.

You need a design where the `StockData` feed doesn’t know the specifics of these widgets but can notify all of them automatically whenever a price update occurs. There must also be provision for adding or removing widgets dynamically at runtime.

---

## 2. Design Pattern Justification: Observer Pattern

- **Why Observer Pattern?**:
  The `StockDataFeed` represents the Subject that emits price update events. The various dashboard components (`TickerTapeWidget`, `GraphWidget`, `BuySellBot`) are Observers implementing a common interface (`DashboardWidget`).
- This decouples the feed from the concrete UI widgets and algorithmic trading bots, making the dashboard highly extensible: new widgets (e.g., AlertPopup, OrderBook) can be added or removed without altering the core feed.

---

## 3. Class Diagram

```
                 +---------------------------------------------+
                 |                StockDataFeed                |
                 +---------------------------------------------+
                 | - widgets: List<DashboardWidget>            |
                 | + attach(widget: DashboardWidget)           |
                 | + detach(widget: DashboardWidget)           |
                 | + setPrice(symbol: String, price: double)   |
                 +---------------------------------------------+
                                        |
                                        v notifies
                 +---------------------------------------------+
                 |                <<interface>>                |
                 |               DashboardWidget               |
                 +---------------------------------------------+
                 | + getWidgetName(): String                   |
                 | + onPriceUpdate(symbol: String, pr: double) |
                 +---------------------------------------------+
                     ^                 ^                  ^
                     |                 |                  |
        +------------+                 |                  +------------+
        |                              |                               |
+--------------------+       +--------------------+          +--------------------+
|  TickerTapeWidget  |       |    GraphWidget     |          |     BuySellBot     |
+--------------------+       +--------------------+          +--------------------+
| + onPriceUpdate()  |       | + onPriceUpdate()  |          | + onPriceUpdate()  |
+--------------------+       +--------------------+          +--------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE22/FinancialDashboard
javac *.java
java Main
```
