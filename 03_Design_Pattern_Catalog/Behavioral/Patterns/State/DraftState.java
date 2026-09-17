/**
 * Concrete State: DraftState
 * Initial state where document can be freely edited by the author and submitted
 * for review.
 */
public class DraftState implements DocumentState {

    @Override
    public void edit(Document doc, User user, String newContent) {
        if (user.getRole() == User.Role.AUTHOR || user.getRole() == User.Role.ADMIN) {
            doc.setContent(newContent);
            doc.log("Content revised by " + user.getName());
            System.out.println("[DRAFT] Content successfully updated by " + user.getName() + ".");
        } else {
            System.out.println("[DRAFT ERROR] Only the author or an admin can edit a draft.");
        }
    }

    @Override
    public void submitForReview(Document doc, User user) {
        if (user.getRole() == User.Role.AUTHOR || user.getRole() == User.Role.ADMIN) {
            System.out.println("[DRAFT] Document submitted for editorial review by " + user.getName() + ".");
            doc.setState(new InReviewState());
        } else {
            System.out.println("[DRAFT ERROR] Only the author or an admin can submit for review.");
        }
    }

    @Override
    public void approve(Document doc, User user) {
        System.out.println("[DRAFT ERROR] Cannot approve a draft directly. It must be submitted for review first.");
    }

    @Override
    public void reject(Document doc, User user, String feedback) {
        System.out.println("[DRAFT ERROR] Cannot reject a draft that has not been submitted for review.");
    }

    @Override
    public void publish(Document doc, User user) {
        System.out.println("[DRAFT ERROR] Drafts cannot be published directly. Must undergo editorial review.");
    }

    @Override
    public void archive(Document doc, User user) {
        if (user.getRole() == User.Role.ADMIN) {
            System.out.println("[DRAFT] Document archived directly by admin.");
            doc.setState(new ArchivedState());
        } else {
            System.out.println("[DRAFT ERROR] Only an administrator can archive an unpublished draft.");
        }
    }

    @Override
    public void rollbackToDraft(Document doc, User user) {
        System.out.println("[DRAFT] Document is already in DRAFT state.");
    }

    @Override
    public String getStateName() {
        return "DRAFT";
    }
}
