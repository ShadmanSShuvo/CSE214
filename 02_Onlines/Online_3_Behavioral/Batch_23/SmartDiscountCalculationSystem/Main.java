public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  SMART DISCOUNT CALCULATION SYSTEM (STRATEGY PATTERN)    ");
        System.out.println("==========================================================");

        SmartDiscountEngine engine = new SmartDiscountEngine();
        engine.addPolicy(new PurchaseAmountDiscountPolicy());
        engine.addPolicy(new CustomerCategoryDiscountPolicy());
        engine.addPolicy(new PaymentMethodDiscountPolicy());

        // Example 1 from Problem Statement:
        // Purchase Amount : ৳3,500
        // Customer Type : PREMIUM
        // Payment Method : CASH
        System.out.println("\n--- Example 1 from Specification ---");
        PurchaseContext purchase1 = new PurchaseContext(3500.0, CustomerCategory.PREMIUM, PaymentMethod.CASH);
        engine.calculatePayable(purchase1);

        // Example 2 from Problem Statement:
        // Purchase Amount : ৳5,500
        // Customer Type : PREMIUM
        // Payment Method : CASH
        System.out.println("\n--- Example 2 from Specification ---");
        PurchaseContext purchase2 = new PurchaseContext(5500.0, CustomerCategory.PREMIUM, PaymentMethod.CASH);
        engine.calculatePayable(purchase2);

        // Example 3: Additional Edge Case
        // Purchase Amount : ৳800 (Below 1000 -> 0%)
        // Customer Type : REGULAR (5%)
        // Payment Method : CARD (2%)
        System.out.println("\n--- Example 3: Small Purchase (Category Dominates) ---");
        PurchaseContext purchase3 = new PurchaseContext(800.0, CustomerCategory.REGULAR, PaymentMethod.CARD);
        engine.calculatePayable(purchase3);

        System.out.println("\nSmart Discount Calculation demonstration completed successfully.");
    }
}
