# Standalone Behavioral Pattern Demos

This directory contains six completely self-contained, single-file Java implementations of core behavioral design patterns. Each file includes the pattern interfaces, concrete classes, and a `main` client execution routine.

---

## 📋 Catalog of Standalone Demos

| File | Pattern | Scenario Demonstrated | Key Classes Involved |
| :--- | :--- | :--- | :--- |
| **`ObserverDemo.java`** | **Observer** | Real-time Stock Market updates dispatched to registered investor clients. | `Subject`, `Stock`, `Observer`, `Investor` |
| **`StateDemo.java`** | **State** | Media Player state transitions (`PlayingState`, `PausedState`, `StoppedState`). | `State`, `AudioPlayer`, `PlayState`, `StopState` |
| **`CommandDemo.java`** | **Command** | Home Automation Remote Control with macro command execution and undo. | `Command`, `LightOnCommand`, `RemoteControl` |
| **`MediatorDemo.java`** | **Mediator** | Chat Room communication where users broadcast messages through a mediator without direct connections. | `ChatMediator`, `ChatRoom`, `User`, `BasicUser` |
| **`StrategyDemo.java`** | **Strategy** | E-commerce Payment processing using interchangeable payment algorithms. | `PaymentStrategy`, `CreditCardPayment`, `ShoppingCart` |
| **`TemplateMethodDemo.java`** | **Template Method** | Data Mining & ETL pipeline parsing CSV and PDF files with invariant extract, process, and output steps. | `DataMiner`, `CSVDataMiner`, `PDFDataMiner` |

---

## ⚡ Compilation & Execution

Each file compiles and runs independently:

```bash
# Observer Demo
javac ObserverDemo.java && java ObserverDemo

# State Demo
javac StateDemo.java && java StateDemo

# Command Demo
javac CommandDemo.java && java CommandDemo

# Mediator Demo
javac MediatorDemo.java && java MediatorDemo

# Strategy Demo
javac StrategyDemo.java && java StrategyDemo

# Template Method Demo
javac TemplateMethodDemo.java && java TemplateMethodDemo
```
