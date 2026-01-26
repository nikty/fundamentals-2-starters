/*
  Exercise 18.5

  Develop examples for the original class hierarchy representing
  vehicles. Turn them into tests and run them. Then lift the common
  cost method and re-run the tests.
*/

import tester.*;

interface IVehicle {
    // compute the cost of refueling this vehicle,
    // given the current price (cp) of fuel
    double cost(double cp);
}

abstract class AVehicle implements IVehicle {
    double tank; // gallons

    AVehicle(double tank) {
        this.tank = tank;
    }

    public abstract double cost(double cp);
}

class Car extends AVehicle {
    Car(double tank) {
        super(tank);
    }

    public double cost(double cp) {
        return this.tank * cp;
    }
}

class Truck extends AVehicle {
    Truck(double tank) {
        super(tank);
    }
    
    public double cost(double cp) {
        return this.tank * cp;
    }
}

class Bus extends AVehicle {
    Bus(double tank) {
        super(tank);
    }
    
    public double cost(double cp) {
        return this.tank * cp;
    }
}

class Examples {
    AVehicle dodgeRam = new Car(25);
    AVehicle gmNewLook = new Bus(50);
    AVehicle fordModelTT = new Truck(10);

    boolean testCost(Tester t) {
        return t.checkInexact(dodgeRam.cost(10), 250.0, .001)
            && t.checkInexact(gmNewLook.cost(10), 500.0, .001)
            && t.checkInexact(fordModelTT.cost(10), 100.0, .001);
    }
}
 
