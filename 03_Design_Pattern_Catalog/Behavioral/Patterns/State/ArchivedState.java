/**
 * Concrete State: ArchivedState
 * Historical, read-only terminal state. Only an administrator can un-archive or
 * restore to draft.
 */
public class ArchivedState implements DocumentState {

    @Override
    public void edit(Document doc, User user, String newContent) {
        System.out.println("[ARCHIVED ERROR] Modifying archived documents is strictly prohibited.");
    }

    @Override
    public void submitForReview(Document doc, User user) {
        System.out.println("[ARCHIVED ERROR] Cannot submit an archived document for review.");
    }

    @Override
    public void approve(Document doc, User user) {
        System.out.println("[ARCHIVED ERROR] Cannot approve an archived document.");
    }

    @Override
    public void reject(Document doc, User user, String feedback) {
        System.out.println("[ARCHIVED ERROR] Cannot reject an archived document.");
    }

    @Override
    public void publish(Document doc, User user) {
        System.out.println("[ARCHIVED ERROR] Cannot directly publish from archive. Must restore to draft first.");
    }

    @Override
    public void archive(Document doc, User user) {
        System.out.println("[ARCHIVED] Document is already archived.");
    }

    @Override
    public void rollbackToDraft(Document doc, User user) {
        if (user.getRole() == User.Role.ADMIN) {
            System.out.println(
                    "[ARCHIVED] Administrative override: Document restored to DRAFT state by " + user.getName() + ".");
            doc.setState(new DraftState());
        } else {
            System.out.println(
                    "[ARCHIVED ERROR] Permission denied: Only system administrators can restore archived documents.");
        }
    }

    @Override
    public String getStateName() {
        return "ARCHIVED";
    }
}
