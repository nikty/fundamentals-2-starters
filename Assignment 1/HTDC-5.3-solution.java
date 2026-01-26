/*
  Exercise 5.3 Consider a revision of the problem in exercise 3.1:

  ... Develop a program that assists real estate agents. The program
  deals with listings of available houses.

  Make examples of listings. Develop a data definition for listings of
  houses.  Implement the definition with classes. Translate the examples
  into objects.
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

class IListing {}

class EmptyListing {}

class ConsListing {
    House first;
    IListing rest;

    ConsListing(House first, IListing rest) {
        this.first = first;
        this.rest = rest;
    }
}

class ExamplesListings {
    IListing empty = new EmptyListing();
    IListing l1 = new ConsListing(ranch, empty);
    IListing l2 = new ConsListing(colonial, empty);
    IListing l3 = new ConsListing(cape, empty);

    IListing all = new ConsListing(ranch, new ConsListing(colonial, new ConsListing(cape, empty)));
}
