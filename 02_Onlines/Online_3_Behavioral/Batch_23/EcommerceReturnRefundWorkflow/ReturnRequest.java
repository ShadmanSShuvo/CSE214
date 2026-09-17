public class ReturnRequest {
    private final String id;
    private final String customerName;
    private final String productName;
    private final double amount;
    private String reason;
    private ReturnState state;

    public ReturnRequest(String id, String customerName, String productName, double amount, String initialReason) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.amount = amount;
        this.reason = initialReason;
        this.state = new RequestedState();
        System.out.println("[Return Request Created] Request #" + id + " created by " + customerName + " for "
                + productName + " ($" + amount + "). Reason: " + initialReason);
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    public double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReturnState getState() {
        return state;
    }

    public void setState(ReturnState state) {
        this.state = state;
    }

    public void updateReason(String newReason) {
        state.updateReason(this, newReason);
    }

    public void approve() {
        state.approve(this);
    }

    public void reject() {
        state.reject(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public void itemDelivered() {
        state.itemDelivered(this);
    }

    public void inspect(boolean eligible) {
        state.inspect(this, eligible);
    }

    public void refundSuccessful() {
        state.refundSuccessful(this);
    }

    public void refundFailed() {
        state.refundFailed(this);
    }

    public void printStatus() {
        System.out.println("  --> [Current Status] Request #" + id + " is in state: " + state.getStateName());
    }
}
