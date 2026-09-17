// builder
// Product
class Bicycle {
    private String frame;
    private String gearSystem;
    private String tireType;

    public void setFrame(String frame) { this.frame = frame; }
    public void setGearSystem(String gearSystem) { this.gearSystem = gearSystem; }
    public void setTireType(String tireType) { this.tireType = tireType; }

    @Override
    public String toString() {
        return "Bicycle [Frame=" + frame + ", Gears=" + gearSystem + ", Tires=" + tireType + "]";
    }
}

// Abstract Builder Interface
interface BicycleBuilder {
    void buildFrame();
    void buildGearSystem();
    void buildTireType();
    Bicycle getBicycle();
}

// Concrete Builder 1: The Commuter
class CommuterBicycleBuilder implements BicycleBuilder {
    private Bicycle bicycle = new Bicycle();

    @Override
    public void buildFrame() { bicycle.setFrame("Aluminum Frame"); }
    @Override
    public void buildGearSystem() { bicycle.setGearSystem("Single Speed Gear"); }
    @Override
    public void buildTireType() { bicycle.setTireType("Road Tires"); }
    @Override
    public Bicycle getBicycle() { return this.bicycle; }
}

// Concrete Builder 2: The Mountain Beast
class MountainBeastBicycleBuilder implements BicycleBuilder {
    private Bicycle bicycle = new Bicycle();

    @Override
    public void buildFrame() { bicycle.setFrame("Carbon Fiber Frame"); }
    @Override
    public void buildGearSystem() { bicycle.setGearSystem("12-Speed Gear"); }
    @Override
    public void buildTireType() { bicycle.setTireType("Off-road Grip Tires"); }
    @Override
    public Bicycle getBicycle() { return this.bicycle; }
}

// Director Class responsible for execution flow orchestration
class BicycleFactoryDirector {
    private BicycleBuilder builder;

    public void setBuilder(BicycleBuilder builder) {
        this.builder = builder;
    }

    public Bicycle assembleBicycle() {
        builder.buildFrame();
        builder.buildGearSystem();
        builder.buildTireType();
        return builder.getBicycle();
    }
}

// Client application
public class MainC1 {
    public static void main(String[] args) {
        BicycleFactoryDirector director = new BicycleFactoryDirector();

        // Target Model: The Commuter
        director.setBuilder(new CommuterBicycleBuilder());
        Bicycle commuter = director.assembleBicycle();
        System.out.println("Assembled model: " + commuter);

        // Target Model: The Mountain Beast
        director.setBuilder(new MountainBeastBicycleBuilder());
        Bicycle mountainBeast = director.assembleBicycle();
        System.out.println("Assembled model: " + mountainBeast);
    }
}