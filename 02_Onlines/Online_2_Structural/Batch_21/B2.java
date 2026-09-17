// Component interface representing the basic hardware component
interface Component {
    double getPrice();

    String getDescription();
}

// Concrete Component representing individual hardware components
class HardwareComponent implements Component {
    private String name;
    private double price;

    public HardwareComponent(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return name;
    }
}

// Abstract Decorator
abstract class ComponentDecorator implements Component {
    protected Component wrappedComponent;

    public ComponentDecorator(Component component) {
        this.wrappedComponent = component;
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice();
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription();
    }
}

// Concrete Decorator: Extended Warranty
class ExtendedWarranty extends ComponentDecorator {
    private static final double COST = 50;

    public ExtendedWarranty(Component component) {
        super(component);
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice() + COST;
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription() + " + Extended Warranty";
    }
}

// Concrete Decorator: Installation Service
class InstallationService extends ComponentDecorator {
    private static final double COST = 30;

    public InstallationService(Component component) {
        super(component);
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice() + COST;
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription() + " + Installation Service";
    }
}

// Concrete Decorator: Performance Boost
class PerformanceBoost extends ComponentDecorator {
    private static final double COST = 80;

    public PerformanceBoost(Component component) {
        super(component);
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice() + COST;
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription() + " + Performance Boost";
    }
}

public class B2 {
    public static void main(String[] args) {
        Component cpu = new HardwareComponent("CPU", 300);

        // Add features dynamically
        Component upgradedCpu = new PerformanceBoost(new ExtendedWarranty(cpu));

        System.out.println(upgradedCpu.getDescription() + " -> " + upgradedCpu.getPrice());

        Component gpu = new HardwareComponent("Graphics Card", 500);
        Component fullyLoadedGpu = new InstallationService(
                new ExtendedWarranty(new PerformanceBoost(gpu)));

        System.out.println(fullyLoadedGpu.getDescription() + " -> " + fullyLoadedGpu.getPrice());
    }
}
