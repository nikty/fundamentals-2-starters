import tester.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

// interp. Represents a tree with stems, branches and leafs
interface ITree {
  // Produce an image of this tree
  WorldImage draw();

  // Return true if any of the twigs in the tree (either stems or branches) are
  // pointing downward rather than upward.
  boolean isDrooping();

  // Given tree, produces a Branch using the given arguments, with
  // this tree on the left and the given tree on the right.
  ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree);

  // Rotate this tree the given degrees
  ITree combineHelperRotate(double theta);

  // Return the width of this tree.
  // Assume: leaves are drawn as circles, and their size is used as their radius.
  // TODO: not done
  // double getWidth();
  
}

// interp. A leaf of a tree
class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  Leaf(int size, Color color) {
    this.size = size;
    this.color = color;
  }

  public WorldImage draw() {
    // Uncomment to see pinholes
    /*
     * return new VisiblePinholeImage( new CircleImage(this.size, OutlineMode.SOLID,
     * this.color) );
     */
    return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }

  public boolean isDrooping() {
    return false; // Assume: leafs always point up.
  }

  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.combineHelperRotate(leftTheta), otherTree.combineHelperRotate(rightTheta));
  }

  public ITree combineHelperRotate(double theta) {
    return this;
  }
}

// interp. A single stem supporting a tree
class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;

  Stem(int length, double theta, ITree tree) {
    this.length = length;
    this.theta = theta;
    this.tree = tree;
  }

  public WorldImage draw() {
    return (new Utils()).drawLineWithTree(this.length, this.theta, this.tree);
  }

  public boolean isDrooping() {
    return !(new Utils()).linePointsUpward(this.theta) || this.tree.isDrooping();
  }

  public ITree combineHelperRotate(double theta) {
    return new Stem(this.length, this.theta + theta, this.tree.combineHelperRotate(theta));
  }

  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.combineHelperRotate(leftTheta - 90), otherTree.combineHelperRotate(rightTheta - 90));
  }
}

// interp. Branch into two subtrees
class Branch implements ITree {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;

  Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left,
      ITree right) {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }

  public WorldImage draw() {
    return new OverlayImage((new Utils()).drawLineWithTree(leftLength, leftTheta, left),
        (new Utils()).drawLineWithTree(rightLength, rightTheta, right));

  }

  public boolean isDrooping() {
    return !(new Utils()).linePointsUpward(this.leftTheta)
        || !(new Utils()).linePointsUpward(this.rightTheta) || this.left.isDrooping()
        || this.right.isDrooping();
  }

  public ITree combineHelperRotate(double theta) {
    return new Branch(this.leftLength, this.rightLength, this.leftTheta + theta,
        this.rightTheta + theta, this.left.combineHelperRotate(theta),
        this.right.combineHelperRotate(theta));
  }

  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree otherTree) {
    return new Branch(leftLength, rightLength, leftTheta, rightTheta,
        this.combineHelperRotate(leftTheta - 90), otherTree.combineHelperRotate(rightTheta - 90));
  }
}

class Utils {
  // Return new Posn from the given length and angle.
  Posn polarToPosn(int length, double theta) {
    return new Posn((int) (length * Math.cos(Math.toRadians(theta))),
        -(int) (length * Math.sin(Math.toRadians(theta)))); // Y axis points downward on canvas
  }

  // Return line image with the pinhole set to line's end
  WorldImage drawLine(int length, double theta) {
    return new LineImage(this.polarToPosn(length, theta), Color.BLACK)
        // Move pinhole with respect to the center of the image
        .movePinholeTo((this.polarToPosn(length / 2, theta)));

  }

  // Produce image of the line connected to the tree.
  // Assume: tree has pinhole set correctly (to its "root")
  WorldImage drawLineWithTree(int length, double theta, ITree tree) {
    return new OverlayImage(tree.draw(), this.drawLine(length, theta))
        // Set pinhole to the start of the line
        .movePinhole(-(int) (length * Math.cos(Math.toRadians(theta))),
            (int) (length * Math.sin(Math.toRadians(theta))));

  }

  // Return true if the given length and angle represent an upward pointing line
  boolean linePointsUpward(double theta) {
    return 0 < theta % 360 && theta % 360 < 180;
  }
}

class ExamplesTree {
  ITree leaf1 = new Leaf(20, Color.RED);
  ITree leaf2 = new Leaf(30, Color.BLUE);
  ITree leaf3 = new Leaf(40, Color.GREEN);

  ITree branch1 = new Branch(50, 60, 135, 45, leaf1, leaf2); // two leaves upward
  ITree branch11 = new Branch(50, 60, 135, 250, leaf1, leaf2); // one leaf upward, one leaf downward
  ITree branch2 = new Branch(70, 80, 135, 45, branch1, leaf3); // three leaves upward
  ITree branch21 = new Branch(70, 80, 135, 45, branch11, leaf3); // three leaves, one of them
                                                                 // downward

  ITree stem1 = new Stem(100, 90, branch2);
  ITree stem11 = new Stem(100, 90, branch21);

  ITree stemWithLeaf = new Stem(100, 45, leaf1);

  ITree exampleTree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED),
      new Leaf(15, Color.BLUE));
  ITree exampleTree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN),
      new Leaf(8, Color.ORANGE));

  ITree combined1 = leaf1.combine(100, 100, 150, 60, leaf2); // two leaves making a branch
  ITree combined2 = branch1.combine(100, 100, 150, 30, leaf1); // two leaves on the left, one on the
                                                               // right
  ITree combined3 = branch1.combine(20, 20, 150, 30, branch2);

  boolean testDrawTree(Tester t) {
    int WIDTH = 500;
    int HEIGHT = 500;
    WorldCanvas wc = new WorldCanvas(WIDTH, HEIGHT);
    WorldScene ws = new WorldScene(WIDTH, HEIGHT);

    return wc.drawScene(ws.placeImageXY(branch1.draw(), WIDTH / 2, HEIGHT / 2)) && wc.show();
  }

  boolean testDrawTree2(Tester t) {
    int WIDTH = 500;
    int HEIGHT = 500;
    WorldCanvas wc = new WorldCanvas(WIDTH, HEIGHT);
    WorldScene ws = new WorldScene(WIDTH, HEIGHT);

    return wc.drawScene(ws.placeImageXY(branch2.draw(), WIDTH / 2, HEIGHT / 2)) && wc.show();
  }

  boolean testDrawCombinedTree(Tester t) {
    int WIDTH = 500;
    int HEIGHT = 500;
    WorldCanvas wc = new WorldCanvas(WIDTH, HEIGHT);
    WorldScene ws = new WorldScene(WIDTH, HEIGHT);

    return wc.drawScene(ws.placeImageXY(combined3.draw(), WIDTH / 2, HEIGHT / 2)) && wc.show();
  }

  boolean testIsDrooping(Tester t) {
    return t.checkExpect(stem1.isDrooping(), false) && t.checkExpect(stem11.isDrooping(), true)
        && t.checkExpect(new Stem(100, 200, leaf1).isDrooping(), true)
        && t.checkExpect(new Stem(100, 45, leaf1).isDrooping(), false);
  }

  boolean testUtilsLinePointsUpward(Tester t) {
    return t.checkExpect((new Utils()).linePointsUpward(200), false)
        && t.checkExpect((new Utils()).linePointsUpward(45), true);
  }
}