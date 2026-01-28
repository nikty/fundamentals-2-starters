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
  // Return a list of runners which satisfy given predicate
  ILoR find(IRunnerPredicate pred);
}

// to represent an empty list of runners in Boston Marathon
class MTLoR implements ILoR {
  MTLoR() {
  }

  public ILoR find(IRunnerPredicate pred) {
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

  public ILoR find(IRunnerPredicate pred) {
    if (pred.apply(this.first)) {
      return new ConsLoR(this.first, this.rest.find(pred));
    }
    else {
      return this.rest.find(pred);
    }
  }
}

// to represent "function object"
interface IRunnerPredicate {
  // to evaluate condition on a Runner object
  boolean apply(Runner r);
}

class RunnerIsMale implements IRunnerPredicate {
  public boolean apply(Runner r) {
    return r.isMale;
  }
}

class RunnerIsFemale implements IRunnerPredicate {
  public boolean apply(Runner r) {
    return !r.isMale;
  }
}

class RunnerIsInFirst50 implements IRunnerPredicate {
  public boolean apply(Runner r) {
    return r.pos <= 50;
  }
}

class RunnerFinishUnder4Hours implements IRunnerPredicate {
  public boolean apply(Runner r) {
    return r.time < 4 * 60;
  }
}

class RunnerYoungerThan40 implements IRunnerPredicate {
  public boolean apply(Runner r) {
    return r.age < 40;
  }
}

// Represents a predicate that is true whenever both of its component predicates are true
class AndPredicate implements IRunnerPredicate {
  IRunnerPredicate left;
  IRunnerPredicate right;

  AndPredicate(IRunnerPredicate left, IRunnerPredicate right) {
    this.left = left;
    this.right = right;
  }
  
  public boolean apply(Runner r) {
    return this.left.apply(r) && this.right.apply(r);
  }
}

//Represents a predicate that is true whenever either or both of its component predicates are true
class OrPredicate implements IRunnerPredicate {
  IRunnerPredicate left;
  IRunnerPredicate right;

  OrPredicate(IRunnerPredicate left, IRunnerPredicate right) {
    this.left = left;
    this.right = right;
  }

  public boolean apply(Runner r) {
    return this.left.apply(r) || this.right.apply(r);
  }
}

// Examples and tests for the Boston Marathon program
class ExamplesMarathon {

  Runner johnny = new Runner("Kelly", 100, 999, true, 30, 360);
  Runner frank = new Runner("Shorter", 32, 888, true, 245, 130);
  Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
  Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);

  ILoR mtlist = new MTLoR();
  ILoR list1 = new ConsLoR(johnny, new ConsLoR(joan, mtlist));
  ILoR list2 = new ConsLoR(frank, new ConsLoR(bill, list1));

  ILoR expectedList2PositionUnder50 = new ConsLoR(johnny, new ConsLoR(joan, mtlist));

  ILoR expectedList2FinishUnder4Hours = new ConsLoR(frank,
      new ConsLoR(bill, new ConsLoR(joan, mtlist)));

  ILoR expectedList2YoungerThan40 = new ConsLoR(frank,
      new ConsLoR(bill, new ConsLoR(joan, mtlist)));
  
  ILoR expectedList2MalesUnder4Hours = new ConsLoR(frank,
      new ConsLoR(bill, mtlist));
  
  ILoR expectedList2FemalesYounger40InFirst50 = new ConsLoR(joan, mtlist);
  
  ILoR expectedList2FemalesOrUnder4Hours =
      new ConsLoR(frank,
          new ConsLoR(bill, new ConsLoR(joan, mtlist)));

  boolean testFindMethods(Tester t) {

    return
    // Male runners
    t.checkExpect(list1.find(new RunnerIsMale()), new ConsLoR(johnny, mtlist))
        // Female runners
        && t.checkExpect(list1.find(new RunnerIsFemale()), new ConsLoR(joan, mtlist))
        // Position under 50
        && t.checkExpect(list2.find(new RunnerIsInFirst50()), expectedList2PositionUnder50)
        // Finish in under 4 hours
        && t.checkExpect(list2.find(new RunnerFinishUnder4Hours()), expectedList2FinishUnder4Hours)
        // Runners younger than 40
        && t.checkExpect(list2.find(new RunnerYoungerThan40()), expectedList2YoungerThan40)
        // Male runners who finish in under 4 hours
        && t.checkExpect(list2.find(
            new AndPredicate(new RunnerIsMale(),
                new RunnerFinishUnder4Hours())),
            expectedList2MalesUnder4Hours
            )
        // Females under 40 started in first 50 positions
        && t.checkExpect(list2.find(
            new AndPredicate(new RunnerIsFemale(),
                new AndPredicate(new RunnerYoungerThan40(),
                    new RunnerIsInFirst50()))),
            expectedList2FemalesYounger40InFirst50)
        // Runners who are female or who finish in less than 4 hours
        && t.checkExpect(list2.find(
            new OrPredicate(new RunnerIsFemale(),
                new RunnerFinishUnder4Hours())),
            expectedList2FemalesOrUnder4Hours);
            
  }

}
