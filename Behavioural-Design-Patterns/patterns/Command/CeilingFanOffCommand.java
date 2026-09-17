/**
 * Concrete Command: CeilingFanOffCommand
 * Turns off the fan, caching prior state for undo.
 */
public class CeilingFanOffCommand implements Command {
    private final CeilingFan fan;
    private int prevSpeed;

    public CeilingFanOffCommand(CeilingFan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.off();
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
        return "CeilingFanOffCommand(" + fan.getLocation() + ")";
    }
}
