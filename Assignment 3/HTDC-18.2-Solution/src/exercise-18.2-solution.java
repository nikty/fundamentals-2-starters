/*
  Exercise 18.2

  Abstract over the common fields of Lion, Snake, and Monkey in the
  class diagram of figure 14 (page 32). First revise the class
  diagram, then define the classes including the constructors.

*/

class ZooAnimal {
    String name;
    int weight;

    ZooAnimal(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}

class Lion extends ZooAnimal {
    int meat;

    Lion(String name, int weight, int meat) {
        super(name, weight);
        this.meat = meat;
    }
}

class Snake extends ZooAnimal {
    int length;

    Snake(String name, int weight, int length) {
        super(name, weight);
        this.length = length;
    }
}

class Monkey extends ZooAnimal {
    String food;

    Monkey(String name, int weight, String food) {
        super(name, weight);
        this.food = food;
    }
}

