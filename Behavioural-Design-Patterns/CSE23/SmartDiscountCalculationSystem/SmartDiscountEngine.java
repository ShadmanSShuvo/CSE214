import java.util.ArrayList;
import java.util.List;

public class SmartDiscountEngine {
    private final List<DiscountPolicy> policies;

    public SmartDiscountEngine() {
        this.policies = new ArrayList<>();
    }

    public void addPolicy(DiscountPolicy policy) {
        policies.add(policy);
    }

    public double calculatePayable(PurchaseContext context) {
        System.out.println("----------------------------------------------------------");
        System.out.printf("Purchase Amount : ৳%.2f\n", context.getPurchaseAmount());
        System.out.println("Customer Type   : " + context.getCustomerCategory());
        System.out.println("Payment Method  : " + context.getPaymentMethod());
        System.out.println("\nThe available discounts are:");

        int maxDiscount = 0;
        DiscountPolicy selectedPolicy = null;

        for (DiscountPolicy policy : policies) {
            int discount = policy.calculateDiscountPercentage(context);
            System.out.printf("  %-26s -> %d%%\n", policy.getPolicyName(), discount);
            if (discount > maxDiscount) {
                maxDiscount = discount;
                selectedPolicy = policy;
            } else if (selectedPolicy == null && discount == maxDiscount) {
                selectedPolicy = policy;
            }
        }

        System.out.println("\nTherefore:");
        System.out.printf("Applied Discount = %d%%", maxDiscount);
        if (selectedPolicy != null) {
            System.out.printf(" (from %s)\n", selectedPolicy.getPolicyName());
        } else {
            System.out.println();
        }

        double discountAmount = context.getPurchaseAmount() * (maxDiscount / 100.0);
        double payableAmount = context.getPurchaseAmount() - discountAmount;

        System.out.printf("Final Payable Amount : ৳%.2f (Discount Savings: ৳%.2f)\n", payableAmount, discountAmount);
        System.out.println("----------------------------------------------------------");

        return payableAmount;
    }
}
