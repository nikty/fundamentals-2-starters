import tester.*;

// to represent a pet owner
class Person {
    String name;
    IPet pet;
    int age;
     
    Person(String name, IPet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }

    // is this Person older than the given Person?
    // boolean isOlder(Person other) {
    //   ... this.age
    //   ... other.age
    // }
    boolean isOlder(Person other) {
        return this.age > other.age;
    }

    // does the name of this person's pet match the given name?
    // boolean sameNamePet(String name) {
    //   ... this.pet.mmm()
    // }
    boolean sameNamePet(String name) {
        return this.pet.sameName(name);
    }

    // Produce a person with the same name ang age as this one but having no pet
    Person perish() {
        return new Person(this.name, new NoPet(), this.age);
    }
}

// to represent a pet
interface IPet {
    // Return true if this pet's name mathes the given name
    boolean sameName(String name);
}
     
// to represent a pet cat
class Cat implements IPet {
    String name;
    String kind;
    boolean longhaired;
     
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }

    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}
     
// to represent a pet dog
class Dog implements IPet {
    String name;
    String kind;
    boolean male;
     
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }

    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}

class NoPet implements IPet {
    NoPet() {}

    public boolean sameName(String name) { return false; }
}

class ExamplesPet {
    IPet dog1 = new Dog("Fluffy", "German Shepherd", true);  // Male
    IPet dog2 = new Dog("Bella", "Beagle", false);           // Female
    IPet cat1 = new Cat("Whiskers", "Persian", true);        // Longhaired
    IPet cat2 = new Cat("Shadow", "Siamese", false);         // Shorthaired

    Person p1 = new Person("Micheal", dog1, 15);
    Person p2 = new Person("Sarah", dog2, 28);
    Person p3 = new Person("Emma", cat1, 22);
    Person p4 = new Person("Daniel", cat2, 30);
    Person p5 = new Person("John", new NoPet(), 40);

    boolean testIsOlder(Tester t) {
        return t.checkExpect(p1.isOlder(p2), false)
            && t.checkExpect(p2.isOlder(p3), true);
    }

    boolean testSameNamePet(Tester t) {
        return t.checkExpect(p1.sameNamePet("Fluffy"), true)
            && t.checkExpect(p2.sameNamePet("Scooby-Doo"), false)
            && t.checkExpect(p3.sameNamePet("Whiskers"), true)
            && t.checkExpect(p4.sameNamePet("Salem"), false)
            && t.checkExpect(p5.sameNamePet("Exists"), false);
    }

    boolean testPerish(Tester t) {
        return t.checkExpect(p1.perish(),
                             new Person(p1.name, new NoPet(), p1.age));
    }
}
