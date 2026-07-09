public class CandyStore {
    public Candy sellCandy(String type){
        return CandyFactory.getCandy(type);
    }
}