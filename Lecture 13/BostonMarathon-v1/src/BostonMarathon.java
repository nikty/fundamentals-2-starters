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
}


// to represent a list of runners in the Boston Marathon
interface ILoR {
  
}
// to represent an empty list of runners in Boston Marathon
class MTLoR implements ILoR {
  MTLoR() {}
}


// to represent a nonempty list of runners in Boston Marathon
class ConsLoR implements ILoR {
  Runner first;
  ILoR rest;

  ConsLoR(Runner first, ILoR rest) {
    this.first = first;
    this.rest = rest;
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

}
