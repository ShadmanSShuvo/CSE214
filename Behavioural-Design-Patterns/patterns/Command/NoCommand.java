/**
 * Null Object implementation for Command interface.
 * Avoids null-checks in the Invoker by providing a safe do-nothing default.
 */
public class NoCommand implements Command {
    @Override
    public void execute() {
        // Do nothing
    }

    @Override
    public void undo() {
        // Do nothing
    }
}
