import java.util.ArrayList;
import java.util.List;

public class SetMenu implements OrderItem {

    private String name;
    private List<Food> foods = new ArrayList<>();

    public SetMenu(String name) {
        this.name = name;
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
        return total * 0.9; // 10% discount
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Set Menu: " + name);
        for (Food food : foods) {
            food.print(indent + " ");
        }
    }
}
