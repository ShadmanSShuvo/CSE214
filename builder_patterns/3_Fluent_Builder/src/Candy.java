public class Candy {
    private String name;
    private String color;
    private String flavor;
    private int sweetness;

    Candy(String name, String color, String flavor, int sweetness) {
        this.name = name;
        this.color = color;
        this.flavor = flavor;
        this.sweetness = sweetness;
    }

    public void display() {
        System.out.println("Fluent Candy -> Name: " + name + ", Flavor: " + flavor);
    }
}