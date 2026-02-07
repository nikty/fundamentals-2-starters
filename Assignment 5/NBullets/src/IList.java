interface IList<T> {
  // Return the length of this list
  int length();
  
  // Append the given list to this list
  IList<T> append(IList<T> lst);
  
  // Fold right
  <R> R foldr(IFunction2<T, R, R> func, R base);
  
  // Map
  <R> IList<R>  map(IFunction<T, R> func);
  
  // Remove
  IList<T> remove(IPred<T> pred);
  
  // Whether the predicate is true for any element of the list
  boolean some(IPred<T> pred);
  
}

class MtList<T> implements IList<T> {
  public int length() {
    return 0;
  }
  
  public IList<T> append(IList<T> lst) {
    return lst;
  }
  
  public <R> R foldr(IFunction2<T, R, R> func, R base) {
    return base;
  }
  
  public <R> IList<R> map(IFunction<T, R> func) {
    return new MtList<R>();
  }
  
  public IList<T> remove(IPred<T> pred) {
    return this;
  }
  
  public boolean some(IPred<T> pred) {
    return false;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public int length() {
    return 1 + this.rest.length();
  }
  
  public IList<T> append(IList<T> lst) {
    return new ConsList<T>(this.first, this.rest.append(lst));
  }
  
  public <R> R foldr(IFunction2<T, R, R> func, R base) {
    return func.apply(this.first, this.rest.foldr(func,  base));
  }
  
  public <R> IList<R> map(IFunction<T, R> func) {
    return new ConsList<R>(func.apply(this.first), this.rest.map(func));
  }
  
  public IList<T> remove(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return this.rest.remove(pred);
    } else {
      return new ConsList<T>(this.first, this.rest.remove(pred));
    }
  }
  
  public boolean some(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return true;
    }
    return this.rest.some(pred);
  }
}

// Function object with 2 parameters
interface IFunction2<A1, A2, R> {
  R apply(A1 a1, A2 a2);
}

// Function object with one parameter
interface IFunction<T, R> {
  R apply(T t);
}

// Predicate object
interface IPred<T> {
  boolean apply(T t);
}

class NotPred<T> implements IPred<T> {
  IPred<T> pred;
  NotPred(IPred<T> pred) {
    this.pred = pred;
  }
  public boolean apply(T t) {
    return !pred.apply(t);
  }
}





