import java.util.Stack;

// Command interface
interface Command {
    void execute();

    void undo(); // supports undo
}

// Receiver - actual business logic
class TextEditor {

    private StringBuilder text = new StringBuilder();

    public void insertText(String s) {
        text.append(s);
        System.out.println("Text: " + text);
    }

    public void deleteText(int len) {
        if (len <= text.length()) {
            text.delete(text.length() - len, text.length());
        }
        System.out.println("Text: " + text);
    }

    public String getText() {
        return text.toString();
    }
}

// Concrete Command
class InsertCommand implements Command {

    private TextEditor receiver;
    private String textToInsert;

    public InsertCommand(TextEditor editor, String text) {
        this.receiver = editor;
        this.textToInsert = text;
    }

    @Override
    public void execute() {
        receiver.insertText(textToInsert);
    }

    @Override
    public void undo() {
        receiver.deleteText(textToInsert.length());
    }
}

// Invoker - stores and executes commands
class CommandHistory {

    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command cmd) {
        cmd.execute();
        history.push(cmd); // store for undo
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}

// Client
public class CommandDemo {

    public static void main(String[] args) {

        TextEditor editor = new TextEditor();
        CommandHistory invoker = new CommandHistory();

        invoker.executeCommand(new InsertCommand(editor, "Hello "));
        invoker.executeCommand(new InsertCommand(editor, "World"));

        invoker.undoLast();
    }
}
