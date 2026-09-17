import java.util.UUID;

/*
 * ============================================================
 * ADAPTER DESIGN PATTERN DEMO
 * Scenario:
 * An e-commerce system supports multiple payment providers.
 *
 * The application expects every payment gateway to expose:
 *      processPayment(...)
 *      refund(...)
 *
 * Unfortunately every vendor has a different API.
 * Adapters convert those APIs into a common interface.
 * ============================================================
 */

public class AdapterComplexDemo {

    public static void main(String[] args) {

        PaymentProcessor stripe =
                new StripeAdapter(new StripeGateway());

        PaymentProcessor paypal =
                new PayPalAdapter(new PayPalGateway());

        PaymentProcessor crypto =
                new CryptoAdapter(new CryptoExchange());

        Checkout checkout = new Checkout();

        checkout.checkout(stripe, 120.50);
        checkout.checkout(paypal, 299.99);
        checkout.checkout(crypto, 700.00);

        System.out.println("\n==============================");

        checkout.refund(stripe, "ORD-1001");
        checkout.refund(paypal, "ORD-1002");
        checkout.refund(crypto, "ORD-1003");
    }
}

/* ============================================================
                    TARGET INTERFACE
   ============================================================ */

interface PaymentProcessor {

    PaymentReceipt processPayment(
            double amount,
            String currency,
            String customer
    );

    boolean refund(String transactionId);
}

/* ============================================================
                    CLIENT
   ============================================================ */

class Checkout {

    public void checkout(PaymentProcessor processor,
                         double amount) {

        PaymentReceipt receipt =
                processor.processPayment(
                        amount,
                        "USD",
                        "Alice");

        System.out.println(receipt);
    }

    public void refund(PaymentProcessor processor,
                       String transactionId) {

        boolean ok = processor.refund(transactionId);

        System.out.println(
                "Refund Status : " + ok
        );
    }
}

/* ============================================================
                    DOMAIN OBJECT
   ============================================================ */

class PaymentReceipt {

    private final String provider;
    private final String transactionId;
    private final double amount;
    private final String status;

    public PaymentReceipt(
            String provider,
            String transactionId,
            double amount,
            String status) {

        this.provider = provider;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {

        return """
                -------------------------------
                Provider      : %s
                TransactionID : %s
                Amount        : %.2f
                Status        : %s
                -------------------------------
                """
                .formatted(
                        provider,
                        transactionId,
                        amount,
                        status
                );
    }
}

/* ============================================================
                    ADAPTER #1
   ============================================================ */

class StripeAdapter implements PaymentProcessor {

    private final StripeGateway stripe;

    public StripeAdapter(StripeGateway stripe) {
        this.stripe = stripe;
    }

    @Override
    public PaymentReceipt processPayment(
            double amount,
            String currency,
            String customer) {

        StripeResponse response =
                stripe.makeCharge(
                        amount,
                        currency,
                        customer
                );

        return new PaymentReceipt(
                "Stripe",
                response.id,
                amount,
                response.status
        );
    }

    @Override
    public boolean refund(String transactionId) {

        return stripe.reverseCharge(transactionId);
    }
}

/* ============================================================
                    ADAPTER #2
   ============================================================ */

class PayPalAdapter implements PaymentProcessor {

    private final PayPalGateway paypal;

    public PayPalAdapter(PayPalGateway paypal) {
        this.paypal = paypal;
    }

    @Override
    public PaymentReceipt processPayment(
            double amount,
            String currency,
            String customer) {

        String tx =
                paypal.sendMoney(
                        customer,
                        amount
                );

        return new PaymentReceipt(
                "PayPal",
                tx,
                amount,
                "SUCCESS"
        );
    }

    @Override
    public boolean refund(String transactionId) {

        paypal.issueRefund(transactionId);

        return true;
    }
}

/* ============================================================
                    ADAPTER #3
   ============================================================ */

class CryptoAdapter implements PaymentProcessor {

    private final CryptoExchange exchange;

    public CryptoAdapter(CryptoExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public PaymentReceipt processPayment(
            double amount,
            String currency,
            String customer) {

        CryptoResult result =
                exchange.transfer(
                        customer,
                        amount,
                        "BTC"
                );

        return new PaymentReceipt(
                "Crypto",
                result.hash,
                amount,
                result.state
        );
    }

    @Override
    public boolean refund(String transactionId) {

        return exchange.reverse(transactionId);
    }
}

/* ============================================================
                    LEGACY API #1
   ============================================================ */

class StripeGateway {

    public StripeResponse makeCharge(
            double amount,
            String currency,
            String customer) {

        System.out.println(
                "[Stripe] Charging "
                        + customer
                        + " "
                        + amount
                        + " "
                        + currency);

        return new StripeResponse(
                UUID.randomUUID().toString(),
                "APPROVED"
        );
    }

    public boolean reverseCharge(String id) {

        System.out.println(
                "[Stripe] Refunding " + id);

        return true;
    }
}

class StripeResponse {

    String id;
    String status;

    public StripeResponse(String id,
                          String status) {

        this.id = id;
        this.status = status;
    }
}

/* ============================================================
                    LEGACY API #2
   ============================================================ */

class PayPalGateway {

    public String sendMoney(
            String account,
            double value) {

        System.out.println(
                "[PayPal] Sending payment to "
                        + account);

        return "PAY-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8);
    }

    public void issueRefund(String id) {

        System.out.println(
                "[PayPal] Refund " + id);
    }
}

/* ============================================================
                    LEGACY API #3
   ============================================================ */

class CryptoExchange {

    public CryptoResult transfer(
            String wallet,
            double dollars,
            String coin) {

        System.out.println(
                "[Crypto] Buying "
                        + coin
                        + " for "
                        + wallet);

        return new CryptoResult(
                UUID.randomUUID()
                        .toString(),
                "CONFIRMED"
        );
    }

    public boolean reverse(String hash) {

        System.out.println(
                "[Crypto] Reverse " + hash);

        return true;
    }
}

class CryptoResult {

    String hash;
    String state;

    public CryptoResult(
            String hash,
            String state) {

        this.hash = hash;
        this.state = state;
    }
}
