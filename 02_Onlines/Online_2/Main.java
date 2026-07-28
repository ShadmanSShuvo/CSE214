import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Foods
        Food burger = new Food("Burger", 8);
        Food pizza = new Food("Pizza", 10);
        Food fries = new Food("French Fries", 3);
        // Set Menu
        SetMenu lunch = new SetMenu("Lunch Combo");
        lunch.addFood(burger);
        lunch.addFood(fries);
        // Grocery Items
        Grocery rice = new Grocery("Rice", 20);
        Grocery oil = new Grocery("Cooking Oil", 12);
        Grocery eggs = new Grocery("Eggs", 6);
        Grocery sugar = new Grocery("Sugar", 5);
        // Small Package
        GroceryPackage breakfastPack = new GroceryPackage("Breakfast Pack");
        breakfastPack.add(eggs);
        breakfastPack.add(sugar);
        // Large Package (contains another package)
        GroceryPackage monthlyPack = new GroceryPackage("Monthly Essentials");
        monthlyPack.add(rice);
        monthlyPack.add(oil);
        monthlyPack.add(breakfastPack);
        // Customer Order
        Order order = new Order();
        order.add(pizza);
        order.add(lunch);
        order.add(rice);
        order.add(monthlyPack);
        order.printReceipt();
    }
}
/*
==========RECEIPT==========Food:Pizza(£10.00)

Set Menu:
Lunch Combo Food:

Burger (£8.00)
Food:

French Fries (£3.00)
Grocery: Rice (£20.00)
Package: Monthly Essentials
Grocery: Rice (£20.00)
Grocery:

Cooking Oil (£12.00)
Package: Breakfast Pack
Grocery: Eggs (£6.00)
Grocery: Sugar (£5.00)
-----------------------------
Total Bill: £84.00
*/
