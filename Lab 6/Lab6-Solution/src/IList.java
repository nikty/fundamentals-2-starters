import tester.*;



// generic list
interface IList<T> {
  // map over a list, and produce a new list with a (possibly different)
  // element type
  <U> IList<U> map(IFunc<T, U> f);
  
  // foldr over a list, and produce a result of the base argument type
  <R> R foldr(IFunc2<T, R, R> func, R base);
  
  // Find the first element in this list where the result of function applied to
  // that element passes the predicate, and then return that result.
  <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup);
}

// empty generic list
class MtList<T> implements IList<T> {
  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }
  
  public <R> R foldr(IFunc2<T, R, R> func, R base) {
    return base;
  }
  
  public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
    return backup;
  }
}

// non-empty generic list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }
  
  public <R> R foldr(IFunc2<T, R, R> func, R base) {
    return func.apply(this.first, this.rest.foldr(func, base));
  }
  
  public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
    U result = convert.apply(this.first);
    if (pred.apply(result)) {
      return result;
    }
    return this.rest.findSolutionOrElse(convert, pred, backup);
  }
}

// Represents functions of signature A -> R, for some argument type A and
// result type R
interface IFunc<A, R> {
  R apply(A input);
}

// Represents functions of signature A1 A2 -> R, for some argument types A1 and
// A2 and result type R
interface IFunc2<A1, A2, R> {
  R apply(A1 input1, A2 input2);
}

// Represents functions that always return a boolean 
interface IPred<T> {
  boolean apply(T input);
}

// To begin, implement the equivalent of foldr from ISL on IList<T>s. What new types will you need?
// Write a test that sums a list of numbers using your foldr method.

class DoubleSquare implements IFunc<Double, Double> {
  public Double apply(Double n) {
    return n * n;
  }
}

class DoubleSum implements IFunc2<Double, Double, Double> {
  public Double apply(Double number, Double base) {
    return number + base;
  }
}

class IntegerSum implements IFunc2<Integer, Integer, Integer> {
  public Integer apply(Integer number, Integer base) {
    return number + base;
  }
}

class DoubleGreaterThanOne implements IPred<Double> {
  public boolean apply(Double n) {
    return n > 1;
  }
}

class IListExamples {
  
  IList<Double> lon1 = new ConsList<>(1.0,
      new ConsList<>(2.0,  new ConsList<>(3.0, new MtList<>())));
  IList<Double> lon1ExpectedSquared = new ConsList<>(1.0,
      new ConsList<>(4.0,  new ConsList<>(9.0, new MtList<>())));
  
  Double lon1ExpectedSum = 6.0;
  
  public boolean testIListMap(Tester t) {
    return t.checkExpect(lon1.map(new DoubleSquare()), lon1ExpectedSquared);
  }
  
  
  public boolean testIListFoldr(Tester t) {
    return t.checkExpect(lon1.foldr(new DoubleSum(), 0.0),
        lon1ExpectedSum);
  }
  
  public boolean testFindSolutionOrElse(Tester t) {
    return t.checkExpect(lon1.findSolutionOrElse(new DoubleSquare(),
        new DoubleGreaterThanOne(), -1.0),
        4.0);
  }
  
}





