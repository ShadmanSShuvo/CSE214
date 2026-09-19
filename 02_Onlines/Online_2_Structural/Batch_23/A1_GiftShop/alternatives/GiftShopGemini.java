// ==========================================
// 1. DECORATOR PATTERN (Gift Items & Wrappers)
// ==========================================

// Component Interface
interface GiftItem {
    String getDescription();
    double getCost();
}

// Concrete Component
class BaseGiftItem implements GiftItem {
    private String description;
    private double price;

    public BaseGiftItem(String description, double price) {
        this.description = description;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getCost() {
        return price;
    }
}

// Abstract Decorator
abstract class GiftDecorator implements GiftItem {
    protected GiftItem giftItem;

    public GiftDecorator(GiftItem giftItem) {
        this.giftItem = giftItem;
    }

    @Override
    public String getDescription() {
        return giftItem.getDescription();
    }

    @Override
    public double getCost() {
        return giftItem.getCost();
    }
}

// Concrete Decorator: Gift Wrapping
class GiftWrappingDecorator extends GiftDecorator {
    public GiftWrappingDecorator(GiftItem giftItem) {
        super(giftItem);
    }

    @Override
    public String getDescription() {
        return giftItem.getDescription() + " + Gift Wrapping";
    }

    @Override
    public double getCost() {
        return giftItem.getCost() + 2.0; // Adds $2 wrapping fee
    }
}

// ==========================================
// 2. BRIDGE PATTERN (Delivery Regions & Modes)
// ==========================================

enum ModeType { STANDARD, EXPRESS, PRIORITY }

// Implementor Interface (Delivery Region)
interface DeliveryRegion {
    double getBaseCharge(double distanceInMiles);
    String getDeliveryTime(ModeType mode);
}

// Concrete Implementor: Local Delivery
class LocalDeliveryRegion implements DeliveryRegion {
    @Override
    public double getBaseCharge(double distanceInMiles) {
        return distanceInMiles * 1.0; // $1 per mile
    }

    @Override
    public String getDeliveryTime(ModeType mode) {
        switch (mode) {
            case EXPRESS: return "2 days";
            case PRIORITY: return "1 day";
            default: return "1 week";
        }
    }
}

// Concrete Implementor: National Delivery
class NationalDeliveryRegion implements DeliveryRegion {
    @Override
    public double getBaseCharge(double distanceInMiles) {
        return (distanceInMiles * 1.0) + 20.0; // $1/mile + $20 fixed surcharge
    }

    @Override
    public String getDeliveryTime(ModeType mode) {
        switch (mode) {
            case EXPRESS: return "2 days";
            case PRIORITY: return "1 day";
            default: return "1-2 weeks";
        }
    }
}

// Concrete Implementor: International Delivery
class InternationalDeliveryRegion implements DeliveryRegion {
    @Override
    public double getBaseCharge(double distanceInMiles) {
        return 500.0; // $500 fixed surcharge
    }

    @Override
    public String getDeliveryTime(ModeType mode) {
        switch (mode) {
            case EXPRESS: return "1 week";
            case PRIORITY: return "5 days";
            default: return "2-3 weeks";
        }
    }
}

// Abstraction (Delivery Mode)
abstract class DeliveryMode {
    protected DeliveryRegion region;

    public DeliveryMode(DeliveryRegion region) {
        this.region = region;
    }

    public abstract double getDeliveryCost(double distanceInMiles);
    public abstract String getEstimatedTime();
}

// Refined Abstraction: Standard Delivery
class StandardDeliveryMode extends DeliveryMode {
    public StandardDeliveryMode(DeliveryRegion region) {
        super(region);
    }

    @Override
    public double getDeliveryCost(double distanceInMiles) {
        return region.getBaseCharge(distanceInMiles);
    }

    @Override
    public String getEstimatedTime() {
        return region.getDeliveryTime(ModeType.STANDARD);
    }
}

// Refined Abstraction: Express Delivery
class ExpressDeliveryMode extends DeliveryMode {
    public ExpressDeliveryMode(DeliveryRegion region) {
        super(region);
    }

    @Override
    public double getDeliveryCost(double distanceInMiles) {
        return region.getBaseCharge(distanceInMiles) + 10.0; // Adds $10
    }

    @Override
    public String getEstimatedTime() {
        return region.getDeliveryTime(ModeType.EXPRESS);
    }
}

// Refined Abstraction: Priority Delivery
class PriorityDeliveryMode extends DeliveryMode {
    public PriorityDeliveryMode(DeliveryRegion region) {
        super(region);
    }

    @Override
    public double getDeliveryCost(double distanceInMiles) {
        return region.getBaseCharge(distanceInMiles) + 25.0; // Adds $25
    }

    @Override
    public String getEstimatedTime() {
        return region.getDeliveryTime(ModeType.PRIORITY);
    }
}

// ==========================================
// 3. MAIN APPLICATION & TEST CASES
// ==========================================

public class GiftShopGemini {
    public static void main(String[] args) {
        // Case 1: Decorative vase ($40), 10 miles local, with gift wrapping
        GiftItem item1 = new GiftWrappingDecorator(new BaseGiftItem("Decorative Vase", 40.0));
        DeliveryMode delivery1 = new StandardDeliveryMode(new LocalDeliveryRegion());
        double totalCost1 = item1.getCost() + delivery1.getDeliveryCost(10);

        System.out.println("=== Case 1 ===");
        System.out.println("Item: " + item1.getDescription());
        System.out.println("Total Cost: $" + totalCost1);
        System.out.println("Estimated Delivery Time: " + delivery1.getEstimatedTime());
        System.out.println();

        // Case 2: Wooden souvenir ($60), 50 miles national, with gift wrapping & Express Delivery
        GiftItem item2 = new GiftWrappingDecorator(new BaseGiftItem("Wooden Souvenir", 60.0));
        DeliveryMode delivery2 = new ExpressDeliveryMode(new NationalDeliveryRegion());
        double totalCost2 = item2.getCost() + delivery2.getDeliveryCost(50);

        System.out.println("=== Case 2 ===");
        System.out.println("Item: " + item2.getDescription());
        System.out.println("Total Cost: $" + totalCost2);
        System.out.println("Estimated Delivery Time: " + delivery2.getEstimatedTime());
        System.out.println();

        // Case 3: Crystal showpiece ($150), international destination using Priority Delivery
        GiftItem item3 = new BaseGiftItem("Crystal Showpiece", 150.0);
        DeliveryMode delivery3 = new PriorityDeliveryMode(new InternationalDeliveryRegion());
        double totalCost3 = item3.getCost() + delivery3.getDeliveryCost(0);

        System.out.println("=== Case 3 ===");
        System.out.println("Item: " + item3.getDescription());
        System.out.println("Total Cost: $" + totalCost3);
        System.out.println("Estimated Delivery Time: " + delivery3.getEstimatedTime());
    }
}
