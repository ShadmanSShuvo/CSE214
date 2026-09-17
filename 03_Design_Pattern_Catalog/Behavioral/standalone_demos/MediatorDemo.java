// Mediator interface
interface DialogMediator {
    void notify(Component sender, String event);
}

// Components - each knows the mediator, NOT other components
abstract class Component {

    protected DialogMediator mediator;

    public Component(DialogMediator mediator) {
        this.mediator = mediator;
    }

    public void click() {
        mediator.notify(this, "click");
    }
}

// Button
class Button extends Component {

    private String label;

    public Button(DialogMediator mediator, String label) {
        super(mediator);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

// Checkbox
class Checkbox extends Component {

    private boolean checked = false;

    public Checkbox(DialogMediator mediator) {
        super(mediator);
    }

    public void toggle() {
        checked = !checked;
        mediator.notify(this, "check");
    }

    public boolean isChecked() {
        return checked;
    }
}

// TextBox
class TextBox extends Component {

    private boolean enabled = true;

    public TextBox(DialogMediator mediator) {
        super(mediator);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}

// Concrete Mediator
class LoginDialog implements DialogMediator {

    private Checkbox rememberMe;
    private TextBox password;
    private Button login;

    // Wire up components
    public void setComponents(Checkbox cb, TextBox tb, Button btn) {
        this.rememberMe = cb;
        this.password = tb;
        this.login = btn;
    }

    @Override
    public void notify(Component sender, String event) {

        if (sender == rememberMe && event.equals("check")) {
            password.setEnabled(!rememberMe.isChecked());

            System.out.println(
                    "Password field " +
                            (rememberMe.isChecked() ? "disabled" : "enabled"));
        }

        if (sender == login && event.equals("click")) {
            System.out.println("Login button clicked - validating...");
        }
    }
}

// Client
public class MediatorDemo {

    public static void main(String[] args) {

        LoginDialog dialog = new LoginDialog();

        Checkbox cb = new Checkbox(dialog);
        TextBox tb = new TextBox(dialog);
        Button btn = new Button(dialog, "Login");

        dialog.setComponents(cb, tb, btn);

        cb.toggle();
        btn.click();
    }
}
