# Batch 21 - Section A1: Document Processor

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Factory Method Pattern
- **Language:** Java

---

## 1. Problem Statement

You are developing a document editor that supports 3 types of file formats: `.docx`, `.pdf`, and `.txt`. To handle each file type, you need different document processors. All document processors implement an interface called `DocumentProcessor`, which contains two methods:
- `loadDocument(String fileName)`
- `saveDocument(String fileName)`

Given a file name, your system must recognize the file type and use the appropriate document processor to load and save the file. The output will be two messages showing that the file was loaded and saved.

---

## 2. Design Pattern & Architecture

### Why Factory Method?
The client application needs to instantiate different `DocumentProcessor` instances dynamically based on file extensions without coupling the business logic directly to concrete processor classes (`DocxProcessor`, `PdfProcessor`, `TxtProcessor`).

```
           +--------------------+
           |  DocumentProcessor | <--------------------+
           +--------------------+                      |
           | +loadDocument()    |                      |
           | +saveDocument()    |                      |
           +--------------------+                      |
                     ^                                 |
       +-------------+-------------+                   | creates
       |             |             |                   |
+---------------+ +--------------+ +--------------+    |
| DocxProcessor | | PdfProcessor | | TxtProcessor |    |
+---------------+ +--------------+ +--------------+    |
                                                       |
                                  +--------------------------+
                                  | DocumentProcessorFactory |
                                  +--------------------------+
                                  | +createProcessor(file)   |
                                  +--------------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product Interface** | `DocumentProcessor` | Common contract defining `loadDocument` and `saveDocument` operations. |
| **Concrete Products** | `DocxProcessor`, `PdfProcessor`, `TxtProcessor` | Implements loading/saving routines tailored to specific file formats. |
| **Creator / Factory** | `DocumentProcessorFactory` | Parses the file extension and instantiates the correct concrete processor. |
| **Client** | `A1_DocumentProcessorFactory` | Interacts solely with `DocumentProcessor` and `DocumentProcessorFactory`. |

---

## 3. Solution Walkthrough

1. **Interface Contract (`DocumentProcessor`)**: Defines the unified abstraction for handling any document format.
2. **Specialized Processors**:
   - `DocxProcessor`: Prints `Loading DOCX document: <file>` and `Saving DOCX document: <file>`.
   - `PdfProcessor`: Prints `Loading PDF document: <file>` and `Saving PDF document: <file>`.
   - `TxtProcessor`: Prints `Loading TXT document: <file>` and `Saving TXT document: <file>`.
3. **Factory Method (`createProcessor`)**:
   - Extracts the substring after the final period (`.`).
   - Normalizes to lowercase and switches over supported formats.
   - Throws `IllegalArgumentException` for unsupported file extensions.
4. **Client Invariant**: Adding a new format (e.g., `.xlsx` or `.md`) only requires adding a new processor class and updating the factory, leaving existing processor classes untouched.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac A1_DocumentProcessorFactory.java
java A1_DocumentProcessorFactory
```

### Sample Output
```
Loading DOCX document: report.docx
Saving DOCX document: report.docx
Loading PDF document: invoice.pdf
Saving PDF document: invoice.pdf
Loading TXT document: notes.txt
Saving TXT document: notes.txt
```
