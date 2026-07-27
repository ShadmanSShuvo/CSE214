public class Candy {
    private String type; // e.g., Starburst, Gumdrop
    private String flavor;
    private String color;

    public Candy(String type, String flavor, String color) {
        this.type = type;
        this.flavor = flavor;
        this.color = color;
    }

    public void display() {
        System.out.println("Factory-Allocated Candy Type [" + type + "] configured with Flavor: " + flavor);
    }
}