// Component interface
interface Coffee {
    String getDescription();

    double getCost();
}

// Concrete Component (base object)
class SimpleCoffee implements Coffee {

    @Override
    public String getDescription() {
        return "Simple coffee";
    }

    @Override
    public double getCost() {
        return 1.00;
    }
}

// Base Decorator - also implements Coffee (same interface)
abstract class CoffeeDecorator implements Coffee {

    protected Coffee wrappee; // holds the wrapped object

    public CoffeeDecorator(Coffee coffee) {
        this.wrappee = coffee;
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription();
    }

    @Override
    public double getCost() {
        return wrappee.getCost();
    }
}

// Concrete Decorator - Milk
class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return wrappee.getCost() + 0.25;
    }
}

// Concrete Decorator - Sugar
class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return wrappee.getCost() + 0.10;
    }
}

// Client
public class DecoratorDemo {

    public static void main(String[] args) {

        Coffee myCoffee = new SimpleCoffee(); // $1.00
        myCoffee = new MilkDecorator(myCoffee); // $1.25
        myCoffee = new SugarDecorator(myCoffee); // $1.35

        System.out.println(myCoffee.getDescription());
        System.out.println("$" + myCoffee.getCost());
    }
}
