public final class CandyFactory {
    public static Candy getCandy(String type){
        switch(type){
            case "Starburst":
                return new Starburst();
            case "Gumdrop":
                return new Gumdrop();
            default:
                throw new IllegalArgumentException();
        }
    }
}