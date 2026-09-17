// =========================================================================
// 1. ABSTRACT PRODUCT INTERFACES
// =========================================================================
interface Button {
    void render();
}

interface TextField {
    void display();
}

interface Dialog {
    void show();
}

// =========================================================================
// 2. CONCRETE PRODUCTS: LIGHT THEME COMPONENT FAMILY
// =========================================================================
class LightButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Light Button");
    }
}

class LightTextField implements TextField {
    @Override
    public void display() {
        System.out.println("Displaying Light Text Field");
    }
}

class LightDialog implements Dialog {
    @Override
    public void show() {
        System.out.println("Showing Light Dialog Box");
    }
}

// =========================================================================
// 3. CONCRETE PRODUCTS: DARK THEME COMPONENT FAMILY
// =========================================================================
class DarkButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Dark Button");
    }
}

class DarkTextField implements TextField {
    @Override
    public void display() {
        System.out.println("Displaying Dark Text Field");
    }
}

class DarkDialog implements Dialog {
    @Override
    public void show() {
        System.out.println("Showing Dark Dialog Box");
    }
}

// =========================================================================
// 4. ABSTRACT FACTORY INTERFACE (Your Theme Setup)
// =========================================================================
interface Theme {
    Button createButton();

    TextField createTextField();

    Dialog createDialog();
}

// =========================================================================
// 5. CONCRETE FACTORIES (Theme Implementations)
// =========================================================================
class LightTheme implements Theme {
    @Override
    public Button createButton() {
        return new LightButton();
    }

    @Override
    public TextField createTextField() {
        return new LightTextField();
    }

    @Override
    public Dialog createDialog() {
        return new LightDialog();
    }
}

class DarkTheme implements Theme {
    @Override
    public Button createButton() {
        return new DarkButton();
    }

    @Override
    public TextField createTextField() {
        return new DarkTextField();
    }

    @Override
    public Dialog createDialog() {
        return new DarkDialog();
    }
}

// =========================================================================
// 6. CLIENT APPLICATION (Decoupled Implementation)
// =========================================================================
class Application {
    private Button button;
    private TextField textField;
    private Dialog dialog;

    // The client strictly requires a polymorphic factory variant instance.
    // This makes it completely impossible to mix components from mismatched themes.
    public Application(Theme theme) {
        this.button = theme.createButton();
        this.textField = theme.createTextField();
        this.dialog = theme.createDialog();
    }

    public void renderUI() {
        button.render();
        textField.display();
        dialog.show();
    }
}

// =========================================================================
// 7. MAIN RUNTIME EXECUTION
// =========================================================================
public class A1Main {
    public static void main(String[] args) {
        // Scenario 1: Configure application to run via Light Theme components
        System.out.println("--- Client Selecting Light Theme ---");
        Theme lightTheme = new LightTheme();
        Application app1 = new Application(lightTheme);
        app1.renderUI();

        System.out.println();

        // Scenario 2: Completely modify themes down the chain without breaking main
        // logic
        System.out.println("--- Client Swapping to Dark Theme ---");
        Theme darkTheme = new DarkTheme();
        Application app2 = new Application(darkTheme);
        app2.renderUI();
    }
}