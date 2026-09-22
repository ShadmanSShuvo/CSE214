# Online 1: Creational Design Patterns

**Course:** BUET CSE 214: Software Engineering & Object-Oriented Design Patterns Sessional  
**Focus:** Creational Design Patterns across Batches 21, 22, and 23  

---

## 🎯 Syllabus & Pattern Coverage

Online 1 tests patterns governing object creation mechanisms. The goal is to encapsulate concrete class instantiation, decoupling clients from specific implementations:
- **Factory Method:** Defines an interface for creating a single product; subclasses decide concrete product type.
- **Abstract Factory:** Provides an interface for creating families of related or dependent products.
- **Builder:** Separates the construction of complex objects from their internal representations.
- **Singleton:** Enforces a single instance per class with a global point of access.

---

## 📊 Problem & Solution Index

| Batch | Section | Problem Domain | Applied Pattern | Directory Location |
| :---: | :---: | :--- | :--- | :--- |
| **23** | **A1** | Multi-Platform GUI Theme Engine (Light / Dark UI Suites) | **Abstract Factory** | [`Batch_23/A1_ThemeAbstractFactory/`](Batch_23/A1_ThemeAbstractFactory/) |
| **23** | **B2** | Enterprise Report Generation Pipeline (PDF, Excel, HTML) | **Factory Method** | [`Batch_23/B2_ReportProcessorFactory/`](Batch_23/B2_ReportProcessorFactory/) |
| **23** | **C1** | Centralized Compliance & Security Audit Logger | **Singleton** | [`Batch_23/C1_AuditLoggerSingleton/`](Batch_23/C1_AuditLoggerSingleton/) |
| **22** | **A1** | Logistics Freight Delivery System (Road, Sea, Air) | **Factory Method** | [`Batch_22/A1_TransportFactory/`](Batch_22/A1_TransportFactory/) |
| **22** | **A2** | Custom Holiday Travel Package Booking System | **Builder** | [`Batch_22/A2_HolidayPackageBuilder/`](Batch_22/A2_HolidayPackageBuilder/) |
| **22** | **B1** | Multi-Threaded Application Event Logger | **Singleton** | [`Batch_22/B1_LoggerSingleton/`](Batch_22/B1_LoggerSingleton/) |
| **22** | **B2** | Multi-Channel Notification Dispatcher (SMS, Email, Push) | **Factory Method** | [`Batch_22/B2_NotificationFactory/`](Batch_22/B2_NotificationFactory/) |
| **22** | **C1** | Custom Modular Bicycle Configuration System | **Builder** | [`Batch_22/C1_BicycleBuilder/`](Batch_22/C1_BicycleBuilder/) |
| **22** | **C2** | Shared Game Settings & Runtime State Manager | **Singleton** | [`Batch_22/C2_GameConfigSingleton/`](Batch_22/C2_GameConfigSingleton/) |
| **21** | **A1** | Document File Processor (.docx, .pdf, .txt) | **Factory Method** | [`Batch_21/A1_DocumentProcessor/`](Batch_21/A1_DocumentProcessor/) |
| **21** | **A2** | Computer Hardware Assembler (WorkPro vs. LiteMax) | **Abstract Factory** | [`Batch_21/A2_ComputerAbstractFactory/`](Batch_21/A2_ComputerAbstractFactory/) |
| **21** | **B**  | Restaurant Meal Plan Assembly (Bengali vs. Chinese) | **Builder** | [`Batch_21/B_MealBuilder/`](Batch_21/B_MealBuilder/) |
| **21** | **C1** | E-commerce Payment Gateway Integration | **Factory Method** | [`Batch_21/C1_PaymentFactory/`](Batch_21/C1_PaymentFactory/) |
| **21** | **C2** | Formal & Informal Document Creator (Letters & Resumes) | **Abstract Factory** | [`Batch_21/C2_DocumentAbstractFactory/`](Batch_21/C2_DocumentAbstractFactory/) |

---

## ⚡ How to Compile & Run Any Solution

Each folder is completely self-contained. Navigate to any problem directory to compile and run:

```bash
# Example: Batch 23 A1 (Theme Abstract Factory)
cd Batch_23/A1_ThemeAbstractFactory
javac *.java
java Main

# Example: Batch 22 A2 (Holiday Package Builder)
cd Batch_22/A2_HolidayPackageBuilder
javac *.java
java Main

# Example: Batch 21 A2 (Computer Abstract Factory)
cd Batch_21/A2_ComputerAbstractFactory
javac *.java
java Main
```
