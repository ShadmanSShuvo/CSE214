public class GiftShopGPT {

    //==================== GIFT ====================

    interface Gift {
        String getDescription();
        double getPrice();
    }

    static class GiftItem implements Gift {
        private String description;
        private double basePrice;

        public GiftItem(String description, double basePrice) {
            this.description = description;
            this.basePrice = basePrice;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return basePrice;
        }
    }

    //==================== DECORATOR ====================

    static abstract class GiftDecorator implements Gift {
        protected Gift gift;

        public GiftDecorator(Gift gift) {
            this.gift = gift;
        }
    }

    static class GiftWrap extends GiftDecorator {

        public GiftWrap(Gift gift) {
            super(gift);
        }

        public String getDescription() {
            return gift.getDescription() + " + Gift Wrap";
        }

        public double getPrice() {
            return gift.getPrice() + 2;
        }
    }

    //==================== BRIDGE ====================

    interface DeliveryMode {
        double extraCharge();
        String deliveryTime(boolean international);
        String getName();
    }

    static class StandardDelivery implements DeliveryMode {

        public double extraCharge() {
            return 0;
        }

        public String deliveryTime(boolean international) {
            if (international)
                return "2-3 weeks";
            return "1 week";
        }

        public String getName() {
            return "Standard";
        }
    }

    static class ExpressDelivery implements DeliveryMode {

        public double extraCharge() {
            return 10;
        }

        public String deliveryTime(boolean international) {
            if (international)
                return "1 week";
            return "2 days";
        }

        public String getName() {
            return "Express";
        }
    }

    static class PriorityDelivery implements DeliveryMode {

        public double extraCharge() {
            return 25;
        }

        public String deliveryTime(boolean international) {
            if (international)
                return "5 days";
            return "1 day";
        }

        public String getName() {
            return "Priority";
        }
    }

    //==================== ABSTRACTION ====================

    static abstract class Delivery {

        protected DeliveryMode mode;

        public Delivery(DeliveryMode mode) {
            this.mode = mode;
        }

        public abstract double deliveryCharge();

        public abstract boolean isInternational();

        public double totalCharge() {
            return deliveryCharge() + mode.extraCharge();
        }

        public String deliveryTime() {
            return mode.deliveryTime(isInternational());
        }

        public abstract String region();
    }

    //==================== REFINED ABSTRACTIONS ====================

    static class LocalDelivery extends Delivery {

        private double miles;

        public LocalDelivery(double miles, DeliveryMode mode) {
            super(mode);
            this.miles = miles;
        }

        public double deliveryCharge() {
            return miles;
        }

        public boolean isInternational() {
            return false;
        }

        public String region() {
            return "Local";
        }
    }

    static class NationalDelivery extends Delivery {

        private double miles;

        public NationalDelivery(double miles, DeliveryMode mode) {
            super(mode);
            this.miles = miles;
        }

        public double deliveryCharge() {
            return miles + 20;
        }

        public boolean isInternational() {
            return false;
        }

        public String region() {
            return "National";
        }
    }

    static class InternationalDelivery extends Delivery {

        public InternationalDelivery(DeliveryMode mode) {
            super(mode);
        }

        public double deliveryCharge() {
            return 500;
        }

        public boolean isInternational() {
            return true;
        }

        public String region() {
            return "International";
        }
    }

    //==================== BILL ====================

    static void printBill(Gift gift, Delivery delivery) {

        System.out.println("----------------------------------------");
        System.out.println("Item           : " + gift.getDescription());
        System.out.println("Region         : " + delivery.region());
        System.out.println("Mode           : " + delivery.mode.getName());

        double total = gift.getPrice() + delivery.totalCharge();

        System.out.println("Gift Price     : $" + gift.getPrice());
        System.out.println("Delivery Charge: $" + delivery.totalCharge());

        System.out.println("----------------------------------------");
        System.out.println("Total Cost     : $" + total);
        System.out.println("Delivery Time  : " + delivery.deliveryTime());
        System.out.println("----------------------------------------");
        System.out.println();
    }

    //==================== MAIN ====================

    public static void main(String[] args) {

        //================ CASE 1 =================

        Gift g1 = new GiftItem("Decorative Vase", 40);
        g1 = new GiftWrap(g1);

        Delivery d1 = new LocalDelivery(
                10,
                new StandardDelivery());

        printBill(g1, d1);

        //================ CASE 2 =================

        Gift g2 = new GiftItem("Wooden Souvenir", 60);
        g2 = new GiftWrap(g2);

        Delivery d2 = new NationalDelivery(
                50,
                new ExpressDelivery());

        printBill(g2, d2);

        //================ CASE 3 =================

        Gift g3 = new GiftItem("Crystal Showpiece", 150);

        Delivery d3 = new InternationalDelivery(
                new PriorityDelivery());

        printBill(g3, d3);
    }
}
