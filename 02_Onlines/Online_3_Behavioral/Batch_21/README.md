# Online 3 (Behavioral Patterns): Batch 21

**Batch:** CSE 21 (BUET)
**Exam Focus:** Behavioral Design Patterns

---

## 📋 Problem & Solution Index

| Problem | Domain Scenario | Applied Pattern | Directory |
| :--- | :--- | :--- | :--- |
| **Traffic Light** | Traffic Light State Machine: Cycling between Red, Green, and Yellow light states with signal-specific duration and behavior. | **State** | [`TrafficLight/`](TrafficLight/) |
| **E-commerce Payment**| Multi-Channel Checkout: Dynamically selecting payment algorithms (BKash, Nagad, Credit Card, Cryptocurrency) at checkout. | **Strategy** | [`EcommercePayment/`](EcommercePayment/) |
| **Stock Trading** | Stock Exchange Ticker: Broadcasting real-time stock price changes to registered investor observers. | **Observer** | [`StockTrading/`](StockTrading/) |
| **Hungry Hippo Order**| Food Delivery Tracking: State transitions across Placed, Confirmed, Shipped, Delivered, and Cancelled orders. | **State** | [`HungryHippoOrder/`](HungryHippoOrder/) |
| **Banking Notification**| Banking Platform Alerts: Sending transaction bulletins to customers across SMS, Email, WhatsApp, and Mobile App push notifications. | **Observer + Strategy** | [`BankingNotification/`](BankingNotification/) |

---

## ⚡ How to Compile & Run
```bash
# Example: Traffic Light State Machine
cd TrafficLight
javac *.java
java Main
```
