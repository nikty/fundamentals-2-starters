import tester.*;

interface IIceCream {
}

class EmptyServing implements IIceCream {
    boolean cone;

    EmptyServing(boolean cone) {
        this.cone = cone;
    }
}

class Scooped implements IIceCream {
    IIceCream more;
    String flavor;

    Scooped(IIceCream more, String flavor) {
        this.more = more;
        this.flavor = flavor;
    }
}

class ExamplesIceCream {
    ExamplesIceCream() {}

    IIceCream cup1 = new Scooped(new Scooped(new Scooped(new EmptyServing(false),
                                                         "mint chip"),
                                             "coffee"),
                                 "black raspberry");
    IIceCream cone1 = new Scooped(new Scooped(new Scooped(new EmptyServing(true),
                                                         "chocolate"),
                                              "vanilla"),
                                  "strawberry");
}
    
