public class CandyBuilder {
    private String name;
    private String color;
    private String flavor;
    private int sweetness;

    // Setters return 'this' to allow method chaining
    public CandyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CandyBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public CandyBuilder setFlavor(String flavor) {
        this.flavor = flavor;
        return this;
    }

    public CandyBuilder setSweetness(int sweetness) {
        this.sweetness = sweetness;
        return this;
    }

    public Candy build() {
        // Great place to enforce business validation logic before creation
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Candy must have a designated name!");
        }
        return new Candy(name, color, flavor, sweetness);
    }
}