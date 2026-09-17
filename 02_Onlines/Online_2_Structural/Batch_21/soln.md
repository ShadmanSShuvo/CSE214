Six structural design pattern problems — Adapter, Composite, Decorator (twice), Decorator, and Adapter again. Let me give you clean, complete Java solutions for each.

## A1 — Adapter Pattern (Weather Service)

```java
// Legacy Weather Service, which we cannot modify
class LegacyWeatherService {
    public String getWeatherData() {
        return "Legacy weather data";
    }
}

// Client Interface expected by the application
interface WeatherProvider {
    String fetchWeather();
}

// Adapter: wraps LegacyWeatherService, exposes WeatherProvider interface
class WeatherServiceAdapter implements WeatherProvider {
    private LegacyWeatherService legacyWeatherService;

    public WeatherServiceAdapter(LegacyWeatherService legacyWeatherService) {
        this.legacyWeatherService = legacyWeatherService;
    }

    @Override
    public String fetchWeather() {
        return legacyWeatherService.getWeatherData();
    }
}

class WeatherApp {
    private WeatherProvider weatherProvider;

    public WeatherApp(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public void displayWeather() {
        System.out.println(weatherProvider.fetchWeather());
    }
}

public class A1 {
    public static void main(String[] args) {
        LegacyWeatherService legacyWeatherService = new LegacyWeatherService();
        WeatherProvider adapter = new WeatherServiceAdapter(legacyWeatherService);
        WeatherApp app = new WeatherApp(adapter);
        app.displayWeather(); // Output: Legacy weather data
    }
}
```

**Why Adapter:** you have an incompatible interface (`getWeatherData`) that must satisfy a target interface (`fetchWeather`) without touching the source class. The adapter is a thin translation wrapper — textbook use case.

---

## A2 — Composite Pattern (Computer Hardware Bundles)

```java
import java.util.ArrayList;
import java.util.List;

// Component: common interface for leaves and composites
interface HardwareComponent {
    double getPrice();
    String getName();
}

// Leaf: individual hardware part
class IndividualComponent implements HardwareComponent {
    private String name;
    private double price;

    public IndividualComponent(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}

// Composite: a bundle that can hold components or other bundles
class Bundle implements HardwareComponent {
    private String name;
    private List<HardwareComponent> components = new ArrayList<>();

    public Bundle(String name) {
        this.name = name;
    }

    public void add(HardwareComponent component) {
        components.add(component);
    }

    public void remove(HardwareComponent component) {
        components.remove(component);
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (HardwareComponent component : components) {
            total += component.getPrice();
        }
        return total;
    }

    @Override
    public String getName() {
        return name;
    }

    public void printStructure(String indent) {
        System.out.println(indent + name + " -> " + getPrice());
        for (HardwareComponent component : components) {
            if (component instanceof Bundle) {
                ((Bundle) component).printStructure(indent + "  ");
            } else {
                System.out.println(indent + "  " + component.getName() + " -> " + component.getPrice());
            }
        }
    }
}

public class A2 {
    public static void main(String[] args) {
        // Individual components
        HardwareComponent cpu = new IndividualComponent("CPU", 300);
        HardwareComponent memory = new IndividualComponent("Memory", 100);
        HardwareComponent storage = new IndividualComponent("Storage", 80);
        HardwareComponent gpu = new IndividualComponent("Graphics Card", 500);
        HardwareComponent extraFan = new IndividualComponent("Extra Cooling Fan", 40);

        // Basic Gaming Setup bundle
        Bundle basicGamingSetup = new Bundle("Basic Gaming Setup");
        basicGamingSetup.add(cpu);
        basicGamingSetup.add(memory);
        basicGamingSetup.add(gpu);

        System.out.println("Basic Gaming Setup Price: " + basicGamingSetup.getPrice());

        // Ultimate Gaming Setup contains Basic Gaming Setup + extras
        Bundle ultimateGamingSetup = new Bundle("Ultimate Gaming Setup");
        ultimateGamingSetup.add(basicGamingSetup);
        ultimateGamingSetup.add(storage);
        ultimateGamingSetup.add(extraFan);

        System.out.println("Ultimate Gaming Setup Price: " + ultimateGamingSetup.getPrice());

        // Demonstrate add/remove
        ultimateGamingSetup.remove(extraFan);
        System.out.println("Ultimate Gaming Setup Price after removing extra fan: "
                + ultimateGamingSetup.getPrice());

        System.out.println("\nStructure:");
        ultimateGamingSetup.printStructure("");
    }
}
```

**Why Composite:** individual components and bundles-of-bundles need to be treated uniformly through one interface (`getPrice()`), with bundles recursively composed of other bundles — the defining trait of Composite.

---

## B1 — Decorator Pattern (Delivery Discounts)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Step 1: Define the base Purchase component interface
interface Purchase {
    double calculatePrice();
}

// Concrete component: base purchase, no discount
class BasePurchase implements Purchase {
    private double price;

    public BasePurchase(double price) {
        this.price = price;
    }

    @Override
    public double calculatePrice() {
        return price;
    }
}

// Abstract decorator
abstract class DiscountDecorator implements Purchase {
    protected Purchase wrappedPurchase;

    public DiscountDecorator(Purchase purchase) {
        this.wrappedPurchase = purchase;
    }

    @Override
    public double calculatePrice() {
        return wrappedPurchase.calculatePrice();
    }
}

// Concrete Decorator 1: Loyalty Discount (10% off)
class LoyaltyDiscount extends DiscountDecorator {
    public LoyaltyDiscount(Purchase purchase) {
        super(purchase);
    }

    @Override
    public double calculatePrice() {
        return wrappedPurchase.calculatePrice() * 0.90;
    }
}

// Concrete Decorator 2: Seasonal Discount (flat 100 units off)
class SeasonalDiscount extends DiscountDecorator {
    public SeasonalDiscount(Purchase purchase) {
        super(purchase);
    }

    @Override
    public double calculatePrice() {
        return wrappedPurchase.calculatePrice() - 100;
    }
}

// Concrete Decorator 3: High-Value Purchase Discount (2% off if > 10000)
class HighValueDiscount extends DiscountDecorator {
    public HighValueDiscount(Purchase purchase) {
        super(purchase);
    }

    @Override
    public double calculatePrice() {
        double price = wrappedPurchase.calculatePrice();
        return price;
    }
}

public class B1 {
    public static void main(String[] args) {
        double basePrice = 12000; // Initial price of the product
        boolean isPremiumMember = true;
        boolean isSeasonalPromotion = true;

        // Create a base purchase
        Purchase purchase = new BasePurchase(basePrice);

        // Apply discounts conditionally
        if (isPremiumMember) {
            purchase = new LoyaltyDiscount(purchase);
        }
        if (isSeasonalPromotion) {
            purchase = new SeasonalDiscount(purchase);
        }
        // High-value check must be based on the ORIGINAL amount, per the spec
        if (basePrice > 10000) {
            purchase = new HighValueDiscount(purchase) {
                @Override
                public double calculatePrice() {
                    return wrappedPurchase.calculatePrice() * 0.98;
                }
            };
        }

        Purchase discountedPurchase = purchase;

        // Calculate final price after all applicable discounts
        double finalPrice = discountedPurchase.calculatePrice();
        System.out.println("Final price after all discounts: " + finalPrice);
    }
}
```

A cleaner version of `HighValueDiscount` (no anonymous class needed) — I'd recommend this instead:

```java
class HighValueDiscount extends DiscountDecorator {
    public HighValueDiscount(Purchase purchase) {
        super(purchase);
    }

    @Override
    public double calculatePrice() {
        return wrappedPurchase.calculatePrice() * 0.98;
    }
}
```

Then in `main`, just do:
```java
if (basePrice > 10000) {
    purchase = new HighValueDiscount(purchase);
}
```

**Note on ordering:** since the 10,000-unit threshold is checked against the *original* purchase amount in this spec, evaluate that condition on `basePrice` before wrapping — not on the already-discounted running total. Discounts then stack in whatever order you apply the decorators.

**Why Decorator:** each discount adds behavior on top of an existing `Purchase` object, discounts combine independently and in any subset, and none of it requires subclassing every combination — Decorator's whole purpose.

---

## B2 — Decorator Pattern (Component Feature Add-ons)

```java
// Component interface representing the basic hardware component
interface Component {
    double getPrice();
    String getDescription();
}

// Concrete Component representing individual hardware components
class HardwareComponent implements Component {
    private String name;
    private double price;

    public HardwareComponent(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return name;
    }
}

// Abstract Decorator
abstract class ComponentDecorator implements Component {
    protected Component wrappedComponent;

    public ComponentDecorator(Component component) {
        this.wrappedComponent = component;
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice();
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription();
    }
}

// Concrete Decorator: Extended Warranty
class ExtendedWarranty extends ComponentDecorator {
    private static final double COST = 50;

    public ExtendedWarranty(Component component) {
        super(component);
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice() + COST;
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription() + " + Extended Warranty";
    }
}

// Concrete Decorator: Installation Service
class InstallationService extends ComponentDecorator {
    private static final double COST = 30;

    public InstallationService(Component component) {
        super(component);
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice() + COST;
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription() + " + Installation Service";
    }
}

// Concrete Decorator: Performance Boost
class PerformanceBoost extends ComponentDecorator {
    private static final double COST = 80;

    public PerformanceBoost(Component component) {
        super(component);
    }

    @Override
    public double getPrice() {
        return wrappedComponent.getPrice() + COST;
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription() + " + Performance Boost";
    }
}

public class B2 {
    public static void main(String[] args) {
        Component cpu = new HardwareComponent("CPU", 300);

        // Add features dynamically
        Component upgradedCpu = new PerformanceBoost(new ExtendedWarranty(cpu));

        System.out.println(upgradedCpu.getDescription() + " -> " + upgradedCpu.getPrice());

        Component gpu = new HardwareComponent("Graphics Card", 500);
        Component fullyLoadedGpu = new InstallationService(
                new ExtendedWarranty(new PerformanceBoost(gpu)));

        System.out.println(fullyLoadedGpu.getDescription() + " -> " + fullyLoadedGpu.getPrice());
    }
}
```

**Why Decorator:** you must add optional, stackable features to a component "without altering the core classes" — the prompt basically states the pattern's definition verbatim.

---

## C1 — Decorator Pattern (CoffeeTong)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Coffee {
    String getIngredients();
    int getCost();
}

// Base concrete components
class BasicBlackCoffee implements Coffee {
    @Override
    public String getIngredients() {
        return "Mug, Water, Grinded Coffee Beans";
    }

    @Override
    public int getCost() {
        return 100 + 30; // mug + coffee beans
    }
}

class BasicMilkCoffee implements Coffee {
    @Override
    public String getIngredients() {
        return "Mug, Milk, Grinded Coffee Beans";
    }

    @Override
    public int getCost() {
        return 100 + 30 + 50; // mug + coffee beans + milk
    }
}

// Abstract Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee wrappedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.wrappedCoffee = coffee;
    }

    @Override
    public String getIngredients() {
        return wrappedCoffee.getIngredients();
    }

    @Override
    public int getCost() {
        return wrappedCoffee.getCost();
    }
}

// Americano: black coffee + extra grinded coffee beans
class Americano extends CoffeeDecorator {
    public Americano(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getIngredients() {
        return wrappedCoffee.getIngredients() + ", Extra Grinded Coffee Beans";
    }

    @Override
    public int getCost() {
        return wrappedCoffee.getCost() + 30;
    }
}

// Cappuccino: milk coffee + cinnamon powder
class Cappuccino extends CoffeeDecorator {
    public Cappuccino(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getIngredients() {
        return wrappedCoffee.getIngredients() + ", Cinnamon Powder";
    }

    @Override
    public int getCost() {
        return wrappedCoffee.getCost() + 50;
    }
}

// Order class to handle multiple coffee orders
class Order {
    private List<Coffee> coffees = new ArrayList<>();

    public void addCoffee(Coffee coffee) {
        coffees.add(coffee);
    }

    public void printOrderDetails() {
        int totalCost = 0;
        int coffeeCount = 1;
        for (Coffee coffee : coffees) {
            System.out.println("Coffee " + coffeeCount + ":");
            System.out.println("Ingredients: " + coffee.getIngredients());
            System.out.println("Cost: " + coffee.getCost() + " taka");
            System.out.println();
            totalCost += coffee.getCost();
            coffeeCount++;
        }
        System.out.println("Total Cost for Order: " + totalCost + " taka");
    }
}

// Main Class
public class C1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();

        while (true) {
            System.out.println("Select coffee type (1: Americano, 2: Espresso, 3: Cappuccino, 4: Mocha, 0: Finish): ");
            int choice = scanner.nextInt();
            if (choice == 0) break;

            Coffee coffee;
            switch (choice) {
                case 1:
                    coffee = new Americano(new BasicBlackCoffee());
                    break;
                case 3:
                    coffee = new Cappuccino(new BasicMilkCoffee());
                    break;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }
            order.addCoffee(coffee);
        }
        order.printOrderDetails();
        scanner.close();
    }
}
```

**Why Decorator:** each coffee type is built by wrapping a base coffee (`BasicBlackCoffee`/`BasicMilkCoffee`) with an ingredient add-on, and new coffee types (Espresso, Mocha) can be added later as new decorators without touching existing classes — exactly what "easily extendable" is asking for.

---

## C2 — Adapter Pattern (SQL/NoSQL Integration)

```java
interface DatabaseQuery {
    void executeQuery(String query);
}

class SQLDatabase implements DatabaseQuery {
    @Override
    public void executeQuery(String sqlQuery) {
        System.out.println("Executing SQL query: " + sqlQuery);
    }
}

// Adaptee: incompatible interface
class NoSQLDatabase {
    public void runQuery(String noSQLQuery) {
        System.out.println("Executing NoSQL query: " + noSQLQuery);
    }
}

// Adapter: makes NoSQLDatabase conform to DatabaseQuery
class NoSQLDatabaseAdapter implements DatabaseQuery {
    private NoSQLDatabase noSQLDatabase;

    public NoSQLDatabaseAdapter(NoSQLDatabase noSQLDatabase) {
        this.noSQLDatabase = noSQLDatabase;
    }

    @Override
    public void executeQuery(String query) {
        noSQLDatabase.runQuery(query);
    }
}

public class C2 {
    public static void main(String[] args) {
        DatabaseQuery sqlDb = new SQLDatabase();
        sqlDb.executeQuery("SELECT * FROM users");

        NoSQLDatabase noSQLDatabase = new NoSQLDatabase();
        DatabaseQuery noSqlAdapter = new NoSQLDatabaseAdapter(noSQLDatabase);
        noSqlAdapter.executeQuery("{ find: 'users', filter: {} }");

        // Legacy code can treat both uniformly through DatabaseQuery
        DatabaseQuery[] databases = { sqlDb, noSqlAdapter };
        for (DatabaseQuery db : databases) {
            db.executeQuery("sample query");
        }
    }
}
```

**Why Adapter:** `NoSQLDatabase.runQuery` is functionally equivalent to `executeQuery` but has an incompatible method signature/name, and legacy client code must keep using the `DatabaseQuery` interface unmodified — a direct interface-translation problem.

---

**Quick pattern recap for your viva/exam:** A1 and C2 are Adapter (incompatible interface → target interface, no source modification). A2 is Composite (tree structure, uniform treatment of leaf and composite). B1, B2, and C1 are Decorator (dynamically stack optional behavior/cost onto a base object without subclass explosion). If you want, I can also write out the UML diagrams for each — useful for viva if your course wants pattern diagrams alongside code.
