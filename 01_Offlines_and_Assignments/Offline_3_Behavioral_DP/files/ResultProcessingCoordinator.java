import java.util.HashMap;
import java.util.Map;

/**
 * Concrete Mediator: the central Result-Processing Coordinator.
 *
 * All offices (Department, Controller, DSW) and all students register
 * with this single coordinator. Every request, confirmation, and status
 * update passes through it -- no office ever calls another office
 * directly, and no office ever talks to a Student object directly either.
 * The coordinator is solely responsible for:
 *   - enforcing the required processing sequence,
 *   - rejecting out-of-order requests,
 *   - notifying the correct student when a step succeeds.
 */
class ResultProcessingCoordinator implements ResultCoordinator {

    private DepartmentOffice departmentOffice;
    private ControllerOfExaminations controllerOffice;
    private DSW dsw;

    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, ProcessingStatus> statusMap = new HashMap<>();

    @Override
    public void registerDepartmentOffice(DepartmentOffice office) {
        this.departmentOffice = office;
        System.out.println("[Coordinator] Department Office registered.");
    }

    @Override
    public void registerControllerOffice(ControllerOfExaminations controller) {
        this.controllerOffice = controller;
        System.out.println("[Coordinator] Controller of Examinations registered.");
    }

    @Override
    public void registerDSW(DSW dsw) {
        this.dsw = dsw;
        System.out.println("[Coordinator] DSW registered.");
    }

    @Override
    public void registerStudent(Student student) {
        students.put(student.getStudentId(), student);
        statusMap.put(student.getStudentId(), new ProcessingStatus());
        System.out.println("[Coordinator] Student registered: " + student.getName()
                + " (" + student.getStudentId() + ")");
    }

    @Override
    public void submitDepartmentalConfirmation(String studentId) {
        ProcessingStatus status = getStatusOrThrow(studentId);
        status.setDepartmentalConfirmationSubmitted(true);
        System.out.println("[Coordinator] Departmental confirmation recorded for " + studentId + ".");
        notifyStudent(studentId, "Your departmental confirmation has been submitted.");
    }

    @Override
    public void requestOfficeOrder(String studentId) {
        ProcessingStatus status = getStatusOrThrow(studentId);

        if (!status.isDepartmentalConfirmationSubmitted()) {
            System.out.println("[Coordinator] REJECTED: Cannot issue office order for " + studentId
                    + " -- departmental confirmation is missing.");
            return;
        }

        status.setOfficeOrderIssued(true);
        System.out.println("[Coordinator] Office order for final result publication issued for " + studentId + ".");
        notifyStudent(studentId, "Your final-result office order has been issued.");
    }

    @Override
    public void requestTestimonial(String studentId) {
        ProcessingStatus status = getStatusOrThrow(studentId);

        if (!status.isOfficeOrderIssued()) {
            System.out.println("[Coordinator] REJECTED: Cannot issue testimonial for " + studentId
                    + " -- office order has not been issued yet.");
            return;
        }

        status.setTestimonialIssued(true);
        System.out.println("[Coordinator] Testimonial issued for " + studentId + ".");
        notifyStudent(studentId, "Your testimonial has been issued by DSW.");
    }

    @Override
    public void requestCertificateAndTranscript(String studentId) {
        ProcessingStatus status = getStatusOrThrow(studentId);

        if (!status.isTestimonialIssued()) {
            System.out.println("[Coordinator] REJECTED: Cannot issue certificate & transcript for " + studentId
                    + " -- testimonial has not been issued yet.");
            return;
        }

        status.setCertificateAndTranscriptIssued(true);
        System.out.println("[Coordinator] Certificate and academic transcript issued for " + studentId + ".");
        notifyStudent(studentId, "Your certificate and academic transcript have been issued. "
                + "All steps are now complete.");
    }

    @Override
    public void displayStatus(String studentId) {
        ProcessingStatus status = getStatusOrThrow(studentId);
        Student student = students.get(studentId);
        System.out.println("[Coordinator] Status for " + student.getName() + " (" + studentId + "): " + status);
    }

    private void notifyStudent(String studentId, String message) {
        Student student = students.get(studentId);
        if (student != null) {
            student.receiveNotification(message);
        }
    }

    private ProcessingStatus getStatusOrThrow(String studentId) {
        ProcessingStatus status = statusMap.get(studentId);
        if (status == null) {
            throw new IllegalArgumentException("Unknown student id: " + studentId);
        }
        return status;
    }
}
