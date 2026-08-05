
/**
 * Base Colleague class (Mediator Design Pattern).
 * Every office/participant holds a reference ONLY to the mediator, never
 * to each other, keeping the offices fully decoupled.
 */
abstract class Colleague {
    protected final ResultCoordinator mediator;

    protected Colleague(ResultCoordinator mediator) {
        this.mediator = mediator;
    }
}

/**
 * Concrete Colleague: Department Office.
 * Responsible only for confirming that a student has completed all
 * academic requirements. It never talks to the Controller, DSW, or the
 * student directly -- it only tells the mediator.
 */
class DepartmentOffice extends Colleague {
    public DepartmentOffice(ResultCoordinator mediator) {
        super(mediator);
    }

    public void confirmCompletion(String studentId) {
        System.out.println("[Department Office] Submitting departmental confirmation for " + studentId);
        mediator.submitDepartmentalConfirmation(studentId);
    }
}

/**
 * Concrete Colleague: Office of the Controller of Examinations.
 * Issues the office order (step 2) and, later, the certificate and
 * transcript (step 4) -- both requests go through the mediator, which
 * enforces that the prerequisite steps have actually happened.
 */
class ControllerOfExaminations extends Colleague {
    public ControllerOfExaminations(ResultCoordinator mediator) {
        super(mediator);
    }

    public void issueOfficeOrder(String studentId) {
        System.out.println("[Controller of Examinations] Attempting to issue office order for " + studentId);
        mediator.requestOfficeOrder(studentId);
    }

    public void issueCertificateAndTranscript(String studentId) {
        System.out
                .println("[Controller of Examinations] Attempting to issue certificate & transcript for " + studentId);
        mediator.requestCertificateAndTranscript(studentId);
    }
}

/**
 * Concrete Colleague: Directorate of Students' Welfare (DSW).
 * Issues the testimonial (step 3), but only after the mediator confirms
 * the office order has already been issued.
 */
class DSW extends Colleague {
    public DSW(ResultCoordinator mediator) {
        super(mediator);
    }

    public void issueTestimonial(String studentId) {
        System.out.println("[DSW] Attempting to issue testimonial for " + studentId);
        mediator.requestTestimonial(studentId);
    }
}

/**
 * Concrete Colleague: Student.
 * Receives notifications from the mediator whenever something relevant
 * to their record happens, and can query their own current status.
 */
class Student extends Colleague {
    private final String studentId;
    private final String name;

    public Student(ResultCoordinator mediator, String studentId, String name) {
        super(mediator);
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void receiveNotification(String message) {
        System.out.println("  [Notify -> Student " + name + " (" + studentId + ")] " + message);
    }

    public void checkStatus() {
        mediator.displayStatus(studentId);
    }
}
