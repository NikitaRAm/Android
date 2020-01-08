package by.bstu.fit.romanovich.searchfortravelcompanions.Requests;

import java.util.ArrayList;

import by.bstu.fit.romanovich.searchfortravelcompanions.units.Driver;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Сompanion;
import by.bstu.fit.romanovich.searchfortravelcompanions.units.Request;

public class Requests {

public static ArrayList<Driver> drivers = new ArrayList<Driver>();
    public static ArrayList<Сompanion> сompanions = new ArrayList<Сompanion>();

    public static void add(Driver person){
        drivers.add(person);
    }
    public static void add(Сompanion person){
        сompanions.add(person);
    }

    public static void remove(Driver person){ drivers.remove(person);}
    public static void remove(Сompanion person){ сompanions.remove(person);}

    public static void setListofDrivers(ArrayList<Driver> newList){
        drivers = newList;
    }
    public static void setListofCompanions(ArrayList<Сompanion> newList){
        сompanions = newList;
    }

    public static ArrayList<Request> getList(){
        ArrayList<Request> list = new ArrayList<Request>();
        list.addAll(drivers);
        list.addAll(сompanions);
        return list;
    }

    public static Driver getDriver(int index){
        return drivers.get(index);
    }
    public static Сompanion getCompanion(int index){
        return сompanions.get(index);
    }

    public static int indexOf(Driver driver){
        return drivers.indexOf(driver);
    }
    public static int indexOf(Сompanion сompanion){
        return сompanions.indexOf(сompanion);
    }
}

