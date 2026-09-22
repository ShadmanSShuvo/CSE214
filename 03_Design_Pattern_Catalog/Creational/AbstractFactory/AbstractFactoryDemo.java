// Abstract Products
interface Button {
    void render();
}

interface Checkbox {
    void render();
}

// Concrete Products - Windows family
class WinButton implements Button {

    @Override
    public void render() {
        System.out.println("Windows Button");
    }
}

class WinCheckbox implements Checkbox {

    @Override
    public void render() {
        System.out.println("Windows Checkbox");
    }
}

// Concrete Products - Mac family
class MacButton implements Button {

    @Override
    public void render() {
        System.out.println("Mac Button");
    }
}

class MacCheckbox implements Checkbox {

    @Override
    public void render() {
        System.out.println("Mac Checkbox");
    }
}

// Abstract Factory - creates a family of products
interface GUIFactory {

    Button createButton();

    Checkbox createCheckbox();
}

// Concrete Factory - Windows
class WinFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}

// Concrete Factory - Mac
class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}

// Client
class Application {

    private Button button;
    private Checkbox checkbox;

    // Factory is injected
    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void render() {
        button.render();
        checkbox.render();
    }
}

// Main class
public class AbstractFactoryDemo {

    public static void main(String[] args) {

        // Windows UI
        Application winApp = new Application(new WinFactory());
        winApp.render();

        System.out.println();

        // Mac UI
        Application macApp = new Application(new MacFactory());
        macApp.render();
    }
}
