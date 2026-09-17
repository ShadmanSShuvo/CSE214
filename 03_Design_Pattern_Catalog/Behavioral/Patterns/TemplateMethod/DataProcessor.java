import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Template Class: DataProcessor
 * Defines the skeleton of an ETL algorithm in process().
 * Subclasses provide implementation for primitive operations without changing
 * the overall workflow.
 */
public abstract class DataProcessor {

    /**
     * The Template Method. Marked 'final' to prevent subclasses from altering
     * the structural sequencing of the algorithm.
     */
    public final ProcessingReport process(String sourcePath) {
        long startTime = System.currentTimeMillis();

        if (shouldLogTelemetry()) {
            System.out.println(
                    "\n[ETL Pipeline] Initiating processing for: " + sourcePath + " (Format: " + getFormatName() + ")");
        }

        // 1. Hook: Pre-processing hook
        preProcessHook(sourcePath);

        // 2. Primitive Step: Read raw data (abstract)
        String rawData = readRawData(sourcePath);

        // 3. Primitive Step: Parse raw text into domain records (abstract)
        List<ParsedRecord> records = parseRecords(rawData);
        System.out.println("[ETL Step 2] Parsed " + records.size() + " raw records.");

        // 4. Concrete Default Step: Validate schema and filter invalid entries
        List<ParsedRecord> validRecords = validateAndFilter(records);

        // 5. Concrete / Overridable Step: Apply business logic transformations
        applyBusinessRules(validRecords);

        // 6. Concrete Default Step: Persist valid records to repository
        saveToDatabase(validRecords);

        // 7. Hook: Post-processing hook (e.g. notifications, indexing)
        postProcessHook(validRecords);

        long elapsedTime = Math.max(1, System.currentTimeMillis() - startTime);

        if (shouldLogTelemetry()) {
            System.out.println("[ETL Pipeline] Completed pipeline execution in " + elapsedTime + "ms.");
        }

        return new ProcessingReport(sourcePath, getFormatName(), records.size(), validRecords.size(), elapsedTime,
                true);
    }

    // ================= PRIMITIVE ABSTRACT OPERATIONS =================
    // Subclasses MUST implement these operations.

    /**
     * Ingests raw content from the source file or network stream.
     */
    protected abstract String readRawData(String sourcePath);

    /**
     * Converts raw text into structured ParsedRecord models.
     */
    protected abstract List<ParsedRecord> parseRecords(String rawData);

    /**
     * Returns the formal format name of this processor.
     */
    protected abstract String getFormatName();

    // ================= CONCRETE DEFAULT OPERATIONS =================
    // Subclasses can inherit directly or selectively override.

    /**
     * Default schema validation rule: record must have non-empty ID and entityName.
     */
    protected List<ParsedRecord> validateAndFilter(List<ParsedRecord> records) {
        List<ParsedRecord> valid = new ArrayList<>();
        for (ParsedRecord r : records) {
            if (r.getId() != null && !r.getId().trim().isEmpty() &&
                    r.getEntityName() != null && !r.getEntityName().trim().isEmpty()) {
                r.setValid(true);
                valid.add(r);
            } else {
                System.out.println("  ⚠️ [Validation] Rejecting malformed record: " + r);
            }
        }
        System.out.println("[ETL Step 3] Validation complete: " + valid.size() + " valid, "
                + (records.size() - valid.size()) + " rejected.");
        return valid;
    }

    /**
     * Applies standard business normalization (e.g., trimming, default timestamps).
     */
    protected void applyBusinessRules(List<ParsedRecord> records) {
        System.out.println("[ETL Step 4] Standard business rules applied to " + records.size() + " records.");
    }

    /**
     * Simulates persisting records into a database or data warehouse.
     */
    protected void saveToDatabase(List<ParsedRecord> records) {
        System.out.println("[ETL Step 5] Persisting " + records.size() + " records to Enterprise SQL Store.");
    }

    // ================= HOOK METHODS =================
    // Subclasses MAY optionally override these hooks to extend behavior.

    /**
     * Hook to decide whether detailed telemetry logging is printed.
     */
    protected boolean shouldLogTelemetry() {
        return true;
    }

    /**
     * Hook called before any file ingestion begins.
     */
    protected void preProcessHook(String sourcePath) {
        // Default: no-op
    }

    /**
     * Hook called after database persistence is complete.
     */
    protected void postProcessHook(List<ParsedRecord> validRecords) {
        // Default: no-op
    }
}
