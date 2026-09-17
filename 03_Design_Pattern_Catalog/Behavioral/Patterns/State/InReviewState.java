/**
 * Concrete State: InReviewState
 * Document is frozen for review. Only Editors or Admins can approve or reject.
 */
public class InReviewState implements DocumentState {

    @Override
    public void edit(Document doc, User user, String newContent) {
        System.out.println(
                "[IN_REVIEW ERROR] Cannot edit document while it is under editorial review! Document is locked.");
    }

    @Override
    public void submitForReview(Document doc, User user) {
        System.out.println("[IN_REVIEW] Document is already under review.");
    }

    @Override
    public void approve(Document doc, User user) {
        if (user.getRole() == User.Role.EDITOR || user.getRole() == User.Role.ADMIN) {
            System.out.println("[IN_REVIEW] Document approved by " + user + "! Auto-publishing to production.");
            doc.setReviewFeedback("Approved without reservations by " + user.getName());
            doc.setState(new PublishedState());
        } else {
            System.out.println("[IN_REVIEW ERROR] Permission denied: Authors cannot approve their own documents.");
        }
    }

    @Override
    public void reject(Document doc, User user, String feedback) {
        if (user.getRole() == User.Role.EDITOR || user.getRole() == User.Role.ADMIN) {
            System.out.println("[IN_REVIEW] Document rejected by " + user + ". Reason: \"" + feedback + "\"");
            doc.setReviewFeedback(feedback);
            doc.setState(new DraftState());
        } else {
            System.out.println("[IN_REVIEW ERROR] Permission denied: Only editors or admins can reject documents.");
        }
    }

    @Override
    public void publish(Document doc, User user) {
        System.out.println("[IN_REVIEW ERROR] Explicit publish disallowed without formal approval.");
    }

    @Override
    public void archive(Document doc, User user) {
        System.out.println("[IN_REVIEW ERROR] Cannot archive document while actively under review.");
    }

    @Override
    public void rollbackToDraft(Document doc, User user) {
        if (user.getRole() == User.Role.AUTHOR || user.getRole() == User.Role.ADMIN) {
            System.out.println("[IN_REVIEW] Author retracted document back to DRAFT.");
            doc.setState(new DraftState());
        } else {
            System.out.println("[IN_REVIEW ERROR] Only author or admin can retract review.");
        }
    }

    @Override
    public String getStateName() {
        return "IN_REVIEW";
    }
}
