public class CandyStore {
    public Candy sellCandy(String type) {
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