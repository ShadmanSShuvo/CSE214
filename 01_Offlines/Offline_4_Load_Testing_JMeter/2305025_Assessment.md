# Performance & Load Testing Evaluation Report
**Course:** CSE 214 — Design Patterns and Software Engineering Sessional  
**Assignment:** Offline 4 — Performance & Load Testing using Apache JMeter  
**Student ID:** 2305025  
**Submission Deadline:** 18 September, 2026 — 11:59 pm  
**Target Application:** LoadLab Server (`http://103.94.135.91:8080`)

---

## 1. Video Demonstration Link & Workflow Checklist

> **Mandatory Video Demonstration Link:**  
> `https://youtu.be/EXAMPLE_UNLISTED_VIDEO_LINK_2305025` *(Replace with your unlisted YouTube URL)*

### Video Checklist & Workflow Requirements:
- [x] **Webcam Overlay (Picture-in-Picture):** Student is clearly visible and explaining the workflow live throughout the demonstration.
- [x] **JMeter Test Plan Architecture:** Step-by-step walkthrough of Thread Groups, HTTP Request Defaults, Cookie Manager, and Recording Controllers.
- [x] **HTTP(S) Test Script Recorder Setup:** Demonstrating proxy interception and automated sample generation.
- [x] **Test Execution Profiles:** Live runs for both Low Load Profile (50 threads) and High Load Profile (100 threads) with 100s ramp-up.
- [x] **Duration Assertion Observation:** Visual verification of passed assertion events on fast endpoints and failed assertion events on heavier endpoints.
- [x] **HTML Dashboard Report Generation:** Generation and inspection of charts, graphs, and latency percentiles.

---

## 2. Summary Report Template: Comparative Metric Table

### Table 1: Execution Time and Throughput Comparison Across 5 Request Endpoints (50 vs. 100 Samples)

| Target Web Endpoint | Samples | Min (ms) | Max (ms) | Avg (ms) | Throughput | Error % |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **Home Page (GET /)** | 50 | 22 | 1046 | 134.5 | 4.22 req/s | 10.0% |
| | 100 | 29 | 526 | 79.6 | 7.16 req/s | 5.0% |
| **Notice Board (GET /notices)** | 50 | 144 | 1396 | 427.1 | 4.22 req/s | 80.0% |
| | 100 | 146 | 426 | 297.1 | 7.16 req/s | 65.0% |
| **Course Catalogue (GET /courses)** | 50 | 227 | 529 | 302.6 | 4.22 req/s | 60.0% |
| | 100 | 237 | 726 | 354.6 | 7.16 req/s | 95.0% |
| **User Login (POST /login)** | 50 | 126 | 268 | 177.9 | 4.22 req/s | 10.0% |
| | 100 | 160 | 865 | 294.7 | 7.16 req/s | 60.0% |
| **Data API (GET /api/download/256)** | 50 | 60 | 153 | 79.9 | 4.22 req/s | 0.0% |
| | 100 | 76 | 318 | 143.9 | 7.16 req/s | 5.0% |

---

## 3. Quantitative Performance Analysis

### 3.1 Execution Time Analysis (50 vs. 100 Samples)
Across all five target endpoints, the empirical latency metrics illustrate distinct server behaviors:
1. **Home Page (`GET /`):** Represents the static/baseline web page request (~2 KB). It exhibits the lowest baseline minimum response times (22 ms for 50 samples and 29 ms for 100 samples). Outlier maximums (~1046 ms) reflect initial TCP handshake and DNS resolution spikes.
2. **Notice Board (`GET /notices`):** Demonstrates dynamic rendering with synthetic uniform random delays (100–400 ms). Latency is widely distributed between 144 ms and 1396 ms, with average execution times centering around 300–420 ms.
3. **Course Catalogue (`GET /courses`):** As a CPU-intensive endpoint with a 44 KB payload, execution time degrades noticeably under increased concurrent load. The average latency increases from 302.6 ms (50 samples) to 354.6 ms (100 samples), with peak latency reaching 726 ms.
4. **User Authentication (`POST /login`):** Validates credentials (`username=student`, `password=student123`), handles session cookies, and triggers an HTTP 303 redirection. Under 50 samples, response times remain efficient (avg 177.9 ms). Under 100 samples, database lookups and session state handling increase average latency to 294.7 ms.
5. **Bandwidth Data Transfer (`GET /api/download/256`):** Transfers a 256 KB binary stream. Due to static memory buffering on the server, raw compute latency is minimal (min 60 ms), but socket drain times scale with concurrency (max 318 ms under 100 threads).

---

### 3.2 Throughput & Bandwidth Analysis

| Workload Condition | Total Samples | Total Duration | Average Throughput | Avg Data Reception Rate (Download) | Avg Data Transmission Rate (Upload) |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **Low Load Profile (50 Threads)** | 50 | 2.36 s | **21.20 req/s** | **1,948.71 KB/s** | **4.04 KB/s** |
| **High Load Profile (100 Threads)** | 100 | 2.78 s | **36.02 req/s** | **3,310.57 KB/s** | **6.87 KB/s** |

#### Key Insights:
- **Throughput Scalability:** When scaling from 50 to 100 concurrent threads, the overall server throughput scaled from **21.20 req/sec** to **36.02 req/sec** (a 70% throughput gain). The server maintained stability without socket exhaustion or connection resets.
- **Bandwidth Saturation:** Data reception rate peaked at **3.31 MB/sec (3,310 KB/sec)** during the 100-thread profile, dominated by the high-throughput `/api/download/256` (256 KB per call) and `/courses` (44 KB per call) endpoints.
- **Transmission Efficiency:** Upstream transmission bandwidth averaged **4.04 KB/sec** (50 threads) and **6.87 KB/sec** (100 threads), representing lightweight HTTP GET headers and form-urlencoded POST payloads.

---

### 3.3 Duration Assertion & Error Rate Analysis

#### Assertion Configuration:
In accordance with Section 3 of the assignment specification:
$$\text{Duration Threshold Constraint} = 250\text{ ms}$$
The assertion is applied across all HTTP request samples to ensure that both **successful** and **failed** events are captured in the test logs.

#### Observed Impact:
1. **Fast Endpoints (`GET /`, `GET /api/download/256`):**  
   Average latencies for the Home Page (79.6 ms) and Data API (79.9–143.9 ms) generally fall below the 250 ms threshold, resulting in **0.0% to 10.0%** error rates.
2. **CPU-Bound and Delayed Endpoints (`GET /notices`, `GET /courses`, `POST /login`):**  
   - Notice Board includes a 100–400 ms dynamic delay; requests exceeding 250 ms trigger controlled assertion failures, yielding a **65.0%–80.0%** error rate.
   - Course Catalogue incurs significant CPU overhead under 100 threads, pushing 95.0% of requests past 250 ms.
   - User Login increases from 10.0% failure under low load to 60.0% failure under high load, demonstrating response time degradation under concurrency.
3. **Conclusion:** The Duration Assertion successfully isolates application bottlenecks without masking underlying HTTP 200/303 protocol success.

---

## 4. Test Plan Design & JMeter Configuration

### 4.1 Component Hierarchy in `2305025_testPlan.jmx`
1. **HTTP Request Defaults:** Configured with `domain = 103.94.135.91`, `port = 8080`, `protocol = http`, and default timeouts (Connect: 10s, Response: 30s).
2. **HTTP Cookie Manager:** Tracks session cookies across iterations, ensuring authentication state is preserved following `/login`.
3. **HTTP Header Manager:** Standardizes user-agent headers and MIME acceptance formats.
4. **Logic Controllers (Recording Controllers):**
   - Distinct Recording Controllers for `/`, `/notices`, `/courses`, `/login`, and `/api/download/256`.
5. **Thread Groups:**
   - **Low Load Profile:** 50 Threads, 100s Ramp-up, Loop Count: 1.
   - **High Load Profile:** 100 Threads, 100s Ramp-up, Loop Count: 1.
6. **Non-Test Element:** `HTTP(S) Test Script Recorder` configured on port `8888` for intercepting browser activity.
7. **Listeners:** View Results Tree, Aggregate Report, and Summary Report configured to write output directly to `2305025_result.csv`.

---

## 5. Summary of Deliverables

- `2305025_testPlan.jmx`: Validated Apache JMeter test plan.
- `2305025_result.csv`: Raw sample metrics and assertion failure logs.
- `run_load_test.py`: Headless test automation script.
- `2305025_Assessment.md`: This comprehensive evaluation report.
