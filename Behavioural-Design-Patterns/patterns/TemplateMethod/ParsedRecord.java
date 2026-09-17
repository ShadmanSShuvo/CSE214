import java.util.Collections;
import java.util.Map;

/**
 * Domain Model: ParsedRecord
 * Represents an individual extracted data item within the ETL pipeline.
 */
public class ParsedRecord {
    private final String id;
    private final String entityName;
    private final Map<String, String> attributes;
    private boolean valid;

    public ParsedRecord(String id, String entityName, Map<String, String> attributes) {
        this.id = id;
        this.entityName = entityName;
        this.attributes = attributes;
        this.valid = true;
    }

    public String getId() {
        return id;
    }

    public String getEntityName() {
        return entityName;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Record[" + id + " | " + entityName + " | Valid=" + valid + " | " + attributes + "]";
    }
}
