/**
 * Concrete Command: LightOffCommand
 * Encapsulates the request to turn a light off.
 */
public class LightOffCommand implements Command {
    private final Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }

    @Override
    public String toString() {
        return "LightOffCommand(" + light.getLocation() + ")";
    }
}
