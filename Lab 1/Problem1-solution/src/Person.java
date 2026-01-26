// To represent a person
interface IPerson {
  
}

class Person implements IPerson {
  String name;
  int age; // in years
  String gender; // aka sex
  
  Person(String name, int age, String gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }
}

class ExamplesPerson {
  IPerson tim = new Person("Tim", 23, "Male");
  IPerson kate = new Person("Kate", 22, "Female");
  IPerson rebecca = new Person("Rebecca", 31, "Female"); // but she thinks it's "Non-binary"
  // here's biology book https://openstax.org/details/books/biology-2e for you
}