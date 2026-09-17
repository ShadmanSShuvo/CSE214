/**
 * Demonstration driver for Task 2: BUET Final Result Publication System
 * (Mediator Design Pattern).
 *
 * Pattern justification:
 * The Department Office, Controller of Examinations, DSW, and Student
 * never invoke each other directly. Instead, every action is routed
 * through a single ResultProcessingCoordinator (the Mediator), which owns
 * the shared processing state and enforces the required step ordering.
 * This centralizes and simplifies what would otherwise be a tangled
 * many-to-many communication network between the four offices --
 * exactly the problem the Mediator pattern is designed to solve.
 */
public class Main {
    public static void main(String[] args) {
        ResultProcessingCoordinator coordinator = new ResultProcessingCoordinator();

        System.out.println("---- Registration ----");
        DepartmentOffice departmentOffice = new DepartmentOffice(coordinator);
        ControllerOfExaminations controller = new ControllerOfExaminations(coordinator);
        DSW dsw = new DSW(coordinator);
        Student rafiq = new Student(coordinator, "1905001", "Rafiq");

        coordinator.registerDepartmentOffice(departmentOffice);
        coordinator.registerControllerOffice(controller);
        coordinator.registerDSW(dsw);
        coordinator.registerStudent(rafiq);

        System.out.println("\n---- Step 1: Attempt to publish result BEFORE departmental confirmation ----");
        controller.issueOfficeOrder(rafiq.getStudentId());

        System.out.println("\n---- Step 2: Submit departmental confirmation ----");
        departmentOffice.confirmCompletion(rafiq.getStudentId());

        System.out.println("\n---- Step 3: Early attempt to issue certificate/transcript (before office order & testimonial) ----");
        controller.issueCertificateAndTranscript(rafiq.getStudentId());

        System.out.println("\n---- Step 4: Issue the final-result office order ----");
        controller.issueOfficeOrder(rafiq.getStudentId());

        System.out.println("\n---- Extra check: Early attempt to issue certificate/transcript (still before testimonial) ----");
        controller.issueCertificateAndTranscript(rafiq.getStudentId());

        System.out.println("\n---- Step 5: Issue the testimonial ----");
        dsw.issueTestimonial(rafiq.getStudentId());

        System.out.println("\n---- Step 6: Issue the certificate and transcript ----");
        controller.issueCertificateAndTranscript(rafiq.getStudentId());

        System.out.println("\n---- Step 7: Student notifications & final status ----");
        rafiq.checkStatus();
    }
}
