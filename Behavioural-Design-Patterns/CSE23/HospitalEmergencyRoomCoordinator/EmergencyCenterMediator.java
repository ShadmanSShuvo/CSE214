public interface EmergencyCenterMediator {
    void requestInvestigation(String patientId, InvestigationType type);
    void submitResult(String patientId, InvestigationType type, String result);
    void registerPatient(Patient patient);
}
