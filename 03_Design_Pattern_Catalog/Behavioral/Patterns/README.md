# Behavioral Patterns: Modular Deep-Dive Packages

This directory contains six modular, multi-class packages demonstrating production-grade implementations of the primary Gang of Four Behavioral patterns.

---

## 📦 Modular Packages Index

| Directory | Pattern | Domain Scenario | Key Subject / Context / Invoker |
| :--- | :--- | :--- | :--- |
| **[`Observer/`](Observer/)** | **Observer** | Real-time meteorological station broadcasting conditions to multi-metric displays. | `WeatherData` (Subject) $\to$ `CurrentConditionsDisplay`, `StatisticsDisplay`, `ForecastDisplay` |
| **[`State/`](State/)** | **State** | Content management publication lifecycle with role-based access checks. | `Document` (Context) $\to$ `DraftState`, `InReviewState`, `PublishedState`, `ArchivedState` |
| **[`Command/`](Command/)** | **Command** | Programmable multi-slot home automation remote control with undo/macro execution. | `RemoteControl` (Invoker) $\to$ `LightOnCommand`, `CeilingFanMediumCommand`, `MacroCommand` |
| **[`Mediator/`](Mediator/)** | **Mediator** | Airport Air Traffic Control (ATC) tower coordinating flight clearances and runway allocation. | `AirportControlTower` (Mediator) $\to$ `CommercialFlight`, `CargoFlight`, `RescueHelicopter` |
| **[`TemplateMethod/`](TemplateMethod/)** | **Template Method** | ETL batch data processing framework with format-specific parsing hooks. | `DataProcessor` (Template) $\to$ `CSVDataProcessor`, `XMLDataProcessor`, `JSONDataProcessor` |
| **[`Strategy/`](Strategy/)** | **Strategy** | GPS Turn-by-Turn Navigation engine dynamically switching transit strategies. | `Navigator` (Context) $\to$ `DrivingStrategy`, `WalkingStrategy`, `BicyclingStrategy`, `PublicTransitStrategy` |

---

## ⚡ How to Run
Navigate to any pattern directory:
```bash
cd <PatternName>
javac *.java
java Main
```
