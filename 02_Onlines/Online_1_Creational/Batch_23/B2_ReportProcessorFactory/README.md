# Batch 23 - Section B2: Extensible Report Processor

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns (January 2026)
- **Pattern:** Factory Method Pattern
- **Language:** Java

---

## 1. Problem Statement

You are developing a report-processing application that supports **PDF**, **Word**, and **HTML** reports. All report types implement a common interface `Report` with methods `open()` and `generate()`.

The general report-processing steps are identical for all report types:
1. Create the report object.
2. Open the report.
3. Generate the report.
4. Display a completion message.

However, different report processors should decide which concrete report object is created.

**Task:** Implement the system so that the common processing steps are defined once, while specialized processor classes create the required report type.
- The general processor must not directly instantiate PDF, Word, or HTML reports.
- Adding another report type should not require changing the common processing steps.
- Simple print statements are sufficient.

---

## 2. Design Pattern & Architecture

### Why Factory Method?
The processing algorithm exhibits an invariant template workflow (Create -> Open -> Generate -> Notify), but the exact product being processed varies by format. The **Factory Method Pattern** delegates instantiation to subclasses via `protected abstract Report createReport()`, allowing new formats (e.g., `ExcelReport`) to be added without touching the base processor workflow (adhering strictly to the Open-Closed Principle).

```
               +----------------------+
               |        Report        | <------------------------+
               +----------------------+                          |
               | +open()              |                          |
               | +generate()          |                          |
               +----------------------+                          |
                          ^                                      |
         +----------------+----------------+                     | creates
         |                |                |                     |
   +-----------+    +------------+   +------------+              |
   | PdfReport |    | WordReport |   | HtmlReport |              |
   +-----------+    +------------+   +------------+              |
                                                                 |
               +-----------------------------+                   |
               |       ReportProcessor       |                   |
               +-----------------------------+                   |
               | # createReport(): Report    | ------------------+
               | + processReport(): void     | (defines invariant 4-step workflow)
               +-----------------------------+
                              ^
         +--------------------+--------------------+
         |                    |                    |
+--------------------+ +---------------------+ +---------------------+
| PdfReportProcessor | | WordReportProcessor | | HtmlReportProcessor |
+--------------------+ +---------------------+ +---------------------+
| createReport() ->  | | createReport() ->   | | createReport() ->   |
|   return PdfReport | |   return WordReport | |   return HtmlReport |
+--------------------+ +---------------------+ +---------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product Interface** | `Report` | Declares report operations `open()` and `generate()`. |
| **Concrete Products** | `PdfReport`, `WordReport`, `HtmlReport`, `ExcelReport` | Implements format-specific file handling and content generation. |
| **Abstract Creator** | `ReportProcessor` | Declares `createReport()` and defines the common 4-step `processReport()` workflow. |
| **Concrete Creators** | `PdfReportProcessor`, `WordReportProcessor`, `HtmlReportProcessor`, `ExcelReportProcessor` | Overrides `createReport()` to instantiate the format-specific report. |
| **Client / Driver** | `B2Main` | Executes jobs polymorphically via `ReportProcessor` references. |

---

## 3. Solution Walkthrough

1. **Common Processing Steps (`processReport`)**:
   ```java
   public void processReport() {
       System.out.println("Starting report processing workflow...");
       Report report = createReport(); // Step 1: Create report object polymorphically
       report.open();                 // Step 2: Open report
       report.generate();             // Step 3: Generate report
       System.out.println("Report processing completed successfully.\n"); // Step 4: Completion message
   }
   ```
2. **Factory Method Hook**:
   `protected abstract Report createReport();` enables subclasses to plug in their concrete report object without modifying the common workflow.
3. **Specialized Processors**:
   - `PdfReportProcessor` returns `new PdfReport()`.
   - `WordReportProcessor` returns `new WordReport()`.
   - `HtmlReportProcessor` returns `new HtmlReport()`.
4. **Open-Closed Principle Demonstration**:
   - An `ExcelReport` and `ExcelReportProcessor` are included to prove that adding a 4th format requires zero modifications to `ReportProcessor` or existing report classes.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac B2Main.java
java B2Main
```

### Sample Output
```
==================================================
  CSE 214 Online: Creational Pattern - B2 Solution
  Pattern: Factory Method Pattern
==================================================

--- 1. Client Requesting PDF Report ---
Starting report processing workflow...
[PDF Report] Opening PDF document stream...
[PDF Report] Generating layout, vector graphics, and paginated content.
Report processing completed successfully.

--- 2. Client Requesting Word Report ---
Starting report processing workflow...
[Word Report] Opening DOCX document buffer...
[Word Report] Generating paragraphs, tables, and XML structures.
Report processing completed successfully.

--- 3. Client Requesting HTML Report ---
Starting report processing workflow...
[HTML Report] Initializing HTML DOM template...
[HTML Report] Rendering semantic HTML5 tags and CSS stylesheets.
Report processing completed successfully.

--- 4. Future Extension: Client Requesting Excel Report ---
Starting report processing workflow...
[Excel Report] Opening spreadsheet workbook...
[Excel Report] Generating worksheets, rows, cells, and formula calculations.
Report processing completed successfully.
```
