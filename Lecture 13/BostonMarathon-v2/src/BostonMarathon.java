import tester.*;

// to represent a runner in the Boston Marathon
class Runner {
  String name;
  int age;
  int bib;
  boolean isMale;
  int pos;
  int time;

  Runner(String name, int age, int bib, boolean isMale, int pos, int time) {
    this.name = name;
    this.age = age;
    this.bib = bib;
    this.isMale = isMale;
    this.pos = pos;
    this.time = time;
  }
  
  // Getter for isMale
  boolean isMale() {
    return this.isMale;
  }
}


// to represent a list of runners in the Boston Marathon
interface ILoR {
  // Return the list of all male runners in this list
  ILoR findAllMaleRunners();
  //Return the list of all female runners in this list
  ILoR findAllFemaleRunners();
}
// to represent an empty list of runners in Boston Marathon
class MTLoR implements ILoR {
  MTLoR() {}
  
  public ILoR findAllMaleRunners() {
    return this;
  }
  
  public ILoR findAllFemaleRunners() {
    return this;
  }
}


// to represent a nonempty list of runners in Boston Marathon
class ConsLoR implements ILoR {
  Runner first;
  ILoR rest;

  ConsLoR(Runner first, ILoR rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public ILoR findAllMaleRunners() {
    if (this.first.isMale()) {
      return new ConsLoR(this.first, this.rest.findAllMaleRunners());
    } else {
      return this.rest.findAllMaleRunners();
    }
  }
  
  public ILoR findAllFemaleRunners() {
    if (!this.first.isMale()) {
      return new ConsLoR(this.first, this.rest.findAllFemaleRunners());
    } else {
      return this.rest.findAllFemaleRunners();
    }
  }
}

// Examples and tests for the Boston Marathon program
class ExamplesMarathon {

  Runner johnny = new Runner("Kelly", 100, 999, true, 30, 360);
  Runner frank  = new Runner("Shorter", 32, 888, true, 245, 130);
  Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
  Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

  ILoR mtlist = new MTLoR();
  ILoR list1 = new ConsLoR(johnny, new ConsLoR(joan, mtlist));
  ILoR list2 = new ConsLoR(frank, new ConsLoR(bill, list1));
  
  boolean testFindAllMaleRunners(Tester t) {
    return t.checkExpect(list1.findAllMaleRunners(),
        new ConsLoR(johnny, mtlist));
  }
  
  boolean testFindAllFemaleRunners(Tester t) {
    return t.checkExpect(list1.findAllFemaleRunners(),
        new ConsLoR(joan, mtlist));
  }

}









