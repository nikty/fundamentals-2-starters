/*
  Exercise 15.3

  Suppose the requirements for the program that tracks a runner’s log
  includes this request:
  
  ... A runner wishes to know the length of his longest run ever.
  [He may eventually wish to restrict this inquiry into a particular
  season or runs between two dates.] ...
  
  Design the method that computes this number and add it to the class
  hierarchy. Assume that the method produces 0 if the log is empty.

  Also consider this variation of the problem:
  
  ... A runner wishes to know whether all distances are shorter
  than some number of miles. ...

  Does the template stay the same?
*/

import tester.*;

interface ILog {
    // Compute total number of miles in this log
    double miles();

    // Return a log of entries for the given month and year
    ILog oneMonth(int month, int year);

    // Return total distamce in a given month
    double milesForMonth(int month, int year);

    // Return the length of the longest run in the log
    double longestRun();

    // Return true if all distances in log are shorter than the given distance
    boolean allShorterThan(double distance);
}

class MtLog implements ILog {
    MtLog() {}

    public double miles() {
        return 0;
    }

    public ILog oneMonth(int month, int year) {
        return new MtLog();
    }

    public double milesForMonth(int month, int year) {
        return 0;
    }

    public double longestRun() {
        return 0;
    }

    public boolean allShorterThan(double distance) {
        return true;
    }
}

class ConsLog implements ILog {
    Entry first;
    ILog rest;
    ConsLog(Entry first, ILog rest) {
        this.first = first;
        this.rest = rest;
    }

    public double miles() {
        return this.first.distance + this.rest.miles();
    }

    public ILog oneMonth(int month, int year) {
        if (this.first.sameMonthAndYear(month, year)) {
            return new ConsLog(this.first, this.rest.oneMonth(month, year));
        } else {
            return this.rest.oneMonth(month, year);
        }
    }

    public double milesForMonth(int month, int year) {
        return this.oneMonth(month, year).miles();
    }

    public double longestRun() {
        if (this.first.distance > this.rest.longestRun()) {
            return this.first.distance;
        } else {
            return this.rest.longestRun();
        }
    }

    public boolean allShorterThan(double distance) {
        if (this.first.distance >= distance) {
            return false;
        } else {
            return this.rest.allShorterThan(distance);
        }
    }
}

class Entry {
    Date d;
    double distance; // miles
    int duration; // minutes
    String comment;

    Entry(Date d, double distance, int duration, String comment) {
        this.d = d;
        this.distance = distance;
        this.duration = duration;
        this.comment = comment;
    }

    // Whether this entry's date mathes the given month and year
    boolean sameMonthAndYear(int month, int year) {
        return this.d.sameMonthAndYear(month, year);
    }

}

class Date {
    int day;
    int month;
    int year;

    Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // Whether this date mathes the given month and year
    boolean sameMonthAndYear(int month, int year) {
        return (this.month == month) && (this.year == year);
    }
}

class CompositeExamples {
    CompositeExamples() { }
    
    Date d1 = new Date(5, 5, 2003);
    Date d2 = new Date(6, 6, 2003);
    Date d3 = new Date(23, 6, 2003);
    Entry e1 = new Entry(d1, 5.0, 25, "Good");
    Entry e2 = new Entry(d2, 3.0, 24, "Tired");
    Entry e3 = new Entry(d3, 26.0, 156, "Great");
    ILog l1 = new MtLog();
    ILog l2 = new ConsLog(e1,l1);
    ILog l3 = new ConsLog(e2,l2);
    ILog l4 = new ConsLog(e3,l3);


    boolean testMiles(Tester t) {
        return t.checkInexact(l1.miles(), 0.0, .1)
            && t.checkInexact(l2.miles(), 5.0, .1)
            && t.checkInexact(l3.miles(), 8.0, .1)
            && t.checkInexact(l4.miles(), 34.0, .1);
    }

    boolean testILogOneMonth(Tester t) {
        return t.checkExpect(l1.oneMonth(6, 2003), new MtLog())
            && t.checkExpect(l3.oneMonth(6, 2003), new ConsLog(e2, new MtLog()))
            && t.checkExpect(l4.oneMonth(6, 2003),
                             new ConsLog(e3, new ConsLog(e2, new MtLog())));
    }

    boolean testILogMilesForMonth(Tester t) {
        return t.checkInexact(l1.milesForMonth(6, 2003), 0.0, .001)
            && t.checkInexact(l2.milesForMonth(6, 2003), 0.0, .001)
            && t.checkInexact(l3.milesForMonth(6, 2003), 3.0, .001)
            && t.checkInexact(l4.milesForMonth(6, 2003), 29.0, .001);
    }

    boolean testILogLongestRun(Tester t) {
        return t.checkInexact(l1.longestRun(), 0.0, .001)
            && t.checkInexact(l2.longestRun(), 5.0, .001)
            && t.checkInexact(l3.longestRun(), 5.0, .001);
    }
    boolean testILogAllShorterThan(Tester t) {
        return t.checkExpect(l1.allShorterThan(5.0), true)
            && t.checkExpect(l2.allShorterThan(10.0), true)
            && t.checkExpect(l4.allShorterThan(10.0), false);
    }
    
}
