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

// a set of integers:
// contains an integer at most once
class Set {
    ILin elements;
    Set(ILin elements) {
        this.elements = elements;
    }
    // add i to this set unless it is already in there
    Set add(int i) {
        if (this.in(i)) {
            return this;
        } else {
            return new Set(new Cin(i, this.elements));
        }
    }
    // is i a member of this set?
    boolean in(int i) {
        return this.elements.howMany(i) > 0;
    }
}
 
// a bag of integers
class Bag {
    ILin elements;
    Bag(ILin elements) {
        this.elements = elements;
    }
    // add i to this bag
    Bag add(int i) {
        return new Bag(new Cin(i, this.elements));
    }
    // is i a member of this bag?
    boolean in(int i) {
        return this.elements.howMany(i) > 0;
    }
    // how often is i in this bag?
    int howMany(int i) {
        return this.elements.howMany(i);
    }
}
