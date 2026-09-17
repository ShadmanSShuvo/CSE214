// A2: Travel Agency - Builder Pattern
// Task: Construct HolidayPackage objects step by step, separating
// construction from representation, using a Director for standard packages.

// ---------- Product ----------
class HolidayPackage {
    private String flight;
    private String hotel;
    private String activity;

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "HolidayPackage [Flight=" + flight + ", Hotel=" + hotel + ", Activity=" + activity + "]";
    }
}

// ---------- Builder Interface ----------
interface HolidayPackageBuilder {
    void buildFlight();
    void buildHotel();
    void buildActivity();
    HolidayPackage getPackage();
}

// ---------- Concrete Builder: Relaxation ----------
class RelaxationPackageBuilder implements HolidayPackageBuilder {
    private HolidayPackage holidayPackage = new HolidayPackage();

    @Override
    public void buildFlight() {
        holidayPackage.setFlight("Business Class Flight");
    }

    @Override
    public void buildHotel() {
        holidayPackage.setHotel("5-Star Resort");
    }

    @Override
    public void buildActivity() {
        holidayPackage.setActivity("Spa Treatment");
    }

    @Override
    public HolidayPackage getPackage() {
        return holidayPackage;
    }
}

// ---------- Concrete Builder: Adventure ----------
class AdventurePackageBuilder implements HolidayPackageBuilder {
    private HolidayPackage holidayPackage = new HolidayPackage();

    @Override
    public void buildFlight() {
        holidayPackage.setFlight("Economy Flight");
    }

    @Override
    public void buildHotel() {
        holidayPackage.setHotel("Mountain Cabin");
    }

    @Override
    public void buildActivity() {
        holidayPackage.setActivity("Hiking Tour");
    }

    @Override
    public HolidayPackage getPackage() {
        return holidayPackage;
    }
}

// ---------- Director ----------
class TravelAgencyDirector {
    private HolidayPackageBuilder builder;

    public TravelAgencyDirector(HolidayPackageBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(HolidayPackageBuilder builder) {
        this.builder = builder;
    }

    // Same construction process, regardless of concrete builder used
    public HolidayPackage constructPackage() {
        builder.buildFlight();
        builder.buildHotel();
        builder.buildActivity();
        return builder.getPackage();
    }
}

// ---------- Client ----------
public class A2_HolidayPackageBuilder {
    public static void main(String[] args) {
        TravelAgencyDirector director = new TravelAgencyDirector(new RelaxationPackageBuilder());
        HolidayPackage relaxation = director.constructPackage();
        System.out.println(relaxation);

        director.setBuilder(new AdventurePackageBuilder());
        HolidayPackage adventure = director.constructPackage();
        System.out.println(adventure);
    }
}
