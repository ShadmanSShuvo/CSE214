public class Main {
    public static void main(String[] args) {
        // 1. Factory manages the variety selection
        // 2. Builder refines the custom micro-attributes
        Candy customStarburst = CandyFactory.createStarburstBuilder()
                                            .setFlavor("Strawberry")
                                            .build();

        Candy customGumdrop = CandyFactory.createGumdropBuilder()
                                          .setFlavor("Spiced Cinnamon")
                                          .build();

        customStarburst.display();
        customGumdrop.display();
    }
}