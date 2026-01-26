/*
  Exercise 18.6

  Complete the class hierarchy for overlapping shapes from
  section 15.3: IComposite, Square, Circle, and SuperImp with all the methods:
  area, distTo0, in, bb. Add the following methods to the class hierarchy:

  1. same, which determines whether this shape is of equal size as some
  other, given IShape up to some given small number delta;

  2. closerTo, which determines whether this shape is closer to the origin
  than some other, given IShape;

  3. drawBoundary, which draws the bounding box around this shape.

  Develop examples and tests for as many methods as possible. Then
  introduce a common superclass for the concrete classes and try to
  lift as many fields and methods as possible into this
  superclass. Make sure that the revised classes function properly
  after this editing step.
*/

import tester.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;


interface IComposite {
    // Return area of this shape
    double area();
    
    // Return the distance of this shape to the origin
    double distanceToOrigin();

    // Determine if the given point is within the bounds of this shape
    boolean contains(CartPt p);

    // Compute the bounding box for this shape
    BoundingBox boundingBox();

    // Return true if this shape is of equal size as the give
    boolean same(IComposite other, double delta);

    // Return true if this shape is closer to the origin than the given shape
    boolean closerTo(IComposite other);

    // Draw the shape onto the scene
    WorldScene draw(WorldScene s);
}

abstract class AShape implements IComposite {
    CartPt loc;
    
    public double distanceToOrigin() {
        return this.loc.distanceToOrigin();
    }

    public boolean same(IComposite other, double delta) {
        return Math.abs(this.area() - other.area()) <= delta;
    }

    public boolean closerTo(IComposite other) {
        return this.distanceToOrigin() < other.distanceToOrigin();
    }


}

class Square extends AShape {
    int size;

    Square(CartPt loc, int size) {
        this.loc = loc;
        this.size = size;
    }

    public double area() {
        return this.size * this.size;
    }

    public boolean contains(CartPt p){
        return this.between(this.loc.x, p.x, this.size)
            && this.between(this.loc.y, p.y, this.size);
    }
    
    // Determine whether x is in the interval [left, left + width]?
    boolean between(int left, int x, int width) {
        return left <= x && x <= left + width;
    }

    public BoundingBox boundingBox() {
        return new BoundingBox(this.loc.x,
                               this.loc.x + this.size,
                               this.loc.y,
                               this.loc.y + this.size);
    }

    public WorldScene draw(WorldScene s) {
        return s.placeImageXY(new RectangleImage(this.size, this.size, OutlineMode.SOLID, Color.GREEN),
                              this.loc.x + this.size/2,
                              this.loc.y + this.size/2);
    }

}

class Circle extends AShape {
    CartPt loc; // center of the circle
    int radius;

    Circle(CartPt loc, int radius) {
        this.loc = loc;
        this.radius = radius;
    }

    public double area() {
        return Math.PI * this.radius * this.radius;
    }

    public double distanceToOrigin() {
        return this.loc.distanceToOrigin() - this.radius;
    }

    public boolean contains(CartPt p) {
        return this.loc.distanceTo(p) <= this.radius;
    }

    public BoundingBox boundingBox() {
        return new BoundingBox(this.loc.x - this.radius,
                               this.loc.x + this.radius,
                               this.loc.y - this.radius,
                               this.loc.y + this.radius);
    }

    public WorldScene draw(WorldScene s) {
        return s.placeImageXY(new CircleImage(this.radius, OutlineMode.SOLID, Color.BLUE),
                              this.loc.x, this.loc.y);
    }
}

class SuperImp extends AShape {
    IComposite bot;
    IComposite top;

    SuperImp(IComposite bot, IComposite top) {
        this.bot = bot;
        this.top = top;
    }

    public double area() {
        return this.bot.area() + this.top.area();
    }

    public double distanceToOrigin() {
        return Math.min(this.bot.distanceToOrigin(),
                        this.top.distanceToOrigin());
    }

    public boolean contains(CartPt p) {
        return this.bot.contains(p) || this.top.contains(p);
    }

    public BoundingBox boundingBox() {
        return this.top.boundingBox().combine(this.bot.boundingBox());
    }

    public WorldScene draw(WorldScene s) {
        return this.top.draw(this.bot.draw(s));
    }
}

class CartPt {
    int x;
    int y;
    CartPt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Compute the distance of this point to the origin
    double distanceToOrigin() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    // Determine whether this CartPt and p are the same
    boolean same(CartPt p) {
        return (this.x == p.x) && (this.y == p.y);
    }
    
    // Compute the distance between this CartPt and p
    double distanceTo(CartPt p){
        return
            Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
    }

}

// interp. a bounding box of a shape
class BoundingBox {
    int left;
    int top;
    int right;
    int bottom;

    BoundingBox(int left, int right, int top, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    // Combine this bounding box with the given one
    BoundingBox combine(BoundingBox other) {
        return new BoundingBox(Math.min(this.left, other.left),
                               Math.max(this.right, other.right),
                               Math.min(this.top, other.top),
                               Math.max(this.bottom, other.bottom));
    }

    WorldScene draw(WorldScene s) {
        return s.placeImageXY(new RectangleImage(this.right - this.left,
                                                 this.bottom - this.top,
                                                 OutlineMode.OUTLINE,
                                                 Color.RED),
                              this.left + (this.right - this.left)/2,
                              this.top + (this.bottom - this.top)/2);
    }

}
            

class Examples {
    IComposite s1 = new Square(new CartPt(40, 30), 40);
    IComposite s2 = new Square(new CartPt(120, 50), 50);
    IComposite c1 = new Circle(new CartPt(50, 120), 20);
    IComposite c2 = new Circle(new CartPt(30, 40), 20);
    IComposite u1 = new SuperImp(s1, s2);
    IComposite u2 = new SuperImp(s1, c2);
    IComposite u3 = new SuperImp(c1, u1);
    IComposite u4 = new SuperImp(u3, u2);
    IComposite s3 = new Square(new CartPt(440, 100), 70);
    IComposite c3 = new Circle(new CartPt(300, 200), 40);
    IComposite u5 = new SuperImp(s3, c3);


    boolean testArea(Tester t) {
        return t.checkInexact(s1.area(), 1600.0, .001)
            && t.checkInexact(c1.area(), 3.14*400, .001)
            && t.checkInexact(u1.area(), s1.area() + s2.area(), .001);
    }
    
    boolean testICompositeDistanceToOrigin(Tester t) {
        return t.checkInexact(s1.distanceToOrigin(), 50.0, .001)
            && t.checkInexact(s2.distanceToOrigin(), 130.0, .001)
            && t.checkInexact(c1.distanceToOrigin(), 110.0,  .001)
            && t.checkInexact(c2.distanceToOrigin(), 30.0, .001)
            && t.checkInexact(u1.distanceToOrigin(), 50.0, .001)
            && t.checkInexact(u2.distanceToOrigin(), 30.0, .001)
            && t.checkInexact(u3.distanceToOrigin(), 50.0, .001)
            && t.checkInexact(u4.distanceToOrigin(), 30.0, .001);
    }

    boolean testICompositeContains(Tester t) {
        return t.checkExpect(s1.contains(new CartPt(20, 5)), false)
            && t.checkExpect(c2.contains(new CartPt(20, 5)), false)
            && t.checkExpect(u1.contains(new CartPt(42, 42)), true)
            && t.checkExpect(u2.contains(new CartPt(45, 40)), true)
            && t.checkExpect(u2.contains(new CartPt(20, 5)), false);
    }

    boolean testICompositeBoundingBox(Tester t) {
        return t.checkExpect(s1.boundingBox(),
                             new BoundingBox(40, 80, 30, 70))
            && t.checkExpect(s2.boundingBox(),
                             new BoundingBox(120, 170, 50, 100))
            && t.checkExpect(c1.boundingBox(),
                             new BoundingBox(30, 70, 100, 140))
            && t.checkExpect(c2.boundingBox(),
                             new BoundingBox(10, 50, 20, 60))
            && t.checkExpect(u1.boundingBox(),
                             new BoundingBox(40, 170, 30, 100))
            && t.checkExpect(u2.boundingBox(),
                             new BoundingBox(10, 80, 20, 70))
            && t.checkExpect(u3.boundingBox(),
                             new BoundingBox(30, 170, 30, 140))
            && t.checkExpect(u4.boundingBox(),
                             new BoundingBox(10, 170, 20, 140));
    }

    boolean testSame(Tester t) {
        return t.checkExpect(s1.same(new Square(new CartPt(0, 0), 41), 100), true)
            && t.checkExpect(s1.same(new Square(new CartPt(0, 0), 41), 10), false);
    }

    boolean testCloserTo(Tester t) {
        return t.checkExpect(s1.closerTo(c1), true);
    }

    WorldScene drawWithBoundingBox(IComposite shape, WorldScene s) {
        return shape.boundingBox().draw(shape.draw(s));
    }

    boolean testDraw(Tester t) {
        WorldCanvas c = new WorldCanvas(600, 400);
        WorldScene scene = new WorldScene(600, 400);

        return c.show() && c.drawScene(drawWithBoundingBox
                                       (s1, drawWithBoundingBox
                                        (s2, drawWithBoundingBox
                                         (c1, drawWithBoundingBox
                                          (c2, drawWithBoundingBox
                                           (u5, scene))))));
                                                          
    }

}
