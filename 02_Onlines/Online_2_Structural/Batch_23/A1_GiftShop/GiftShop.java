import java.util.*;

/**
 * Solution for CSE 214 Online 2 (A1) - Structural Design Patterns
 * Design: Decorator Pattern (Wrapping & Delivery Enhancements) + Strategy Pattern (Delivery Region & Mode)
 */

/* ======================================================================
   1. COMPONENT INTERFACE (Decorator Pattern)
   ====================================================================== */
interface Gift {
    double getPrice();
    String getDesc();
    default String getDeliveryTime() {
        return "No Delivery Requested";
    }
}

/* ======================================================================
   2. CONCRETE COMPONENT (Base Gift Items)
   ====================================================================== */
class GiftItem implements Gift {
    private final double price;
    private final String desc;

    public GiftItem(double price, String desc) {
        this.price = price;
        this.desc = desc;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}

/* ======================================================================
   3. BASE DECORATOR
   ====================================================================== */
abstract class GiftDecorator implements Gift {
    protected final Gift wrappee;

    public GiftDecorator(Gift gift) {
        if (gift == null) {
            throw new IllegalArgumentException("Wrapped gift cannot be null");
        }
        this.wrappee = gift;
    }

    @Override
    public String getDesc() {
        return wrappee.getDesc();
    }

    @Override
    public double getPrice() {
        return wrappee.getPrice();
    }

    @Override
    public String getDeliveryTime() {
        return wrappee.getDeliveryTime();
    }
}

/* ======================================================================
   4. CONCRETE DECORATOR: Gift Wrapping (Adds $2)
   ====================================================================== */
class GiftWrapDecorator extends GiftDecorator {
    private static final double WRAP_CHARGE = 2.0;

    public GiftWrapDecorator(Gift gift) {
        super(gift);
    }

    @Override
    public String getDesc() {
        return wrappee.getDesc() + " + Gift Wrapping ($2)";
    }

    @Override
    public double getPrice() {
        return wrappee.getPrice() + WRAP_CHARGE;
    }
}

/* ======================================================================
   5. STRATEGY AXIS 1: Delivery Region (Local, National, International)
   ====================================================================== */
interface DeliveryRegion {
    double getCost(double miles);
    String getBaseDeliveryTime();
    String getRegionName();
}

class LocalRegion implements DeliveryRegion {
    @Override
    public double getCost(double miles) {
        return miles * 1.0;
    }

    @Override
    public String getBaseDeliveryTime() {
        return "1 week";
    }

    @Override
    public String getRegionName() {
        return "Local";
    }
}

class NationalRegion implements DeliveryRegion {
    @Override
    public double getCost(double miles) {
        return (miles * 1.0) + 20.0;
    }

    @Override
    public String getBaseDeliveryTime() {
        return "1-2 weeks";
    }

    @Override
    public String getRegionName() {
        return "National";
    }
}

class InternationalRegion implements DeliveryRegion {
    @Override
    public double getCost(double miles) {
        return 500.0; // Flat surcharge
    }

    @Override
    public String getBaseDeliveryTime() {
        return "2-3 weeks";
    }

    @Override
    public String getRegionName() {
        return "International";
    }
}

/* ======================================================================
   6. STRATEGY AXIS 2: Delivery Mode (Standard, Express, Priority)
   ====================================================================== */
interface DeliveryMode {
    double getSurcharge();
    String getDeliveryTime(DeliveryRegion region);
    String getModeName();
}

class StandardMode implements DeliveryMode {
    @Override
    public double getSurcharge() {
        return 0.0;
    }

    @Override
    public String getDeliveryTime(DeliveryRegion region) {
        return region.getBaseDeliveryTime();
    }

    @Override
    public String getModeName() {
        return "Standard";
    }
}

class ExpressMode implements DeliveryMode {
    @Override
    public double getSurcharge() {
        return 10.0;
    }

    @Override
    public String getDeliveryTime(DeliveryRegion region) {
        if (region instanceof InternationalRegion) {
            return "1 week";
        }
        return "2 days";
    }

    @Override
    public String getModeName() {
        return "Express";
    }
}

class PriorityMode implements DeliveryMode {
    @Override
    public double getSurcharge() {
        return 25.0;
    }

    @Override
    public String getDeliveryTime(DeliveryRegion region) {
        if (region instanceof InternationalRegion) {
            return "5 days";
        }
        return "1 day";
    }

    @Override
    public String getModeName() {
        return "Priority";
    }
}

/* ======================================================================
   7. CONCRETE DECORATOR: Home Delivery
   ====================================================================== */
class HomeDeliveryDecorator extends GiftDecorator {
    private final DeliveryRegion region;
    private final DeliveryMode mode;
    private final double miles;

    public HomeDeliveryDecorator(Gift gift, DeliveryRegion region, DeliveryMode mode, double miles) {
        super(gift);
        this.region = region;
        this.mode = mode;
        this.miles = miles;
    }

    @Override
    public double getPrice() {
        return wrappee.getPrice() + region.getCost(miles) + mode.getSurcharge();
    }

    @Override
    public String getDesc() {
        return wrappee.getDesc() + " + " + region.getRegionName() + " Delivery (" + mode.getModeName() + ")";
    }

    @Override
    public String getDeliveryTime() {
        return mode.getDeliveryTime(region);
    }
}

/* ======================================================================
   8. TEST & CLIENT RUNNER
   ====================================================================== */
public class GiftShop {

    public static void verify(String testCase, Gift gift, double expectedPrice, String expectedTime) {
        double actualPrice = gift.getPrice();
        String actualTime = gift.getDeliveryTime();
        boolean priceMatch = Math.abs(actualPrice - expectedPrice) < 0.001;
        boolean timeMatch = actualTime.equalsIgnoreCase(expectedTime);

        System.out.println("---------------------------------------------------------------");
        System.out.println("Test: " + testCase);
        System.out.println("  Item: " + gift.getDesc());
        System.out.printf("  Price: $%.2f (Expected: $%.2f) -> %s%n", actualPrice, expectedPrice, priceMatch ? "PASS" : "FAIL");
        System.out.println("  Delivery Time: " + actualTime + " (Expected: " + expectedTime + ") -> " + (timeMatch ? "PASS" : "FAIL"));

        if (!priceMatch || !timeMatch) {
            throw new AssertionError("Test failed for " + testCase);
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("      CSE 214 Online 2 (A1) - Gift Shop Delivery System        ");
        System.out.println("===============================================================");

        // Case 1: Decorative Vase ($40) @ 10 miles local city with gift wrapping
        Gift case1 = new GiftItem(40, "Decorative Vase");
        case1 = new GiftWrapDecorator(case1);
        case1 = new HomeDeliveryDecorator(case1, new LocalRegion(), new StandardMode(), 10);
        verify("Case 1: Local Delivery + Gift Wrapping", case1, 52.0, "1 week");

        // Case 2: Wooden Souvenir ($60) @ 50 miles national with wrapping + Express Delivery
        Gift case2 = new GiftItem(60, "Wooden Souvenir");
        case2 = new GiftWrapDecorator(case2);
        case2 = new HomeDeliveryDecorator(case2, new NationalRegion(), new ExpressMode(), 50);
        verify("Case 2: National Delivery + Wrapping + Express", case2, 142.0, "2 days");

        // Case 3: Crystal Showpiece ($150) international with Priority Delivery (no wrapping)
        Gift case3 = new GiftItem(150, "Crystal Showpiece");
        case3 = new HomeDeliveryDecorator(case3, new InternationalRegion(), new PriorityMode(), 0);
        verify("Case 3: International Priority Delivery", case3, 675.0, "5 days");

        System.out.println("---------------------------------------------------------------");
        System.out.println("All 3 assignment test cases PASSED successfully!");
        System.out.println("===============================================================");
    }
}
