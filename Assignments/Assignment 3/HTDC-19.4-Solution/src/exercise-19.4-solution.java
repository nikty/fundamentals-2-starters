/*
  Exercise 19.4

  Compare the two classes in figure 86. A Set is a collection of
  integers that contains each element at most once. A Bag is also a
  collecion of integers, but an integer may show up many times in a
  bag.

  Your tasks are:
  
  1. The two class definitions use lists of integers to keep track of
  elements. Design a representation of lists of integers with this
  interface:
  
  // // a list of integers
  // interface ILin {
  //   int howMany(int i);
  // }
  
  The constructors are MTin for the empty list and Cin for
  constructing a list from an integer and an existing list.

  2. Develop examples of Sets and Bags.
  
  3. Develop functional examples for all methods in Set and Bag. Turn
  them into tests.
  
  4. The two classes clearly share a number of similarities. Create a
  union and lift the commonalities into an abstract superclass. Name
  the union interface ICollection. Don’t forget to re-run your test
  suite at each step.
  
  5. Develop the method size, which determines how many elements a Bag
  or a Set contain. If a Bag contains an integer n times, it
  contributes n to size.
  
  6. Develop the method rem, which removes a given integer. If a Bag
  contains an integer more than once, only one of them is removed.
*/

import tester.*;

// Union Interface for the bags and sets
interface ICollection {
    // add i to this collection
    ICollection add(int i);

    // is i a member of this collection?
    boolean in(int i);

    // return this collection size
    int size();

    // Return this collection with given integer removed
    ICollection rem(int i);
}

abstract class ACollection implements ICollection {

    ILin elements;
    ACollection(ILin elements) {
        this.elements = elements;
    }
    public abstract ICollection add(int i);

    public boolean in(int i) {
        return this.elements.howMany(i) > 0;
    }

    public int size() {
        return this.elements.size();
    }

    public abstract ICollection rem(int i);
}

// a set of integers:
// contains an integer at most once
class Set extends ACollection {
    Set(ILin elements) {
        super(elements);
    }
    // add i to this set unless it is already in there
    public ICollection add(int i) {
        if (this.in(i)) {
            return this;
        } else {
            return new Set(new Cin(i, this.elements));
        }
    }

    public ICollection rem(int i) {
        return new Set(this.elements.rem(i));
    }
}
 
// a bag of integers
class Bag extends ACollection {
    Bag(ILin elements) {
        super(elements);
    }
    // add i to this bag
    public ICollection add(int i) {
        return new Bag(new Cin(i, this.elements));
    }
    // how often is i in this bag?
    int howMany(int i) {
        return this.elements.howMany(i);
    }

    public ICollection rem(int i) {
        return new Bag(this.elements.rem(i));
    }
}

// a list of integers
interface ILin {
    // count how many times i occurs in this list
    int howMany(int i);

    // return number of integers in this list
    int size();

    // remove first occurrence of given integer from this list
    ILin rem(int i);
}

class MTin implements ILin {
    MTin() {}

    public int howMany(int i) {
        return 0;
    }

    public int size() {
        return 0;
    }

    public ILin rem(int i) {
        return this;
    }
}

class Cin implements ILin {
    int first;
    ILin rest;
    Cin(int first, ILin rest) {
        this.first = first;
        this.rest = rest;
    }

    public int howMany(int i) {
        if (this.first == i) {
            return 1 + this.rest.howMany(i);
        } else {
            return this.rest.howMany(i);
        }
    }

    public int size() {
        return 1 + this.rest.size();
    }

    public ILin rem(int i) {
        if (this.first == i) {
            return this.rest;
        } else {
            return new Cin(this.first, this.rest.rem(i));
        }
    }
}

class Examples {
    ILin l1 = new Cin(1, new Cin(2, new Cin(3, new MTin())));
    ILin l2 = new Cin(1, new Cin(1, new Cin(2, new MTin())));
    Set s1 = new Set(l1);
    Bag b1 = new Bag(l1);
    Bag b2 = new Bag(l2);

    boolean testILinHowMany(Tester t) {
        return t.checkExpect(l1.howMany(1), 1)
            && t.checkExpect(l1.howMany(0), 0);
    }

    boolean testAdd(Tester t) {
        return t.checkExpect(s1.add(1), s1)
            && t.checkExpect(b1.add(1), new Bag(new Cin(1, l1)));
    }

    boolean testIn(Tester t) {
        return t.checkExpect(s1.in(1), true)
            && t.checkExpect(s1.in(5), false)
            && t.checkExpect(b1.in(1), true)
            && t.checkExpect(b1.in(5), false);
    }

    boolean testHowMany(Tester t) {
        return t.checkExpect(b1.howMany(1), 1)
            && t.checkExpect(b2.howMany(1), 2);
    }

    boolean testSize(Tester t) {
        return t.checkExpect(s1.size(), 3)
            && t.checkExpect(b1.size(), 3)
            && t.checkExpect(b2.size(), 3);
    }

    boolean testICRem(Tester t) {
        return t.checkExpect(s1.rem(0), s1)
            && t.checkExpect(s1.rem(1), new Set(new Cin(2, new Cin(3, new MTin()))))
            && t.checkExpect(b2.rem(1), new Bag(new Cin(1, new Cin(2, new MTin()))));
    }
}
