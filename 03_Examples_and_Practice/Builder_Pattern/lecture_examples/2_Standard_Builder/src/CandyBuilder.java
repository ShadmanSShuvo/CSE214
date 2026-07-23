public class CandyBuilder {
    private String name;
    private String color;
    private String flavor;
    private int sweetness;

    // Standard void setters
    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public void setSweetness(int sweetness) {
        this.sweetness = sweetness;
    }

    public Candy build() {
        return new Candy(name, color, flavor, sweetness);
    }
}