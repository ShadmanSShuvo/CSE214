public class Candy {
    private String name;
    private String color;
    private String flavor;
    private int sweetness;
    private int weight;
    private boolean sugarFree;

    // The "Mega Constructor" that forces you to remember parameter order
    public Candy(String name, String color, String flavor, int sweetness, int weight, boolean sugarFree) {
        this.name = name;
        this.color = color;
        this.flavor = flavor;
        this.sweetness = sweetness;
        this.weight = weight;
        this.sugarFree = sugarFree;
    }

    // Overloaded constructor attempt to handle fewer arguments
    public Candy(String name, String flavor) {
        this(name, null, flavor, 0, 0, false);
    }

    public void display() {
        System.out.println("Candy: " + name + " | Flavor: " + flavor + " | Color: " + color + 
                           " | Sweetness: " + sweetness + " | Weight: " + weight + "g | SugarFree: " + sugarFree);
    }
}