// Product interface
interface Document {
    void open();
}

// Concrete Products
class PDFDocument implements Document {

    @Override
    public void open() {
        System.out.println("Opening PDF");
    }
}

class WordDocument implements Document {

    @Override
    public void open() {
        System.out.println("Opening Word Doc");
    }
}

// Creator (abstract) - has the factory method
abstract class Application {

    // Factory Method - subclasses must implement this
    public abstract Document createDocument();

    public void newDocument() {
        Document doc = createDocument(); // doesn't know the concrete type
        doc.open();
    }
}

// Concrete Creators
class PDFApplication extends Application {

    @Override
    public Document createDocument() {
        return new PDFDocument();
    }
}

class WordApplication extends Application {

    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}

// Client
public class FactoryMethodDemo {

    public static void main(String[] args) {

        Application app;

        app = new PDFApplication();
        app.newDocument();

        app = new WordApplication();
        app.newDocument();
    }
}
