public class Main {

    public static void main(String[] args) {

        Coordinator coordinator = new ResultCoord();

        DeptOffice dept = new DeptOffice(coordinator);
        CtrlOffice ctrl = new CtrlOffice(coordinator);
        DSW dsw = new DSW(coordinator);
        Student student = new Student(
                coordinator,
                "1905001",
                "Rafiq");

        coordinator.register(dept);
        coordinator.register(ctrl);
        coordinator.register(dsw);
        coordinator.register(student);

        System.out.println("\n--- 1. Early office-order attempt ---");
        ctrl.issueOrder(student.getId());

        System.out.println("\n--- 2. Departmental confirmation ---");
        dept.confirm(student.getId());

        System.out.println("\n--- 3. Early certificate attempt ---");
        ctrl.issueCertificate(student.getId());

        System.out.println("\n--- 4. Office order ---");
        ctrl.issueOrder(student.getId());

        System.out.println("\n--- 5. Testimonial ---");
        dsw.issueTestimonial(student.getId());

        System.out.println("\n--- 6. Certificate & transcript ---");
        ctrl.issueCertificate(student.getId());

        System.out.println("\n--- 7. Final status ---");
        student.checkStatus();
    }
}
