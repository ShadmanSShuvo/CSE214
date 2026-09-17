
interface RamadanPackage {
    double getPrice();

    String getDescription();
}

// Concrete existing packages (unchanged)
class StandardPackage implements RamadanPackage {
    public double getPrice() {
        return 1500;
    }

    public String getDescription() {
        return "Standard Ramadan Package";
    }
}

class SpecialPackage implements RamadanPackage {
    public double getPrice() {
        return 2500;
    }

    public String getDescription() {
        return "Special Ramadan Package";
    }
}

class PremiumPackage implements RamadanPackage {
    public double getPrice() {
        return 4000;
    }

    public String getDescription() {
        return "Premium Ramadan Package";
    }
}

abstract class PackageDecorator implements RamadanPackage {
    protected RamadanPackage wrappee;

    PackageDecorator(RamadanPackage wrappee) {
        this.wrappee = wrappee;
    }
}

class FruitAddOn extends PackageDecorator {
    FruitAddOn(RamadanPackage w) {
        super(w);
    }

    public double getPrice() {
        return wrappee.getPrice() + 500;
    }

    public String getDescription() {
        return wrappee.getDescription() + " + Fruit Package";
    }
}

class SweetAddOn extends PackageDecorator {
    SweetAddOn(RamadanPackage w) {
        super(w);
    }

    public double getPrice() {
        return wrappee.getPrice() + 400;
    }

    public String getDescription() {
        return wrappee.getDescription() + " + Sweet Package";
    }
}

class GiftPackagingAddOn extends PackageDecorator {
    GiftPackagingAddOn(RamadanPackage w) {
        super(w);
    }

    public double getPrice() {
        return wrappee.getPrice() + 200;
    }

    public String getDescription() {
        return wrappee.getDescription() + " + Premium Gift Packaging";
    }
}

// Usage
public class C1Decorator {
    public static void main(String[] args) {
        RamadanPackage order = new GiftPackagingAddOn(
                new FruitAddOn(
                        new SpecialPackage()));
        System.out.println(order.getDescription());
        System.out.println("Total: " + order.getPrice());
    }
}
