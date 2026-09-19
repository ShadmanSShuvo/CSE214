import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract Composite in the Composite Pattern.
 * A gift package consists of individual gift items and/or other nested
 * packages.
 */
public abstract class GiftPackage implements GiftComponent {
    protected final String name;
    protected final String creatorName;
    protected PackagingStyle packagingStyle;
    protected final List<GiftComponent> components = new ArrayList<>();

    public GiftPackage(String name, String creatorName, PackagingStyle packagingStyle) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Package name cannot be empty.");
        }
        if (creatorName == null || creatorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Creator name cannot be empty.");
        }
        this.name = name.trim();
        this.creatorName = creatorName.trim();
        this.packagingStyle = (packagingStyle != null) ? packagingStyle : new PackagingStyle.StandardGiftBox();
    }

    public void add(GiftComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("Cannot add a null component to package.");
        }
        if (component == this) {
            throw new IllegalArgumentException("Cannot add a package to itself.");
        }
        components.add(component);
    }

    public void remove(GiftComponent component) {
        components.remove(component);
    }

    public List<GiftComponent> getComponents() {
        return Collections.unmodifiableList(components);
    }

    public void setPackagingStyle(PackagingStyle packagingStyle) {
        if (packagingStyle == null) {
            throw new IllegalArgumentException("Packaging style cannot be null.");
        }
        this.packagingStyle = packagingStyle;
    }

    public PackagingStyle getPackagingStyle() {
        return packagingStyle;
    }

    public String getCreatorName() {
        return creatorName;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Descriptive label for this package type.
     */
    public abstract String getPackageType();

    /**
     * Calculates base price of all child items/packages without packaging extra
     * cost.
     */
    public double getBaseComponentsPrice() {
        double total = 0.0;
        for (GiftComponent c : components) {
            total += c.getPrice();
        }
        return total;
    }

    @Override
    public double getPrice() {
        return getBaseComponentsPrice() + packagingStyle.getAdditionalCost();
    }

    @Override
    public int getIndividualItemCount() {
        int count = 0;
        for (GiftComponent c : components) {
            count += c.getIndividualItemCount();
        }
        return count;
    }

    /**
     * Validates whether this package meets creation rules:
     * Customers must add two or more individual gift items (can also contain
     * existing packages).
     */
    public boolean isValidForPublication() {
        return getIndividualItemCount() >= 2;
    }

    @Override
    public void print(String indent) {
        System.out.printf("%s[%s] \"%s\" (Created by: %s)%n", indent, getPackageType(), name, creatorName);
        System.out.printf("%s  Packaging: %s%n", indent, packagingStyle.getPresentationDescription());
        System.out.printf("%s  Contents:%n", indent);
        for (GiftComponent c : components) {
            c.print(indent + "    ");
        }
        System.out.printf("%s  Subtotal Items: $%.2f | Packaging: $%.2f | Total Price: $%.2f%n",
                indent, getBaseComponentsPrice(), packagingStyle.getAdditionalCost(), getPrice());
    }
}
