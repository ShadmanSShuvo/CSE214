import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ==========================================
// 1. COMPONENT INTERFACE
// ==========================================
interface Potion {
    String getName();

    List<String> getIngredients();

    double getCostPerJar();

    double getWeightPerJarGrams(); // in grams

    double getPenaltyPointsPerJar(); // 2 points per gram wasted
}

// ==========================================
// 2. CONCRETE COMPONENT (Base Potion)
// ==========================================
class MakersSolution implements Potion {
    private static final double INGREDIENT_QTY_GRAMS = 25.0;
    private static final double WHITE_SPIRIT_RATE = 1.23 / 10.0; // $0.123 per gram
    private static final double CASTOR_OIL_RATE = 2.47 / 10.0; // $0.247 per gram

    @Override
    public String getName() {
        return "Maker's Solution Base";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("White Spirit (25g)");
        ingredients.add("Castor Oil (25g)");
        return ingredients;
    }

    @Override
    public double getCostPerJar() {
        return (INGREDIENT_QTY_GRAMS * WHITE_SPIRIT_RATE) + (INGREDIENT_QTY_GRAMS * CASTOR_OIL_RATE);
    }

    @Override
    public double getWeightPerJarGrams() {
        return INGREDIENT_QTY_GRAMS + INGREDIENT_QTY_GRAMS; // 50g total base weight
    }

    @Override
    public double getPenaltyPointsPerJar() {
        return getWeightPerJarGrams() * 2.0;
    }
}

// ==========================================
// 3. ABSTRACT DECORATOR
// ==========================================
abstract class PotionDecorator implements Potion {
    protected Potion decoratedPotion;

    public PotionDecorator(Potion potion) {
        this.decoratedPotion = potion;
    }

    @Override
    public List<String> getIngredients() {
        return decoratedPotion.getIngredients();
    }

    @Override
    public double getCostPerJar() {
        return decoratedPotion.getCostPerJar();
    }

    @Override
    public double getWeightPerJarGrams() {
        return decoratedPotion.getWeightPerJarGrams();
    }

    @Override
    public double getPenaltyPointsPerJar() {
        return this.getWeightPerJarGrams() * 2.0;
    }
}

// ==========================================
// 4. CONCRETE DECORATORS (Special Ingredients)
// ==========================================
class PolyjuicePotion extends PotionDecorator {
    private static final double POISON_IVY_RATE = 3.38 / 10.0; // $0.338 per gram
    private static final double QTY_GRAMS = 25.0;

    public PolyjuicePotion(Potion basePotion) {
        super(basePotion);
    }

    @Override
    public String getName() {
        return "Polyjuice Potion";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Poison Ivy (25g)");
        return ingredients;
    }

    @Override
    public double getCostPerJar() {
        return super.getCostPerJar() + (QTY_GRAMS * POISON_IVY_RATE);
    }

    @Override
    public double getWeightPerJarGrams() {
        return super.getWeightPerJarGrams() + QTY_GRAMS;
    }
}

class FelixFelicis extends PotionDecorator {
    private static final double UNICORN_HORN_RATE = 6.31 / 10.0; // $0.631 per gram
    private static final double QTY_GRAMS = 25.0;

    public FelixFelicis(Potion basePotion) {
        super(basePotion);
    }

    @Override
    public String getName() {
        return "Felix Felicis";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Unicorn Horn (25g)");
        return ingredients;
    }

    @Override
    public double getCostPerJar() {
        return super.getCostPerJar() + (QTY_GRAMS * UNICORN_HORN_RATE);
    }

    @Override
    public double getWeightPerJarGrams() {
        return super.getWeightPerJarGrams() + QTY_GRAMS;
    }
}

class Veritaserum extends PotionDecorator {
    private static final double DRAGON_KIDNEY_RATE = 5.86 / 10.0; // $0.586 per gram
    private static final double QTY_GRAMS = 25.0;

    public Veritaserum(Potion basePotion) {
        super(basePotion);
    }

    @Override
    public String getName() {
        return "Veritaserum";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Dragon Kidney (25g)");
        return ingredients;
    }

    @Override
    public double getCostPerJar() {
        return super.getCostPerJar() + (QTY_GRAMS * DRAGON_KIDNEY_RATE);
    }

    @Override
    public double getWeightPerJarGrams() {
        return super.getWeightPerJarGrams() + QTY_GRAMS;
    }
}

class SkeleGro extends PotionDecorator {
    private static final double CHINESE_CABBAGE_RATE = 4.13 / 10.0; // $0.413 per gram
    private static final double QTY_GRAMS = 25.0;

    public SkeleGro(Potion basePotion) {
        super(basePotion);
    }

    @Override
    public String getName() {
        return "Skele-Gro";
    }

    @Override
    public List<String> getIngredients() {
        List<String> ingredients = super.getIngredients();
        ingredients.add("Chinese Chomping Cabbage (25g)");
        return ingredients;
    }

    @Override
    public double getCostPerJar() {
        return super.getCostPerJar() + (QTY_GRAMS * CHINESE_CABBAGE_RATE);
    }

    @Override
    public double getWeightPerJarGrams() {
        return super.getWeightPerJarGrams() + QTY_GRAMS;
    }
}

// ==========================================
// 5. FACTORY PATTERN
// ==========================================
class PotionFactory {
    public static Potion createPotion(String potionType) {
        Potion base = new MakersSolution();
        switch (potionType.trim().toLowerCase()) {
            case "polyjuice potion":
            case "polyjuice":
                return new PolyjuicePotion(base);
            case "felix felicis":
            case "felix":
                return new FelixFelicis(base);
            case "veritaserum":
                return new Veritaserum(base);
            case "skele-gro":
            case "skelegro":
                return new SkeleGro(base);
            default:
                throw new IllegalArgumentException("Unknown potion type: " + potionType);
        }
    }
}

// ==========================================
// 6. MAIN APPLICATION / CLI
// ==========================================
public class HogwartsPotionSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("   Hogwarts Potions & Penalty Management System   ");
        System.out.println("            Professor Severus Snape               ");
        System.out.println("==================================================");

        System.out.print("Enter Potion Name (Polyjuice, Felix Felicis, Veritaserum, Skele-Gro): ");
        String potionName = scanner.nextLine();

        System.out.print("Enter Quantity (Number of Jars): ");
        int jarCount = scanner.nextInt();

        try {
            Potion potion = PotionFactory.createPotion(potionName);

            double totalCost = potion.getCostPerJar() * jarCount;
            double totalWeightGrams = potion.getWeightPerJarGrams() * jarCount;
            double totalPenaltyPoints = potion.getPenaltyPointsPerJar() * jarCount;

            System.out.println("\n----------------- POTION REPORT -----------------");
            System.out.printf("Potion Name        : %s\n", potion.getName());
            System.out.printf("Quantity Requested : %d Jar(s)\n", jarCount);
            System.out.println("Required Ingredients:");
            for (String ingredient : potion.getIngredients()) {
                System.out.println("  - " + ingredient);
            }
            System.out.println("--------------------------------------------------");
            System.out.printf("Cost Per Jar       : $%.2f\n", potion.getCostPerJar());
            System.out.printf("Total Cost         : $%.2f\n", totalCost);
            System.out.printf("Total Weight       : %.1fg\n", totalWeightGrams);
            System.out.printf("Gryffindor Penalty : %.0f Points (if botched)\n", totalPenaltyPoints);
            System.out.println("--------------------------------------------------");

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }

        scanner.close();
    }
}
