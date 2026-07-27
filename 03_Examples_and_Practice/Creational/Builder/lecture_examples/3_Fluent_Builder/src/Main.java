public class Main {
    public static void main(String[] args) {
        // Clean, readable, and perfectly scoped fluent execution
        Candy luxuryCandy = new CandyBuilder()
                                .setName("Luxury Chocolate")
                                .setFlavor("Mint")
                                .setColor("Dark Brown")
                                .setSweetness(4)
                                .build();
                                
        luxuryCandy.display();
    }
}