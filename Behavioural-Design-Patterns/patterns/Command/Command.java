/**
 * Command Interface declaring methods for executing an operation
 * and reversing its effect (undo).
 */
public interface Command {
    /**
     * Executes the encapsulated action on the receiver.
     */
    void execute();

    /**
     * Reverses the effect of execute().
     */
    void undo();
}
