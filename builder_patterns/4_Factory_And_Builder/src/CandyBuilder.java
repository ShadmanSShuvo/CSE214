public class CandyBuilder {
    private String type;
    private String flavor;
    private String color;

    // Constructor forces the type parameter (usually assigned via the Factory)
    public CandyBuilder(String type) {
        this.type = type;
    }

    public CandyBuilder setFlavor(String flavor) {
        this.flavor = flavor;
        return this;
    }

    public CandyBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public Candy build() {
        return new Candy(type, flavor, color);
    }
}