public class CryptoPayment implements PaymentStrategy {
    private final String cryptoCurrency; // e.g. Bitcoin (BTC), Ethereum (ETH)
    private final String walletAddress;

    public CryptoPayment(String cryptoCurrency, String walletAddress) {
        this.cryptoCurrency = cryptoCurrency;
        this.walletAddress = walletAddress;
    }

    @Override
    public String getMethodName() {
        return "Cryptocurrency [" + cryptoCurrency + "] (" + walletAddress + ")";
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[Payment Processing - Cryptocurrency]");
        System.out.println("Coin: " + cryptoCurrency);
        System.out.println("Destination Wallet: " + walletAddress);
        System.out.println("Generating transaction hash & broadcasting to network...");
        System.out.printf("Payment equivalent to $%.2f confirmed on blockchain ledger.\n", amount);
        return true;
    }
}
