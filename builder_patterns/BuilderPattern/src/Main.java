public class Main {

    public static void main(String[] args) {

        Candy candy = new CandyBuilder()
                .setName("Starburst")
                .setColor("Yellow")
                .setFlavor("Orange")
                .setSweetness(8)
                .build();

        candy.display();

    }

}