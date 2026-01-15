// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // Produce a new list, sorted in alphabetical order, treating all Strings in
  // this list as if they were given in all lowercase
  ILoString sort();

  // Insert given string to this list
  // Assume: the list is sorted according to sort()
  ILoString sortHelperInsert(String s);

  // Return true if this list is sorted in alphabetical order
  // in case-insensitive way.
  boolean isSorted();

  // Helper method for isSorted
  boolean isSortedHelper(String prev);
  
  // Return true if the first string in this list is alphabetically before
  // the given string. Comparison is case-insensitive
  boolean firstBeforeCI(String s);

  // Given a list of Strings, produces a list where the first, third, fifth...
  // elements are from this list, and the second, fourth, sixth... elements are
  // from the given list. Any “leftover” elements (when one list is longer than
  // the other) are left at the end.
  ILoString interleave(ILoString los);

  /*
   * // Return a list formed from the given string and first element of this list
   * (if // any) ILoString interleavePair(String s);
   * 
   * // Helper method to interleave given list with the rest of this list
   * ILoString interleaveRest(ILoString los);
   * 
   */

  // Append given list to the end of this list
  ILoString append(ILoString los);

  // Given sorted list of Strings, produce a sorted list of Strings that contains
  // all items in
  // both lists, including duplicates.
  // Assume: this list is sorted.
  ILoString merge(ILoString los);

  // Produce a new list of Strings containing the
  // same elements as this list of Strings, but in reverse order.
  ILoString reverse();

  // Helper method with accumulator for reverse()
  ILoString reverseHelper(ILoString acc);

  // Return true if this list contains pairs of identical
  // strings, that is, the first and second strings are the same, the third and
  // fourth are the same, the fifth and sixth are the same, etc.
  boolean isDoubledList();

  // Return true if first element of this equals given element, and
  // the rest of this is doubled list
  boolean isDoubleListHelper(String s);

  // Return true if this list contains the same words
  // reading the list in either order.
  boolean isPalindromeList();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  public ILoString sort() {
    return this;
  }

  public ILoString sortHelperInsert(String s) {
    return new ConsLoString(s, this);
  }

  public boolean isSorted() {
    return true;
  }

  public boolean isSortedHelper(String prev) {
    return true;
  }
  
  public boolean firstBeforeCI(String s) {
    return true;
  }

  public ILoString interleave(ILoString los) {
    return los;
  }

  /*
   * public ILoString interleavePair(String s) { return new ConsLoString(s, this);
   * }
   * 
   * public ILoString interleaveRest(ILoString los) { return los; }
   */
  public ILoString append(ILoString los) {
    return los;
  }

  public ILoString merge(ILoString los) {
    return los;
  }

  public ILoString reverse() {
    return this;
  }

  public ILoString reverseHelper(ILoString acc) {
    return acc;
  }

  public boolean isDoubledList() {
    return true;
  }

  public boolean isDoubleListHelper(String s) {
    return false;
  }
  
  public boolean isPalindromeList() {
    return true;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  public ILoString sort() {
    return this.rest.sort().sortHelperInsert(this.first);
  }

  public ILoString sortHelperInsert(String s) {
    if (this.firstBeforeCI(s)) {
      return new ConsLoString(this.first, this.rest.sortHelperInsert(s));
    }
    return new ConsLoString(s, this);
  }

  public boolean isSorted() {
    return this.rest.isSortedHelper(this.first);
  }

  public boolean isSortedHelper(String prev) {
    if (this.firstBeforeCI(prev)) {
      return false;
    }
    return this.isSorted();
  }
  
  public boolean firstBeforeCI(String s) {
    return this.first.toLowerCase().compareTo(s.toLowerCase()) < 0;
  }

  /*
   * public ILoString interleave(ILoString los) { return
   * los.interleavePair(this.first).append(los.interleaveRest(this.rest)); }
   */

  public ILoString interleave(ILoString los) {
    return new ConsLoString(this.first, los.interleave(this.rest));
  }

  /*
   * public ILoString interleavePair(String s) { return new ConsLoString(s, new
   * ConsLoString(this.first, new MtLoString())); }
   * 
   * public ILoString interleaveRest(ILoString los) { return
   * los.interleave(this.rest); }
   */

  public ILoString append(ILoString los) {
    return new ConsLoString(this.first, this.rest.append((los)));
  }

  public ILoString merge(ILoString los) {
    return this.rest.merge(los.sortHelperInsert(this.first));
  }

  public ILoString reverse() {
    return this.reverseHelper(new MtLoString());
  }

  public ILoString reverseHelper(ILoString acc) {
    return this.rest.reverseHelper(new ConsLoString(this.first, acc));
  }

  public boolean isDoubledList() {
    return this.rest.isDoubleListHelper(this.first);
  }

  public boolean isDoubleListHelper(String s) {
    return this.first.equals(s) && this.rest.isDoubledList();
  }
  
  public boolean isPalindromeList() {
    // a, b, b, a + a, b, b, a == a, a, b, b, b, b, a, a
    return this.interleave(this.reverse()).isDoubledList();
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));

  ILoString los1 = new ConsLoString("C",
      new ConsLoString("b", new ConsLoString("a", new MtLoString())));

  ILoString los2 = new ConsLoString("foo",
      new ConsLoString("quux", new ConsLoString("baz", new ConsLoString("abc", new MtLoString()))));

  ILoString los3 = new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("c", new MtLoString())));

  ILoString los4 = new ConsLoString("a",
      new ConsLoString("a", new ConsLoString("c", new MtLoString())));

  ILoString los5 = new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("b", new MtLoString())));

  ILoString los6 = new ConsLoString("a",
      new ConsLoString("a", new ConsLoString("b", new ConsLoString("b", new MtLoString()))));
  
  ILoString los7 = new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("b", new ConsLoString("a", new MtLoString()))));

  ILoString los1_sorted = new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("C", new MtLoString())));

  ILoString los2_sorted = new ConsLoString("abc",
      new ConsLoString("baz", new ConsLoString("foo", new ConsLoString("quux", new MtLoString()))));

  ILoString los1_reversed = new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("C", new MtLoString())));

  ILoString los2_reversed = new ConsLoString("abc",
      new ConsLoString("baz", new ConsLoString("quux", new ConsLoString("foo", new MtLoString()))));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  boolean testSort(Tester t) {
    return t.checkExpect(los1.sort(), los1_sorted);
  }

  boolean testIsSorted(Tester t) {
    return t.checkExpect(los1.isSorted(), false) && t.checkExpect(los1_sorted.isSorted(), true)
        && t.checkExpect(los2.isSorted(), false) && t.checkExpect(los2_sorted.isSorted(), true);
  }

  boolean testInterleave(Tester t) {
    return t.checkExpect(los1.interleave(new MtLoString()), los1)
        && t.checkExpect(new MtLoString().interleave(los1), los1)
        && t.checkExpect(los1.interleave(los2),
            new ConsLoString("C",
                new ConsLoString("foo",
                    new ConsLoString("b", new ConsLoString("quux",
                        new ConsLoString("a",
                            new ConsLoString("baz", new ConsLoString("abc", new MtLoString()))))))))
        && t.checkExpect(los2.interleave(los1),
            new ConsLoString("foo",
                new ConsLoString("C",
                    new ConsLoString("quux", new ConsLoString("b", new ConsLoString("baz",
                        new ConsLoString("a", new ConsLoString("abc", new MtLoString()))))))));
  }

  boolean testMerge(Tester t) {
    return t.checkExpect(los1_sorted.merge(los2_sorted),
        new ConsLoString("a",
            new ConsLoString("abc",
                new ConsLoString("b", new ConsLoString("baz", new ConsLoString("C",
                    new ConsLoString("foo", new ConsLoString("quux", new MtLoString()))))))));
  }

  boolean testReverse(Tester t) {
    return t.checkExpect((new MtLoString()).reverse(), new MtLoString())
        && t.checkExpect(los1.reverse(), los1_reversed)
        && t.checkExpect(los2.reverse(), los2_reversed);
  }

  boolean testIsDoubledList(Tester t) {
    return t.checkExpect((new MtLoString()).isDoubledList(), true)
        && t.checkExpect(los3.isDoubledList(), false) && t.checkExpect(los4.isDoubledList(), false)
        && t.checkExpect(los5.isDoubledList(), false) && t.checkExpect(los6.isDoubledList(), true);
  }
  
  boolean testIsPalindromeList(Tester t) {
    return t.checkExpect(los1.isPalindromeList(), false)
        && t.checkExpect(los7.isPalindromeList(), true);
  }
}