// TODO:
// Today we want to help the race organizers find the final standings,
// find the winner of the race,
// and handle check-in registration, which requires knowing the names of all the runners.

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
  
  // Sort runners
  ILoR sortBy(IRunnerComparator comp);
  ILoR insertBy(IRunnerComparator comp, Runner r);
  
  // Finds the fastest Runner in this list of Runners
  Runner findWinner();
  
  // Finds the first Runner in this list of Runners
  Runner getFirst();
  
  Runner findMin(IRunnerComparator comp);
  
  Runner findMinHelper(IRunnerComparator comp, Runner rsf);
  
  Runner findMax(IRunnerComparator comp);
}

// to represent an empty list of runners in Boston Marathon
class MTLoR implements ILoR {
  MTLoR() {
  }

  public ILoR find(IRunnerPredicate pred) {
    return this;
  }
  
  public ILoR sortBy(IRunnerComparator comp) {
    return this;
  }
  
  public ILoR insertBy(IRunnerComparator comp, Runner r) {
    return new ConsLoR(r, this);
  }
  
  public Runner findWinner() {
    throw new RuntimeException("No winner of the empty list of Runners");
  }
  
  public Runner getFirst() {
    throw new RuntimeException("No first of the empty list of Runners");
  }
  
  public Runner findMin(IRunnerComparator comp) {
    throw new RuntimeException("No minimum of the empty list of Runners");
  }
  
  public Runner findMinHelper(IRunnerComparator comp, Runner rsf) {
    return rsf;
  }
  
  public Runner findMax(IRunnerComparator comp) {
    throw new RuntimeException("No max of the empty list of Runners");
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
  
  public ILoR sortBy(IRunnerComparator comp) {
    return this.rest.sortBy(comp).insertBy(comp, this.first);
  }
  
  public ILoR insertBy(IRunnerComparator comp, Runner r) {
    if (comp.compare(this.first, r) < 0) {
      return new ConsLoR(this.first, this.rest.insertBy(comp, r));
    }
    else {
      return new ConsLoR(r, this);
    }
  }
  
  public Runner findWinner() {
    return this.findMin(new CompareByTime());
  }
  
  public Runner getFirst() {
    return this.first;
  }
  
  public Runner findMin(IRunnerComparator comp) {
    return findMinHelper(comp, this.first);
  }
  
  public Runner findMinHelper(IRunnerComparator comp, Runner rsf) {
    if (comp.compare(this.first, rsf) < 0) {
      return this.rest.findMinHelper(comp, this.first);
    } else {
      return this.rest.findMinHelper(comp, rsf);
    }
  }
  
  public Runner findMax(IRunnerComparator comp) {
    return this.findMin(new ReverseComparator(comp));
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

interface ICompareRunners {
  // Returns true if r1 comes before r2 according to this ordering
  boolean comesBefore(Runner r1, Runner r2);
}

class CompareByTime implements IRunnerComparator {
  public int compare(Runner r1, Runner r2) {
    if (r1.time < r2.time) {
      return -1;
    } else if (r1.time == r2.time) {
      return 0;
    } else {
      return 1;
    }
  }
}

class CompareByName implements IRunnerComparator {
  public int compare(Runner r1, Runner r2) {
    return r1.name.compareTo(r2.name);
  }
}

// To compute a three-way comparison between two Runners
interface IRunnerComparator {
  // Returns a negative number if r1 comes before r2 in this order
  // Returns zero if r1 is tied with r2 in this order
  // Returns a positive number if r1 comes after r2 in this order
  int compare(Runner r1, Runner r2);
}

class ReverseComparator implements IRunnerComparator {
  IRunnerComparator comp;
  ReverseComparator(IRunnerComparator comp) {
    this.comp = comp;
  }
  public int compare(Runner r1, Runner r2) {
    return this.comp.compare(r2, r1);
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

  ILoR expectedList2MalesUnder4Hours = new ConsLoR(frank, new ConsLoR(bill, mtlist));

  ILoR expectedList2FemalesYounger40InFirst50 = new ConsLoR(joan, mtlist);

  ILoR expectedList2FemalesOrUnder4Hours = new ConsLoR(frank,
      new ConsLoR(bill, new ConsLoR(joan, mtlist)));
  
  ILoR expectedList2SortByTime =
      new ConsLoR(bill,
          new ConsLoR(frank, new ConsLoR(joan, (new ConsLoR(johnny, mtlist)))));
  
  ILoR expectedList2SortByName =
      new ConsLoR(joan,
          new ConsLoR(johnny, new ConsLoR(bill, (new ConsLoR(frank, mtlist)))));

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
        && t.checkExpect(
            list2.find(new AndPredicate(new RunnerIsMale(), new RunnerFinishUnder4Hours())),
            expectedList2MalesUnder4Hours)
        // Females under 40 started in first 50 positions
        && t.checkExpect(
            list2.find(new AndPredicate(new RunnerIsFemale(),
                new AndPredicate(new RunnerYoungerThan40(), new RunnerIsInFirst50()))),
            expectedList2FemalesYounger40InFirst50)
        // Runners who are female or who finish in less than 4 hours
        && t.checkExpect(
            list2.find(new OrPredicate(new RunnerIsFemale(), new RunnerFinishUnder4Hours())),
            expectedList2FemalesOrUnder4Hours);

  }
  
  boolean testSort(Tester t) {
    return t.checkExpect(list2.sortBy(new CompareByTime()), expectedList2SortByTime)
        && t.checkExpect(list2.sortBy(new CompareByName()), expectedList2SortByName);
        
  }

  boolean testFindWinner(Tester t) {
    return t.checkException(new RuntimeException("No winner of the empty list of Runners"),
        mtlist, "findWinner")
        && t.checkExpect(list2.findWinner(), bill);
  }
  
  boolean testFindMax(Tester t) {
    return t.checkExpect(list2.findMax(new CompareByTime()),
        johnny);
  }
}
