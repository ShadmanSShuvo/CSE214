// B: Restaurant Meal Plan - Builder Pattern
// Task: Construct a Meal (starter, main, dessert) step by step,
// for either a Bengali or Chinese meal, using a Director.

// ---------- Product ----------
class Meal {
    private String starter;
    private String mainDish;
    private String dessert;

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public void setMainDish(String mainDish) {
        this.mainDish = mainDish;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    @Override
    public String toString() {
        return "Meal [Starter=" + starter + ", Main Dish=" + mainDish + ", Dessert=" + dessert + "]";
    }
}

// ---------- Builder Interface ----------
interface MealBuilder {
    void buildStarter();
    void buildMainDish();
    void buildDessert();
    Meal getMeal();
}

// ---------- Concrete Builder: Bengali ----------
class BengaliMealBuilder implements MealBuilder {
    private Meal meal = new Meal();

    @Override
    public void buildStarter() {
        meal.setStarter("Vegetable");
    }

    @Override
    public void buildMainDish() {
        meal.setMainDish("Chicken Curry");
    }

    @Override
    public void buildDessert() {
        meal.setDessert("Sweet Curd");
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}

// ---------- Concrete Builder: Chinese ----------
class ChineseMealBuilder implements MealBuilder {
    private Meal meal = new Meal();

    @Override
    public void buildStarter() {
        meal.setStarter("Soup");
    }

    @Override
    public void buildMainDish() {
        meal.setMainDish("Peking Duck");
    }

    @Override
    public void buildDessert() {
        meal.setDessert("Pudding");
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}

// ---------- Director ----------
class MealDirector {
    private MealBuilder builder;

    public MealDirector(MealBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(MealBuilder builder) {
        this.builder = builder;
    }

    // Same construction process, regardless of concrete builder used
    public Meal constructMeal() {
        builder.buildStarter();
        builder.buildMainDish();
        builder.buildDessert();
        return builder.getMeal();
    }
}

// ---------- Client ----------
public class B_MealBuilder {
    public static void main(String[] args) {
        MealDirector director = new MealDirector(new BengaliMealBuilder());
        Meal bengaliMeal = director.constructMeal();
        System.out.println(bengaliMeal);

        director.setBuilder(new ChineseMealBuilder());
        Meal chineseMeal = director.constructMeal();
        System.out.println(chineseMeal);
    }
}
