/*
  Exercise 18.1

  Add constructors to the following six classes.

  Assume Schedule, Route, Stops, and Place are defined elsewhere.
*/


// 1. train schedule:
class Train {
    Schedule s;
    Route r;

    Train(Schedule s, Route r) {
        this.s = s;
        this.r = r;
    }
}

class ExpressTrain extends Train {
    Stops st;
    String name;

    ExpressTrain(Schedule s, Route r, Stops st, String name) {
        super(s, r);
        this.st = st;
        this.name = name;
    }
}

 
// 2. restaurant guides:
class Restaurant {
    String name;
    String price;
    Place place;

    Restaurant(String name, String price, Place place) {
        this.name = name;
        this.price = price;
        this.place = place;
    }
}

class ChineseRestaurant extends Restaurant {
    boolean usesMSG;

    ChineseRestaurant(String name, String price, Place place, boolean usesMSG) {
        super(name, price, place);
        this.usesMSG = usesMSG;
    }
}

// 3. vehicle management:
class Vehicle {
    int mileage;
    int price;

    Vehicle(int mileage, int price) {
        this.mileage = mileage;
        this.price = price;
    }
}

class Sedan extends Vehicle {
    Sedan(int mileage, int price) {
        super(mileage, price);
    }
}
    
    
