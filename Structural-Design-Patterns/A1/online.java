import java.util.*;
import java.util.List;
import java.util.ArrayList;
import javax.sound.sampled.SourceDataLine;


interface Gift{
    double getPrice();
    String getDesc();

}

class Showpiece implements Gift{
    double price;
    String desc;
    public Showpiece(double price, String desc) {
        this.price = price;
        this.desc = desc;
    }

    @Override
    public double getPrice(){
        return price;
    }

    @Override
    public String getDesc(){
        return desc;
    }
}

abstract class GiftDecorator implements Gift{
    protected Gift wrappee;

    public GiftDecorator(Gift gift) {
        this.wrappee = gift;
    }

    @Override
    public String getDesc() {
        return wrappee.getDesc();
    }

    @Override
    public double getPrice() {
        return wrappee.getPrice();
    }
}

class GiftWrapDecorator extends GiftDecorator{

    public GiftWrapDecorator(Gift gift) {
        super(gift);
    }
    @Override
    public String getDesc() {
        return wrappee.getDesc() + " with Wrapping";
    }

    @Override
    public double getPrice(){
        return wrappee.getPrice() + 2;
    }
    
}

class GiftHomeDeliveryDecorator extends GiftDecorator{
    Delivery method;

    public GiftHomeDeliveryDecorator(Gift gift) {
        super(gift);
    }
    @Override
    public String getDesc() {
        return wrappee.getDesc() + " with Home delivery";
    }

    @Override
    public double getPrice(){
        return method.getCost();
    }
    
}

class Ornament implements Gift{
    double price;
    String desc;

    

    public Ornament(double price, String desc) {
        this.price = price;
        this.desc = desc;
    }

    @Override
    public double getPrice(){
        return price;
    }

    @Override
    public String getDesc(){
        return desc;
    }
}

class Souvenir implements Gift{
    double price;
    String desc;

    

    public Souvenir(double price, String desc) {
        this.price = price;
        this.desc = desc;
    }

    @Override
    public double getPrice(){
        return price;
    }

    @Override
    public String getDesc(){
        return desc;
    }
}


abstract class Delivery implements Gift{
    ArrayList<Gift> items = new ArrayList<Gift>();
    String name;
    public Delivery(String name){
        this.name = name;
    }
    public double getPrice(){
        double total = 0;
        for(Gift item : items){
            total += item.getPrice();
        }
        return total;
    }
    public String getDesc(){
        StringBuilder sb = new StringBuilder(" Items: ");
        for(Gift item : items){
            sb.append(item.getDesc() + " ");
        }
        sb.append(" with home delivery");
        return sb.toString();
    }
    public void addGift(Gift g){
        items.add(g);
    }

    abstract double getCost();
}

class LocalDelivery extends Delivery{
    public LocalDelivery(String name){
       super(name);
    }
    @Override
    public double getCost(){
        return 1 + getPrice();
    }
}

class NationalDelivery extends Delivery{
    public NationalDelivery(String name){
       super(name);
    }
    @Override
    public double getCost(){
        return 20 + getPrice();
    }
}

class InterNationalDelivery extends Delivery{
    public InterNationalDelivery(String name){
       super(name);
    }
    @Override
    public double getCost(){
        return 500 + getPrice();
    }
}

public class online{
    public static void main(String[] args) {
        Delivery delivery = new LocalDelivery("gift1");
        Gift g = new Showpiece(5, "globe");
        Gift h = new GiftWrapDecorator(g);
        delivery.addGift(h);
        double TotalCost = delivery.getCost();
        System.out.println(TotalCost);
    }
}