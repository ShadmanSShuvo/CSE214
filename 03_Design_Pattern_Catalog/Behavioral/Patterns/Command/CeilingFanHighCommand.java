/**
 * Concrete Command: CeilingFanHighCommand
 * Stores previous speed state to restore accurately upon undo.
 */
public class CeilingFanHighCommand implements Command {
    private final CeilingFan fan;
    private int prevSpeed;

    public CeilingFanHighCommand(CeilingFan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.high();
    }

    @Override
    public void undo() {
        restorePreviousSpeed();
    }

    private void restorePreviousSpeed() {
        switch (prevSpeed) {
            case CeilingFan.HIGH:
                fan.high();
                break;
            case CeilingFan.MEDIUM:
                fan.medium();
                break;
            case CeilingFan.LOW:
                fan.low();
                break;
            case CeilingFan.OFF:
            default:
                fan.off();
                break;
        }
    }

    @Override
    public String toString() {
        return "CeilingFanHighCommand(" + fan.getLocation() + ")";
    }
}
