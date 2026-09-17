/**
 * Test Driver: State Pattern Template Demonstration
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("           STATE DESIGN PATTERN TEMPLATE DEMO             ");
        System.out.println("==========================================================");

        // 1. Create Users with different roles
        User author = new User("Alice", User.Role.AUTHOR);
        User editor = new User("Bob", User.Role.EDITOR);
        User admin = new User("Charlie", User.Role.ADMIN);

        // 2. Create Document Context (starts in DRAFT)
        System.out.println("\n--- Section 1: Creation & Initial Draft Operations ---");
        Document doc = new Document("Behavioral Patterns in Modern Architecture", author, "Initial brief outline.");
        doc.printDocumentSummary();

        // 3. Author edits draft and tries illegal direct publish
        System.out.println("--- Section 2: Valid Draft Edits vs Illegal Transitions ---");
        doc.edit(author, "Comprehensive guide to 6 key Behavioral Design Patterns.");
        doc.publish(author); // Illegal!

        // 4. Submit for review
        System.out.println("\n--- Section 3: Submission & Review State Enforcement ---");
        doc.submitForReview(author);

        // Try modifying while under review (Locked)
        doc.edit(author, "Sneaking in an unreviewed paragraph.");

        // Author tries to approve own work
        doc.approve(author);

        // 5. Editor Rejection Cycle
        System.out.println("\n--- Section 4: Editorial Review Rejection ---");
        doc.reject(editor, "Please provide complete UML diagrams and code samples.");

        // Back in Draft state, author revises content
        System.out.println("\nAuthor revises draft according to editorial feedback:");
        doc.edit(author,
                "Comprehensive guide to 6 key Behavioral Design Patterns with UML diagrams and runnable code.");
        doc.submitForReview(author);

        // 6. Approval & Publication
        System.out.println("\n--- Section 5: Editorial Approval & Publication ---");
        doc.approve(editor);

        // Attempting to edit live published article
        doc.edit(author, "Modifying live article without new revision.");

        // 7. Archiving & Rollback
        System.out.println("\n--- Section 6: Archiving & Role-Protected Rollback ---");
        doc.archive(editor);

        // Author tries to unarchive
        doc.rollbackToDraft(author);

        // Admin performs rollback
        doc.rollbackToDraft(admin);

        // 8. Final Document State & Audit Trail
        System.out.println("\n--- Section 7: Audit Trail ---");
        doc.printDocumentSummary();

        System.out.println("==========================================================");
        System.out.println("          STATE PATTERN DEMO COMPLETED SUCCESSFULLY       ");
        System.out.println("==========================================================");
    }
}
