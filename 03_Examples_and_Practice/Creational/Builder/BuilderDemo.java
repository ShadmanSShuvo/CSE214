// Product
class House {

    private String foundation;
    private String walls;
    private String roof;
    private String garage;

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public void setWalls(String walls) {
        this.walls = walls;
    }

    public void setRoof(String roof) {
        this.roof = roof;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    @Override
    public String toString() {
        return "House {" +
                "\n  Foundation = " + foundation +
                "\n  Walls      = " + walls +
                "\n  Roof       = " + roof +
                "\n  Garage     = " + garage +
                "\n}";
    }
}

// Builder interface
interface HouseBuilder {

    void buildFoundation();

    void buildWalls();

    void buildRoof();

    House getResult();
}

// Concrete Builder
class WoodenHouseBuilder implements HouseBuilder {

    private House house = new House();

    @Override
    public void buildFoundation() {
        house.setFoundation("Wood posts");
    }

    @Override
    public void buildWalls() {
        house.setWalls("Wooden planks");
    }

    @Override
    public void buildRoof() {
        house.setRoof("Shingle roof");
    }

    @Override
    public House getResult() {
        return house;
    }
}

// Director - controls the order of building
class Director {

    private HouseBuilder builder;

    public Director(HouseBuilder builder) {
        this.builder = builder;
    }

    public void constructHouse() {
        builder.buildFoundation();
        builder.buildWalls();
        builder.buildRoof();
    }
}

// Client
public class BuilderDemo {

    public static void main(String[] args) {

        HouseBuilder builder = new WoodenHouseBuilder();

        Director director = new Director(builder);

        director.constructHouse();

        House house = builder.getResult();

        System.out.println(house);
    }
}
