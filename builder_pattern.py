import os
import zipfile

# Define the file contents for all 4 Builder pattern codebases
codebases = {
    # 1. No Builder (Telescoping Constructor / Overloading Nightmare)
    "1_No_Builder/src/Candy.java": """public class Candy {
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
}""",
    "1_No_Builder/src/Main.java": """public class Main {
    public static void main(String[] args) {
        // Problem 1: Unreadable parameters. What do 8 and 15 represent?
        Candy starburst = new Candy("Starburst", "Yellow", "Orange", 8, 15, false);
        starburst.display();

        // Problem 2: The "Null/Zero" graveyard just to use default options
        Candy mysteryCandy = new Candy("Mystery", null, "Cherry", 0, 0, false);
        mysteryCandy.display();
    }
}""",

    # 2. Standard Builder (Separated construction, line-by-line configuration)
    "2_Standard_Builder/src/Candy.java": """public class Candy {
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
}""",
    "2_Standard_Builder/src/CandyBuilder.java": """public class CandyBuilder {
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
}""",
    "2_Standard_Builder/src/Main.java": """public class Main {
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
}""",

    # 3. Fluent Interface / Method Chaining (Elegant configuration + Validation)
    "3_Fluent_Builder/src/Candy.java": """public class Candy {
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
}""",
    "3_Fluent_Builder/src/CandyBuilder.java": """public class CandyBuilder {
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
}""",
    "3_Fluent_Builder/src/Main.java": """public class Main {
    public static void main(String[] args) {
        // Clean, readable, and perfectly scoped fluent execution
        Candy luxuryCandy = new CandyBuilder()
                                .setName("Luxury Chocolate")
                                .setFlavor("Mint")
                                .setColor("Dark Brown")
                                .setSweetness(4)
                                .build();
                                
        luxuryCandy.display();
    }
}""",

    # 4. Factory + Builder Pattern (Combining structural choice with customization)
    "4_Factory_And_Builder/src/Candy.java": """public class Candy {
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
}""",
    "4_Factory_And_Builder/src/CandyBuilder.java": """public class CandyBuilder {
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
}""",
    "4_Factory_And_Builder/src/CandyFactory.java": """public class CandyFactory {
    
    // The factory abstracts away which core base builder configuration you receive
    public static CandyBuilder createStarburstBuilder() {
        return new CandyBuilder("Starburst")
                    .setColor("Pink"); // sets structural defaults specific to Starbursts
    }

    public static CandyBuilder createGumdropBuilder() {
        return new CandyBuilder("Gumdrop")
                    .setColor("Red"); // sets structural defaults specific to Gumdrops
    }
}""",
    "4_Factory_And_Builder/src/Main.java": """public class Main {
    public static void main(String[] args) {
        // 1. Factory manages the variety selection
        // 2. Builder refines the custom micro-attributes
        Candy customStarburst = CandyFactory.createStarburstBuilder()
                                            .setFlavor("Strawberry")
                                            .build();

        Candy customGumdrop = CandyFactory.createGumdropBuilder()
                                          .setFlavor("Spiced Cinnamon")
                                          .build();

        customStarburst.display();
        customGumdrop.display();
    }
}"""
}

zip_filename = "builder_patterns.zip"

print("Parsing structural layout and files...")
with zipfile.ZipFile(zip_filename, 'w', zipfile.ZIP_DEFLATED) as zipf:
    for filepath, content in codebases.items():
        # Clean spacing boundaries and package directly into the zip target stream
        zipf.writestr(filepath, content.strip())

print(f"\\nSuccess! '{zip_filename}' generated dynamically. Extract it to view all 4 clean steps.")