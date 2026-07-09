public class Main {
    public static void main(String[] args) {
        // Problem 1: Unreadable parameters. What do 8 and 15 represent?
        Candy starburst = new Candy("Starburst", "Yellow", "Orange", 8, 15, false);
        starburst.display();

        // Problem 2: The "Null/Zero" graveyard just to use default options
        Candy mysteryCandy = new Candy("Mystery", null, "Cherry", 0, 0, false);
        mysteryCandy.display();
    }
}