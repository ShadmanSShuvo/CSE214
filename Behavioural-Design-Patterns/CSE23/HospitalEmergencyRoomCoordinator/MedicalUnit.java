public abstract class MedicalUnit {
    protected final String unitName;
    protected EmergencyCenterMediator mediator;

    public MedicalUnit(String unitName) {
        this.unitName = unitName;
    }

    public void setMediator(EmergencyCenterMediator mediator) {
        this.mediator = mediator;
    }

    public String getUnitName() {
        return unitName;
    }
}
