import java.util.ArrayList;
import java.util.List;

/**
 * Context: Document
 * Maintains a reference to an instance of a Concrete State subclass that
 * defines the current state.
 * Delegates state-specific operations to the current state object.
 */
public class Document {
    private final String title;
    private final User author;
    private String content;
    private DocumentState currentState;
    private String reviewFeedback;
    private final List<String> historyLog;

    public Document(String title, User author, String initialContent) {
        this.title = title;
        this.author = author;
        this.content = initialContent;
        this.reviewFeedback = "";
        this.historyLog = new ArrayList<>();

        // Initial state is always Draft
        this.currentState = new DraftState();
        log("Document created in DRAFT state by " + author.getName());
    }

    public void setState(DocumentState state) {
        String fromState = (this.currentState != null) ? this.currentState.getStateName() : "None";
        this.currentState = state;
        log("State transitioned: [" + fromState + " -> " + state.getStateName() + "]");
    }

    public void edit(User user, String newContent) {
        currentState.edit(this, user, newContent);
    }

    public void submitForReview(User user) {
        currentState.submitForReview(this, user);
    }

    public void approve(User user) {
        currentState.approve(this, user);
    }

    public void reject(User user, String feedback) {
        currentState.reject(this, user, feedback);
    }

    public void publish(User user) {
        currentState.publish(this, user);
    }

    public void archive(User user) {
        currentState.archive(this, user);
    }

    public void rollbackToDraft(User user) {
        currentState.rollbackToDraft(this, user);
    }

    public void log(String entry) {
        historyLog.add(entry);
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DocumentState getCurrentState() {
        return currentState;
    }

    public String getReviewFeedback() {
        return reviewFeedback;
    }

    public void setReviewFeedback(String reviewFeedback) {
        this.reviewFeedback = reviewFeedback;
    }

    public List<String> getHistoryLog() {
        return historyLog;
    }

    public void printDocumentSummary() {
        System.out.println("\n---------------- DOCUMENT SUMMARY ----------------");
        System.out.println("Title:   " + title);
        System.out.println("Author:  " + author);
        System.out.println("State:   " + currentState.getStateName());
        System.out.println("Content: \"" + content + "\"");
        if (reviewFeedback != null && !reviewFeedback.isEmpty()) {
            System.out.println("Feedback: " + reviewFeedback);
        }
        System.out.println("History Log:");
        for (String entry : historyLog) {
            System.out.println("  * " + entry);
        }
        System.out.println("--------------------------------------------------\n");
    }
}
