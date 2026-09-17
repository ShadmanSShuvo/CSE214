/**
 * Domain Model: ProcessingReport
 * Summarizes the outcome and health metrics of an executed ETL pipeline run.
 */
public class ProcessingReport {
    private final String sourcePath;
    private final String format;
    private final int totalRead;
    private final int validSaved;
    private final long elapsedMs;
    private final boolean success;

    public ProcessingReport(String sourcePath, String format, int totalRead, int validSaved, long elapsedMs,
            boolean success) {
        this.sourcePath = sourcePath;
        this.format = format;
        this.totalRead = totalRead;
        this.validSaved = validSaved;
        this.elapsedMs = elapsedMs;
        this.success = success;
    }

    public void printReport() {
        System.out.println("---------------- ETL EXECUTION REPORT ----------------");
        System.out.printf("Source File:      %s%n", sourcePath);
        System.out.printf("Data Format:      %s%n", format);
        System.out.printf("Records Ingested: %d%n", totalRead);
        System.out.printf("Records Persisted:%d (Filtered out: %d)%n", validSaved, (totalRead - validSaved));
        System.out.printf("Execution Latency:%d ms%n", elapsedMs);
        System.out.printf("Pipeline Status:  %s%n", success ? "COMPLETED SUCCESS (200 OK)" : "FAILED");
        System.out.println("------------------------------------------------------\n");
    }

    public boolean isSuccess() {
        return success;
    }
}
