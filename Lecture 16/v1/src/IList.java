interface IList<T> {
  // Return a list of elements satisfying the given predicate
  IList<T> find(IPred<T> pred);
  
  // Sort this list by the given predicate
  IList<T> sort(IComp<T> comp);
  IList<T> insert(IComp<T> comp, T t);
  
  <R>IList<R> map(IFunction<T, R> func); 
  
  <R> R foldr(IFunction2<T, R, R> func, R base);
  
  <R> R foldl(IFunction2<T, R, R> func, R base);
}


class MtList<T> implements IList<T> {
  public IList<T> find(IPred<T> pred) {
    return this;
  }
  
  public IList<T> sort(IComp<T> comp) {
    return this;
  }
  
  public IList<T> insert(IComp<T> comp, T t) {
    return new ConsList<T>(t, this);
  }
  
  public <R> IList<R> map(IFunction<T, R> func) {
    return new MtList<R>();
  }
  
  public <R> R foldr(IFunction2<T, R, R> func, R base) {
    return base;
  }
  
  public <R> R foldl(IFunction2<T, R, R> func, R base) {
    return base;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public IList<T> find(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.find(pred));
    }
    else {
      return this.rest.find(pred);
    }
  }
  
  public IList<T> sort(IComp<T> comp) {
    return this.rest.sort(comp).insert(comp,  this.first);
  }
  
  public IList<T> insert(IComp<T> comp, T t) {
    if (comp.compare(this.first, t) < 0) {
      return new ConsList<T>(this.first, this.rest.insert(comp, t));
    } else {
      return new ConsList<T>(t, this);
    }
  }
  
  public <R> IList<R> map(IFunction<T, R> func) {
    return new ConsList<R>(func.apply(this.first),
        this.rest.map(func));
  }
  
  public <R> R foldr(IFunction2<T, R, R> func, R base) {
    return func.apply(this.first, this.rest.foldr(func, base));
  }
  
  public <R> R foldl(IFunction2<T, R, R> func, R base) {
    
    return this.rest.foldl(func, func.apply(this.first, base));
  }
}




