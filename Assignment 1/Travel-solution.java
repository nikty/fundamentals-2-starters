import tester.*;

// IHousing is one of
// - Hut
// - Inn
// - Castle
interface IHousing {
}

// interp. a hut in the game
class Hut implements IHousing {
    int capacity;
    int population; // current count of hut's population; MUST be less than the capacity

    Hut(int capacity, int population) {
        this.capacity = capacity;
        this.population = population;
    }
}

// interp. an inn in the game
class Inn implements IHousing {
    String name;
    int capacity;
    int population; // current count of ins's population; MUST be less than the capacity
    int stalls; // number of stalls in the stable

    Inn(String name, int capacity, int population, int stalls) {
        this.name = name;
        this.capacity = capacity;
        this.population = population;
        this.stalls = stalls;
    }
}

// interp. a castle in the game
class Castle implements IHousing {
    String name;
    String familyName; // family name of the owners
    int population; // current count of castle's population
    int carriages; // number of carriages in the castle's carriage house

    Castle(String name, String familyName, int population, int carriages) {
        this.name = name;
        this.familyName = familyName;
        this.population = population;
        this.carriages = carriages;
    }
}

// ITransportation is one of
// - Horse
// - Carriage
interface ITransportation {
}

// interp. a horse in the game
class Horse implements ITransportation {
    IHousing from; // hut, castle, inn
    IHousing to; // any hut, any castle; inn which has room in the stables
    String name;
    String color;

    Horse(IHousing from, IHousing to, String name, String color) {
        this.from = from;
        this.to = to;
        this.name = name;
        this.color = color;
    }
}

// interp. a carriage in the game
class Carriage implements ITransportation {
    // from inn to castle or vice versa
    IHousing from;
    IHousing to; // any inn; castle which has room in the carriage house
    int tonnage; // how much it can carry in kilograms

    Carriage(IHousing from, IHousing to, int tonnage) {
        this.from = from;
        this.to = to;
        this.tonnage = tonnage;
    }
}

class ExamplesTravel {
    ExamplesTravel() {}

    // Housing examples
    IHousing hovel = new Hut(5, 1);
    IHousing winterfell = new Castle("Winterfell",
                                     "Stark", 500, 6);
    IHousing crossroads = new Inn("Inn At The Crossroads",
                                  40, 20, 12);
    IHousing smallHut = new Hut(3, 2);
    IHousing goldenEagleInn = new Inn("Golden Eagle Inn", 100, 50, 20);
    IHousing dragonKeep = new Castle("Dragon Keep", "Targaryen", 200, 10);

    // Transportation examples

    Horse horse1 = new Horse(hovel, goldenEagleInn, "Black Beauty", "Black");
    Horse horse2 = new Horse(winterfell, crossroads, "Spirit", "Chestnut");

    Carriage carriage1 = new Carriage(crossroads, winterfell, 2000); // 2000 kg capacity
    Carriage carriage2 = new Carriage(winterfell, goldenEagleInn, 1500); // 1500 kg capacity
}
