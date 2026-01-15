/*
  Exercise 18.1

  Add constructors to the following six classes.

  Assume Schedule, Route, Stops, and Place are defined elsewhere.
*/


// 1. train schedule:
class Train {
    Schedule s;
    Route r;
}

class ExpressTrain extends Train {
    Stops st;
    String name;
}

 
// 2. restaurant guides:
class Restaurant {
    String name;
    String price;
    Place place;
}

class ChineseRestaurant extends Restaurant {
    boolean usesMSG;
}

// 3. vehicle management:
class Vehicle {
    int mileage;
    int price;
}

class Sedan extends Vehicle {}
