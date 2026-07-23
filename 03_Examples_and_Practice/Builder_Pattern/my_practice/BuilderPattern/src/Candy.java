public class Candy {

    private String name;
    private String color;
    private String flavor;
    private int sweetness;

    public Candy(String name,
            String color,
            String flavor,
            int sweetness) {

        this.name = name;
        this.color = color;
        this.flavor = flavor;
        this.sweetness = sweetness;

    }

    public void display() {

        System.out.println(name);
        System.out.println(color);
        System.out.println(flavor);
        System.out.println(sweetness);

    }

}