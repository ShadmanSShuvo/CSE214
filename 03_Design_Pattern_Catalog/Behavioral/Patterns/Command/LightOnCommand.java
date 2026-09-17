/**
 * Concrete Command: LightOnCommand
 * Encapsulates the request to turn a light on.
 */
public class LightOnCommand implements Command {
    private final Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }

    @Override
    public String toString() {
        return "LightOnCommand(" + light.getLocation() + ")";
    }
}
