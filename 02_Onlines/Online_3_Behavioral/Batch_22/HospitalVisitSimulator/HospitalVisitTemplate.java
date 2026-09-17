import java.util.UUID;

public abstract class HospitalVisitTemplate {
    private final String departmentName;

    public HospitalVisitTemplate(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    // Template Method - defines the invariable 5-step visit algorithm skeleton
    public final void processVisit(Patient patient) {
        System.out.println("==========================================================");
        System.out.println(">>> Visiting Department: " + departmentName.toUpperCase() + " <<<");
        System.out.println("==========================================================");

        checkIn(patient);
        recordVitals(patient);
        assessment(patient);
        treatment(patient);
        dischargeSummary(patient);

        System.out.println("----------------------------------------------------------\n");
    }

    // Step 1: Check-In (common)
    protected void checkIn(Patient patient) {
        String id = "VID-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        patient.setVisitId(id);
        System.out.println("1. Check-In: Registered patient \"" + patient.getName() + "\" (Age: " + patient.getAge()
                + ") -> Assigned Visit ID: " + patient.getVisitId());
    }

    // Step 2: Record Vitals (common)
    protected void recordVitals(Patient patient) {
        System.out.printf("2. Record Vitals: Temperature = %.1f°F, Blood Pressure = %s\n", patient.getTemperature(),
                patient.getBloodPressure());
    }

    // Step 3: Assessment (customized per department)
    protected abstract void assessment(Patient patient);

    // Step 4: Treatment (customized per department)
    protected abstract void treatment(Patient patient);

    // Step 5: Discharge Summary (common)
    protected void dischargeSummary(Patient patient) {
        System.out.println(
                "5. Discharge Summary: Patient discharged. Notes: Follow prescribed regimen and schedule follow-up if symptoms persist.");
    }
}
