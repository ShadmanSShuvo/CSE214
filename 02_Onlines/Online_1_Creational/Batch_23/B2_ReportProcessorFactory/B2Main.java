// January 2026 CSE 214 - Online on Creational Pattern (Section B2)
// Pattern: Factory Method Pattern
//
// Requirements:
// 1. Common interface Report with methods open() and generate().
// 2. Concrete reports: PDF, Word, and HTML.
// 3. General report-processing steps defined once in an abstract ReportProcessor:
//    - Step 1: Create the report object (via factory method)
//    - Step 2: Open the report
//    - Step 3: Generate the report
//    - Step 4: Display a completion message
// 4. General processor must not directly instantiate concrete reports.
// 5. Specialized processor classes create the required report type.
// 6. Adding new report types must not modify common processing steps (Open-Closed Principle).

// =========================================================================
// 1. PRODUCT INTERFACE
// =========================================================================
interface Report {
    void open();
    void generate();
}

// =========================================================================
// 2. CONCRETE PRODUCTS
// =========================================================================
class PdfReport implements Report {
    @Override
    public void open() {
        System.out.println("[PDF Report] Opening PDF document stream...");
    }

    @Override
    public void generate() {
        System.out.println("[PDF Report] Generating layout, vector graphics, and paginated content.");
    }
}

class WordReport implements Report {
    @Override
    public void open() {
        System.out.println("[Word Report] Opening DOCX document buffer...");
    }

    @Override
    public void generate() {
        System.out.println("[Word Report] Generating paragraphs, tables, and XML structures.");
    }
}

class HtmlReport implements Report {
    @Override
    public void open() {
        System.out.println("[HTML Report] Initializing HTML DOM template...");
    }

    @Override
    public void generate() {
        System.out.println("[HTML Report] Rendering semantic HTML5 tags and CSS stylesheets.");
    }
}

// Example of extensibility (Open-Closed Principle):
// Adding a new report format requires NO change to existing classes or common steps.
class ExcelReport implements Report {
    @Override
    public void open() {
        System.out.println("[Excel Report] Opening spreadsheet workbook...");
    }

    @Override
    public void generate() {
        System.out.println("[Excel Report] Generating worksheets, rows, cells, and formula calculations.");
    }
}

// =========================================================================
// 3. CREATOR (General Processor defining common processing workflow)
// =========================================================================
abstract class ReportProcessor {

    // Factory Method: Decouples creation from the invariant workflow.
    // Subclasses decide which concrete Report to instantiate.
    protected abstract Report createReport();

    // Common invariant processing lifecycle defined ONCE.
    // Must NOT directly instantiate concrete report classes.
    public void processReport() {
        System.out.println("Starting report processing workflow...");

        // Step 1: Create the report object (via polymorphic factory method)
        Report report = createReport();

        // Step 2: Open the report
        report.open();

        // Step 3: Generate the report
        report.generate();

        // Step 4: Display a completion message
        System.out.println("Report processing completed successfully.\n");
    }
}

// =========================================================================
// 4. CONCRETE CREATORS (Specialized Processors)
// =========================================================================
class PdfReportProcessor extends ReportProcessor {
    @Override
    protected Report createReport() {
        return new PdfReport();
    }
}

class WordReportProcessor extends ReportProcessor {
    @Override
    protected Report createReport() {
        return new WordReport();
    }
}

class HtmlReportProcessor extends ReportProcessor {
    @Override
    protected Report createReport() {
        return new HtmlReport();
    }
}

// Concrete creator for future extension
class ExcelReportProcessor extends ReportProcessor {
    @Override
    protected Report createReport() {
        return new ExcelReport();
    }
}

// =========================================================================
// 5. CLIENT APPLICATION / DRIVER
// =========================================================================
public class B2Main {

    // Client operates purely against the abstract ReportProcessor
    public static void runProcessingJob(ReportProcessor processor) {
        processor.processReport();
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  CSE 214 Online: Creational Pattern - B2 Solution");
        System.out.println("  Pattern: Factory Method Pattern");
        System.out.println("==================================================\n");

        // 1. Processing PDF Report
        System.out.println("--- 1. Client Requesting PDF Report ---");
        ReportProcessor pdfProcessor = new PdfReportProcessor();
        runProcessingJob(pdfProcessor);

        // 2. Processing Word Report
        System.out.println("--- 2. Client Requesting Word Report ---");
        ReportProcessor wordProcessor = new WordReportProcessor();
        runProcessingJob(wordProcessor);

        // 3. Processing HTML Report
        System.out.println("--- 3. Client Requesting HTML Report ---");
        ReportProcessor htmlProcessor = new HtmlReportProcessor();
        runProcessingJob(htmlProcessor);

        // 4. Extensibility Demonstration (Open-Closed Principle)
        System.out.println("--- 4. Future Extension: Client Requesting Excel Report ---");
        ReportProcessor excelProcessor = new ExcelReportProcessor();
        runProcessingJob(excelProcessor);
    }
}
