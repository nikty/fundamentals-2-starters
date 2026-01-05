/*
  Variant A
  =========
  
  Suppose you are given a list of integers. Your task is to determine
  if this list contains:

  - A number that is even
  - A number that is positive and odd
  - A number between 5 and 10, inclusive

  The order in which you find numbers in the list satisfying these
  requirements does not matter. The list could have many more numbers
  than you need. Any number in the list may satisfy multiple
  requirements. For example, the list (in Racket notation) (list 6 5)
  satisfies all three requirements, while the list (list 4 3) does
  not.

  Design a method on lists of integers to check whether the list
  satisfies these criteria. Hint: what information do you need to
  propagate down the recursive calls as you process the list?

  Variant B
  =========

  Again, the list must contain numbers satisfying the three
  requirements above. Again, order does not matter. This time, a given
  number in the list may only be used to satisfy a single requirement;
  however, duplicate numbers are permitted to satisfy multiple
  requirements. So, (list 6 5) does not meet all the criteria for this
  variant, but (list 6 5 6) does.

  Design a new method on lists of integers to check for this stricter
  property. How does your design differ from Variant A?

  Variant C
  =========

  Again, the list must contain numbers satisfying the three
  requirements above. Again, order does not matter. Again, a given
  number in the list may only be used to satisfy a single
  requirement. This time, however, the list may not contain any
  extraneous numbers. So, (list 6 5 6) satisfies all our criteria for
  this variant, but (list 6 5 42 6) does not.

  Design a third method on lists of integers to check whether the list
  meets this new property.
*/

import tester.*;

// interp. To register if criteria check
class Criteria {
    boolean isEven;
    boolean isPositiveOdd;
    boolean is5to10;

    Criteria(boolean isEven, boolean isPositiveOdd, boolean is5to10) {
        this.isEven = isEven;
        this.isPositiveOdd = isPositiveOdd;
        this.is5to10 = is5to10;
    }

    boolean allSatisfied() {
        return this.isEven && this.isPositiveOdd && this.is5to10;
    }

    Criteria checkEven(int n) {
        if (n % 2 == 0) {
            return new Criteria(true, this.isPositiveOdd, this.is5to10);
        }
        return this;
    }

    Criteria checkPositiveOdd(int n) {
        if (n % 2 != 0 && n > 0) {
            return new Criteria(this.isEven, true, this.is5to10);
        }
        return this;
    }

    Criteria check5to10(int n) {
        if (n >= 5 && n <= 10) {
            return new Criteria(this.isEven, this.isPositiveOdd, true);
        }
        return this;
    }
}

// interp. A list of integers
interface ILoI {
    boolean isVariantA();
    
    boolean isVariantAHelper(Criteria c);

    boolean isVariantB();

    boolean isVariantBHelper(Criteria c);

    boolean isVariantC();

    boolean isVariantCHelper(Criteria c, int count);
}
        

// interp. empty list
class MtLoI implements ILoI {
    MtLoI() {}

    public boolean isVariantA() {
        return false;
    }

    public boolean isVariantAHelper(Criteria c) {
        return false;
    }

    public boolean isVariantB() {
        return false;
    }

    public boolean isVariantBHelper(Criteria c) {
        if (c.allSatisfied()) {
            return true;
        }
        return false;
    }

    public boolean isVariantC() {
        return false;
    }

    public boolean isVariantCHelper(Criteria c, int count) {
        if (c.allSatisfied() && count == 3) {
            return true;
        }
        return false;
    }
}

class ConsLoI implements ILoI {
    int first;
    ILoI rest;

    ConsLoI(int first, ILoI rest) {
        this.first = first;
        this.rest = rest;
    }

    public boolean isVariantA() {
        return this.isVariantAHelper(new Criteria(false, false, false));
    }

    public boolean isVariantAHelper(Criteria c) {
        if (c.checkEven(this.first)
            .checkPositiveOdd(this.first)
            .check5to10(this.first)
            .allSatisfied()) {
            return true;
        } else {
            return this.rest.isVariantAHelper(c.checkEven(this.first)
                                              .checkPositiveOdd(this.first)
                                              .check5to10(this.first));
        }
    }

    public boolean isVariantB() {
        return this.isVariantBHelper(new Criteria(false, false, false));
    }

    public boolean isVariantBHelper(Criteria c) {
        return this.rest.isVariantBHelper(c.checkEven(this.first))
            || this.rest.isVariantBHelper(c.checkPositiveOdd(this.first))
            || this.rest.isVariantBHelper(c.check5to10(this.first));
    }

    public boolean isVariantC() {
        return this.isVariantCHelper(new Criteria(false, false, false), 0);
    }

    public boolean isVariantCHelper(Criteria c, int count) {
        return this.rest.isVariantCHelper(c.checkEven(this.first), count + 1)
            || this.rest.isVariantCHelper(c.checkPositiveOdd(this.first), count + 1)
            || this.rest.isVariantCHelper(c.check5to10(this.first), count + 1);
    }
}

class Examples {
    ILoI loi1 = new ConsLoI(6, new ConsLoI(5, new MtLoI()));
    ILoI loi2 = new ConsLoI(3, new ConsLoI(4, new MtLoI()));
    ILoI loi3 = new ConsLoI(2, new ConsLoI(5, new ConsLoI(11, new MtLoI())));
    ILoI loi4 = new ConsLoI(5, new ConsLoI(5, new MtLoI()));
    ILoI loi5 = new ConsLoI(6, new ConsLoI(5, new ConsLoI(6, new MtLoI())));
    ILoI loi6 = new ConsLoI(6, new ConsLoI(3, new ConsLoI(2, new MtLoI())));
    ILoI loi7 = new ConsLoI(2, new ConsLoI(3, new ConsLoI(6, new MtLoI())));
    ILoI loi8 = new ConsLoI(6, new ConsLoI(5, new ConsLoI(42, new ConsLoI(6, new MtLoI()))));
    ILoI loi9 = new ConsLoI(42, new ConsLoI(5, new ConsLoI(42, new ConsLoI(7, new MtLoI()))));
    
    boolean testIsVariantA(Tester t) {
        return t.checkExpect(loi1.isVariantA(), true)
            && t.checkExpect(loi2.isVariantA(), false)
            && t.checkExpect(loi3.isVariantA(), true)
            && t.checkExpect(new MtLoI().isVariantA(), false)
            && t.checkExpect(loi4.isVariantA(), false);
    }

    boolean testIsVariantB(Tester t) {
        return t.checkExpect(loi1.isVariantB(), false)
            && t.checkExpect(loi5.isVariantB(), true)
            && t.checkExpect(loi6.isVariantB(), true)
            && t.checkExpect(loi7.isVariantB(), true);
    }

    boolean testIsVariantC(Tester t) {
        return t.checkExpect(loi5.isVariantC(), true)
            && t.checkExpect(loi8.isVariantC(), false)
            && t.checkExpect(loi9.isVariantC(), false);
    }
}
