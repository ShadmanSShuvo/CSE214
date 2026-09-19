/**
 * Implementor interface in the Bridge Pattern.
 * Defines the contract for redeeming loyalty points into different benefit
 * channels.
 */
public interface RedemptionMethod {
    /**
     * @return Human-readable name of the redemption method.
     */
    String getMethodName();

    /**
     * Executes the redemption of points for a customer.
     *
     * @param customerName  name of customer redeeming points.
     * @param points        number of points being redeemed.
     * @param monetaryValue monetary equivalent of the redeemed points in GBP (£).
     * @param currentBill   current purchase bill amount (if applicable for instant
     *                      discount).
     */
    void redeem(String customerName, int points, double monetaryValue, double currentBill);
}
