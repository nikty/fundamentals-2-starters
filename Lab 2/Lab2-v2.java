import tester.*;

// Represents a mode of transportation
interface IMOT {
    // returns true if this mode of transportation is at least
    // as efficient as the given mpg, false otherwise
    boolean isMoreFuelEfficientThan(int mpg);

    // Return true if this mode of transportation is at least
    // as efficient as the given mode
    boolean isMoreFuelEfficientThanMot(IMOT mot);

    // Return this MOT's mpg
    int getMpg();
}
        
     
// Represents a bicycle as a mode of transportation
class Bicycle implements IMOT {
    String brand;
     
    Bicycle(String brand) {
        this.brand = brand;
    }
    public boolean isMoreFuelEfficientThan(int mpg) {
        return true;
    }

    /* Exercise
       
    // ASSUME: bicycle is the most efficient of them all
    public boolean isMoreFuelEfficientThanMot(IMOT mot) {
        return true;
    }

    public int getMpg() {
        return Integer.MAX_VALUE;
    }
    */
}
     
// Represents a car as a mode of transportation
class Car implements IMOT {
    String make;
    int mpg; // represents the fuel efficiency in miles per gallon
     
    Car(String make, int mpg) {
        this.make = make;
        this.mpg = mpg;
    }

    public boolean isMoreFuelEfficientThan(int mpg) {
        return this.mpg >= mpg;
    }

    /* Exercise
       
    // boolean isMoreFuelEfficientThanMot(IMOT mot) {
    //   ... this.mpg
    //   ... mot.mmm()
    // }
    public boolean isMoreFuelEfficientThanMot(IMOT mot) {
        return this.getMpg() >= mot.getMpg();
    }

    public int getMpg() {
        return this.mpg;
    }
    */    
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
    // Template:
    // boolean motMeetsFuelEfficiency(int mpg) {
    // ... this.mot.mmm?
    // }
    boolean motMeetsFuelEfficiency(int mpg) {
        return this.mot.isMoreFuelEfficientThan(mpg);
    }

    /* Q: Why do we not want to simply write a getMpg method on IMOT and its implementing classes?

       A: Implementing a getMpg method in the IMOT interface would
       violate the *principle of abstraction* that interfaces
       provide. The IMOT interface is meant to define a contract for
       different modes of transportation without imposing unnecessary
       details on the specific implementations.

       *Separation of Concerns*: The getMpg method implies that all
       transportation modes must have a miles-per-gallon metric, which
       is relevant only to certain modes (like cars) but not to others
       (like bicycles). By forcing every mode of transportation to
       implement this method, we blur the distinctions between these
       modes and reduce the descriptive power of the classes.

       *Inflexibility*: If we included a getMpg method, we would need to
       handle how each concrete class implements it. For bicycles,
       which don't have an mpg rating, the implementation could be
       misleading or arbitrary. This can lead to inconsistencies in
       how different modes report their efficiency, potentially
       complicating the interface.

       *Encapsulation*: The current design allows each mode to handle
       its efficiency metrics without the need to expose that data to
       the Person class explicitly. It maintains encapsulation, where
       transportation modes internally manage their relevant
       properties and behaviors.

       *Polymorphism and Type Safety*: Without a universal MPG method,
       the interface can interact with any mode of transportation
       regardless of whether it implements miles-per-gallon efficiency
       or another form of measurement. This creates flexibility in the
       design, allowing the Person class to focus on transportation
       behavior rather than specific properties.

       Using an interface like IMOT promotes a cleaner, more flexible
       design, allowing for future expansions with other modes of
       transportation without enforcing unwanted constraints.
    */

    /* Exercise
       
       Q: Where do you get stuck?

       A: We would need for all classes implementing IMOT to have
       information about fuel efficiency. ???
       
    // Does this person's MOT is mmore fuel efficient than given MOT?
    // boolean motIsMoreFuelEfficientThan(IMOT mot) {
    //   ... this.mot.mmm()
    // }
    boolean motIsMoreFuelEfficientThan(IMOT mot) {
        return this.mot.isMoreFuelEfficientThanMot(mot);
    }
    */
}

class ExamplesPerson {
    IMOT diamondback = new Bicycle("Diamondback");
    IMOT toyota = new Car("Toyota", 30);
    IMOT lamborghini = new Car("Lamborghini", 17);
     
    Person bob = new Person("Bob", diamondback);
    Person ben = new Person("Ben", toyota);
    Person becca = new Person("Becca", lamborghini);

    boolean testMotMeetsFuelEfficiency(Tester t) {
        return t.checkExpect(bob.motMeetsFuelEfficiency(15), true)
            && t.checkExpect(ben.motMeetsFuelEfficiency(15), true)
            && t.checkExpect(becca.motMeetsFuelEfficiency(25), false);
    }

    /* Exercise 
    boolean testMotIsMoreFuelEfficientThan(Tester t) {
        return t.checkExpect(bob.motIsMoreFuelEfficientThan(lamborghini), true);
    }
    */
}
