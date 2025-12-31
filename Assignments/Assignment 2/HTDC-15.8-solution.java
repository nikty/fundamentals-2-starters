/*
  Exercise 15.8

  Design the following methods for the class hierarchy representing
  river systems:
  
  1. maxLength, which computes the length of the longest path through
  the river system;
  
  2. confluences, which counts the number of confluences in the river
  system; and

  3. locations, which produces a list of all locations on this river,
  including sources, mouths, and confluences.
*/

import tester.*;

// a river system
interface IRiver {
    // ??? nnn();

    // Count the number of sources for this river system
    int sources();

    // Return true if the given location belongs to the river system
    boolean onRiver(Location loc);

    // Return the length of the river system
    int length();

    // Return the length of the longest path through the river system
    int maxLength();

    // Return the number of confluences in the river system
    int confluences();

    // Produce a list of all locations on this river
    ILoL locations();
}

// interp. the end of a river
class Mouth {
    Location loc;
    IRiver river;

    Mouth(Location loc, IRiver river) {
        this.loc = loc;
        this.river = river;
    }

    // ??? nnn() {
    //   this.loc.mmm()
    //   this.river.nnn()
    // }

    // Count the number of sources for this river mouth
    int sources() {
        return this.river.sources();
    }

    // Return true if the given location belongs to the river with this mouth
    boolean onRiver(Location loc) {
        return this.loc.same(loc) || this.river.onRiver(loc);
    }

    // Return the length of the river system
    int length() {
        return river.length();
    }

    // Return the length of the longest path through the river system
    int maxLength() {
        return this.river.maxLength();
    }

    // Return the number of confluences in the river system
    int confluences() {
        return this.river.confluences();
    }

    // Produce a list of all locations on this river
    ILoL locations() {
        return new ConsLoL(this.loc, this.river.locations());
    }
}

// interp. a location on a river
class Location {
    int x;
    int y;
    String name;

    Location(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    // ??? nnn() {
    //   ... this.x
    //   ... this.y
    //   ... this.name
    // }

    // Return true if the given location is the same as this
    boolean same(Location loc) {
        return this.x == loc.x && this.y == loc.y;
    }
}

// interp. the source of a river
class Source implements IRiver {
    Location loc;
    int miles; // length of the downward segment

    Source(Location loc, int miles) {
        this.loc = loc;
        this.miles = miles;
    }
    
    // ??? nnn() {
    //   this.loc.mmm()
    // }

    public int sources() {
        return 1;
    }

    public boolean onRiver(Location loc) {
        return this.loc.same(loc);
    }

    public int length() {
        return this.miles;
    }

    public int maxLength() {
        return this.miles;
    }

    public int confluences() {
        return 0;
    }

    public ILoL locations() {
        return new ConsLoL(this.loc, new EmptyLoL());
    }

}

// interp. a confluence of two rivers
class Confluence implements IRiver {
    Location loc;
    IRiver left;
    IRiver right;
    int miles; // length of the downward segment

    Confluence(Location loc, IRiver left, IRiver right, int miles) {
        this.loc = loc;
        this.left = left;
        this.right = right;
        this.miles = miles;
    }

    // ??? nnn() {
    //   ... this.loc.mmm()
    //   ... this.left.nnn()
    //   ... this.right.nnn()
    // }

    public int sources() {
        return left.sources() + right.sources();
    }

    public boolean onRiver(Location loc) {
        return this.loc.same(loc) || this.left.onRiver(loc) || this.right.onRiver(loc);
    }

    public int length() {
        return this.miles + this.left.length() + this.right.length();
    }

    public int maxLength() {
        return this.miles + Math.max(this.left.maxLength(),
                                     this.right.maxLength());
    }

    public int confluences() {
        return 1 + this.left.confluences() + this.right.confluences();
    }

    public ILoL locations() {
        return new ConsLoL(this.loc,
                           this.left.locations().append(this.right.locations()));
    }
}

// interp. list of locations; one of
// - EmptyLoL
// - ConsLoL
interface ILoL {
    // Append given list to this list
    ILoL append(ILoL lst);
}

class EmptyLoL implements ILoL {
    EmptyLoL() {}

    public ILoL append(ILoL lst) {
        return lst;
    }
}

class ConsLoL implements ILoL {
    Location first;
    ILoL rest;
    
    ConsLoL(Location first, ILoL rest) {
        this.first = first;
        this.rest = rest;
    }

    public ILoL append(ILoL lst) {
        return new ConsLoL(this.first,
                            this.rest.append(lst));
    }
}

 
class RiverSystemExample {
    RiverSystemExample() { }

    /*
      s
       \    t
        \  /
         b      u
          \    /
           \  /
            a
            |
            |
            m
    */
    /* Lengths:
       sb: 3
       tb: 2
       ba: 3
       ua: 1
       am: 4
    */
    Location lm = new Location(7, 5, "m");
    Location la = new Location(5, 5, "a");
    Location lb = new Location(3, 3, "b");
    Location ls = new Location(1, 1, "s");
    Location lt = new Location(1, 5, "t");
    Location lu = new Location(3, 7, "u");
    IRiver s = new Source(ls, 3);
    IRiver t = new Source(lt, 2);
    IRiver u = new Source(lu, 1);
    IRiver b = new Confluence(lb, s, t, 3);
    IRiver a = new Confluence(la, b, u, 4);
    Mouth mouth = new Mouth(lm, a);

    boolean testSources(Tester t) {
        return t.checkExpect(s.sources(), 1)
            && t.checkExpect(a.sources(), 3)
            && t.checkExpect(b.sources(), 2)
            && t.checkExpect(mouth.sources(), 3);
    }

    boolean testOnRiver(Tester t) {
        return t.checkExpect(mouth.onRiver(new Location(7, 5, "m")), true)
            && t.checkExpect(mouth.onRiver(new Location(1, 2, "f")), false)
            && t.checkExpect(mouth.onRiver(new Location(1, 1, "s")), true)
            && t.checkExpect(mouth.onRiver(new Location(5, 5, "a")), true)
            && t.checkExpect(a.onRiver(new Location(100, 200, "x")), false);
    }

    boolean testLength(Tester t) {
        return t.checkExpect(s.length(), 3)
            && t.checkExpect(this.t.length(), 2)
            && t.checkExpect(u.length(), 1)
            && t.checkExpect(b.length(), 8)
            && t.checkExpect(a.length(), 13)
            && t.checkExpect(mouth.length(), 13);
    }

    boolean testMaxLength(Tester t) {
        return t.checkExpect(mouth.maxLength(), 10)
            && t.checkExpect(s.maxLength(), 3)
            && t.checkExpect(b.maxLength(), 6);
    }

    boolean testConfluences(Tester t) {
        return t.checkExpect(mouth.confluences(), 2)
            && t.checkExpect(s.confluences(), 0)
            && t.checkExpect(b.confluences(), 1);
    }

    boolean testLocations(Tester t) {
        return t.checkExpect(mouth.locations(),
                             new ConsLoL
                             (lm, new ConsLoL
                              (la, new ConsLoL
                               (lb, new ConsLoL
                                (ls, new ConsLoL
                                 (lt, new ConsLoL
                                  (lu, new EmptyLoL())))))))
            && t.checkExpect(b.locations(),
                             new ConsLoL
                             (lb, new ConsLoL
                              (ls, new ConsLoL
                               (lt, new EmptyLoL()))));
    }
}
