public class RadiologyUnit extends MedicalUnit {
    public RadiologyUnit(String name) {
        super(name);
    }

    public void processInvestigation(String patientId, String result) {
        // Result: OK or NOT OK
        mediator.submitResult(patientId, InvestigationType.RADIOLOGY_INVESTIGATION, result);
    }
}
