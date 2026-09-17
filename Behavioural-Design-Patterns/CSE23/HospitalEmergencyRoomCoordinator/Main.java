public class Main {
    public static void main(String[] args) {
        // Instantiate Central Mediator
        EmergencyCenter emergencyCenter = new EmergencyCenter();

        // Instantiate Medical Units (Colleagues)
        DoctorUnit doctor = new DoctorUnit("Dr. House");
        PathologyLab pathologyLab = new PathologyLab("Central Pathology Lab");
        RadiologyUnit radiologyUnit = new RadiologyUnit("Advanced Radiology Suite");

        // Register medical units with Emergency Center
        emergencyCenter.registerUnits(doctor, pathologyLab, radiologyUnit);

        // Register Patient P101
        Patient patientP101 = new Patient("P101", "John Smith");
        emergencyCenter.registerPatient(patientP101);

        // Doctor requests both investigations for Patient P101
        doctor.requestInvestigation("P101", InvestigationType.PATHOLOGY_TEST);
        doctor.requestInvestigation("P101", InvestigationType.RADIOLOGY_INVESTIGATION);

        // Pathology Lab submits result first: CRITICAL (urgent)
        pathologyLab.processTest("P101", "CRITICAL");

        // Later, Radiology Unit submits result: OK
        radiologyUnit.processInvestigation("P101", "OK");
    }
}
