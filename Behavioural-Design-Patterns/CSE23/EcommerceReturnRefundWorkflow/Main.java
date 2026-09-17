public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  E-COMMERCE RETURN & REFUND WORKFLOW (STATE PATTERN)     ");
        System.out.println("==========================================================");

        // Case 1: Complete Happy Path (Requested -> Approved -> Delivered -> Inspected
        // -> Processing -> Failed -> Succeeded -> Refunded)
        System.out.println("\n--- Case 1: Happy Path with Retry on Payout Failure ---");
        ReturnRequest req1 = new ReturnRequest("RET-101", "Alice", "Wireless Headphones", 129.99, "Audio cuts out");
        req1.printStatus();

        // Update reason while still in Requested
        req1.updateReason("Left earbud completely dead");

        // Approve request
        req1.approve();
        req1.printStatus();

        // Attempt invalid action: update reason after approval
        System.out.println("\n[Testing Invalid Action]: Attempting to update reason in Approved state:");
        req1.updateReason("Changed mind again");

        // Mark item delivered
        req1.itemDelivered();
        req1.printStatus();

        // Attempt invalid action: cancel after delivered
        System.out.println("\n[Testing Invalid Action]: Attempting to cancel after item delivered:");
        req1.cancel();

        // Inspect item (eligible = true)
        req1.inspect(true);
        req1.printStatus();

        // Refund fails on first attempt
        req1.refundFailed();
        req1.printStatus();

        // Retry refund succeeds
        req1.refundSuccessful();
        req1.printStatus();

        // Attempt invalid action on terminal state
        System.out.println("\n[Testing Invalid Action]: Attempting operation on terminal Refunded state:");
        req1.cancel();

        // Case 2: Ineligible product during inspection -> Rejected
        System.out.println("\n--- Case 2: Rejection Following Inspection Failure ---");
        ReturnRequest req2 = new ReturnRequest("RET-102", "Bob", "Smartwatch", 199.50, "Screen glitch");
        req2.approve();
        req2.itemDelivered();
        req2.inspect(false); // Fails inspection!
        req2.printStatus();
        req2.refundSuccessful(); // Invalid on rejected

        // Case 3: Cancellation while in Approved state (before delivery)
        System.out.println("\n--- Case 3: Customer Cancellation in Approved State ---");
        ReturnRequest req3 = new ReturnRequest("RET-103", "Charlie", "Gaming Keyboard", 89.00, "Wrong switch type");
        req3.approve();
        req3.cancel(); // Allowed before delivery
        req3.printStatus();
        req3.itemDelivered(); // Invalid on cancelled

        System.out.println("\n==========================================================");
        System.out.println("  Return & Refund Workflow demonstration completed.       ");
        System.out.println("==========================================================");
    }
}
