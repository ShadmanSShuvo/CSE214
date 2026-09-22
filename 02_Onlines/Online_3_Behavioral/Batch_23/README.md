# Online 3 (Behavioral Patterns): Batch 23

**Batch:** CSE 23 (BUET)
**Exam Focus:** Behavioral Design Patterns

---

## 📋 Problem & Solution Index

| Problem | Domain Scenario | Applied Pattern | Directory |
| :--- | :--- | :--- | :--- |
| **Adaptive Task Scheduler** | Workload-Sensitive Scheduler: Evaluates incoming queues and dynamically switches between FCFS, Priority, and SJF policies. | **Strategy** | [`AdaptiveTaskScheduler/`](AdaptiveTaskScheduler/) |
| **Automatic AI Model Selector**| Intelligent LLM Gateway: ComplexityAnalyzer routes user queries to FlashMind, CoreMind, or DeepMindX models via an invariant evaluation pipeline. | **Template Method + Strategy** | [`AutomaticAIModelSelectionSystem/`](AutomaticAIModelSelectionSystem/) |
| **E-commerce Return Refund** | Return Processing Pipeline: State transitions across Request Submitted, In Review, Approved, Processing Refund, and Rejected. | **State** | [`EcommerceReturnRefundWorkflow/`](EcommerceReturnRefundWorkflow/) |
| **Hospital ER Coordinator** | Emergency Room Command Center: Central mediator arbitrating urgent requests between Doctors, Pathology Lab, and Radiology Unit. | **Mediator** | [`HospitalEmergencyRoomCoordinator/`](HospitalEmergencyRoomCoordinator/) |
| **Online Exam Evaluation** | Standardized Exam Scoring Engine: Invariant grading skeleton with customizable hooks for MCQ, Written, and Programming tests. | **Template Method** | [`OnlineExamEvaluationProcess/`](OnlineExamEvaluationProcess/) |
| **Smart Discount System** | Dynamic Pricing Engine: Interchangeable pricing algorithms calculating discounts based on order volume, customer loyalty tier, or promo vouchers. | **Strategy** | [`SmartDiscountCalculationSystem/`](SmartDiscountCalculationSystem/) |

---

## ⚡ How to Compile & Run
```bash
# Example: Adaptive Task Scheduler
cd AdaptiveTaskScheduler
javac *.java
java Main
```
