import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)
import java.util.Random;

class MyPosn extends Posn {
  MyPosn(int x, int y) {
    super(x, y);
  }
  
  // to convert from Posn
  MyPosn(Posn p) {
    super(p.x, p.y);
  }
  
  // to add two positions
  MyPosn add(Posn p) {
    return new MyPosn(this.x + p.x, this.y + p.y);
  }
  
  // Given two numbers representing the width and height of a screen,
  // determine if this position lies outside of it
  boolean isOffscreen(int w, int h) {
    return this.x < 0 || this.x > w || this.y < 0 || this.y > h;
  }
}

class Circle {
  MyPosn position; // in pixels
  MyPosn velocity; // in pixels/tick
  
  Circle(MyPosn p, MyPosn v) {
    this.position = p;
    this.velocity = v;
  }
  
  // Move this circle after one tick
  Circle move() {
    return new Circle(this.position.add(this.velocity),
        this.velocity);
  }
  
  // Return true if this circle lies outside the screen
  // when given two numbers for width and height of a screen
  boolean isOffscreen(int w, int h) {
    return this.position.isOffscreen(w, h);
  }
  
  // Return image of this circle
  WorldImage draw() {
    return new CircleImage(20, OutlineMode.SOLID, Color.BLUE);
  }
  
  // Render this circle onto the given scene
  WorldScene place(WorldScene ws) {
    return ws.placeImageXY(this.draw(), this.position.x, this.position.y);
  }
}

// interp. represents a list of Circles
interface ILoCircle {
  // Move all circles in this list
  ILoCircle moveAll();
  
  // Return a list of circles with offscreen circles removed
  // given width and height of a screen
  ILoCircle removeOffscreen(int w, int h);
  
  // Place this list of circles onto a given worldscene
  WorldScene placeAll(WorldScene ws);
  
  // Return length of this list
  int length();
}

class MtLoCircle implements ILoCircle {
  MtLoCircle() {}
  
  public ILoCircle moveAll() {
    return this;
  }
  
  public ILoCircle removeOffscreen(int w, int h) {
    return this;
  }
  
  public WorldScene placeAll(WorldScene ws) {
    return ws;
  }
  
  public int length() {
    return 0;
  }
}

class ConsLoCircle implements ILoCircle {
  Circle first;
  ILoCircle rest;
  ConsLoCircle(Circle first, ILoCircle rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public ILoCircle moveAll() {
    return new ConsLoCircle(this.first.move(), this.rest.moveAll());
  }
  
  public ILoCircle removeOffscreen(int w, int h) {
    if (this.first.isOffscreen(w, h)) {
      return this.rest.removeOffscreen(w, h);
    } else {
      return new ConsLoCircle(this.first, this.rest.removeOffscreen(w, h));
    }
  }
  
  public WorldScene placeAll(WorldScene ws) {
    return this.first.place(this.rest.placeAll(ws));
  }
  
  public int length() {
    return 1 + this.rest.length();
  }
}

class MyGame extends World {
  int WIDTH;
  int HEIGHT;
  int offscreenCount; // number of circles that went offscreen
  int currentTick;
  int finalTick;
  ILoCircle circles;
  MyPosn VELOCITY = new MyPosn(0, -20);
  Random rand;
  
  MyGame(int width, int height, int currentTick, int finalTick, ILoCircle circles, int ncircles) {
    if ( width < 0 || height < 0 || finalTick < 2) {
      throw new IllegalArgumentException("Invalid arguments passed to constructor.");
    }
    this.WIDTH = width;
    this.HEIGHT = height;
    this.currentTick = currentTick;
    this.finalTick = finalTick;
    this.offscreenCount = ncircles;
    this.circles = circles;
    this.rand = new Random();
  }
  
  MyGame(int ncircles) {
    this(500, 500, 1, 1000, new MtLoCircle(), ncircles);
  }
  
  // Constructor for testing random objects
  MyGame(Random rand, int ncircles) {
    this(500, 500, 1, 1000, new MtLoCircle(), ncircles);
  }
  
  @Override
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);
    scene = this.circles.placeAll(scene);
    return scene;
  }
  
  @Override
  public WorldEnd worldEnds() {
    if (this.offscreenCount <= 0) {
      return new WorldEnd(true, this.makeALastScene());
    } else {
      return new WorldEnd(false, this.makeScene());
    }
  }
  
  public WorldScene makeALastScene() {
    WorldScene scene = new WorldScene(this.WIDTH, this.HEIGHT);
    return scene.placeImageXY(new TextImage("GAME OVER",
        this.HEIGHT/10, FontStyle.BOLD_ITALIC, Color.RED), this.WIDTH/2, this.HEIGHT/2);
  }
  
  // To run the game
  public boolean start() {
    return this.bigBang(this.WIDTH, this.HEIGHT, 1.0/28);
  }
  
  // Update the game on every tick
  @Override
  public MyGame onTick() {
    return this.moveCircles().removeOffscreenCircles().incrementTick();
  }
  
  public MyGame removeOffscreenCircles() {
    ILoCircle onScreen = this.circles.removeOffscreen(this.WIDTH, this.HEIGHT);
    return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.finalTick,
        onScreen, this.offscreenCount - (this.circles.length() - onScreen.length()));
  }
  
  // Move all circles for one tick
  public MyGame moveCircles() {
    return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.finalTick,
        this.circles.moveAll(), this.offscreenCount);
  }
  
  public MyGame incrementTick() {
    return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.finalTick,
        this.circles, this.offscreenCount);
  }
  
  @Override
  public MyGame onMouseClicked(Posn pos, String buttonName) {
    if (buttonName.equals("LeftButton")) {
      int randX = this.rand.nextInt(20);
      int randY = this.rand.nextInt(20);
      int signX = (this.rand.nextInt(2) == 0) ? 1 : -1;
      int signY = (this.rand.nextInt(2) == 0) ? 1 : -1;
      
      MyPosn newvel = new MyPosn(randX * signX, randY * signY);
      return new MyGame(this.WIDTH, this.HEIGHT, this.currentTick, this.finalTick,
          new ConsLoCircle(new Circle(new MyPosn(pos), newvel), this.circles),
          this.offscreenCount);
    } else {
      return this;
    }
  }
}

class Examples {
  
  MyPosn p1 = new MyPosn(0, 0);
  MyPosn p2 = new MyPosn(10, 10);
  MyPosn p3 = new MyPosn(50, 200);
  MyPosn vel1 = new MyPosn(0, -3); // moving straight up
  
  Circle c1 = new Circle(p1, vel1);
  Circle c2 = new Circle(p2, vel1);
  Circle c3 = new Circle(p3, vel1);
  
  Circle c1Moved = new Circle(new MyPosn(0, -3), vel1);
  Circle c2Moved = new Circle(new MyPosn(10, 7), vel1);
  Circle c3Moved = new Circle(new MyPosn(50, 193), vel1);
  
  
  ILoCircle loc1 = new ConsLoCircle(c1,
      new ConsLoCircle(c2, new ConsLoCircle(c3, new MtLoCircle())));
  ILoCircle expectedLoc1MoveAll = new ConsLoCircle(c1Moved,
      new ConsLoCircle(c2Moved, new ConsLoCircle(c3Moved, new MtLoCircle())));
  
  boolean testGame(Tester t) {
    MyGame world = new MyGame(30);
    return world.start();
  }
  
  boolean testMyPosnAdd(Tester t) {
    return t.checkExpect(p1.add(p2), p2)
        && t.checkExpect(p2.add(p3), new MyPosn(60, 210));
  }
  
  boolean testMyPosnIsOffscreen(Tester t) {
    return t.checkExpect(p1.isOffscreen(10, 10), false)
        && t.checkExpect(p3.isOffscreen(100, 100), true)
        && t.checkExpect(new MyPosn(-1, 0).isOffscreen(100, 100), true);
  }
  
  boolean testCircleMove(Tester t) {
    return t.checkExpect(c1.move(), c1Moved);
  }
  
  boolean testILoCircleMoveAll(Tester t) {
    return t.checkExpect(loc1.moveAll(), expectedLoc1MoveAll);
  }
  
  boolean testILoCircleRemoveOffscreen(Tester t) {
    return t.checkExpect(expectedLoc1MoveAll.removeOffscreen(100, 100),
        new ConsLoCircle(c2Moved, new MtLoCircle()));
  }
}



