# BUET CSE 214: Software Engineering & Object-Oriented Design Patterns Sessional

**Department of Computer Science and Engineering**  
**Bangladesh University of Engineering and Technology (BUET)**  
**Student ID:** `2305025`  
**Course Code:** CSE 214 | **Course Title:** Software Engineering Sessional / Object-Oriented Programming Language Sessional

---

## 📖 Overview

This repository is a comprehensive academic and practical engineering archive for **CSE 214**, containing:
1. **Four Production-Grade Sessional Offlines**:
   - **Offline 1 (Creational Patterns)**: *FoodFlow* — High-performance food ordering & menu customization engine utilizing the Builder, Factory Method, and Singleton patterns, with interactive CLI and web variants.
   - **Offline 2 (Structural Patterns)**: *SmartHome Automation Hub* — Dynamic IoT device and zone management infrastructure combining Composite and Decorator patterns with runtime constraints (Eco Mode, Guest Mode, Power Throttling, PIN Security).
   - **Offline 3 (Behavioral Patterns)**: *Disaster Alert & Academic Result Coordinator* — Two enterprise implementations featuring Observer Pattern (geographical & categorical emergency broadcasting) and Mediator Pattern (multi-departmental BUET graduation result publication workflow).
   - **Offline 4 (Software Quality & Performance Testing)**: *Apache JMeter Load & Stress Testing* — Quantitative performance evaluation, throughput profiling, concurrency benchmarking (50 vs. 100 threads), latency assertions, and HTML reporting on a production web service.
2. **Exhaustive Lab Online Exam Solutions**:
   - Complete problem specifications, architectural analysis, and verified Java solutions covering Creational, Structural, and Behavioral patterns across **Batches 19, 21, 22, and 23**.
3. **Curated Design Pattern Catalog**:
   - Clean reference implementations, lecture code, single-file runnable demonstrations, and comprehensive exam revision cheat sheets for the Gang of Four (GoF) patterns.
4. **Organized Historical Archives**:
   - Subfolder-partitioned zip archives preserving all assignment distribution bundles and submission iterations.

---

## 🗂️ Repository Architecture

```
CSE214/
├── 01_Offlines/                                  # Major sessional course assignments (Offlines 1 - 4)
│   ├── README.md                                 # Offlines master index & syllabus mapping
│   ├── Offline_1_Creational_FoodFlow/            # FoodFlow restaurant ordering system (Builder, Factory)
│   ├── Offline_2_Structural_SmartHome/           # SmartHome automation hub (Composite, Decorator)
│   ├── Offline_3_Behavioral_BDAlert_ResultCoord/ # BDAlert (Observer) & Result Coordinator (Mediator)
│   └── Offline_4_Load_Testing_JMeter/            # Apache JMeter load, stress & performance profiling
│
├── 02_Onlines/                                   # Sessional lab online exams across batches
│   ├── README.md                                 # Onlines master index & pattern matrix
│   ├── Online_1_Creational/                      # Batches 21, 22, 23 (Factory, Abstract Factory, Builder, Singleton)
│   ├── Online_2_Structural/                      # Batches 19, 21, 22, 23 (Adapter, Bridge, Composite, Decorator)
│   └── Online_3_Behavioral/                      # Batches 21, 22, 23 (Observer, State, Strategy, Mediator, Template)
│
├── 03_Design_Pattern_Catalog/                    # Reference implementations, demos & study resources
│   ├── README.md                                 # Catalog master index & GoF taxonomy
│   ├── Creational/                               # Singleton, Abstract Factory, Builder, Factory Method
│   ├── Structural/                               # Adapter, Bridge, Composite, Decorator
│   ├── Behavioral/                               # Patterns, AdaptiveSorting, standalone_demos
│   └── Study_Guides_and_CheatSheets/             # Exam notes, quick references, problem sets & solutions
│
├── Archives/                                     # Subfolder-partitioned zip backups of all sources
│   ├── README.md                                 # Archive manifest & metadata catalog
│   ├── Offline_1_Creational_FoodFlow/            # FoodFlow starter bundles & submission zips
│   ├── Offline_2_Structural_SmartHome/           # SmartHome distribution zips
│   ├── Offline_3_Behavioral_BDAlert_ResultCoord/ # BDAlert & ResultCoordinator iteration zips
│   ├── Offline_4_Load_Testing_JMeter/            # JMeter reports & test plan packages
│   └── Design_Pattern_Catalog/                   # Catalog practice archives
│
└── .gitignore                                    # Clean ignore rules (Java classes, logs, OS artifacts)
```

---

## 🧭 Quick Navigation Index

### 1. Offline Assignments
| Offline | Topic & Domain | Core Design Patterns / Tools | Submission Folder | Documentation |
| :---: | :--- | :--- | :--- | :--- |
| **Offline 1** | **FoodFlow**: Restaurant Ordering & Menu Engine | **Builder**, **Factory Method**, **Singleton** | [`01_Offlines/Offline_1_Creational_FoodFlow/Submission_2305025`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_1_Creational_FoodFlow/Submission_2305025) | [`README`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_1_Creational_FoodFlow/README.md) |
| **Offline 2** | **SmartHome**: Dynamic IoT Device & Room Hub | **Composite**, **Decorator** | [`01_Offlines/Offline_2_Structural_SmartHome/2305025`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_2_Structural_SmartHome/2305025) | [`README`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_2_Structural_SmartHome/README.md) |
| **Offline 3** | **Disaster Alert** & **Result Coordinator** | **Observer**, **Mediator** | [`01_Offlines/Offline_3_Behavioral_BDAlert_ResultCoord/2305025`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_3_Behavioral_BDAlert_ResultCoord/2305025) | [`README`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_3_Behavioral_BDAlert_ResultCoord/README.md) |
| **Offline 4** | **Load & Stress Testing Web Endpoints** | **Apache JMeter 5.6+**, Python | [`01_Offlines/Offline_4_Load_Testing_JMeter/2305025`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_4_Load_Testing_JMeter/2305025) | [`README`](file:///Users/shuvo/Dev/CSE214/01_Offlines/Offline_4_Load_Testing_JMeter/README.md) |

### 2. Lab Online Exams Matrix
| Exam Series | Focus Area | Batches Covered | Key Patterns Tested | Master Directory |
| :--- | :--- | :---: | :--- | :--- |
| **Online 1** | **Creational Patterns** | Batch 21, 22, 23 | Factory Method, Abstract Factory, Builder, Singleton | [`02_Onlines/Online_1_Creational`](file:///Users/shuvo/Dev/CSE214/02_Onlines/Online_1_Creational) |
| **Online 2** | **Structural Patterns** | Batch 19, 21, 22, 23 | Adapter, Bridge, Composite, Decorator | [`02_Onlines/Online_2_Structural`](file:///Users/shuvo/Dev/CSE214/02_Onlines/Online_2_Structural) |
| **Online 3** | **Behavioral Patterns** | Batch 21, 22, 23 | Observer, State, Strategy, Mediator, Template Method | [`02_Onlines/Online_3_Behavioral`](file:///Users/shuvo/Dev/CSE214/02_Onlines/Online_3_Behavioral) |

### 3. Design Pattern Reference Catalog
- **Creational**: [`Singleton`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Creational/Singleton), [`Abstract Factory`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Creational/AbstractFactory), [`Builder`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Creational/Builder), [`Factory Method`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Creational/Factory)
- **Structural**: [`Adapter`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Structural/Adapter), [`Bridge`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Structural/Bridge), [`Composite`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Structural/Composite), [`Decorator`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Structural/Decorator)
- **Behavioral**: [`Observer`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Behavioral/Patterns/Observer), [`State`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Behavioral/Patterns/State), [`Strategy`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Behavioral/Patterns/Strategy), [`Mediator`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Behavioral/Patterns/Mediator), [`Template Method`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Behavioral/Patterns/TemplateMethod), [`Command`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Behavioral/Patterns/Command)
- **Study Resources**: [`Study Guides and CheatSheets`](file:///Users/shuvo/Dev/CSE214/03_Design_Pattern_Catalog/Study_Guides_and_CheatSheets)

---

## 🛠️ Technology Stack & Requirements

- **Programming Language:** Java 17 / Java 21 (Standard JDK)
- **Performance & Testing Tools:** Apache JMeter 5.6.3, Python 3.10+ (automation scripts)
- **Full-Stack Demos:** Node.js 18+, React 18, Vite, TypeScript, Tailwind CSS
- **Operating Environment:** macOS / Linux / Windows (POSIX-compliant commands)

---

## 🚀 Build, Compilation & Execution

All Java projects and solutions are self-contained and compile cleanly using standard `javac`:

```bash
# 1. Compile and run Offline 1 (FoodFlow Submission)
cd 01_Offlines/Offline_1_Creational_FoodFlow/Submission_2305025
javac src/*.java src/*/*.java
java -cp src Main

# 2. Compile and run Offline 2 (SmartHome Submission)
cd 01_Offlines/Offline_2_Structural_SmartHome/2305025
javac *.java
java SmartHome

# 3. Compile and run Offline 3 Task 1 (Disaster Alert - Observer)
cd 01_Offlines/Offline_3_Behavioral_BDAlert_ResultCoord/2305025/alert
javac *.java
java Main

# 4. Compile and run Offline 3 Task 2 (Result Coordinator - Mediator)
cd 01_Offlines/Offline_3_Behavioral_BDAlert_ResultCoord/2305025/result
javac *.java
java Main

# 5. Compile and run any Lab Online solution (e.g., Batch 23 Gift Shop)
cd 02_Onlines/Online_2_Structural/Batch_23/A1_GiftShop
javac *.java
java Main
```
