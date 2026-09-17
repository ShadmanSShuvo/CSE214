public class EmergencyDepartmentVisit extends HospitalVisitTemplate {
    public EmergencyDepartmentVisit() {
        super("Emergency Department");
    }

    @Override
    protected void assessment(Patient patient) {
        System.out.println("3. Assessment: Quick triage check (urgent/non-urgent)");
    }

    @Override
    protected void treatment(Patient patient) {
        System.out.println("4. Treatment: Immediate emergency procedure");
    }
}
