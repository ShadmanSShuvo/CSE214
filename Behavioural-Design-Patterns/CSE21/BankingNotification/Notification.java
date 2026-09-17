import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {
    private final String id;
    private final NotificationType type;
    private final String title;
    private final String message;
    private final LocalDateTime timestamp;

    public Notification(String id, NotificationType type, String title, String message) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
