interface IPred<T> {
  boolean apply(T t);
}

interface IComp<T> {
  int compare(T t1, T t2);
}

interface IFunction<T, R> {
  R apply(T t);
}

interface IFunction2<A1, A2, R> {
  R apply(A1 a1, A2 a2);
}

interface IVisitorShape<R> extends IFunction<IShape, R> {
  R visit(Circle circle);
  R visit(Rect rect);
}

class VisitorShapeArea implements IVisitorShape<Double> {
  public Double apply(IShape shape) {
    return shape.accept(this);
  }
  
  public Double visit(Circle circle) {
    return Math.PI * circle.radius * circle.radius;
  }
  public Double visit(Rect rect) {
    return rect.w * rect.h * 1.0;
  }
}

class VisitorShapeColor implements IVisitorShape<String> {
  public String apply(IShape shape) {
    return shape.accept(this);
  }
  
  public String visit(Circle circle) {
    return circle.color;
  }
  public String visit(Rect rect) {
    return rect.color;
  }
}




