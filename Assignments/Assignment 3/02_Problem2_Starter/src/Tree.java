import tester.*;
//import javalib.worldcanvas.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

// interp. Represents a tree with stems, branches and leafs
interface ITree { /* see methods below */ }

// interp. A leaf of a tree
class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it
}

// interp. A single stem supporting a tree
class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;
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
}