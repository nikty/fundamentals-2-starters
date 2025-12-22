import tester.*;

// Represents a mode of transportation
interface IMOT {}
     
// Represents a bicycle as a mode of transportation
class Bicycle implements IMOT {
    String brand;
     
    Bicycle(String brand) {
        this.brand = brand;
    }
}
     
// Represents a car as a mode of transportation
class Car implements IMOT {
    String make;
    int mpg; // represents the fuel efficiency in miles per gallon
     
    Car(String make, int mpg) {
        this.make = make;
        this.mpg = mpg;
    }
}
     
// Keeps track of how a person is transported
class Person {
    String name;
    IMOT mot;
     
    Person(String name, IMOT mot) {
        this.name = name;
        this.mot = mot;
    }

    // Does this person's mode of transportation meet the given fuel
    // efficiency target (in miles per gallon)?
    // ...
}

class ExamplesPerson {
    IMOT diamondback = new Bicycle("Diamondback");
    IMOT toyota = new Car("Toyota", 30);
    IMOT lamborghini = new Car("Lamborghini", 17);
     
    Person bob = new Person("Bob", diamondback);
    Person ben = new Person("Ben", toyota);
    Person becca = new Person("Becca", lamborghini);
}
