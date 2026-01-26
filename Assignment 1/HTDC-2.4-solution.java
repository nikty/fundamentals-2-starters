/*

  Exercise 2.4

  Translate the class diagram into a class definition.
  
  Also create instances of the class.

*/

class Automobile {
    String model;
    int price; // in dollars
    double mileage; // in miles per gallon
    boolean used;

    Automobile(String model, int price, double mileage, boolean used) {
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        this.used = used;
    }
}

class AutomobileExamples {
    Automobile auto1 = new Automobile("Toyota Camry", 24000, 32.5, false);
    Automobile auto2 = new Automobile("Honda Accord", 22000, 30.0, false);
    Automobile auto3 = new Automobile("Ford Mustang", 35000, 25.0, true);
    Automobile auto4 = new Automobile("Chevrolet Malibu", 19000, 28.0, false);
    Automobile auto5 = new Automobile("Nissan Altima", 23000, 29.5, true);
    Automobile auto6 = new Automobile("Tesla Model 3", 35000, 142.0, false);
}
