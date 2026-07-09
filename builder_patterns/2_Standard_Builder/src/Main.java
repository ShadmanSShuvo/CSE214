public class Main {
    public static void main(String[] args) {
        CandyBuilder builder = new CandyBuilder();
        
        // Configured step-by-step. Readable, but requires a lot of repetitive variable typing
        builder.setName("Gumdrop");
        builder.setColor("Red");
        builder.setFlavor("Cherry");
        builder.setSweetness(6);

        Candy candy = builder.build();
        candy.display();
    }
}