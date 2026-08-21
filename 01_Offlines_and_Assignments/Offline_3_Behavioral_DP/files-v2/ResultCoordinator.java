/**
 * Mediator interface (Mediator Design Pattern).
 * Every colleague (Department Office, Controller of Examinations, DSW,
 * Student) talks only to this coordinator -- never directly to each other.
 */
interface ResultCoordinator {

    void registerDepartmentOffice(DepartmentOffice office);
    void registerControllerOffice(ControllerOfExaminations controller);
    void registerDSW(DSW dsw);
    void registerStudent(Student student);

    // Step 1: Department confirms academic completion.
    void submitDepartmentalConfirmation(String studentId);

    // Step 2: Controller issues the office order (requires step 1).
    void requestOfficeOrder(String studentId);

    // Step 3: DSW issues the testimonial (requires step 2).
    void requestTestimonial(String studentId);

    // Step 4: Controller issues certificate + transcript (requires step 3).
    void requestCertificateAndTranscript(String studentId);

    void displayStatus(String studentId);
}
