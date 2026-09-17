// C1: Bicycle Manufacturer - Builder Pattern
// Task: Construct Bicycle objects step-by-step (frame, gears, tires),
// using a Director so the client only asks for a model.

// ---------- Product ----------
class Bicycle {
    private String frame;
    private String gearSystem;
    private String tireType;

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public void setGearSystem(String gearSystem) {
        this.gearSystem = gearSystem;
    }

    public void setTireType(String tireType) {
        this.tireType = tireType;
    }

    @Override
    public String toString() {
        return "Bicycle [Frame=" + frame + ", Gear System=" + gearSystem + ", Tire Type=" + tireType + "]";
    }
}

// ---------- Builder Interface ----------
interface BicycleBuilder {
    void buildFrame();
    void buildGearSystem();
    void buildTireType();
    Bicycle getBicycle();
}

// ---------- Concrete Builder: Commuter ----------
class CommuterBuilder implements BicycleBuilder {
    private Bicycle bicycle = new Bicycle();

    @Override
    public void buildFrame() {
        bicycle.setFrame("Aluminum Frame");
    }

    @Override
    public void buildGearSystem() {
        bicycle.setGearSystem("Single Speed Gear");
    }

    @Override
    public void buildTireType() {
        bicycle.setTireType("Road Tires");
    }

    @Override
    public Bicycle getBicycle() {
        return bicycle;
    }
}

// ---------- Concrete Builder: Mountain Beast ----------
class MountainBeastBuilder implements BicycleBuilder {
    private Bicycle bicycle = new Bicycle();

    @Override
    public void buildFrame() {
        bicycle.setFrame("Carbon Fiber Frame");
    }

    @Override
    public void buildGearSystem() {
        bicycle.setGearSystem("12-Speed Gear");
    }

    @Override
    public void buildTireType() {
        bicycle.setTireType("Off-road Grip Tires");
    }

    @Override
    public Bicycle getBicycle() {
        return bicycle;
    }
}

// ---------- Director ----------
class BicycleFactoryDirector {
    private BicycleBuilder builder;

    public BicycleFactoryDirector(BicycleBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(BicycleBuilder builder) {
        this.builder = builder;
    }

    // Directs the step-by-step construction process
    public Bicycle constructBicycle() {
        builder.buildFrame();
        builder.buildGearSystem();
        builder.buildTireType();
        return builder.getBicycle();
    }
}

// ---------- Client ----------
public class C1_BicycleBuilder {
    public static void main(String[] args) {
        BicycleFactoryDirector director = new BicycleFactoryDirector(new CommuterBuilder());
        Bicycle commuter = director.constructBicycle();
        System.out.println(commuter);

        director.setBuilder(new MountainBeastBuilder());
        Bicycle mountainBeast = director.constructBicycle();
        System.out.println(mountainBeast);
    }
}
