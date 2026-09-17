import java.util.*;

public class EmergencyCenter implements EmergencyCenterMediator {
    private DoctorUnit doctor;
    private PathologyLab pathologyLab;
    private RadiologyUnit radiologyUnit;
    private final Map<String, Patient> patients = new HashMap<>();

    private static class PatientTracker {
        final String patientId;
        final Set<InvestigationType> requested = new LinkedHashSet<>();
        final Set<InvestigationType> pending = new LinkedHashSet<>();
        final Map<InvestigationType, String> results = new LinkedHashMap<>();

        PatientTracker(String patientId) {
            this.patientId = patientId;
        }
    }

    private final Map<String, PatientTracker> trackers = new HashMap<>();

    public void registerUnits(DoctorUnit doctor, PathologyLab pathologyLab, RadiologyUnit radiologyUnit) {
        this.doctor = doctor;
        this.pathologyLab = pathologyLab;
        this.radiologyUnit = radiologyUnit;

        this.doctor.setMediator(this);
        this.pathologyLab.setMediator(this);
        this.radiologyUnit.setMediator(this);
    }

    @Override
    public void registerPatient(Patient patient) {
        patients.put(patient.getId(), patient);
        trackers.put(patient.getId(), new PatientTracker(patient.getId()));
    }

    @Override
    public void requestInvestigation(String patientId, InvestigationType type) {
        PatientTracker tracker = trackers.computeIfAbsent(patientId, PatientTracker::new);
        tracker.requested.add(type);
        tracker.pending.add(type);

        System.out.println(type.getDisplayName() + " requested for Patient " + patientId + ".");
    }

    @Override
    public void submitResult(String patientId, InvestigationType type, String result) {
        PatientTracker tracker = trackers.get(patientId);
        if (tracker == null) {
            System.out.println("Unknown patient: " + patientId);
            return;
        }

        tracker.results.put(type, result);
        tracker.pending.remove(type);

        boolean isUrgent = false;

        if (type == InvestigationType.PATHOLOGY_TEST) {
            if ("CRITICAL".equalsIgnoreCase(result)) {
                System.out.println("Critical pathology result received for Patient " + patientId + ".");
                isUrgent = true;
            } else {
                System.out.println("Normal pathology result received for Patient " + patientId + ".");
            }
        } else if (type == InvestigationType.RADIOLOGY_INVESTIGATION) {
            if ("NOT OK".equalsIgnoreCase(result)) {
                System.out.println("Urgent radiology (NOT OK) result received for Patient " + patientId + ".");
                isUrgent = true;
            } else {
                System.out.println("Radiology result received for Patient " + patientId + ".");
            }
        }

        Patient patient = patients.get(patientId);

        // Immediate urgent notification
        if (isUrgent) {
            if (doctor != null) {
                doctor.receiveUrgentNotification("Critical alert for Patient " + patientId);
            }
            if (patient != null) {
                patient.receiveUrgentNotification("Critical alert for your test");
            }
        }

        // Completion check
        if (tracker.pending.isEmpty()) {
            System.out.println("All requested investigations completed for Patient " + patientId + ".");
            if (doctor != null) {
                doctor.receiveCompleteResults("Summary: " + tracker.results);
            }
            if (patient != null) {
                patient.receiveCompleteResults("Summary: " + tracker.results);
            }
        }
    }
}
