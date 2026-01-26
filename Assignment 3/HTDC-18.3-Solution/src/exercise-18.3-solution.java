/*
  Exercise 18.3
  
  Abstract over the common fields in the class diagram of figure 16
  (page 34). Revise the class diagram and the classes.
*/

class TaxiVehicle {
    int idNum;
    int passengers;
    int pricePerMile;

    TaxiVehicle(int idNum, int passengers, int pricePerMile) {
        this.idNum = idNum;
        this.passengers = passengers;
        this.pricePerMile = pricePerMile;
    }
}

class Cab extends TaxiVehicle {
    Cab(int idNum, int passengers, int pricePerMile) {
        super(idNum, passengers, pricePerMile);
    }
}

class Limo extends TaxiVehicle {
    int minRental;
    
    Limo(int idNum, int passengers, int pricePerMile, int minRental) {
        super(idNum, passengers, pricePerMile);
        this.minRental = minRental;
    }
}

class Van extends TaxiVehicle {
    boolean access;
    
    Van(int idNum, int passengers, int pricePerMile, boolean access) {
        super(idNum, passengers, pricePerMile);
        this.access = access;
    }
}
