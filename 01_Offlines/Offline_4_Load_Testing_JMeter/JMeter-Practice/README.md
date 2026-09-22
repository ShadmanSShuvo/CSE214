# JMeter Practice & Experimental Workbench

This directory houses experimental Apache JMeter test plan configurations, practice thread group designs, exploratory load runs, and generated HTML performance dashboards.

---

## 🗂️ Directory Contents

### 1. Test Plans (`.jmx`)
- **`2305025_TestPlan.jmx` / `2305025-test.jmx`**: Iterative variations of the student load testing test plan.
- **`Graph Results.jmx`, `HTTP Cookie Manager.jmx`, `View Results Tree.jmx`**: Fragment test components demonstrating specific JMeter elements.
- **`testPlan.jmx`, `testPlan_v2.jmx`, `practice1.jmx`**: Incremental trial scripts exploring proxy recording and assertion thresholds.
- **`yourID_testPlan.jmx`**: Assignment base template.

### 2. Automation & Drafts ([`automation_and_drafts/`](automation_and_drafts/))
- **`run_load_test.py`**: Python automation script for launching headless JMeter runs and recording CSV metric outputs.
- **`ApacheJMeterTemporaryRootCA.crt`**: Local SSL/TLS proxy certificate for recording HTTPS traffic.
- **`report2-2305019/`**: Peer comparison metrics and reference dashboards.
- **`draft_2305025_testPlan_copy.jmx`**: Preliminary draft test plan retained for reference.

### 3. Generated HTML Dashboards
- **`2305025_html/`**: Student test run graphical HTML dashboards (`final_html_dashboard-1/`, `final_html_dashboard-2/`).
- **`report1/` & `report2/`**: Standalone HTML reporting bundles visualizing throughput over time, response time percentiles, and active thread counts.

The finalized, evaluated submission is maintained in [`../2305025/`](../2305025/).
