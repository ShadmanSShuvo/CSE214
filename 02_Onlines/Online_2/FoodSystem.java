public interface FoodSystem{
    void add();
    double getPrice();
}

class Burger implements FoodSystem{
    String name;
    double price;

    public Burger(String name, double price){
        this.name = name;
        this.price = price;
    }

    public double getPrice(){
        return price;
    }
}
