public class DoctorUnit extends MedicalUnit {
    public DoctorUnit(String name) {
        super(name);
    }

    public void requestInvestigation(String patientId, InvestigationType type) {
        mediator.requestInvestigation(patientId, type);
    }

    public void receiveUrgentNotification(String message) {
        System.out.println("URGENT notification sent to Doctor.");
    }

    public void receiveCompleteResults(String summary) {
        System.out.println("Complete results sent to Doctor.");
    }
}
