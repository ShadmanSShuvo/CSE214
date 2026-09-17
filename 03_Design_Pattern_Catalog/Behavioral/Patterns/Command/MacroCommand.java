import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Composite / Macro Command: MacroCommand
 * Executes a sequence of commands sequentially, and undoes them in reverse order.
 */
public class MacroCommand implements Command {
    private final String name;
    private final List<Command> commands;

    public MacroCommand(String name, Command... commands) {
        this.name = name;
        this.commands = new ArrayList<>(Arrays.asList(commands));
    }

    public MacroCommand(String name, List<Command> commands) {
        this.name = name;
        this.commands = new ArrayList<>(commands);
    }

    @Override
    public void execute() {
        System.out.println(">>> Executing Macro: [" + name + "] <<<");
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        System.out.println("<<< Undoing Macro: [" + name + "] (reverse order) <<<");
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }

    @Override
    public String toString() {
        return "MacroCommand[" + name + " (" + commands.size() + " sub-commands)]";
    }
}
