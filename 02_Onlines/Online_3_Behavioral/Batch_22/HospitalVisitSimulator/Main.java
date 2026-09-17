public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   HOSPITAL VISIT SIMULATOR (TEMPLATE METHOD PATTERN)     ");
        System.out.println("==========================================================\n");

        // 1. General Department Visit
        HospitalVisitTemplate generalDept = new GeneralDepartmentVisit();
        Patient patient1 = new Patient("John Doe", 45, 98.6, "120/80");
        generalDept.processVisit(patient1);

        // 2. Pediatrics Department Visit
        HospitalVisitTemplate pediatricsDept = new PediatricsDepartmentVisit();
        Patient patient2 = new Patient("Timmy Smith", 6, 101.2, "95/60");
        pediatricsDept.processVisit(patient2);

        // 3. Emergency Department Visit
        HospitalVisitTemplate emergencyDept = new EmergencyDepartmentVisit();
        Patient patient3 = new Patient("Sarah Connor", 32, 99.4, "150/95");
        emergencyDept.processVisit(patient3);

        System.out.println("Hospital visit simulation completed for all departments.");
    }
}
