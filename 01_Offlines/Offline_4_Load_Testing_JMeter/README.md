# Offline 4: Performance & Load Testing with Apache JMeter

**Course:** BUET CSE 214: Software Engineering Sessional
**Assignment:** Offline 4 (Performance & Concurrency Load Testing)
**Student ID:** `2305025`
**Tooling:** Apache JMeter 5.6.3, Python 3

---

## 🎯 Assignment Objectives

Offline 4 investigates software quality engineering, system scalability, and performance degradation under concurrent user traffic. Students benchmark a live remote application (`LoadLab Server`) by constructing non-trivial JMeter test plans, executing multi-threaded load profiles, and conducting quantitative bottleneck analysis.

---

## 🌐 Target Server & Endpoints Under Test

The workload script targets the remote LoadLab server across 5 distinct endpoint profiles:

| # | Endpoint | Method | Payload / Dynamics | Primary System Stress |
| :-: | :--- | :---: | :--- | :--- |
| **1** | `/` | `GET` | Static Home Page (~2 KB) | Baseline socket I/O & TCP connection overhead |
| **2** | `/notices` | `GET` | Dynamic synthetic delay (100–400 ms) | Thread pool scheduling & latency variance |
| **3** | `/courses` | `GET` | Heavy HTML table payload (~44 KB) | CPU serialization & server-side DOM rendering |
| **4** | `/login` | `POST` | Form authentication with session cookies | State management, session tokens, HTTP 303 redirection |
| **5** | `/api/download/256` | `GET` | 256 KB binary stream download | Network interface card (NIC) bandwidth & socket drain time |

---

## ⚙️ Load Test Profiles & Configuration

Two workload conditions were executed to observe scalability curves:
1. **Low Load Profile:**
   - Number of Threads (Users): **50**
   - Ramp-Up Period: **100 seconds**
   - Loop Count: **1**
2. **High Load Profile:**
   - Number of Threads (Users): **100**
   - Ramp-Up Period: **100 seconds**
   - Loop Count: **1**

### Core JMeter Elements Applied:
- **`HTTP Request Defaults`**: Centralized host (`103.94.135.91`) and port (`8080`).
- **`HTTP Cookie Manager`**: Automatically stores and passes session cookies across requests (critical for `/login`).
- **`Recording Controller`**: Segregates sample transactions per endpoint.
- **`Duration Assertion`**: Verifies whether endpoints respond within specified response time budgets.
- **Listeners:** View Results Tree, Summary Report, Aggregate Report, Graph Results.

---

## 📈 Key Empirical Results (50 vs. 100 Samples)

| Endpoint | Samples | Min (ms) | Max (ms) | Avg (ms) | Throughput | Error % |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **Home Page (`/`)** | 50 | 22 | 1046 | 134.5 | 4.22 req/s | 10.0% |
| | 100 | 29 | 526 | 79.6 | 7.16 req/s | 5.0% |
| **Notice Board (`/notices`)** | 50 | 144 | 1396 | 427.1 | 4.22 req/s | 80.0% |
| | 100 | 146 | 426 | 297.1 | 7.16 req/s | 65.0% |
| **Course Catalogue (`/courses`)**| 50 | 227 | 529 | 302.6 | 4.22 req/s | 60.0% |
| | 100 | 237 | 726 | 354.6 | 7.16 req/s | 95.0% |
| **User Login (`/login`)** | 50 | 126 | 268 | 177.9 | 4.22 req/s | 10.0% |
| | 100 | 160 | 865 | 294.7 | 7.16 req/s | 60.0% |
| **Data API (`/api/download/256`)**| 50 | 60 | 153 | 79.9 | 4.22 req/s | 0.0% |
| | 100 | 76 | 318 | 143.9 | 7.16 req/s | 5.0% |

---

## 📁 Subdirectory Map

| Directory / File | Description |
| :--- | :--- |
| **[`2305025/`](2305025/)** | **Official evaluated submission package** containing test plans, CSV results, and formal assessment reports. |
| **[`JMeter-Practice/`](JMeter-Practice/)** | Experimental workbench with test plan variations and HTML dashboards. |
| **[`JMeter-Practice/automation_and_drafts/`](JMeter-Practice/automation_and_drafts/)** | Automated Python test runner (`run_load_test.py`), intermediate certificates, and drafts. |
| **[`CSE214-offline-4-load-testing.pdf`](CSE214-offline-4-load-testing.pdf)** | Formal course specification and evaluation criteria. |
| **[`jmeter-slide.pdf`](jmeter-slide.pdf)** | Lecture slides on software performance testing and Apache JMeter. |

---

## 🚀 Running JMeter from CLI

To execute a test plan in non-GUI (headless) mode and generate an HTML report:
```bash
jmeter -n -t 2305025/2305025_testPlan.jmx -l result.jtl -e -o html_report/
```
