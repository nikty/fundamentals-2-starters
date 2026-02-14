import tester.*;

interface IShape {
  <R> R accept(IVisitorShape<R> visitor);
}

class Circle implements IShape {
  int x, y;
  int radius;
  String color;
  
  Circle(int x, int y, int r, String color) {
    this.x = x;
    this.y = y;
    this.radius = r;
    this.color = color;
  }
  
  public <R> R accept(IVisitorShape<R> visitor) {
    return visitor.visit(this);
  }
}
class Rect implements IShape {
  int x, y, w, h;
  String color;
  
  Rect(int x, int y, int w, int h, String color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }
  
  public <R> R accept(IVisitorShape<R> visitor) {
    return visitor.visit(this);
  }
}

class IShapeExamples {
  
  IShape circle1 = new Circle(0, 0, 10, "red");
  IShape rect1 = new Rect(0, 0, 10, 10, "blue");
  
  IList<IShape> emptyIshape = new MtList<IShape>();
  IList<Double> emptyDouble = new MtList<Double>();
  IList<String> emptyString = new MtList<String>();
  
  IList<IShape> shapes = new ConsList<IShape>(circle1,
      new ConsList<IShape>(rect1, emptyIshape));
  
  IList<Double> shapesExpectedAreas = new ConsList<Double>(314.15,
      new ConsList<Double>(100.0, emptyDouble));
  
  IList<String> shapesExpectedColors = new ConsList<String>("red",
      new ConsList<String>("blue", emptyString));
  
  boolean testIShapeMap(Tester t) {
    return t.checkInexact(shapes.map(new VisitorShapeArea()), shapesExpectedAreas, 0.01)
        && t.checkInexact(shapes.map(new VisitorShapeColor()), shapesExpectedColors, 0.01);
  }
  
}

















