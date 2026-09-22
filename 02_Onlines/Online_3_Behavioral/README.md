# Online 3: Behavioral Design Patterns

**Course:** BUET CSE 214: Software Engineering & Object-Oriented Design Patterns Sessional  
**Focus:** Behavioral Design Patterns across Batches 21, 22, and 23  

---

## 🎯 Syllabus & Pattern Coverage

Online 3 tests design patterns that identify common communication patterns between objects and realize these patterns:
- **Observer Pattern:** Decoupled one-to-many publish-subscribe communication.
- **State Pattern:** Encapsulates varying behavior inside dedicated state objects to eliminate sprawling conditionals.
- **Strategy Pattern:** Defines a family of interchangeable algorithmic policies selectable at runtime.
- **Mediator Pattern:** Centralizes communication between collaborating colleague classes.
- **Template Method Pattern:** Defines the skeleton of an algorithm in a superclass while deferring specific steps to subclasses.

---

## 📊 Problem & Solution Index

| Batch | Problem / Section | Domain Scenario | Applied Pattern | Subfolder Location |
| :---: | :---: | :--- | :--- | :--- |
| **23** | **Task Scheduler** | Dynamic workload-driven scheduler switching between FCFS, Priority, and SJF | **Strategy** | [`Batch_23/AdaptiveTaskScheduler/`](Batch_23/AdaptiveTaskScheduler/) |
| **23** | **AI Model Selector** | Complexity-driven AI query routing (FlashMind, CoreMind, DeepMindX) | **Template Method + Strategy** | [`Batch_23/AutomaticAIModelSelectionSystem/`](Batch_23/AutomaticAIModelSelectionSystem/) |
| **23** | **Return / Refund** | Multi-stage e-commerce return request lifecycle (Review, Approval, Refund) | **State** | [`Batch_23/EcommerceReturnRefundWorkflow/`](Batch_23/EcommerceReturnRefundWorkflow/) |
| **23** | **ER Coordinator** | Emergency room hub mediating Doctor, Pathology Lab, and Radiology Unit | **Mediator** | [`Batch_23/HospitalEmergencyRoomCoordinator/`](Batch_23/HospitalEmergencyRoomCoordinator/) |
| **23** | **Exam Evaluation** | Online exam scoring pipeline (MCQ, Written, Programming test cases) | **Template Method** | [`Batch_23/OnlineExamEvaluationProcess/`](Batch_23/OnlineExamEvaluationProcess/) |
| **23** | **Discount Engine** | Tier-based, bulk, and seasonal discount calculation strategies | **Strategy** | [`Batch_23/SmartDiscountCalculationSystem/`](Batch_23/SmartDiscountCalculationSystem/) |
| **22** | **Raven Board** | King's Landing raven announcement broadcast to Great Houses | **Observer** | [`Batch_22/KingsLandingRavenBoard/`](Batch_22/KingsLandingRavenBoard/) |
| **22** | **Finance Dashboard** | Real-time stock ticker & exchange rate streaming to analytics widgets | **Observer** | [`Batch_22/FinancialDashboard/`](Batch_22/FinancialDashboard/) |
| **22** | **Hospital Visit** | Patient clinic admission, doctor triage, billing, and pharmacy clearance | **Mediator + State** | [`Batch_22/HospitalVisitSimulator/`](Batch_22/HospitalVisitSimulator/) |
| **22** | **SaaS Subscription**| Subscription lifecycle (Trial, Active, Paused, Grace Period, Cancelled) | **State** | [`Batch_22/BrainSupportSubscription/`](Batch_22/BrainSupportSubscription/) |
| **22** | **Smart Hub** | Central automation hub mediating motion sensors, sirens, and smart locks | **Mediator** | [`Batch_22/SmartHomeAutomationHub/`](Batch_22/SmartHomeAutomationHub/) |
| **21** | **Traffic Light** | Intersection signal controller cycling Red $\to$ Green $\to$ Yellow | **State** | [`Batch_21/TrafficLight/`](Batch_21/TrafficLight/) |
| **21** | **E-commerce Pay** | Multi-channel payment strategy (BKash, Nagad, Credit Card, Crypto) | **Strategy** | [`Batch_21/EcommercePayment/`](Batch_21/EcommercePayment/) |
| **21** | **Stock Trading** | Stock price fluctuation alerts dispatched to registered investor observers | **Observer** | [`Batch_21/StockTrading/`](Batch_21/StockTrading/) |
| **21** | **Hippo Order** | Food delivery tracking (Placed $\to$ Confirmed $\to$ Shipped $\to$ Delivered) | **State** | [`Batch_21/HungryHippoOrder/`](Batch_21/HungryHippoOrder/) |
| **21** | **Bank Notifications**| Multi-channel transaction dispatch (SMS, Email, WhatsApp, App Push) | **Observer + Strategy** | [`Batch_21/BankingNotification/`](Batch_21/BankingNotification/) |

---

## ⚡ How to Compile & Run Any Solution

Each folder is completely self-contained. Navigate to any problem directory to compile and run:

```bash
# Example: Batch 23 Adaptive Task Scheduler (Strategy)
cd Batch_23/AdaptiveTaskScheduler
javac *.java
java Main

# Example: Batch 23 Hospital ER Coordinator (Mediator)
cd Batch_23/HospitalEmergencyRoomCoordinator
javac *.java
java Main

# Example: Batch 21 Traffic Light Controller (State)
cd Batch_21/TrafficLight
javac *.java
java Main
```
