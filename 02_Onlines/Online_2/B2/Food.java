public class Food implements FoodItem {
    private String name;
    private double price;


    public Food(String name, double price){
        this.name=name;
        this.price=price;
    }
    @Override
    public double getPrice(){
        return price;
    }
    @Override
    public void print(String prefix){
        System.out.println("%s%s Food:%n", prefix, name);
    }
}
