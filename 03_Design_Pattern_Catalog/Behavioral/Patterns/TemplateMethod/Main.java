/**
 * Test Driver: Template Method Pattern Template Demonstration
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("      TEMPLATE METHOD DESIGN PATTERN TEMPLATE DEMO        ");
        System.out.println("==========================================================");

        // 1. Process Flat CSV Data
        System.out.println("\n--- Section 1: CSV Data Processing Pipeline ---");
        DataProcessor csvProcessor = new CSVDataProcessor();
        ProcessingReport csvReport = csvProcessor.process("data/employees.csv");
        csvReport.printReport();

        // 2. Process JSON Payload with Post-Processing Hook
        System.out.println("--- Section 2: JSON REST Pipeline (Custom Post-Hook) ---");
        DataProcessor jsonProcessor = new JSONDataProcessor();
        ProcessingReport jsonReport = jsonProcessor.process("https://api.cloud.internal/v1/servers.json");
        jsonReport.printReport();

        // 3. Process XML Feed with Pre-Hook & Overridden Business Rules
        System.out.println("--- Section 3: XML Pipeline (Pre-Hook & Custom Rules) ---");
        DataProcessor xmlProcessor = new XMLDataProcessor();
        ProcessingReport xmlReport = xmlProcessor.process("feeds/warehouse_inventory.xml");
        xmlReport.printReport();

        System.out.println("==========================================================");
        System.out.println("     TEMPLATE METHOD PATTERN DEMO COMPLETED SUCCESSFULLY  ");
        System.out.println("==========================================================");
    }
}
