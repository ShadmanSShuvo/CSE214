public class PediatricsDepartmentVisit extends HospitalVisitTemplate {
    public PediatricsDepartmentVisit() {
        super("Pediatrics Department");
    }

    @Override
    protected void assessment(Patient patient) {
        System.out.println("3. Assessment: Doctor checks symptoms by ensuring child comfort level");
    }

    @Override
    protected void treatment(Patient patient) {
        System.out.println("4. Treatment: Give child-safe medicine, friendly reassurance message");
    }
}
