import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Invoker: RemoteControl
 * Holds command slots and orchestrates execution, multi-level undo, and redo.
 */
public class RemoteControl {
    private static final int DEFAULT_SLOTS = 5;

    private final Command[] onCommands;
    private final Command[] offCommands;
    private final Deque<Command> undoStack;
    private final Deque<Command> redoStack;

    public RemoteControl() {
        this(DEFAULT_SLOTS);
    }

    public RemoteControl(int slots) {
        onCommands = new Command[slots];
        offCommands = new Command[slots];
        undoStack = new ArrayDeque<>();
        redoStack = new ArrayDeque<>();

        Command noCommand = new NoCommand();
        for (int i = 0; i < slots; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        checkSlot(slot);
        onCommands[slot] = onCommand != null ? onCommand : new NoCommand();
        offCommands[slot] = offCommand != null ? offCommand : new NoCommand();
    }

    public void onButtonWasPushed(int slot) {
        checkSlot(slot);
        Command command = onCommands[slot];
        System.out.println("\n[Remote] Pressed ON for Slot #" + slot + ": " + command);
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo history on new action
    }

    public void offButtonWasPushed(int slot) {
        checkSlot(slot);
        Command command = offCommands[slot];
        System.out.println("\n[Remote] Pressed OFF for Slot #" + slot + ": " + command);
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo history on new action
    }

    public void undoButtonWasPushed() {
        if (undoStack.isEmpty()) {
            System.out.println("\n[Remote] Nothing to UNDO.");
            return;
        }
        Command lastCommand = undoStack.pop();
        System.out.println("\n[Remote] Pressed UNDO -> Reverting: " + lastCommand);
        lastCommand.undo();
        redoStack.push(lastCommand);
    }

    public void redoButtonWasPushed() {
        if (redoStack.isEmpty()) {
            System.out.println("\n[Remote] Nothing to REDO.");
            return;
        }
        Command nextCommand = redoStack.pop();
        System.out.println("\n[Remote] Pressed REDO -> Re-executing: " + nextCommand);
        nextCommand.execute();
        undoStack.push(nextCommand);
    }

    private void checkSlot(int slot) {
        if (slot < 0 || slot >= onCommands.length) {
            throw new IllegalArgumentException("Slot index out of range: " + slot);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n------ Remote Control Panel ------\n");
        for (int i = 0; i < onCommands.length; i++) {
            sb.append(String.format("[Slot %d] ON: %-30s | OFF: %s%n",
                    i, onCommands[i].getClass().getSimpleName(), offCommands[i].getClass().getSimpleName()));
        }
        sb.append("Undo Stack Size: ").append(undoStack.size())
                .append(" | Redo Stack Size: ").append(redoStack.size()).append("\n");
        sb.append("----------------------------------\n");
        return sb.toString();
    }
}
