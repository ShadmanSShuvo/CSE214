import java.util.ArrayList;
import java.util.List;

// Component: common interface for leaves and composites
interface HardwareComponent {
    double getPrice();
    String getName();
}

// Leaf: individual hardware part
class IndividualComponent implements HardwareComponent {
    private String name;
    private double price;

    public IndividualComponent(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}

// Composite: a bundle that can hold components or other bundles
class Bundle implements HardwareComponent {
    private String name;
    private List<HardwareComponent> components = new ArrayList<>();

    public Bundle(String name) {
        this.name = name;
    }

    public void add(HardwareComponent component) {
        components.add(component);
    }

    public void remove(HardwareComponent component) {
        components.remove(component);
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (HardwareComponent component : components) {
            total += component.getPrice();
        }
        return total;
    }

    @Override
    public String getName() {
        return name;
    }

    public void printStructure(String indent) {
        System.out.println(indent + name + " -> " + getPrice());
        for (HardwareComponent component : components) {
            if (component instanceof Bundle) {
                ((Bundle) component).printStructure(indent + "  ");
            } else {
                System.out.println(indent + "  " + component.getName() + " -> " + component.getPrice());
            }
        }
    }
}

public class A2 {
    public static void main(String[] args) {
        // Individual components
        HardwareComponent cpu = new IndividualComponent("CPU", 300);
        HardwareComponent memory = new IndividualComponent("Memory", 100);
        HardwareComponent storage = new IndividualComponent("Storage", 80);
        HardwareComponent gpu = new IndividualComponent("Graphics Card", 500);
        HardwareComponent extraFan = new IndividualComponent("Extra Cooling Fan", 40);

        // Basic Gaming Setup bundle
        Bundle basicGamingSetup = new Bundle("Basic Gaming Setup");
        basicGamingSetup.add(cpu);
        basicGamingSetup.add(memory);
        basicGamingSetup.add(gpu);

        System.out.println("Basic Gaming Setup Price: " + basicGamingSetup.getPrice());

        // Ultimate Gaming Setup contains Basic Gaming Setup + extras
        Bundle ultimateGamingSetup = new Bundle("Ultimate Gaming Setup");
        ultimateGamingSetup.add(basicGamingSetup);
        ultimateGamingSetup.add(storage);
        ultimateGamingSetup.add(extraFan);

        System.out.println("Ultimate Gaming Setup Price: " + ultimateGamingSetup.getPrice());

        // Demonstrate add/remove
        ultimateGamingSetup.remove(extraFan);
        System.out.println("Ultimate Gaming Setup Price after removing extra fan: "
                + ultimateGamingSetup.getPrice());

        System.out.println("\nStructure:");
        ultimateGamingSetup.printStructure("");
    }
}
