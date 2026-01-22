import tester.*;

interface IShape {
  boolean sameShape(IShape that);
}
class Circle implements IShape {
  int x, y;
  int radius;
  Circle(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }
  
  //In Circle
  public boolean sameCircle(Circle that) {
    /* Template:
     * Fields:
     * this.x, this.y, this.radius
     *
     * Fields of parameters:
     * that.x, that.y, that.radius
     */
    return this.x == that.x &&
        this.y == that.y &&
        this.radius == that.radius;
  }
  
  public boolean sameShape(IShape that) {
    return this.sameCircle((Circle)that); // cast that to a Circle, and use the sameCircle helper
  }

}
class Rect implements IShape {
  int x, y;
  int w, h;
  Rect(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  //In Rect
  public boolean sameRect(Rect that) {
    /* Template:
     * Fields:
     * this.x, this.y, this.w, this.h
     *
     * Fields of parameters:
     * that.x, that.y, that.w, that.h
     */
    return this.x == that.x &&
        this.y == that.y &&
        this.w == that.w &&
        this.h == that.h;
  }
  
  public boolean sameShape(IShape that) {
    return this.sameRect((Rect)that); // cast that to a Circle, and use the sameCircle helper
  }

}

class Examples {
  //In test method in an Examples class
  IShape c1 = new Circle(3, 4, 5);
  IShape c2 = new Circle(4, 5, 6);
  IShape c3 = new Circle(3, 4, 5);
  IShape r1 = new Rect(3, 4, 5, 5);
  IShape r2 = new Rect(4, 5, 6, 7);
  IShape r3 = new Rect(3, 4, 5, 5);

//  boolean testSameCircle(Tester t) {
//    return t.checkExpect(c1.sameCircle(c2), false)
//        && t.checkExpect(c2.sameCircle(c1), false)
//        && t.checkExpect(c1.sameCircle(c3), true)
//        && t.checkExpect(c3.sameCircle(c1), true);
//  }
//
//  boolean testSameRect(Tester t) {
//    return t.checkExpect(r1.sameRect(r2), false)
//        && t.checkExpect(r2.sameRect(r1), false)
//        && t.checkExpect(r1.sameRect(r3), true)
//        && t.checkExpect(r3.sameRect(r1), true);
//  }
  
  boolean testSameShape(Tester t) {
    return t.checkExpect(c1.sameShape(c3), true)  // works
        && t.checkExpect(c1.sameShape(c2), false) // works
        && t.checkExpect(r1.sameShape(r3), true)  // works
        && t.checkExpect(r1.sameShape(r2), false) // works
        && t.checkExpect(c1.sameShape(r1), false); // CRASH! with a ClassCastException
  }
}