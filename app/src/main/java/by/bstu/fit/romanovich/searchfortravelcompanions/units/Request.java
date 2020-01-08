package by.bstu.fit.romanovich.searchfortravelcompanions.units;

public class Request {
    private String date;
    private String time;
    private String wherefrom;
    private String where;
    private String car;
    private Integer place;
    private Integer price;
    private String pathPhoto;

    public Request(String date, String  time, String wherefrom, String  where, String car, Integer place, Integer price, String pathPhoto){
        this.date = date;
        this.time = time;
        this.wherefrom = wherefrom;
        this.where = where;
        this.car = car;
        this.place = place;
        this.price = price;
        this.pathPhoto = pathPhoto;
    }

    public String toString(){
        return date + " " + time;
    }
    public String toWhere(){
        return wherefrom + "->" + where;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String geTtime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getWherefrom() {
        return wherefrom;
    }
    public void setWherefrom(String wherefrom) {
        this.wherefrom = wherefrom;
    }
    public void setWhere(String where) {
        this.where = where;
    }
    public String getWhere() {
        return where;
    }


    public String getCar() {
        return car;
    }
    public void setCar(String car) {
        this.car = car;
    }
    public Integer getPlace() {
        return place;
    }
    public void setPlace(Integer place) {
        this.place = place;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getPathPhoto() {
        return pathPhoto;
    }
    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }
}
