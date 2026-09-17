/**
 * State Interface: DocumentState
 * Declares all context-dependent operations that vary based on the document's
 * lifecycle state.
 */
public interface DocumentState {
    void edit(Document doc, User user, String newContent);

    void submitForReview(Document doc, User user);

    void approve(Document doc, User user);

    void reject(Document doc, User user, String feedback);

    void publish(Document doc, User user);

    void archive(Document doc, User user);

    void rollbackToDraft(Document doc, User user);

    String getStateName();
}
