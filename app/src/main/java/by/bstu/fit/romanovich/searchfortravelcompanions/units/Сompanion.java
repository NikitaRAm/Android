package by.bstu.fit.romanovich.searchfortravelcompanions.units;

public class Сompanion extends Request {
    private int rating;
    public Сompanion(String date, String time,  String wherefrom, String where, String car,Integer price,Integer place,String image, int rating)
    {
        super(date, time,  wherefrom, where,  car, price, place,image);
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
