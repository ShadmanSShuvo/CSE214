public class PathologyLab extends MedicalUnit {
    public PathologyLab(String name) {
        super(name);
    }

    public void processTest(String patientId, String result) {
        // Result: NORMAL or CRITICAL
        mediator.submitResult(patientId, InvestigationType.PATHOLOGY_TEST, result);
    }
}
