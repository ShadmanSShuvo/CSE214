class CtrlOffice extends Colleague {

    public CtrlOffice(Coordinator coordinator) {
        super(coordinator);
    }

    public void issueOrder(String id) {
        System.out.println("[Controller] Requesting office order for " + id);
        coordinator.officeOrder(id);
    }

    public void issueCertificate(String id) {
        System.out.println("[Controller] Requesting certificate & transcript for " + id);
        coordinator.certificate(id);
    }
}
