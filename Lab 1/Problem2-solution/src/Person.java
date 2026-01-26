// To represent a person
interface IPerson {
  
}

class Person implements IPerson {
  String name;
  int age; // in years
  String gender; // aka sex
  Address address;
  
  Person(String name, int age, String gender, Address address) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
  }
}

// to represend Person's address
class Address {
  String city;
  String state;
  
  Address(String city, String state) {
    this.city = city;
    this.state = state;
  }
}

class ExamplesPerson {
  Address timAddress = new Address("Boston", "MA");
  Address kateAddress = new Address("Warwick", "RI");
  Address rebeccaAddress = new Address("Nashua", "NH");
  
  IPerson tim = new Person("Tim", 23, "Male", timAddress);
  IPerson kate = new Person("Kate", 22, "Female", kateAddress);
  IPerson rebecca = new Person("Rebecca", 31, "Female", rebeccaAddress);
  
  Address mccarthyAddress = new Address("Palo Alto", "CA");
  Address neumannAddress = new Address("Princeton", "NJ");
  
  IPerson johnMcCarthy = new Person("John McCarthy", 84, "Male", mccarthyAddress);
  IPerson johnVonNeumann = new Person("John von Neumann", 53, "Male", neumannAddress);
}