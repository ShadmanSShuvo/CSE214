# CSE 214: Sessional Offline Assignments

This directory contains the four major sessional offline assignments completed for **BUET CSE 214: Software Engineering & Object-Oriented Design Patterns Sessional** (Student ID: `2305025`).

---

## 📋 Offline Assignments Overview

| Assignment | Domain & Application | Core Patterns / Tools | Submission Directory | Status |
| :---: | :--- | :--- | :--- | :---: |
| **[Offline 1](Offline_1_Creational_FoodFlow/)** | **FoodFlow**: Restaurant Ordering & Menu Engine | **Builder**, **Factory Method**, **Singleton** | [`2305025/`](Offline_1_Creational_FoodFlow/2305025/) | **Completed** |
| **[Offline 2](Offline_2_Structural_SmartHome/)** | **SmartHome**: IoT Device, Room & Home Automation Hub | **Composite**, **Decorator** | [`2305025/`](Offline_2_Structural_SmartHome/2305025/) | **Completed** |
| **[Offline 3](Offline_3_Behavioral_BDAlert_ResultCoord/)** | **BDAlert** (Disaster Warning) & **BUET Result Publication** | **Observer**, **Mediator** | [`2305025/`](Offline_3_Behavioral_BDAlert_ResultCoord/2305025/) | **Completed** |
| **[Offline 4](Offline_4_Load_Testing_JMeter/)** | **Performance & Concurrency Profiling** on Web Endpoints | **Apache JMeter 5.6+**, Python | [`2305025/`](Offline_4_Load_Testing_JMeter/2305025/) | **Completed** |

---

## 🔍 Detailed Assignment Breakdown

### 🍔 Offline 1: FoodFlow (Creational Design Patterns)
- **Problem Formulation:** Design an enterprise food ordering and bill generation application supporting customizable menu items, multi-channel delivery (Pickup / Delivery), varied payment methods (Cash, Card, Digital Wallet), scheduled deliveries, discount coupons, and dynamic loyalty point redemption.
- **Architectural Solution:**
  - **Builder Pattern:** Used for complex immutable `Order` and `OrderItem` construction, preventing telescoping constructor anti-patterns and enforcing rigorous business validations (e.g. mandatory delivery address when `DeliveryType == DELIVERY`, positive item quantities).
  - **Factory Method:** Dynamic creation of delivery handlers and receipt formats.
  - **Singleton Pattern:** Thread-safe menu catalog caching and pricing configuration.
- **Directory Structure:**
  - `2305025/`: Official student submission package with automated test harness, domain models, and menu data.
  - `Starter_Template/`: Course starter template skeleton provided by instructors.

---

### 🏠 Offline 2: SmartHome Automation Hub (Structural Design Patterns)
- **Problem Formulation:** NexaHome requires an automation system to control smart home entities uniformly at individual device, room, and whole-house scales. The system must support dynamic runtime upgrades with stacking capabilities and power management policies.
- **Architectural Solution:**
  - **Composite Pattern:** Uniform `SmartDevice` component interface shared by leaf devices (`SmartLight`, `SmartThermostat`, `SmartSpeaker`) and composite structures (`Room`, `Home`). Enables recursive traversal for status reporting, activation, and aggregate power computation.
  - **Decorator Pattern (Device-Level):** Stackable dynamic behavior modification:
    - `AccessRestricted`: PIN-protected security wrapper ignoring commands when locked.
    - `TimerControlled`: Automatic countdown shutoff mechanism.
    - `PowerThrottled`: Hard cap on power consumption for energy conservation.
  - **Decorator Pattern (Room & Home-Level):**
    - `EcoMode`: Dynamically sheds devices in reverse installation order when aggregate room/home power exceeds a defined budget.
    - `GuestMode`: Silently restricts operation of prohibited device types for guests while isolating authorized controls.
- **Verification Demos:** Demos A through F proving composition over inheritance, decorator stacking order sensitivity, and uniform tree manipulation.
- **Directory Structure:** Official evaluated submission in `2305025/`, iterative variant `fixed-after-IJ.java`, and assignment specifications (`SmartHome_Assignment.docx`, `.md`, `.pdf`).

---

### 🚨 Offline 3: Disaster Alert & Result Publication (Behavioral Design Patterns)
- **Task 1 — BD Alert (Observer Design Pattern):**
  - **Domain:** National disaster alerting infrastructure broadcasting real-time emergency bulletins (`EARTHQUAKE`, `FLOOD`, `FIRE`, etc.).
  - **Mechanism:** Citizen observers register and dynamically subscribe/unsubscribe to specific hazard categories. Category subjects push notifications strictly to current subscribers, preventing leak of past alerts to newly attached subscribers.
- **Task 2 — BUET Final Result Publication (Mediator Design Pattern):**
  - **Domain:** Multi-stage graduation result processing involving Controller of Examinations, Department Office, Directorate of Students' Welfare (DSW), and graduating Students.
  - **Mechanism:** Decouples the 4 colleague entities through `ResultProcessingCoordinator`. Enforces strict state machines: Departmental clearance $\to$ Controller office order $\to$ DSW testimonial $\to$ Official certificate & transcript issuance.
- **Directory Structure:** Official evaluated submission in `2305025/` (modular `alert/` and `result/` packages) and formatted assignment spec (`CSE-214_Offline-3_Formatted.pdf`).

---

### 📊 Offline 4: Performance & Load Testing (Apache JMeter)
- **Problem Formulation:** Subject a live production web service (`LoadLab Server`) to comprehensive stress and load testing, profiling latency, throughput, error percentages, and thread contention under varying traffic loads.
- **Methodology & Key Profiles:**
  - **Low Load Profile:** 50 concurrent threads, 100s ramp-up period, 5 sequential target endpoints.
  - **High Load Profile:** 100 concurrent threads, 100s ramp-up period, 5 sequential target endpoints.
  - **Tested Endpoints:** Home Page (`GET /`), Notice Board (`GET /notices`), Course Catalogue (`GET /courses`), User Authentication (`POST /login`), Bandwidth Streaming (`GET /api/download/256`).
  - **Analysis:** Duration assertions, latency percentiles, error rate analysis, and automated HTML dashboard generation.
- **Key Deliverables:** Official submission in `2305025/` containing test plans (`2305025_testPlan.jmx`), comprehensive assessment report (`.docx` & `.md`), metric logs (`result/`), and HTML report dashboards (`report1/`, `report2/`).
