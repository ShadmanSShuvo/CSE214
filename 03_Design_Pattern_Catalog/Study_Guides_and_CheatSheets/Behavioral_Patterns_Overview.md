# Behavioral Design Patterns - Lab Solutions & Architecture Templates

A comprehensive repository containing modular, object-oriented solutions for **15 behavioral design pattern lab problems** across three batches (**CSE21**, **CSE22**, and **CSE23**) alongside **6 production-grade behavioral design pattern reference templates** in [`patterns/`](patterns).
A comprehensive repository containing modular, object-oriented solutions for **16 behavioral design pattern lab problems** across three batches (**CSE21**, **CSE22**, and **CSE23**) alongside **6 production-grade behavioral design pattern reference templates** in [`patterns/`](patterns).

Each folder is completely self-contained with:
- `README.md`: Complete problem statement / pattern theory, architectural justification, sequence and UML diagrams, and execution guides.
- Pure standard Java implementation adhering to SOLID principles and clean OOP patterns.
- `Main.java`: Executable test driver verifying all features, transitions, and edge cases.

---

## Quick Navigation

- [Summary Matrix - Lab Problems](#summary-matrix---lab-problems)
- [Summary Matrix - Pattern Templates](#summary-matrix---pattern-templates)
- [Behavioral Design Pattern Templates](#behavioral-design-pattern-templates)
  - [1. Command Pattern](#1-command-pattern)
  - [2. Mediator Pattern](#2-mediator-pattern)
  - [3. Observer Pattern](#3-observer-pattern)
  - [4. State Pattern](#4-state-pattern)
  - [5. Strategy Pattern](#5-strategy-pattern)
  - [6. Template Method Pattern](#6-template-method-pattern)
- [CSE21 Lab Problems](#cse21-lab-problems)
  - [1. Banking Notification System](#1-banking-notification-system)
  - [2. HungryHippo Order Lifecycle](#2-hungryhippo-order-lifecycle)
  - [3. E-Commerce Payment System](#3-e-commerce-payment-system)
  - [4. Traffic Light Controller](#4-traffic-light-controller)
  - [5. Real-Time Stock Tracking](#5-real-time-stock-tracking)
- [CSE22 Lab Problems](#cse22-lab-problems)
  - [6. Brain-Support Subscription Simulator](#6-brain-support-subscription-simulator)
  - [7. King's Landing RavenBoard](#7-kings-landing-ravenboard)
  - [8. Smart Home Automation Hub](#8-smart-home-automation-hub)
  - [9. Hospital Visit Simulator](#9-hospital-visit-simulator)
  - [10. Day-Trading Financial Dashboard](#10-day-trading-financial-dashboard)
- [CSE23 Lab Problems](#cse23-lab-problems)
  - [11. Adaptive Task Scheduler](#11-adaptive-task-scheduler)
  - [12. E-Commerce Return and Refund Workflow](#12-e-commerce-return-and-refund-workflow)
  - [13. Hospital Emergency Room Coordinator](#13-hospital-emergency-room-coordinator)
  - [14. Smart Discount Calculation System](#14-smart-discount-calculation-system)
  - [15. Online Exam Evaluation Process](#15-online-exam-evaluation-process)
  - [16. Automatic AI Model Selection System](#16-automatic-ai-model-selection-system)
- [How to Build & Run](#how-to-build--run)

---

## Summary Matrix - Lab Problems

| # | Batch | Problem Name | Folder | Design Pattern | Key Concepts |
|:---:|:---:|:---|:---|:---:|:---|
| 1 | **CSE21** | Banking Notification System | [`CSE21/BankingNotification`](CSE21/BankingNotification) | **Strategy** | Dynamic notification channels (Email, SMS, App, WhatsApp), uniform payload |
| 2 | **CSE21** | HungryHippo Order Lifecycle | [`CSE21/HungryHippoOrder`](CSE21/HungryHippoOrder) | **State** | Order flow (`Placed` &rarr; `Confirmed` &rarr; `Shipped` &rarr; `Delivered`/`Cancelled`), transition guards |
| 3 | **CSE21** | E-Commerce Payment System | [`CSE21/EcommercePayment`](CSE21/EcommercePayment) | **Strategy** | Pluggable payment strategies (Credit Card, bKash, Crypto, Nagad) at checkout |
| 4 | **CSE21** | Traffic Light Controller | [`CSE21/TrafficLight`](CSE21/TrafficLight) | **State** | Cyclic state transitions (`RED` 5s &rarr; `YELLOW` 2s &rarr; `GREEN` 10s &rarr; `RED`) with timer |
| 5 | **CSE21** | Real-Time Stock Tracking | [`CSE21/StockTrading`](CSE21/StockTrading) | **Observer** | Real-time stock ticker alerts; multi-user follow/unfollow mechanisms |
| 6 | **CSE22** | Brain-Support Subscription | [`CSE22/BrainSupportSubscription`](CSE22/BrainSupportSubscription) | **State** | Tiers (`Common`, `Plus`, `Lux`), safe travel radius, temporary Lux timer, mood control |
| 7 | **CSE22** | King's Landing RavenBoard | [`CSE22/KingsLandingRavenBoard`](CSE22/KingsLandingRavenBoard) | **Observer** | Broadcast message board with dynamic group subscription (Commander, Scouts, Supply) |
| 8 | **CSE22** | Smart Home Automation Hub | [`CSE22/SmartHomeAutomationHub`](CSE22/SmartHomeAutomationHub) | **Mediator** | Central coordinator managing LightSensor &rarr; Blinds &rarr; AirConditioner cascade |
| 9 | **CSE22** | Hospital Visit Simulator | [`CSE22/HospitalVisitSimulator`](CSE22/HospitalVisitSimulator) | **Template Method** | Invariable 5-step visit algorithm skeleton with department-specific overrides |
| 10 | **CSE22** | Financial Dashboard | [`CSE22/FinancialDashboard`](CSE22/FinancialDashboard) | **Observer** | Live feed broadcasting price ticks to Ticker Tape, Graph, and Auto-Trading Bot |
| 11 | **CSE23** | Adaptive Task Scheduler | [`CSE23/AdaptiveTaskScheduler`](CSE23/AdaptiveTaskScheduler) | **Strategy** | Workload-driven adaptive strategy selection (`Priority`, `SJF`, `FCFS`) |
| 12 | **CSE23** | Return & Refund Workflow | [`CSE23/EcommerceReturnRefundWorkflow`](CSE23/EcommerceReturnRefundWorkflow) | **State** | 7-condition return lifecycle, validation gates, and payout retry handling |
| 13 | **CSE23** | ER Coordinator | [`CSE23/HospitalEmergencyRoomCoordinator`](CSE23/HospitalEmergencyRoomCoordinator) | **Mediator** | Centralized coordination for Doctor, Pathology Lab, Radiology Unit, and Patient |
| 14 | **CSE23** | Smart Discount Calculation | [`CSE23/SmartDiscountCalculationSystem`](CSE23/SmartDiscountCalculationSystem) | **Strategy** | Independent evaluation of Purchase, Customer, and Payment discounts; selects max |
| 15 | **CSE23** | Online Exam Evaluation Process | [`CSE23/OnlineExamEvaluationProcess`](CSE23/OnlineExamEvaluationProcess) | **Template Method** | Invariable 5-step evaluation workflow (`Validate` &rarr; `Evaluate` &rarr; `Calculate` &rarr; `Adjust` &rarr; `Publish`) with exam-specific overrides |
| 16 | **CSE23** | Automatic AI Model Selection | [`CSE23/AutomaticAIModelSelectionSystem`](CSE23/AutomaticAIModelSelectionSystem) | **Strategy** | Complexity-based model routing (`FlashMind`, `CoreMind`, `DeepMindX`), usage quota enforcement, fallback delegation |

---

## Summary Matrix - Pattern Templates

| # | Pattern | Template Folder | Real-World Domain | Key Concepts Demonstrated |
|:---:|:---|:---|:---|:---|
| 1 | **Command** | [`patterns/Command`](patterns/Command) | Smart Home Remote & Automation | Encapsulated requests, multi-level Undo & Redo stacks, state-tracking undo (CeilingFan), Null Object (`NoCommand`), Composite/Macro command |
| 2 | **Mediator** | [`patterns/Mediator`](patterns/Mediator) | Air Traffic Control (ATC) System | Loose coupling, centralized resource management (`Runway`), landing/departure queues, emergency priority preemption, decoupled colleague broadcasts |
| 3 | **Observer** | [`patterns/Observer`](patterns/Observer) | Weather Telemetry Station | 1-to-many publish, Push/Pull hybrid telemetry model, dynamic subscription/unsubscription, threshold-based emergency sirens, defensive iteration (safe unsubscription) |
| 4 | **State** | [`patterns/State`](patterns/State) | Editorial Document Publishing | Finite State Machine (`Draft` &rarr; `InReview` &rarr; `Published` &rarr; `Archived`), state-driven transitions, role-based permission enforcement, elimination of nested conditionals |
| 5 | **Strategy** | [`patterns/Strategy`](patterns/Strategy) | Multimodal GPS Navigation | Family of interchangeable routing algorithms (`Driving`, `Walking`, `Bicycling`, `Transit`), runtime strategy hot-swapping, modern Java `@FunctionalInterface` & lambda strategies |
| 6 | **Template Method** | [`patterns/TemplateMethod`](patterns/TemplateMethod) | Enterprise ETL Data Pipeline | Hollywood Principle ("Don't call us, we'll call you"), `final` algorithm skeleton, abstract primitives (`CSV`, `JSON`, `XML`), overridable hooks (`preProcess`, `postProcess`) |

---

## Behavioral Design Pattern Templates

### 1. Command Pattern
- **Path**: [`patterns/Command`](patterns/Command)
- **Domain**: Smart Home Multi-Device Remote Control
- **Core Components**:
  - `Command` interface declaring `execute()` and `undo()`
  - Concrete commands: [`LightOnCommand`](patterns/Command/LightOnCommand.java), [`LightOffCommand`](patterns/Command/LightOffCommand.java), [`StereoOnWithCDCommand`](patterns/Command/StereoOnWithCDCommand.java), [`CeilingFanHighCommand`](patterns/Command/CeilingFanHighCommand.java)
  - Composite command: [`MacroCommand`](patterns/Command/MacroCommand.java)
  - Invoker: [`RemoteControl`](patterns/Command/RemoteControl.java) managing undo and redo history stacks
  - Null Object: [`NoCommand`](patterns/Command/NoCommand.java) to eliminate null pointer checks
- **Documentation**: Detailed guide in [`patterns/Command/README.md`](patterns/Command/README.md).

### 2. Mediator Pattern
- **Path**: [`patterns/Mediator`](patterns/Mediator)
- **Domain**: Airport Air Traffic Control (ATC) Coordination Hub
- **Core Components**:
  - Mediator interface: [`AirTrafficControlMediator`](patterns/Mediator/AirTrafficControlMediator.java)
  - Concrete Mediator: [`AirportControlTower`](patterns/Mediator/AirportControlTower.java) managing runway allocation and priority queues
  - Abstract Colleague: [`Aircraft`](patterns/Mediator/Aircraft.java)
  - Concrete Colleagues: [`CommercialFlight`](patterns/Mediator/CommercialFlight.java), [`CargoFlight`](patterns/Mediator/CargoFlight.java), [`RescueHelicopter`](patterns/Mediator/RescueHelicopter.java) (with emergency preemption)
  - Shared Resource: [`Runway`](patterns/Mediator/Runway.java)
- **Documentation**: Detailed guide in [`patterns/Mediator/README.md`](patterns/Mediator/README.md).

### 3. Observer Pattern
- **Path**: [`patterns/Observer`](patterns/Observer)
- **Domain**: Real-Time Weather Telemetry Station
- **Core Components**:
  - Subject interface: [`Subject`](patterns/Observer/Subject.java)
  - Concrete Subject: [`WeatherData`](patterns/Observer/WeatherData.java) tracking temperature, humidity, and barometric pressure
  - Observer interface: [`Observer`](patterns/Observer/Observer.java) with Hybrid Push-Pull signature
  - Display contract: [`DisplayElement`](patterns/Observer/DisplayElement.java)
  - Concrete Observers: [`CurrentConditionsDisplay`](patterns/Observer/CurrentConditionsDisplay.java), [`StatisticsDisplay`](patterns/Observer/StatisticsDisplay.java), [`ForecastDisplay`](patterns/Observer/ForecastDisplay.java), [`SevereWeatherAlertSystem`](patterns/Observer/SevereWeatherAlertSystem.java)
- **Documentation**: Detailed guide in [`patterns/Observer/README.md`](patterns/Observer/README.md).

### 4. State Pattern
- **Path**: [`patterns/State`](patterns/State)
- **Domain**: Enterprise Document Editorial Lifecycle
- **Core Components**:
  - Context: [`Document`](patterns/State/Document.java)
  - State interface: [`DocumentState`](patterns/State/DocumentState.java)
  - Concrete States: [`DraftState`](patterns/State/DraftState.java), [`InReviewState`](patterns/State/InReviewState.java), [`PublishedState`](patterns/State/PublishedState.java), [`ArchivedState`](patterns/State/ArchivedState.java)
  - Actor & Permissions: [`User`](patterns/State/User.java) (`AUTHOR`, `EDITOR`, `ADMIN`)
- **Documentation**: Detailed guide in [`patterns/State/README.md`](patterns/State/README.md).

### 5. Strategy Pattern
- **Path**: [`patterns/Strategy`](patterns/Strategy)
- **Domain**: Multimodal GPS Navigation Engine
- **Core Components**:
  - Strategy interface: [`RouteStrategy`](patterns/Strategy/RouteStrategy.java) annotated with `@FunctionalInterface`
  - Concrete Strategies: [`DrivingStrategy`](patterns/Strategy/DrivingStrategy.java), [`WalkingStrategy`](patterns/Strategy/WalkingStrategy.java), [`BicyclingStrategy`](patterns/Strategy/BicyclingStrategy.java), [`PublicTransitStrategy`](patterns/Strategy/PublicTransitStrategy.java)
  - Modern Java Feature: Anonymous / Lambda strategy construction via `RouteStrategy.of()`
  - Context: [`Navigator`](patterns/Strategy/Navigator.java) supporting runtime strategy hot-swapping and comparative route evaluation
- **Documentation**: Detailed guide in [`patterns/Strategy/README.md`](patterns/Strategy/README.md).

### 6. Template Method Pattern
- **Path**: [`patterns/TemplateMethod`](patterns/TemplateMethod)
- **Domain**: Enterprise ETL Data Ingestion Pipeline
- **Core Components**:
  - Abstract Template: [`DataProcessor`](patterns/TemplateMethod/DataProcessor.java) declaring `final process(String path)`
  - Concrete Subclasses: [`CSVDataProcessor`](patterns/TemplateMethod/CSVDataProcessor.java), [`JSONDataProcessor`](patterns/TemplateMethod/JSONDataProcessor.java), [`XMLDataProcessor`](patterns/TemplateMethod/XMLDataProcessor.java)
  - Primitive Operations: `readRawData()`, `parseRecords()`
  - Hook Methods: `preProcessHook()`, `postProcessHook()`, `shouldLogTelemetry()`
- **Documentation**: Detailed guide in [`patterns/TemplateMethod/README.md`](patterns/TemplateMethod/README.md).

---

## CSE21 Lab Problems

### 1. Banking Notification System
- **Path**: [`CSE21/BankingNotification`](CSE21/BankingNotification)
- **Source**: `CSE21/Online 3 A2.pdf`
- **Pattern**: **Strategy Pattern**
- **Description**: A multi-channel notification dispatcher for a banking platform. Allows bank customers to receive transaction alerts, low-balance warnings, and promotional offers across channels such as Email, SMS, Mobile App Push, and WhatsApp. Customers can switch their preferred channel dynamically at runtime without modifying the underlying banking dispatch logic.

### 2. HungryHippo Order Lifecycle
- **Path**: [`CSE21/HungryHippoOrder`](CSE21/HungryHippoOrder)
- **Source**: `CSE21/Online 3 C2.pdf`
- **Pattern**: **State Pattern**
- **Description**: Food delivery order state machine modeling `Placed`, `Confirmed`, `Shipped`, `Delivered`, and `Cancelled` states. Restricts illegal operations (e.g., cannot skip from `Placed` directly to `Delivered`, cannot cancel an order once shipped, cannot revert once dispatched).

### 3. E-Commerce Payment System
- **Path**: [`CSE21/EcommercePayment`](CSE21/EcommercePayment)
- **Source**: `CSE21/Sec A.pdf`
- **Pattern**: **Strategy Pattern**
- **Description**: Flexible e-commerce payment checkout system enabling customers to choose and switch between payment strategies (Credit Card, bKash, Cryptocurrency/Bitcoin) on the fly. Demonstrates Open/Closed extensibility with a new `NagadPayment` strategy.

### 4. Traffic Light Controller
- **Path**: [`CSE21/TrafficLight`](CSE21/TrafficLight)
- **Source**: `CSE21/Sec_B.pdf`
- **Pattern**: **State Pattern**
- **Description**: Finite state machine managing a cyclic traffic light sequence: Red (5 seconds) &rarr; Yellow (2 seconds) &rarr; Green (10 seconds) &rarr; Red. Encapsulates duration and state transitions within concrete light state objects with timer support.

### 5. Real-Time Stock Tracking
- **Path**: [`CSE21/StockTrading`](CSE21/StockTrading)
- **Source**: `CSE21/Sec C.pdf`
- **Pattern**: **Observer Pattern**
- **Description**: Stock price tracking system allowing users to follow and unfollow multiple stocks. When a stock price changes, all subscribed observers are automatically updated. Fully replicates the sample code structure and output requested in the problem specification.

---

## CSE22 Problems

*(Extracted from `CSE22/Behavioral_Online_Questions.pdf`)*

### 6. Brain-Support Subscription Simulator
- **Path**: [`CSE22/BrainSupportSubscription`](CSE22/BrainSupportSubscription)
- **Source**: `CSE22/Behavioral_Online_Questions.pdf` (Subsection A1)
- **Pattern**: **State Pattern**
- **Description**: Brain-injury support service managing three tiers (`Common`, `Plus`, `Lux`). Each tier defines a safe travel radius (10 km vs 50 km). Exceeding coverage causes patient blackout until brought back into range (`travelCheck(0)`). Features temporary Lux tier activation with automatic restoration of prior tier, and mood calibration restricted to the Lux tier.

### 7. King's Landing RavenBoard
- **Path**: [`CSE22/KingsLandingRavenBoard`](CSE22/KingsLandingRavenBoard)
- **Source**: `CSE22/Behavioral_Online_Questions.pdf` (Subsection A2)
- **Pattern**: **Observer Pattern**
- **Description**: Message board system in King's Landing where ravens deliver strategic scrolls. Subscribed factions (`Commander`, `Scouts`, `SupplyTeam`) receive messages and trigger domain-specific actions. Supports dynamic subscription and unsubscription at runtime.

### 8. Smart Home Automation Hub
- **Path**: [`CSE22/SmartHomeAutomationHub`](CSE22/SmartHomeAutomationHub)
- **Source**: `CSE22/Behavioral_Online_Questions.pdf` (Subsection B2)
- **Pattern**: **Mediator Pattern**
- **Description**: Central smart home hub decoupling communication between IoT devices. When `LightSensor` detects high brightness, it notifies the hub, which commands `AutomaticBlinds` to close. Upon closing, the blinds notify the hub, which switches on the `AirConditioner` to keep the room ventilated.

### 9. Hospital Visit Simulator
- **Path**: [`CSE22/HospitalVisitSimulator`](CSE22/HospitalVisitSimulator)
- **Source**: `CSE22/Behavioral_Online_Questions.pdf` (Subsection C1)
- **Pattern**: **Template Method Pattern**
- **Description**: Standardizes a 5-step patient visit flow (Check-In &rarr; Record Vitals &rarr; Assessment &rarr; Treatment &rarr; Discharge Summary). Invariable steps are locked in the base template, while `GeneralDepartmentVisit`, `PediatricsDepartmentVisit`, and `EmergencyDepartmentVisit` provide customized diagnosis and treatment implementations.

### 10. Day-Trading Financial Dashboard
- **Path**: [`CSE22/FinancialDashboard`](CSE22/FinancialDashboard)
- **Source**: `CSE22/Behavioral_Online_Questions.pdf` (Subsection C2)
- **Pattern**: **Observer Pattern**
- **Description**: Real-time market feed broadcasting price ticks to decoupled UI widgets (`TickerTapeWidget`, `GraphWidget`) and an algorithmic `BuySellBot` that triggers automated market orders when price thresholds are crossed.

---

## CSE23 Problems

### 11. Adaptive Task Scheduler
- **Path**: [`CSE23/AdaptiveTaskScheduler`](CSE23/AdaptiveTaskScheduler)
- **Source**: `CSE23/Adaptive Task Scheduler.pdf`
- **Pattern**: **Strategy Pattern (Adaptive Workload Rules)**
- **Description**: Intelligent CPU task scheduler supporting FCFS, Priority Scheduling, and SJF (Shortest Job First). Dynamically re-evaluates queue state before dispatching each task:
  - **Rule 1**: Any `HIGH` priority task present &rarr; force `PriorityScheduling`.
  - **Rule 2**: No `HIGH` priority task and $\ge 3$ tasks with execution time $\le 3$ &rarr; force `SJF`.
  - **Rule 3**: Otherwise &rarr; fallback to user's preferred policy (e.g. `FCFS`).

### 12. E-Commerce Return and Refund Workflow
- **Path**: [`CSE23/EcommerceReturnRefundWorkflow`](CSE23/EcommerceReturnRefundWorkflow)
- **Source**: `CSE23/E-commerce Return and Refund Workflow.pdf`
- **Pattern**: **State Pattern**
- **Description**: Return lifecycle managing transitions through `Requested`, `Approved`, `Delivered`, `Processing Refund`, and terminal states `Refunded`, `Rejected`, `Cancelled`. Disallows invalid actions at each stage and supports retry logic on payment gateway failure.

### 13. Hospital Emergency Room Coordinator
- **Path**: [`CSE23/HospitalEmergencyRoomCoordinator`](CSE23/HospitalEmergencyRoomCoordinator)
- **Source**: `CSE23/Hospital Emergency Room Coordinator.pdf`
- **Pattern**: **Mediator Pattern**
- **Description**: Central emergency center mediating investigation requests between `DoctorUnit`, `PathologyLab`, `RadiologyUnit`, and `Patient`. Tracks pending investigations per patient; immediately dispatches urgent alerts on `CRITICAL` or `NOT OK` findings, and delivers consolidated reports once all investigations finish. Replicates the exact sample output specified in the exam paper.

### 14. Smart Discount Calculation System
- **Path**: [`CSE23/SmartDiscountCalculationSystem`](CSE23/SmartDiscountCalculationSystem)
- **Source**: `CSE23/Smart Discount Calculation System.pdf`
- **Pattern**: **Strategy Pattern**
- **Description**: Checkout discount calculation engine evaluating three independent strategies:
  1. *Purchase Amount Discount*: 5% per ৳1,000 up to 25%.
  2. *Customer Category Discount*: Regular (5%), Premium (15%).
  3. *Payment Method Discount*: Card (2%), MFS (5%), Cash (8%).
  Applies only the single highest discount percentage (no stacking) and computes the final payable amount.

### 15. Online Exam Evaluation Process
- **Path**: [`CSE23/OnlineExamEvaluationProcess`](CSE23/OnlineExamEvaluationProcess)
- **Source**: `CSE23/Online Exam Evaluation Process.pdf`
- **Pattern**: **Template Method Pattern**
- **Description**: Standardizes a 5-step evaluation workflow (`Validate Submission` &rarr; `Evaluate Answers` &rarr; `Calculate Score` &rarr; `Apply Adjustment` &rarr; `Publish Result`) across MCQ Exam (negative marking), Programming Exam (compilation check, test case scoring, 50% plagiarism penalty), and Written Exam (moderation bonus up to max 100).

### 16. Automatic AI Model Selection System
- **Path**: [`CSE23/AutomaticAIModelSelectionSystem`](CSE23/AutomaticAIModelSelectionSystem)
- **Source**: `CSE23/Automatic AI Model Selection System.pdf`
- **Pattern**: **Strategy Pattern** (with **Chain of Responsibility** Fallback Delegation)
- **Description**: An AI platform providing access to three distinct language models (`FlashMind`, `CoreMind`, `DeepMindX`). Analyzes prompt complexity using `ComplexityAnalyzer` and dynamically selects the appropriate model strategy (0–30: `FlashMind`, 31–70: `CoreMind`, 71–100: `DeepMindX`). Enforces strict usage limits on expensive models (`DeepMindX`: 10 prompts, `CoreMind`: 50 prompts) and delegates to lower tiers via a fallback chain (`DeepMindX` &rarr; `CoreMind` &rarr; `FlashMind`) without selecting higher models unnecessarily.

---

## How to Build & Run

### Prerequisites
- JDK 17 or higher (tested with OpenJDK 21 and OpenJDK 26)

### Running an Individual Problem
Navigate into any problem directory, compile all `.java` files, and execute the `Main` class:

```bash
# Example: Running the Adaptive Task Scheduler
cd CSE23/AdaptiveTaskScheduler
javac *.java
java Main
```

```bash
# Example: Running King's Landing RavenBoard
cd CSE22/KingsLandingRavenBoard
javac *.java
java Main
```

### Running Individual Pattern Templates
Navigate into any pattern directory under `patterns/`, compile all `.java` files, and run `Main`:

```bash
# Example: Running the Command pattern template
cd patterns/Command
javac *.java
java Main
```

```bash
# Example: Running the Mediator pattern template
cd patterns/Mediator
javac *.java
java Main
```

### Running All 15 Problems & 6 Pattern Templates (Automated Verification)
You can run a one-line Python script from the root repository to verify all 21 test suites:
### Running All 16 Problems & 6 Pattern Templates (Automated Verification)
You can run a one-line Python script from the root repository to verify all 22 test suites:

```bash
python3 -c "
import subprocess

folders = [
    # 15 Lab Problems
    # 16 Lab Problems
    'CSE21/BankingNotification',
    'CSE21/HungryHippoOrder',
    'CSE21/EcommercePayment',
    'CSE21/TrafficLight',
    'CSE21/StockTrading',
    'CSE22/BrainSupportSubscription',
    'CSE22/KingsLandingRavenBoard',
    'CSE22/SmartHomeAutomationHub',
    'CSE22/HospitalVisitSimulator',
    'CSE22/FinancialDashboard',
    'CSE23/AdaptiveTaskScheduler',
    'CSE23/EcommerceReturnRefundWorkflow',
    'CSE23/HospitalEmergencyRoomCoordinator',
    'CSE23/SmartDiscountCalculationSystem',
    'CSE23/OnlineExamEvaluationProcess',
    'CSE23/AutomaticAIModelSelectionSystem',
    # 6 Behavioral Pattern Templates
    'patterns/Command',
    'patterns/Mediator',
    'patterns/Observer',
    'patterns/State',
    'patterns/Strategy',
    'patterns/TemplateMethod'
]

for f in folders:
    print(f'===> Testing {f}...')
    subprocess.check_call('javac *.java', shell=True, cwd=f)
    subprocess.check_call(['java', 'Main'], cwd=f)
    print(f'PASS: {f}\n')

print('All 15 lab problems and 6 pattern templates compiled and ran successfully!')
print('All 16 lab problems and 6 pattern templates compiled and ran successfully!')
"
```
