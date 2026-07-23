public class Candy {
    private String name;
    private String color;
    private String flavor;
    private int sweetness;

    // Package-private constructor; Client shouldn't call this directly
    Candy(String name, String color, String flavor, int sweetness) {
        this.name = name;
        this.color = color;
        this.flavor = flavor;
        this.sweetness = sweetness;
    }

    public void display() {
        System.out.println("Candy: " + name + " [Color=" + color + ", Flavor=" + flavor + ", Sweetness=" + sweetness + "]");
    }
}