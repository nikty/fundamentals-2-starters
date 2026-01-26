import tester.*;

/*
  Problem 2: A Picture is worth a thousand words

  Define the file Pictures.java that will contain the entire solution to this problem.

  For this problem, you’re going to implement a small fragment of the
  image library you’ve been using for Fundies 1 and will be using in
  Fundies 2. Each picture is either a single Shape or a Combo that
  connects one or more pictures. Each Shape has a kind, which is a
  string describing what simple shape it is (e.g., "circle" or
  "square"), and a size. (For this problem, we will simplify and
  assume that each simple shape is as tall as it is wide.) A Combo
  consists of a name describing the resulting picture, and an
  operation describing how this image was put together.

  There are three kinds of operations: Scale (takes a single picture
  and draws it twice as large), Beside (takes two pictures, and draws
  picture1 to the left of picture2), and Overlay (takes two pictures,
  and draws top-picture on top of bottom-picture, with their centers
  aligned).
*/

//
// Design the classes (and interfaces) needed to represent the given information.
//

// interp. represents image. One of
// - Shape
// - Combo
interface IPicture {
    // Produce the overall width of this picture
    int getWidth();

    // Produce the number of single shapes involved in producing the final image.
    int countShapes();

    // Return the depth of the nested operations in the construction
    // of this picture
    int comboDepth();

    // Produce a copy of this image with all sub-images in Beside
    // combos flipped
    IPicture mirror();

    // Produce string representation of this picture up to (including) the given depth
    String pictureRecipe(int depth);
}

// interp. Simple shape
class Shape implements IPicture {
    String kind; // what simple shape it is
    int size; // size of both the height and width

    Shape(String kind, int size) {
        this.kind = kind;
        this.size = size;
    }

    public int getWidth() {
        return this.size;
    }

    public int countShapes() {
        return 1;
    }

    public int comboDepth() {
        return 0;
    }

    public IPicture mirror() {
        return this;
    }

    public String pictureRecipe(int depth) {
        return this.kind;
    }
}

// interp. To represent images derived with some operation on them
class Combo implements IPicture {
    String name; // picture description
    IOperation operation; // how this image was put together

    Combo(String name, IOperation operation) {
        this.name = name;
        this.operation = operation;
    }

    public int getWidth() {
        return this.operation.getWidth();
    }

    public int countShapes() {
        return this.operation.countShapes();
    }

    public int comboDepth() {
        return 1 + this.operation.comboDepth();
    }
    
    public IPicture mirror() {
        return new Combo(name, this.operation.mirror());
    }
    
    public String pictureRecipe(int depth) {
        if (depth <= 0) {
            return this.name;
        } else {
            return this.operation.pictureRecipe(depth);
        }
    }
}

// interp. An operation to apply to images
interface IOperation {
    // Produce the overall width of the resulting picture
    int getWidth();

    // Produce the number of single shapes involved in producing of resulting picture
    int countShapes();

    // Produce the depth of nested operations for the resulting image
    int comboDepth();

    // Produce copy of resulting image for this with sub-images in all
    // Beside combos flipped
    IOperation mirror();

    // Produce string representation for the resulting image up to (including) the given depth
    String pictureRecipe(int depth);
}

// interp. Draw a picture twice as large
class Scale implements IOperation {
    IPicture picture;

    Scale(IPicture picture) {
        this.picture = picture;
    }

    public int getWidth() {
        return this.picture.getWidth() * 2;
    }

    public int countShapes() {
        return this.picture.countShapes();
    }

    public int comboDepth() {
        return this.picture.comboDepth();
    }

    public IOperation mirror() {
        return new Scale(this.picture.mirror());
    }

    public String pictureRecipe(int depth) {
        return "scale(" + this.picture.pictureRecipe(depth - 1) + ")";
    }
}

// interp. Draw picture1 to the left of picture2 other
class Beside implements IOperation {
    IPicture picture1;
    IPicture picture2;

    Beside(IPicture picture1, IPicture picture2) {
        this.picture1 = picture1;
        this.picture2 = picture2;
    }

    public int getWidth() {
        return this.picture1.getWidth() + this.picture2.getWidth();
    }

    public int countShapes() {
        return this.picture1.countShapes() + this.picture2.countShapes();
    }

    public int comboDepth() {
        return Math.max(this.picture1.comboDepth(), this.picture2.comboDepth());
    }

    public IOperation mirror() {
        return new Beside(this.picture2.mirror(), this.picture1.mirror());
    }

    public String pictureRecipe(int depth) {
        return "beside(" + this.picture1.pictureRecipe(depth - 1) + ", "
            + this.picture2.pictureRecipe(depth - 1) + ")";
    }
}

// interp. Draw top picture on top of bottom picture
class Overlay implements IOperation {
    IPicture topPicture;
    IPicture bottomPicture;

    Overlay(IPicture topPicture, IPicture bottomPicture) {
        this.topPicture = topPicture;
        this.bottomPicture = bottomPicture;
    }

    public int getWidth() {
        return Math.max(this.topPicture.getWidth(), this.bottomPicture.getWidth());
    }

    public int countShapes() {
        return this.topPicture.countShapes() + this.bottomPicture.countShapes();
    }

    public int comboDepth() {
        return Math.max(this.topPicture.comboDepth(), this.bottomPicture.comboDepth());
    }

    public IOperation mirror() {
        return new Overlay(this.topPicture.mirror(), this.bottomPicture.mirror());
    }

    public String pictureRecipe(int depth) {
        return "overlay(" + this.topPicture.pictureRecipe(depth - 1) + ", "
            + this.bottomPicture.pictureRecipe(depth - 1) + ")";
    }
}

//
// Sketch the class diagram for the classes and interfaces you have
// designed. (You can draw this on paper, or in ASCII art as a comment
// in your submitted file. You do not need to hand this in.)
//

// ...

//
// In the ExamplesPicture class define example data that represents
// the following images (the colors don’t matter; they’re just for
// illustration here):
//
// * A /circle/ - a single circle of size 20
// * A /square/ - a single square of size 30
// * A /big circle/ - the result of scaling circle
// * A /square on circle/
// * A /doubled square on circle/
//
// You should define each picture by its name (e.g. square or
// bigCircle). Any combo image should use the /description text/ given
// above as its description.
// _Our test program will use this data to test your methods._

class ExamplesPicture {
    ExamplesPicture() {}

    IPicture circle = new Shape("circle", 20);
    IPicture square = new Shape("square", 30);
    IPicture bigCircle = new Combo("big circle", new Scale(circle));
    IPicture squareOnCircle = new Combo("square on circle",
                                        new Overlay(square, bigCircle));
    IPicture doubledSquareOnCircle = new Combo("doubled square on circle",
                                               new Beside(squareOnCircle, squareOnCircle));

    IPicture scaledSquare = new Combo("scaled circle",
                                      new Scale(new Shape("square", 50)));
    IPicture besideSquareCircle = new Combo("square beside circle",
                                            new Beside(new Shape("square", 50),
                                                       new Shape("circle", 50)));
    IPicture overlayCircleCircle = new Combo("circle on circle",
                                             new Overlay(new Shape("circle", 20),
                                                         new Shape("circle", 40)));
    IPicture s1 = new Shape("square", 30);
    IPicture s2 = new Combo("", new Scale(s1));
    IPicture c1 = new Shape("circle", 30);
    IPicture c2 = new Combo("", new Scale(c1));
    IPicture combo1 = new Combo("", new Beside(s1, c1));
    IPicture combo2 = new Combo("", new Beside(c1, s1));
    IPicture combo3 = new Combo("", new Overlay(combo1, c2));
    IPicture combo4 = new Combo("", new Overlay(combo2, s2));
    IPicture combo5 = new Combo("", new Beside(combo3, combo4));
    IPicture combo5Mirror = new Combo("", new Beside(new Combo("", new Overlay(new Combo("", new Beside(s1, c1)), s2)),
                                                     new Combo("", new Overlay(new Combo("", new Beside(c1, s1)), c2))));
    IPicture scaledScaledCircle = new Combo("scaled scaled circle",
                                            new Scale(new Combo("scaled circle",
                                                                new Scale(c1))));
                                                                 
    
    boolean testIPictureGetWidth(Tester t) {
        return t.checkExpect(circle.getWidth(), 20)
            && t.checkExpect(square.getWidth(), 30)
            && t.checkExpect(squareOnCircle.getWidth(), 40)
            && t.checkExpect(doubledSquareOnCircle.getWidth(), 80)
            && t.checkExpect(scaledSquare.getWidth(), 100);
    }

    boolean testIPCountShapes(Tester t) {
        return t.checkExpect(circle.countShapes(), 1)
            && t.checkExpect(squareOnCircle.countShapes(), 2)
            && t.checkExpect(doubledSquareOnCircle.countShapes(), 4);
    }

    boolean testIPComboDepth(Tester t) {
        return t.checkExpect(circle.comboDepth(), 0)
            && t.checkExpect(squareOnCircle.comboDepth(), 2)
            && t.checkExpect(doubledSquareOnCircle.comboDepth(), 3);
    }

    boolean testIPmirror(Tester t) {
        
        return t.checkExpect(circle.mirror(), circle)
            && t.checkExpect(squareOnCircle.mirror(), squareOnCircle)
            && t.checkExpect(doubledSquareOnCircle.mirror(), doubledSquareOnCircle)
            && t.checkExpect(combo5.mirror(), combo5Mirror);
    }

    boolean testIPPictureRecipe(Tester t) {
        return t.checkExpect(circle.pictureRecipe(0), "circle")
            && t.checkExpect(bigCircle.pictureRecipe(0), "big circle")
            && t.checkExpect(bigCircle.pictureRecipe(1), "scale(circle)")
            && t.checkExpect(scaledScaledCircle.pictureRecipe(0), "scaled scaled circle")
            && t.checkExpect(scaledScaledCircle.pictureRecipe(1), "scale(scaled circle)")
            && t.checkExpect(scaledScaledCircle.pictureRecipe(2), "scale(scale(circle))")
            && t.checkExpect(doubledSquareOnCircle.pictureRecipe(0), "doubled square on circle")
            && t.checkExpect(doubledSquareOnCircle.pictureRecipe(1),
                             "beside(square on circle, square on circle)")
            && t.checkExpect(doubledSquareOnCircle.pictureRecipe(2),
                             "beside(overlay(square, big circle), overlay(square, big circle))")
            && t.checkExpect(doubledSquareOnCircle.pictureRecipe(3),
                             "beside(overlay(square, scale(circle)), overlay(square, scale(circle)))");
    }
}

// 
// Add to your examples one more example of picture for each of the
// possible operations. Do not modify the existing pictures.
//

// ...

// 
// Design the method ~getWidth~ that computes the overall width of this
// picture.
//
// Hint: follow the design recipe... working through examples really
// helps.
//

// ...

//
// Design the method ~countShapes~ that computes the number of single
// shapes involved in producing the final image.
//
// Note: Make sure you count every shape each time it is used.
//

// ...

//
// Design the method ~comboDepth~, that computes how deeply operations
// are nested in the construction of this picture. For example, the
// comboDepth of the last example picture above is 3.
//

// ...

//
// Design the method ~mirror~. This should leave the entire image
// unchanged, except Beside combos, which should have their two
// sub-images flipped (all names can remain untouched). This mirroring
// should happen the entire way down the image.
//

// ...

//
// Tricky! Design the method pictureRecipe that takes an integer
// depth, and produces a String representing the contents of this
// picture, where the recipe for the picture has been expanded only
// depth times. For example, the pictureRecipe at depth 0 for the last
// example image above is "doubled square on circle", at depth 2 is
// "beside(overlay(square, big circle), overlay(square, big circle))",
// and at depth 3 or more is "beside(overlay(square, scale(circle)),
// overlay(square, scale(circle)))".
//
// In more detail: invoking pictureRecipe on a Combo produces its name
// if the given depth is less than or equal to 0, and the formula of
// its mixture (at that depth) otherwise.
//
// Hint: If you get stuck, you may want to use a wish list to
// determine subproblems that may be of use to you, and that you can
// delegate to when needed.
//

// ...
