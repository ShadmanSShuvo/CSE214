class DeptOffice extends Colleague {

    public DeptOffice(Coordinator coordinator) {
        super(coordinator);
    }

    public void confirm(String id) {
        System.out.println("[Department] Confirming completion for " + id);
        coordinator.confirm(id);
    }
}
