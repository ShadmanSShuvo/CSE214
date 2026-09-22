# Offline 4: Official Submission Package (Student ID: 2305025)

**Course:** BUET CSE 214: Software Engineering Sessional  
**Assignment:** Offline 4 (Performance & Load Testing using Apache JMeter)  
**Author:** Shadman S. Shuvo (`2305025`)  

---

## 📦 Submission Deliverables

```
2305025/
├── 2305025_testPlan.jmx       # Validated Apache JMeter test plan XML
├── 2305025_Assessment.docx   # Formal evaluation and bottleneck report (Word document)
├── 2305025_Assessment.md     # Markdown transcription of evaluation metrics and report
└── result/
    ├── 2305025_result1.csv   # Metric log: Profile 1 (50 concurrent threads, 100s ramp-up)
    └── 2305025_result2.csv   # Metric log: Profile 2 (100 concurrent threads, 100s ramp-up)
```

---

## 📊 Summary of Assessment Metrics

Detailed qualitative and quantitative explanations are available in [`2305025_Assessment.md`](2305025_Assessment.md).

### Workload Throughput & Transfer Rates
| Workload Condition | Total Samples | Total Duration | Average Throughput | Download Data Rate | Upload Data Rate |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **Low Load (50 Threads)** | 50 | 2.36 s | **21.20 req/s** | **1,948.71 KB/s** | **4.04 KB/s** |
| **High Load (100 Threads)**| 100 | 1.40 s | **71.63 req/s** | **6,577.83 KB/s** | **13.68 KB/s** |

---

## ⚡ How to Inspect & Re-run Test Plans

### 1. Launch in JMeter GUI
```bash
jmeter -t 2305025_testPlan.jmx
```

### 2. Run Headless Execution with Output Dashboard
```bash
jmeter -n -t 2305025_testPlan.jmx -l run_output.csv -e -o dashboard_html/
```
Once execution completes, open `dashboard_html/index.html` to review graphical latency graphs and response time percentiles.
