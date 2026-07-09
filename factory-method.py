import os
import zipfile

# Define the file contents for all 4 codebases
codebases = {
    # 1. No Factory
    "1_No_Factory/src/Candy.java": """public abstract class Candy {
    public abstract String name();
}""",
    "1_No_Factory/src/Starburst.java": """public class Starburst extends Candy {
    @Override
    public String name() {
        return "Starburst";
    }
}""",
    "1_No_Factory/src/Gumdrop.java": """public class Gumdrop extends Candy {
    @Override
    public String name() {
        return "Gumdrop";
    }
}""",
    "1_No_Factory/src/CandyStore.java": """public class CandyStore {
    public Candy sellCandy(String type) {
        switch(type){
            case "Starburst":
                return new Starburst();
            case "Gumdrop":
                return new Gumdrop();
            default:
                throw new IllegalArgumentException();
        }
    }
}""",
    "1_No_Factory/src/Main.java": """public class Main {
    public static void main(String[] args) {
        CandyStore store = new CandyStore();
        Candy candy = store.sellCandy("Starburst");
        System.out.println(candy.name());
    }
}""",

    # 2. Simple Factory
    "2_Simple_Factory/src/Candy.java": """public abstract class Candy {
    public abstract String name();
}""",
    "2_Simple_Factory/src/Starburst.java": """public class Starburst extends Candy {
    @Override
    public String name() {
        return "Starburst";
    }
}""",
    "2_Simple_Factory/src/Gumdrop.java": """public class Gumdrop extends Candy {
    @Override
    public String name() {
        return "Gumdrop";
    }
}""",
    "2_Simple_Factory/src/CandyFactory.java": """public final class CandyFactory {
    public static Candy getCandy(String type){
        switch(type){
            case "Starburst":
                return new Starburst();
            case "Gumdrop":
                return new Gumdrop();
            default:
                throw new IllegalArgumentException();
        }
    }
}""",
    "2_Simple_Factory/src/CandyStore.java": """public class CandyStore {
    public Candy sellCandy(String type){
        return CandyFactory.getCandy(type);
    }
}""",
    "2_Simple_Factory/src/Main.java": """public class Main {
    public static void main(String[] args){
        CandyStore store = new CandyStore();
        Candy candy = store.sellCandy("Gumdrop");
        System.out.println(candy.name());
    }
}""",

    # 3. Factory Method Pattern
    "3_Factory_Method/src/Candy.java": """public abstract class Candy {
    public abstract String name();
}""",
    "3_Factory_Method/src/Starburst.java": """public class Starburst extends Candy {
    @Override
    public String name() {
        return "Starburst";
    }
}""",
    "3_Factory_Method/src/Gumdrop.java": """public class Gumdrop extends Candy {
    @Override
    public String name() {
        return "Gumdrop";
    }
}""",
    "3_Factory_Method/src/CandyStore.java": """public abstract class CandyStore {
    protected abstract Candy createCandy();
    public Candy sellCandy(){
        Candy candy = createCandy();
        System.out.println("Selling " + candy.name());
        return candy;
    }
}""",
    "3_Factory_Method/src/StarburstStore.java": """public class StarburstStore extends CandyStore {
    @Override
    protected Candy createCandy() {
        return new Starburst();
    }
}""",
    "3_Factory_Method/src/GumdropStore.java": """public class GumdropStore extends CandyStore {
    @Override
    protected Candy createCandy() {
        return new Gumdrop();
    }
}""",
    "3_Factory_Method/src/Main.java": """public class Main {
    public static void main(String[] args){
        CandyStore store1 = new StarburstStore();
        CandyStore store2 = new GumdropStore();
        store1.sellCandy();
        store2.sellCandy();
    }
}""",

    # 4. Abstract Factory
    "4_Abstract_Factory/src/Starburst.java": """public interface Starburst {
    String name();
}""",
    "4_Abstract_Factory/src/Gumdrop.java": """public interface Gumdrop {
    String name();
}""",
    "4_Abstract_Factory/src/AmericanStarburst.java": """public class AmericanStarburst implements Starburst {
    @Override
    public String name() {
        return "American Starburst";
    }
}""",
    "4_Abstract_Factory/src/JapaneseStarburst.java": """public class JapaneseStarburst implements Starburst {
    @Override
    public String name() {
        return "Japanese Starburst";
    }
}""",
    "4_Abstract_Factory/src/AmericanGumdrop.java": """public class AmericanGumdrop implements Gumdrop {
    @Override
    public String name() {
        return "American Gumdrop";
    }
}""",
    "4_Abstract_Factory/src/JapaneseGumdrop.java": """public class JapaneseGumdrop implements Gumdrop {
    @Override
    public String name() {
        return "Japanese Gumdrop";
    }
}""",
    "4_Abstract_Factory/src/CandyFactory.java": """public interface CandyFactory {
    Starburst createStarburst();
    Gumdrop createGumdrop();
}""",
    "4_Abstract_Factory/src/AmericanCandyFactory.java": """public class AmericanCandyFactory implements CandyFactory {
    @Override
    public Starburst createStarburst() {
        return new AmericanStarburst();
    }
    @Override
    public Gumdrop createGumdrop() {
        return new AmericanGumdrop();
    }
}""",
    "4_Abstract_Factory/src/JapaneseCandyFactory.java": """public class JapaneseCandyFactory implements CandyFactory {
    @Override
    public Starburst createStarburst() {
        return new JapaneseStarburst();
    }
    @Override
    public Gumdrop createGumdrop() {
        return new JapaneseGumdrop();
    }
}""",
    "4_Abstract_Factory/src/Main.java": """public class Main {
    public static void main(String[] args){
        CandyFactory factory = new AmericanCandyFactory();
        Starburst s = factory.createStarburst();
        Gumdrop g = factory.createGumdrop();
        System.out.println(s.name());
        System.out.println(g.name());
    }
}"""
}

zip_filename = "factory_patterns.zip"

print("Generating codebase directories and files...")
with zipfile.ZipFile(zip_filename, 'w', zipfile.ZIP_DEFLATED) as zipf:
    for filepath, content in codebases.items():
        # Write directly into the zip archive buffer
        zipf.writestr(filepath, content.strip())

print(f"Success! Created {zip_filename} containing all 4 pattern codebases structured cleanly.")