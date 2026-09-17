import java.util.HashMap;
import java.util.Map;

class ResultCoord implements Coordinator {
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Status> states = new HashMap<>();

    @Override
    public void register(Colleague colleague) {
        if (colleague instanceof Student) {
            Student student = (Student) colleague;
            students.put(student.getId(), student);
            states.put(student.getId(), new Status());
        }
    }

    @Override
    public void confirm(String id) {
        Status s = get(id);
        s.confirmed = true;
        System.out.println("[Coordinator] Departmental confirmation accepted.");
        notify(id, "Departmental confirmation completed.");
    }

    @Override
    public void officeOrder(String id) {
        Status s = get(id);
        if (!s.confirmed) {
            System.out.println("[Coordinator] REJECTED: Departmental confirmation missing.");
            return;
        }
        s.orderIssued = true;
        System.out.println("[Coordinator] Office order issued.");
        notify(id, "Final-result office order has been issued.");
    }

    @Override
    public void testimonial(String id) {
        Status s = get(id);
        if (!s.orderIssued) {
            System.out.println("[Coordinator] REJECTED: Office order not issued.");
            return;
        }
        s.testimonialIssued = true;
        System.out.println("[Coordinator] Testimonial issued.");
        notify(id, "Testimonial has been issued.");
    }

    @Override
    public void certificate(String id) {
        Status s = get(id);
        if (!s.confirmed || !s.orderIssued || !s.testimonialIssued) {
            System.out.println("[Coordinator] REJECTED: Previous steps are incomplete.");
            return;
        }
        s.certificateIssued = true;
        System.out.println("[Coordinator] Certificate and transcript issued.");
        notify(id, "Certificate and transcript have been issued.");
    }

    @Override
    public void status(String id) {
        Student student = students.get(id);
        if (student == null) {
            System.out.println("[Coordinator] Unknown student.");
            return;
        }
        System.out.println("[Coordinator] Status of " + student.getId() + ": " + states.get(id));
    }

    private Status get(String id) {
        Status s = states.get(id);
        if (s == null) {
            throw new IllegalArgumentException("Unknown student: " + id);
        }
        return s;
    }

    private void notify(String id, String msg) {
        Student student = students.get(id);
        if (student != null) {
            student.notify(msg);
        }
    }
}
