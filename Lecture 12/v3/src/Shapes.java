// Combo shape

import tester.*;

interface IShape {
  boolean sameShape(IShape that);
  boolean sameCircle(Circle that);
  boolean sameRect(Rect that);
  boolean sameSquare(Square that);
  boolean sameCombo(Combo that);
}

abstract class AShape implements IShape {
  public boolean sameCircle(Circle that) {
    return false;
  }
  public boolean sameRect(Rect that) {
    return false;
  }
  public boolean sameSquare(Square that) {
    return false;
  }
  
  public boolean sameCombo(Combo that) {
    return false;
  }  
}

class Circle extends AShape {
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
    return that.sameCircle(this);
  }
}

class Rect extends AShape {
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
    return that.sameRect(this);
  }
}

class Square extends Rect {
  Square(int x, int y, int s) { super(x, y, s, s); }
  public boolean sameShape(IShape that) {
    return that.sameSquare(this);
  }
  public boolean sameSquare(Square that) {
    return this.x == that.x &&
           this.y == that.y &&
           this.w == that.w; // No need to check the h field, too...
  }
  
  public boolean sameRect(Rect that) {
    return false;
  }
}

class Combo extends AShape {
  
  IShape left;
  IShape right;
  Combo(IShape left, IShape right) {
    this.left = left;
    this.right = right;
  }
  
  public boolean sameShape(IShape that) {
    return that.sameCombo(this);
  }
  
  public boolean sameCombo(Combo that) {
    return this.left.sameShape(that.left) &&
           this.right.sameShape(that.right);
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

  IShape s1 = new Square(3, 4, 5);
  IShape s2 = new Square(4, 5, 6);
  IShape s3 = new Square(3, 4, 5);
  
  IShape combo1 = new Combo(c1, s1);
  IShape combo2 = new Combo(c1, s1);
  
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
        && t.checkExpect(c1.sameShape(r1), false)
        && t.checkExpect(r1.sameShape(c1), false)
        
        // basic checks comparing two Squares should work
        && t.checkExpect(s1.sameShape(s2), false)
        && t.checkExpect(s2.sameShape(s1), false)
        && t.checkExpect(s1.sameShape(s3), true)
        && t.checkExpect(s3.sameShape(s1), true)
        
        // Comparing a Square with a Rect of a different size
        && t.checkExpect(s1.sameShape(r2), false) // Good
        && t.checkExpect(r2.sameShape(s1), false) // Good
        // Comparing a Square with a Rect of the same size
        && t.checkExpect(s1.sameShape(r1), false) // Good
        && t.checkExpect(r1.sameShape(s1), false)  // Good
        
        // Combo
        && t.checkExpect(combo1.sameShape(r1), false)
        && t.checkExpect(r1.sameShape(combo1), false)
        && t.checkExpect(combo1.sameShape(combo2), true);
  }
}