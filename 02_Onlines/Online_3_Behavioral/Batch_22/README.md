# Online 3 (Behavioral Patterns): Batch 22

**Batch:** CSE 22 (BUET)  
**Exam Focus:** Behavioral Design Patterns  

---

## 📋 Problem & Solution Index

| Problem | Domain Scenario | Applied Pattern | Directory |
| :--- | :--- | :--- | :--- |
| **Kings Landing Raven Board**| Westeros Announcement Network: King's Landing raven messaging dispatched to subscribed Great Houses. | **Observer** | [`KingsLandingRavenBoard/`](KingsLandingRavenBoard/) |
| **Financial Dashboard** | Market Analytics: Real-time currency and equity ticker streams pushing dynamic data to charts and portfolio monitors. | **Observer** | [`FinancialDashboard/`](FinancialDashboard/) |
| **Hospital Visit Simulator** | Clinic Consultation Workflow: Coordinated interactions between Patient, Receptionist, Doctor, Laboratory, and Billing. | **Mediator + State** | [`HospitalVisitSimulator/`](HospitalVisitSimulator/) |
| **Brain Support Subscription**| SaaS Subscription Manager: Managing account states across Trial, Active, Grace Period, and Suspended tiers. | **State** | [`BrainSupportSubscription/`](BrainSupportSubscription/) |
| **Smart Home Automation Hub** | Central IoT Hub: Coordinating motion detectors, door sensors, smart locks, and alarms without direct device coupling. | **Mediator** | [`SmartHomeAutomationHub/`](SmartHomeAutomationHub/) |

---

## ⚡ How to Compile & Run
```bash
# Example: Financial Dashboard Observer
cd FinancialDashboard
javac *.java
java Main
```
