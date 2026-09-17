/**
 * Concrete Command: CeilingFanMediumCommand
 * Sets fan to medium speed, caching prior state for undo.
 */
public class CeilingFanMediumCommand implements Command {
    private final CeilingFan fan;
    private int prevSpeed;

    public CeilingFanMediumCommand(CeilingFan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.medium();
    }

    @Override
    public void undo() {
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
        return "CeilingFanMediumCommand(" + fan.getLocation() + ")";
    }
}
