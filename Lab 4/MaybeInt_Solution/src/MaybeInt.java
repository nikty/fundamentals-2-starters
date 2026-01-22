import tester.*;

// Recall [Maybe X] from last semester; that is, a [Maybe X] is either an X or false.
// Design a [Maybe Int] interface and the relevant classes.
// Note that false is somewhat of a red herring here, as it just represents an absent value.
// Think about what kind of class would represent an absent value well.

interface IMaybeInt {
}

class IntMaybeInt implements IMaybeInt {
  int value;

  IntMaybeInt(int value) {
    this.value = value;
  }
}

class AbsentMaybeInt implements IMaybeInt {
}

// Design a method on a list of integers which produces the integer
// (or a value indicating the list was empty) that appears in the longest
// consecutive sublist.
// For example, the list of 1,1,5,5,5,4,3,4,4,4 would produce 5
// (note that ties are broken by the sublist that appears earlier in the list).

interface ILoInt {
  
  IMaybeInt longestConsecutive();
  
  IMaybeInt longestConsecutiveHelper(IMaybeInt prev, int count,
      IMaybeInt longest, int longestCount);
}

class ConsLoInt implements ILoInt {
  IMaybeInt first;
  ILoInt rest;
  ConsLoInt(IMaybeInt first, ILoInt rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public IMaybeInt longestConsecutive() {
    return this.rest.longestConsecutiveHelper(this.first, 1, this.first, 1);
  }
  
  public IMaybeInt longestConsecutiveHelper(IMaybeInt prev, int count,
      IMaybeInt longest, int longestCount) {
    if (this.first.equals(prev)) {
      return this.rest.longestConsecutiveHelper(prev, count + 1, longest, longestCount);
    } else if (count > longestCount) {
      return this.rest.longestConsecutiveHelper(this.first, 1, prev, count);
    } else {
      return this.rest.longestConsecutiveHelper(this.first, 1, longest, longestCount);
    }
  }
}

class MtLoInt implements ILoInt {
  MtLoInt() {}
  
  public IMaybeInt longestConsecutive() {
    return new AbsentMaybeInt();
  }
  
  public IMaybeInt longestConsecutiveHelper(IMaybeInt prev, int count,
      IMaybeInt longest, int longestCount) {
    return longest;
  }
}

class Examples {
  IMaybeInt i1 = new IntMaybeInt(1);
  IMaybeInt i2 = new IntMaybeInt(2);
  IMaybeInt i3 = new IntMaybeInt(3);
  IMaybeInt i4 = new IntMaybeInt(4);
  IMaybeInt i5 = new IntMaybeInt(5);
  IMaybeInt iAbsent = new AbsentMaybeInt();
  
  ILoInt l1 = new ConsLoInt(i1,
      new ConsLoInt(i1,
          new ConsLoInt(i5,
              new ConsLoInt(i5,
                  new ConsLoInt(i5,
                      new ConsLoInt(i4,
                          new ConsLoInt(i3,
                              new ConsLoInt(i4,
                                  new ConsLoInt(i4,
                                      new ConsLoInt(i4, new MtLoInt()))))))))));
  ILoInt l0 = new MtLoInt();
  
  boolean testLongestConsecutive(Tester t) {
    return t.checkExpect(l1.longestConsecutive(), i5)
        && t.checkExpect(l0.longestConsecutive(), new AbsentMaybeInt());
  }
  
}
