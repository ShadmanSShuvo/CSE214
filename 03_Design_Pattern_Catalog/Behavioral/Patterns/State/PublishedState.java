/**
 * Concrete State: PublishedState
 * Document is live and publicly accessible. Edits are prohibited without
 * rolling back to draft.
 */
public class PublishedState implements DocumentState {

    @Override
    public void edit(Document doc, User user, String newContent) {
        System.out.println(
                "[PUBLISHED ERROR] Cannot edit live published document. Must create a revision (rollback to draft).");
    }

    @Override
    public void submitForReview(Document doc, User user) {
        System.out.println("[PUBLISHED] Document is already published.");
    }

    @Override
    public void approve(Document doc, User user) {
        System.out.println("[PUBLISHED] Document is already approved and live.");
    }

    @Override
    public void reject(Document doc, User user, String feedback) {
        System.out.println("[PUBLISHED ERROR] Cannot reject an already published document.");
    }

    @Override
    public void publish(Document doc, User user) {
        System.out.println("[PUBLISHED] Document is already published.");
    }

    @Override
    public void archive(Document doc, User user) {
        if (user.getRole() == User.Role.EDITOR || user.getRole() == User.Role.ADMIN) {
            System.out.println("[PUBLISHED] Document un-published and moved to ARCHIVE by " + user.getName() + ".");
            doc.setState(new ArchivedState());
        } else {
            System.out.println("[PUBLISHED ERROR] Only editors or admins can archive published content.");
        }
    }

    @Override
    public void rollbackToDraft(Document doc, User user) {
        if (user.getRole() == User.Role.AUTHOR || user.getRole() == User.Role.ADMIN) {
            System.out
                    .println("[PUBLISHED] Document moved back to DRAFT for major revision by " + user.getName() + ".");
            doc.setState(new DraftState());
        } else {
            System.out.println("[PUBLISHED ERROR] Only the author or an admin can roll back a published document.");
        }
    }

    @Override
    public String getStateName() {
        return "PUBLISHED";
    }
}
