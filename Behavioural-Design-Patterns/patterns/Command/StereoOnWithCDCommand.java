/**
 * Concrete Command: StereoOnWithCDCommand
 * Demonstrates a command that invokes multiple receiver actions as part of a
 * single request.
 */
public class StereoOnWithCDCommand implements Command {
    private final Stereo stereo;

    public StereoOnWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(25);
    }

    @Override
    public void undo() {
        stereo.off();
    }

    @Override
    public String toString() {
        return "StereoOnWithCDCommand(" + stereo.getLocation() + ")";
    }
}
