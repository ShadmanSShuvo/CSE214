import java.util.ArrayList;
import java.util.List;

interface BazarComponent {
    double getPrice();

    double getWeight();

    String display(String indent);
}

// Leaf
class SingleItem implements BazarComponent {
    private final String name;
    private final double price, weight;

    SingleItem(String name, double price, double weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String display(String indent) {
        return indent + name + " ($" + price + ", " + weight + "kg)\n";
    }
}

// Composite
class CustomBazar implements BazarComponent {
    private final String name;
    private final List<BazarComponent> children = new ArrayList<>();

    CustomBazar(String name) {
        this.name = name;
    }

    void add(BazarComponent c) {
        children.add(c);
    }

    @Override
    public double getPrice() {
        return children.stream().mapToDouble(BazarComponent::getPrice).sum();
    }

    @Override
    public double getWeight() {
        return children.stream().mapToDouble(BazarComponent::getWeight).sum();
    }

    @Override
    public String display(String indent) {
        StringBuilder sb = new StringBuilder(indent + name + ":\n");
        for (BazarComponent c : children)
            sb.append(c.display(indent + "  "));
        return sb.toString();
    }
}

// Usage
public class A2Composite {
    public static void main(String[] args) {
        CustomBazar smallPackage = new CustomBazar("Small Package");
        smallPackage.add(new SingleItem("Rice", 60, 5));
        smallPackage.add(new SingleItem("Oil", 180, 2));

        CustomBazar myBazar = new CustomBazar("My Custom Bazar");
        myBazar.add(smallPackage);
        myBazar.add(new SingleItem("Pulse", 90, 1));
        myBazar.add(smallPackage); // reuse a previously created custom package

        System.out.println(myBazar.display(""));
        System.out.println("Total Price: " + myBazar.getPrice());
        System.out.println("Total Weight: " + myBazar.getWeight());
    }
}
