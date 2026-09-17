import java.util.*;

/* ======================================================================
   COMPONENT INTERFACE (Decorator pattern)
   ====================================================================== */
interface Gift {
    double getPrice();
    String getDesc();
    // Default: a plain gift item has no delivery time associated with it
    default String getDeliveryTime() { return "N/A"; }
}

/* ======================================================================
   CONCRETE COMPONENTS
   ====================================================================== */
class GiftItem implements Gift {
    private final double price;
    private final String desc;

    public GiftItem(double price, String desc) {
        this.price = price;
        this.desc = desc;
    }

    @Override
    public double getPrice() { return price; }

    @Override
    public String getDesc() { return desc; }
}

/* ======================================================================
   BASE DECORATOR
   ====================================================================== */
abstract class GiftDecorator implements Gift {
    protected Gift wrappee;

    public GiftDecorator(Gift gift) {
        this.wrappee = gift;
    }

    @Override
    public String getDesc() { return wrappee.getDesc(); }

    @Override
    public double getPrice() { return wrappee.getPrice(); }

    @Override
    public String getDeliveryTime() { return wrappee.getDeliveryTime(); }
}

/* ======================================================================
   CONCRETE DECORATOR: Gift Wrapping
   ====================================================================== */
class GiftWrapDecorator extends GiftDecorator {
    private static final double WRAP_CHARGE = 2.0;

    public GiftWrapDecorator(Gift gift) { super(gift); }

    @Override
    public String getDesc() { return wrappee.getDesc() + " + Gift Wrap"; }

    @Override
    public double getPrice() { return wrappee.getPrice() + WRAP_CHARGE; }
}

/* ======================================================================
   STRATEGY 1: DeliveryRegion
   Independent axis #1 — where the package is going.
   Adding a new region = one new class. Nothing else changes.
   ====================================================================== */
interface DeliveryRegion {
    double getCharge(double miles);
    String getBaseTime();     // default time when no special mode is chosen
    String getRegionName();
}

class LocalRegion implements DeliveryRegion {
    private static final double RATE_PER_MILE = 1.0;

    @Override
    public double getCharge(double miles) { return RATE_PER_MILE * miles; }

    @Override
    public String getBaseTime() { return "1 week"; }

    @Override
    public String getRegionName() { return "Local"; }
}

class NationalRegion implements DeliveryRegion {
    private static final double RATE_PER_MILE = 1.0;
    private static final double SURCHARGE = 20.0;

    @Override
    public double getCharge(double miles) { return RATE_PER_MILE * miles + SURCHARGE; }

    @Override
    public String getBaseTime() { return "1-2 weeks"; }

    @Override
    public String getRegionName() { return "National"; }
}

class InternationalRegion implements DeliveryRegion {
    private static final double SURCHARGE = 500.0;

    @Override
    public double getCharge(double miles) { return SURCHARGE; } // distance-independent

    @Override
    public String getBaseTime() { return "2-3 weeks"; }

    @Override
    public String getRegionName() { return "International"; }
}

/* ======================================================================
   STRATEGY 2: DeliveryMode
   Independent axis #2 — how fast, layered on top of any region.
   Adding a new mode = one new class. Nothing else changes.
   ====================================================================== */
interface DeliveryMode {
    double getSurcharge();
    // Time depends on which region it's paired with, so region is passed in
    String getTime(DeliveryRegion region);
    String getModeName();
}

class StandardMode implements DeliveryMode {
    @Override
    public double getSurcharge() { return 0.0; }

    @Override
    public String getTime(DeliveryRegion region) { return region.getBaseTime(); }

    @Override
    public String getModeName() { return "Standard"; }
}

class ExpressMode implements DeliveryMode {
    private static final double SURCHARGE = 10.0;

    @Override
    public double getSurcharge() { return SURCHARGE; }

    @Override
    public String getTime(DeliveryRegion region) {
        return region instanceof InternationalRegion ? "1 week" : "2 days";
    }

    @Override
    public String getModeName() { return "Express"; }
}

class PriorityMode implements DeliveryMode {
    private static final double SURCHARGE = 25.0;

    @Override
    public double getSurcharge() { return SURCHARGE; }

    @Override
    public String getTime(DeliveryRegion region) {
        return region instanceof InternationalRegion ? "5 days" : "1 day";
    }

    @Override
    public String getModeName() { return "Priority"; }
}

/* ======================================================================
   CONCRETE DECORATOR: Home Delivery
   Composes a DeliveryRegion + DeliveryMode via Strategy, so both axes
   vary independently without the decorator itself changing.
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
        return wrappee.getPrice() + region.getCharge(miles) + mode.getSurcharge();
    }

    @Override
    public String getDesc() {
        return wrappee.getDesc() + " + " + region.getRegionName() + " Delivery ("
                + mode.getModeName() + ")";
    }

    @Override
    public String getDeliveryTime() {
        return mode.getTime(region);
    }
}

/* ======================================================================
   DEMO / VERIFICATION against the assignment's 3 worked examples
   ====================================================================== */
public class GiftShopClaude {
    public static void main(String[] args) {

        // ---------- Case 1 ----------
        // Vase $40, 10 miles, local, with wrapping, standard delivery
        Gift vase = new GiftItem(40, "Decorative Vase");
        vase = new GiftWrapDecorator(vase);
        vase = new HomeDeliveryDecorator(vase, new LocalRegion(), new StandardMode(), 10);
        printResult("Case 1", vase, 52.0);

        // ---------- Case 2 ----------
        // Souvenir $60, 50 miles, national, with wrapping, Express
        Gift souvenir = new GiftItem(60, "Wooden Souvenir");
        souvenir = new GiftWrapDecorator(souvenir);
        souvenir = new HomeDeliveryDecorator(souvenir, new NationalRegion(), new ExpressMode(), 50);
        printResult("Case 2", souvenir, 142.0);

        // ---------- Case 3 ----------
        // Showpiece $150, international, Priority, no wrapping
        Gift showpiece = new GiftItem(150, "Crystal Showpiece");
        showpiece = new HomeDeliveryDecorator(showpiece, new InternationalRegion(), new PriorityMode(), 0);
        printResult("Case 3", showpiece, 675.0);
    }

    private static void printResult(String label, Gift g, double expected) {
        System.out.println(label + ": " + g.getDesc());
        System.out.printf("  Total Cost: $%.2f (expected $%.2f) -> %s%n",
                g.getPrice(), expected,
                Math.abs(g.getPrice() - expected) < 0.001 ? "MATCH" : "MISMATCH");
        System.out.println("  Estimated Delivery Time: " + g.getDeliveryTime());
        System.out.println();
    }
}
