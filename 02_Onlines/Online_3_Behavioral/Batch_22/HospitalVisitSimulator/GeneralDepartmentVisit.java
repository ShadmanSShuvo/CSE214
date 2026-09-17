public class GeneralDepartmentVisit extends HospitalVisitTemplate {
    public GeneralDepartmentVisit() {
        super("General Department");
    }

    @Override
    protected void assessment(Patient patient) {
        System.out.println("3. Assessment: Doctor performs normal diagnosis");
    }

    @Override
    protected void treatment(Patient patient) {
        System.out.println("4. Treatment: Prescribe standard medicine");
    }
}
