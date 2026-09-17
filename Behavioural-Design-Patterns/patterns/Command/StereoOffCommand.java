/**
 * Concrete Command: StereoOffCommand
 * Turns the stereo off.
 */
public class StereoOffCommand implements Command {
    private final Stereo stereo;

    public StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.off();
    }

    @Override
    public void undo() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(25);
    }

    @Override
    public String toString() {
        return "StereoOffCommand(" + stereo.getLocation() + ")";
    }
}
