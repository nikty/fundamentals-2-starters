/*
  Exercise 3.1

  Design a data representation for this problem:

  Develop a “real estate assistant” program. The “assistant” helps real
  estate agents locate available houses for clients.  The information
  about a house includes its kind, the number of rooms, its address, and
  the asking price. An address consists of a street number, a street
  name, and a city.

  Represent the following examples using your classes:

  1. Ranch, 7 rooms, $375,000, 23 Maple Street, Brookline;

  2. Colonial, 9 rooms, $450,000, 5 Joye Road, Newton; and

  3. Cape, 6 rooms, $235,000, 83 Winslow Road, Waltham.
*/

class House {
    String kind;
    int rooms;
    Address address;
    int price;

    House(String kind, int rooms, Address address, int price) {
        this.kind = kind;
        this.rooms = rooms;
        this.address = address;
        this.price = price;
    }
}

class Address {
    int streetNumber;
    String streetName;
    String city;

    Address(int streetNumber, String streetName, String city) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
    }
}

class HouseExamples {

    // Addresses
    Address a1 = new Address(23, "Maple Street", "Brookline");
    Address a2 = new Address(5, "Joye Road", "Newton");
    Address a3 = new Address(83, "Winslow Road", "Waltham");
    
    House ranch = new House("Ranch", 7, a1, 375000);
    House colonial = new House("Colonial", 9, a2, 450000);
    House cape = new House("Cape", 6, a3, 235000);
}
