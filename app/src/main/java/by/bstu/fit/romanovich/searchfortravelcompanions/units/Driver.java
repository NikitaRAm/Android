package by.bstu.fit.romanovich.searchfortravelcompanions.units;

public class Driver extends Request {
    private int ball;
    public Driver(String date, String time,  String wherefrom, String where, String car,Integer price,Integer place,String image, int ball)
    {
        super(date, time,  wherefrom, where,  car, price, place,image);
        this.ball = ball;
    }

    public int getBall() {
        return ball;
    }

    public void setBall(int ball) {
        this.ball = ball;
    }
}

