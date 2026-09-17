// Builder

// Product Class representing the final complex object
class HolidayPackage {
    private String flight;
    private String hotel;
    private String dailyActivity;

    public void setFlight(String flight) { this.flight = flight; }
    public void setHotel(String hotel) { this.hotel = hotel; }
    public void setDailyActivity(String dailyActivity) { this.dailyActivity = dailyActivity; }

    @Override
    public String toString() {
        return "HolidayPackage [Flight=" + flight + ", Hotel=" + hotel + ", Activity=" + dailyActivity + "]";
    }
}

// Abstract Builder interface specifying the construction steps
interface HolidayPackageBuilder {
    void buildFlight();
    void buildHotel();
    void buildDailyActivity();
    HolidayPackage getPackage();
}

// Concrete Builder 1: Relaxation Package
class RelaxationPackageBuilder implements HolidayPackageBuilder {
    private HolidayPackage holidayPackage = new HolidayPackage();

    @Override
    public void buildFlight() { holidayPackage.setFlight("Business Class Flight"); }
    @Override
    public void buildHotel() { holidayPackage.setHotel("5-Star Resort"); }
    @Override
    public void buildDailyActivity() { holidayPackage.setDailyActivity("Spa Treatment"); }
    @Override
    public HolidayPackage getPackage() { return this.holidayPackage; }
}

// Concrete Builder 2: Adventure Package
class AdventurePackageBuilder implements HolidayPackageBuilder {
    private HolidayPackage holidayPackage = new HolidayPackage();

    @Override
    public void buildFlight() { holidayPackage.setFlight("Economy Flight"); }
    @Override
    public void buildHotel() { holidayPackage.setHotel("Mountain Cabin"); }
    @Override
    public void buildDailyActivity() { holidayPackage.setDailyActivity("Hiking Tour"); }
    @Override
    public HolidayPackage getPackage() { return this.holidayPackage; }
}

// Director controlling the sequence of construction steps
class TravelAgencyDirector {
    private HolidayPackageBuilder builder;

    public TravelAgencyDirector(HolidayPackageBuilder builder) {
        this.builder = builder;
    }

    public void constructPackage() {
        builder.buildFlight();
        builder.buildHotel();
        builder.buildDailyActivity();
    }
}

// Client Application
public class MainA2 {
    public static void main(String[] args) {
        // Construct a Relaxation Package
        HolidayPackageBuilder relaxationBuilder = new RelaxationPackageBuilder();
        TravelAgencyDirector director1 = new TravelAgencyDirector(relaxationBuilder);
        director1.constructPackage();
        HolidayPackage relaxationPkg = relaxationBuilder.getPackage();
        System.out.println(relaxationPkg);

        // Construct an Adventure Package
        HolidayPackageBuilder adventureBuilder = new AdventurePackageBuilder();
        TravelAgencyDirector director2 = new TravelAgencyDirector(adventureBuilder);
        director2.constructPackage();
        HolidayPackage adventurePkg = adventureBuilder.getPackage();
        System.out.println(adventurePkg);
    }
}