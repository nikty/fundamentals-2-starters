import tester.*;

class Dog {
    String name; // dog's nameb
    String breed; // dog's breed
    int yob; // dog's year of birth as a four digit number
    String state; // US state of residence
    boolean hypoallergenic; // whether the dog is hypoallergenic or not

    Dog(String name, String breed, int yob, String state, boolean hypoallergenic) {
        this.name = name;
        this.breed = breed;
        this.yob = yob;
        this.state = state;
        this.hypoallergenic = hypoallergenic;
    }
}

class ExamplesDog {
    ExamplesDog() {}

    Dog hufflepuff = new Dog("Hufflepuff", "Wheaten Terrier", 2012, "TX", true);
    Dog pearl = new Dog("Pearl", "Labrador Retriever", 2016, "MA", false);
    Dog max = new Dog("Max", "Beagle", 2015, "CA", false);
    Dog bella = new Dog("Bella", "Poodle", 2018, "NY", true);
    Dog buddy = new Dog("Buddy", "Golden Retriever", 2020, "FL", false);
}
