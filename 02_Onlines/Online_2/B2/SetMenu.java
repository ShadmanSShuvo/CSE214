import java.util.ArrayList;
import java.util.List;
public class SetMenu implements FoodItem {
    private String name;
    private List<Food> foods;

    public SetMenu(String name) {
        this.name = name;
        this.foods = new ArrayList<>();
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    @Override
    public double getPrice() {
        double total = 0;
        for (Food food : foods) {
            total += food.getPrice();
        }
        return total;
    }

    @Override
    public void print(String prefix) {
        System.out.printf("%sSet Menu:%n", prefix);
        System.out.printf("%s%s Food:%n", prefix, name);
        for (Food food : foods) {
            food.print(prefix + "  ");
        }
    }

}
