# CSE 214: Lab Online Exams Archive

This directory contains the complete archive of problem statements, architectural design analyses, and verified Java solutions for the **BUET CSE 214 Sessional Lab Online Exams** spanning **Batches 19, 21, 22, and 23**.

---

## 🎯 Online Exams Syllabus & Structure

The lab online exams evaluate real-time problem-solving, object-oriented modeling, and design pattern selection under strict time constraints (typically 30–45 minutes per problem).

```
02_Onlines/
├── Online_1_Creational/     # Online 1: Factory Method, Abstract Factory, Builder, Singleton
├── Online_2_Structural/     # Online 2: Adapter, Bridge, Composite, Decorator
└── Online_3_Behavioral/     # Online 3: Observer, State, Strategy, Mediator, Template Method
```

---

## 📊 Comprehensive Exam Series Index

### 1. [Online 1: Creational Design Patterns](Online_1_Creational/)
Focuses on object instantiation mechanisms, decoupling the client from concrete product classes, managing families of related objects, and step-by-step object assembly.

| Batch | Problem / Section | Domain Scenario | Primary Pattern | Location |
| :---: | :---: | :--- | :--- | :--- |
| **23** | **A1** | Multi-Platform Theme Manager | **Abstract Factory** | [`Online_1_Creational/Batch_23/A1_ThemeAbstractFactory`](Online_1_Creational/Batch_23/A1_ThemeAbstractFactory/) |
| **23** | **B2** | Enterprise Report Processor | **Factory Method** | [`Online_1_Creational/Batch_23/B2_ReportProcessorFactory`](Online_1_Creational/Batch_23/B2_ReportProcessorFactory/) |
| **23** | **C1** | Global Centralized Audit Logger | **Singleton** | [`Online_1_Creational/Batch_23/C1_AuditLoggerSingleton`](Online_1_Creational/Batch_23/C1_AuditLoggerSingleton/) |
| **22** | **A1** | Logistics Multi-Modal Transport System | **Factory Method** | [`Online_1_Creational/Batch_22/A1_TransportFactory`](Online_1_Creational/Batch_22/A1_TransportFactory/) |
| **22** | **A2** | Custom Holiday Package Booking | **Builder** | [`Online_1_Creational/Batch_22/A2_HolidayPackageBuilder`](Online_1_Creational/Batch_22/A2_HolidayPackageBuilder/) |
| **22** | **B1** | Multi-Threaded Application Logger | **Singleton** | [`Online_1_Creational/Batch_22/B1_LoggerSingleton`](Online_1_Creational/Batch_22/B1_LoggerSingleton/) |
| **22** | **B2** | Multi-Channel Notification Dispatcher | **Factory Method** | [`Online_1_Creational/Batch_22/B2_NotificationFactory`](Online_1_Creational/Batch_22/B2_NotificationFactory/) |
| **22** | **C1** | Custom Bicycle Assembly System | **Builder** | [`Online_1_Creational/Batch_22/C1_BicycleBuilder`](Online_1_Creational/Batch_22/C1_BicycleBuilder/) |
| **22** | **C2** | Shared Game Settings & State Manager | **Singleton** | [`Online_1_Creational/Batch_22/C2_GameConfigSingleton`](Online_1_Creational/Batch_22/C2_GameConfigSingleton/) |
| **21** | **A1** | Document Processor (PDF, Word, Text) | **Factory Method** | [`Online_1_Creational/Batch_21/A1_DocumentProcessor`](Online_1_Creational/Batch_21/A1_DocumentProcessor/) |
| **21** | **A2** | Computer Hardware Assembler | **Abstract Factory** | [`Online_1_Creational/Batch_21/A2_ComputerAbstractFactory`](Online_1_Creational/Batch_21/A2_ComputerAbstractFactory/) |
| **21** | **B**  | Restaurant Meal Plan Builder | **Builder** | [`Online_1_Creational/Batch_21/B_MealBuilder`](Online_1_Creational/Batch_21/B_MealBuilder/) |
| **21** | **C1** | E-commerce Payment Gateway Factory | **Factory Method** | [`Online_1_Creational/Batch_21/C1_PaymentFactory`](Online_1_Creational/Batch_21/C1_PaymentFactory/) |
| **21** | **C2** | Formal & Informal Document Creator | **Abstract Factory** | [`Online_1_Creational/Batch_21/C2_DocumentAbstractFactory`](Online_1_Creational/Batch_21/C2_DocumentAbstractFactory/) |

---

### 2. [Online 2: Structural Design Patterns](Online_2_Structural/)
Focuses on composing classes and objects into larger structures while maintaining flexibility, efficiency, and loose coupling.

- **Adapter:** Bridging incompatible interfaces in legacy systems, 3rd party APIs, and hardware devices.
- **Bridge:** Decoupling abstraction from implementation to avoid exponential class hierarchies.
- **Composite:** Building recursive tree hierarchies where leaves and compositions are treated uniformly.
- **Decorator:** Attaching dynamic responsibilities and stacked behaviors to individual objects.
- *Detailed matrix and walkthrough available in [Online 2 Structural Master Guide](Online_2_Structural/README.md).*

---

### 3. [Online 3: Behavioral Design Patterns](Online_3_Behavioral/)
Focuses on algorithms, communication protocols, and assignment of responsibilities between cooperating objects.

- **Observer:** Dynamic event broadcasting (Stock Trading, Traffic Light, Disaster Warning, Banking Notifications).
- **State:** Finite state machine modeling where behavior mutates based on internal state (Order workflows, Return/Refund processes).
- **Strategy:** Runtime interchangeable algorithmic policies (Adaptive Task Scheduling, Dynamic Discount calculation, Payment processing).
- **Mediator:** Centralizing complex many-to-many communication networks (Hospital Emergency Room Coordination, Smart Home Hub).
- **Template Method:** Invariant skeleton algorithms with customizable hook steps (AI Model Evaluation, Exam grading).
- *Detailed matrix and walkthrough available in [Online 3 Behavioral Master Guide](Online_3_Behavioral/README.md).*

---

## ⚡ How to Compile & Run Any Online Solution

Each online problem is housed in an independent, self-contained directory to prevent namespace collisions.

```bash
# General compile and run pattern:
cd 02_Onlines/<Exam_Category>/<Batch>/<Problem_Directory>
javac *.java
java Main
```
