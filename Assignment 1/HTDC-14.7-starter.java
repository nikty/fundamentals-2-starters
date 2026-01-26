/*
  Exercise 14.7

  A software house that is working with a grocery chain receives this
  problem statement:

  ... Develop a program that keeps track of the items in the
  grocery store. For now, assume that the store deals only with
  ice cream, coffee, and juice. Each of the items is specified by
  its brand name (String), weight (grams) and price (cents). Each
  coffee is also labeled as either regular or decaffeinated. Juice
  items come in different flavors, and can be pkgd as frozen,
  fresh, bottled, or canned. Each pkg of ice cream specifies its
  flavor. ...
  
  Design the following methods:
  
  1. unitPrice, which computes the unit price (cents per gram) of a
  grocery item;
  
  2. lowerUnitPrice, which determines whether the unit price of a
  grocery item is lower than some given amount;
  
  3. cheaperThan, which determines whether a grocery item’s unit price
  is less than some other (presumably) comparable item’s unit price.

*/

import tester.*;

interface IGroceryItem {
    // ...
}

class IceCream implements IGroceryItem {
    String brand;
    double weight; // in grams
    int price; // in cents
    String flavor;

    IceCream(String brand, double weight, int price, String flavor) {
        this.brand = brand;
        this.weight = weight;
        this.price = price;
        this.flavor = flavor;
    }

    // ...
}

class Coffee implements IGroceryItem {
    String brand;
    double weight; // in grams
    int price; // in cents
    boolean isDecaf; // whether refular or decaffeinated

    Coffee(String brand, double weight, int price, boolean isDecaf) {
        this.brand = brand;
        this.weight = weight;
        this.price = price;
        this.isDecaf = isDecaf;
    }

    // ...
}

class Juice implements IGroceryItem {
    String brand;
    double weight; // in grams
    int price; // in cents
    String flavor;
    String pkg;

    Juice(String brand, double weight, int price, String flavor, String pkg) {
        this.brand = brand;
        this.weight = weight;
        this.price = price;
        this.flavor = flavor;
        this.pkg = pkg;
    }

    // ...
}

class Examples {
    IGroceryItem vanillaIceCream = new IceCream("Haagen-Dazs", 500, 1200, "vanilla");
    IGroceryItem chocolateIceCream = new IceCream("B&R", 400, 950, "chocolate");
    IGroceryItem mangoIceCream = new IceCream("Breyers", 300, 800, "mango");
    IGroceryItem strawberryIceCream = new IceCream("Ben & Jerry's", 450, 1100, "strawberry");

    IGroceryItem regularCoffee = new Coffee("Starbucks", 200, 1500, false);
    IGroceryItem decafCoffee = new Coffee("Folgers", 250, 1300, true);
    IGroceryItem espresso = new Coffee("Lavazza", 150, 1700, false);
    IGroceryItem flavoredCoffee = new Coffee("McCafe", 300, 1600, true);

    IGroceryItem orangeJuice = new Juice("Tropicana", 1000, 1200, "orange", "carton");
    IGroceryItem appleJuice = new Juice("Simply", 900, 1100, "apple", "bottle");
    IGroceryItem grapeJuice = new Juice("Welch's", 750, 950, "grape", "carton");
    IGroceryItem cranberryJuice = new Juice("Ocean Spray", 1000, 1300, "cranberry", "bottle");

    boolean testUnitPrice(Tester t) {
        return t.checkInexact(vanillaIceCream.unitPrice(), 1200/500.0, 0.001)
            && t.checkInexact(regularCoffee.unitPrice(), 1500/200.0, 0.001);
    }

    boolean testLowerUnitPrice(Tester t) {
        return t.checkExpect(chocolateIceCream.lowerUnitPrice(3.00), true)
            && t.checkExpect(vanillaIceCream.lowerUnitPrice(1.00), false);
    }

    boolean testCheaperThan(Tester t) {
        return t.checkExpect(chocolateIceCream.cheaperThan(grapeJuice), false)
            && t.checkExpect(regularCoffee.cheaperThan(cranberryJuice), false);
    }
}
