# Problem Statement: Real-Time Stock Tracking System

**Batch**: CSE21
**Source**: `Sec C.pdf`
**Duration**: 25 Minutes
**Design Pattern**: **Observer Pattern**

---

## 1. Problem Description

A stock trading company wants to build an app that helps their clients track stock prices in real-time. If the price of a stock that a client is interested in changes, the app should automatically notify them.

In this app, each user can follow multiple stocks. Whenever the price of a stock changes, all users who are tracking that stock should receive an update.

Each stock has these details:
- Stock name
- Stock price

The app will allow users to:
- Add or remove the stocks they want to track.
- Get informed when the price of the stocks they are following changes.

### Sample Code Given in Problem:
```java
public static void main(String[] args) {
    // Create stocks
    Stock googleStock = new Stock("Google", 1500);
    Stock appleStock = new Stock("Apple", 1200);
    // Create users
    User user1 = new User("Alice");
    User user2 = new User("Bob");
    // Code for following stocks
    // Simulate price changes
    System.out.println("Updating Google stock price...");
    googleStock.setPrice(1550);
    System.out.println("\nUpdating Apple stock price...");
    appleStock.setPrice(1250);
    // Code for unfollowing stocks
    // Simulate price changes again
}
```

### Expected Output:
```
Updating Google stock price...
Alice has been notified: The price of Google is now 1550.0
Bob has been notified: The price of Google is now 1550.0
Updating Apple stock price...
Alice has been notified: The price of Apple is now 1250.0
Updating Google stock price again...
Bob has been notified: The price of Google is now 1600.0
```

---

## 2. Design Pattern Justification: Observer Pattern

- **Why Observer Pattern?**:
  There is a one-to-many dependency between the Subject (`Stock`) and multiple Observers (`User`). When the stock price state changes, all registered observers are automatically notified without coupling `Stock` to specific user types.
- Users can subscribe (`registerObserver` / `follow`) or unsubscribe (`removeObserver` / `unfollow`) dynamically at runtime.

---

## 3. Class Diagram

```
       +-----------------------------------+
       |          <<interface>>            |
       |             Observer              |
       +-----------------------------------+
       | + update(stockName, newPrice)     |
       +-----------------------------------+
                         ^
                         | implements
       +-----------------------------------+
       |               User                |
       +-----------------------------------+
       | - name: String                    |
       | + follow(stock: Stock)            |
       | + unfollow(stock: Stock)          |
       | + update(stockName, newPrice)     |
       +-----------------------------------+
                         |
                         | observes
                         v
       +-----------------------------------+
       |               Stock               |
       +-----------------------------------+
       | - name: String                    |
       | - price: double                   |
       | - observers: List<Observer>       |
       +-----------------------------------+
       | + registerObserver(observer)      |
       | + removeObserver(observer)        |
       | + notifyObservers()               |
       | + setPrice(newPrice: double)      |
       +-----------------------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE21/StockTrading
javac *.java
java Main
```
