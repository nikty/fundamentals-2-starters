import javalib.funworld.*;
import javalib.worldcanvas.*;
import tester.*;
import java.util.Random;

class ExamplesTests {
  NBullets world1 = new NBullets(10, new Random(1));
  // Speeds
  GamePosn bulletVelocity= new GamePosn(0, - NBullets.BULLET_SPEED);
  GamePosn shipSpeedLeft = new GamePosn(- NBullets.SHIP_SPEED, 0);
  // Locations
  GamePosn posCenter = new GamePosn(NBullets.WIDTH/2, NBullets.HEIGHT/2);
  GamePosn pos1 = new GamePosn(NBullets.WIDTH/3, NBullets.HEIGHT/3);
  
  IMovable bullet1 = new Bullet(posCenter, bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0);
  IMovable bullet1Exploded0 = new Bullet(posCenter, new GamePosn(NBullets.BULLET_SPEED, 0), NBullets.BULLET_INITIAL_SIZE + NBullets.BULLET_SIZE_INCREMENT, 1);
  IMovable bullet1Exploded1 = new Bullet(posCenter, new GamePosn(- NBullets.BULLET_SPEED, 0), NBullets.BULLET_INITIAL_SIZE + NBullets.BULLET_SIZE_INCREMENT, 1);
  IMovable bullet1Moved = new Bullet(new GamePosn(NBullets.WIDTH/2, NBullets.HEIGHT/2 - NBullets.BULLET_SPEED),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0);
  
  IMovable bullet2 = new Bullet(pos1, bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0);
  IMovable bullet2Moved = new Bullet(new GamePosn(NBullets.WIDTH/3, NBullets.HEIGHT/3 - NBullets.BULLET_SPEED),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0);
  
  IMovable bullet3 = new Bullet(new GamePosn(NBullets.WIDTH, NBullets.HEIGHT/2),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // near the right edge, half visible
  IMovable bullet4 = new Bullet(new GamePosn(NBullets.WIDTH + NBullets.BULLET_INITIAL_SIZE, NBullets.HEIGHT/2),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // beyond the right edge, not visible
  IMovable bullet5 = new Bullet(new GamePosn(0, NBullets.HEIGHT/2),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // near the left edge, half visible
  IMovable bullet6 = new Bullet(new GamePosn(0 - NBullets.BULLET_INITIAL_SIZE, NBullets.HEIGHT/2),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // beyond the left edge, not visible
  IMovable bullet7 = new Bullet(new GamePosn(NBullets.WIDTH/2, NBullets.HEIGHT),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // near the bottom edge, half visible
  IMovable bullet8 = new Bullet(new GamePosn(NBullets.WIDTH/2, NBullets.HEIGHT + NBullets.BULLET_INITIAL_SIZE),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // below the bottom edge, not visible
  IMovable bullet9 = new Bullet(new GamePosn(NBullets.WIDTH/2, 0),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // near the top edge, half visible
  IMovable bullet10 = new Bullet(new GamePosn(NBullets.WIDTH/2, 0 - NBullets.BULLET_INITIAL_SIZE),
      bulletVelocity, NBullets.BULLET_INITIAL_SIZE, 0); // above the top edge, not visible
  
  IList<IMovable> lob1 = new ConsList<IMovable>(bullet1, new MtList<IMovable>());
  IList<IMovable> lob1Moved = new ConsList<IMovable>(bullet1Moved, new MtList<IMovable>());
  IList<IMovable> lob2 = new ConsList<IMovable>(bullet2, lob1);
  IList<IMovable> lob2Moved = new ConsList<IMovable>(bullet2Moved, lob1Moved);
  IList<IMovable> lob3 = new ConsList<>(bullet4, new ConsList<>(bullet3, lob2));
  IList<IMovable> lob3RemovedOffscreen = new ConsList<>(bullet3, lob2);
  
  IMovable ship1 = new Ship(posCenter, shipSpeedLeft, NBullets.SHIP_SIZE);
  
  IList<IMovable> los1 = new ConsList<>(ship1, new MtList<>());
  
  // Gun
  Gun gun = new Gun();
  
  /*
   * Drawing tests
   * =============
   * Remove "__" from the start of the method name to enable
   */
  boolean __testMakeScene(Tester t) {
    WorldCanvas wc = new WorldCanvas(NBullets.WIDTH, NBullets.HEIGHT);
    //WorldCanvas wc = world1.theCanvas;
    world1.bullets = lob1;
    world1.ships = los1;
    return wc.show() && wc.drawScene(world1.makeScene());
  }
  
  boolean __testShipPlace(Tester t) {
    WorldCanvas wc = new WorldCanvas(NBullets.WIDTH, NBullets.HEIGHT);
    WorldScene ws = world1.getEmptyScene();
    ws = ship1.place(ws);
    return wc.show() && wc.drawScene(ws);
  }
  
  boolean __testShipsPlace(Tester t) {
    WorldCanvas wc = new WorldCanvas(NBullets.WIDTH, NBullets.HEIGHT);
    WorldScene ws = world1.getEmptyScene();
    ws = los1.foldr(new Func2IMovablePlace(), ws);
    return wc.show() && wc.drawScene(ws);
  }
  
  boolean __testGunPlace(Tester t) {
    WorldCanvas wc = new WorldCanvas(NBullets.WIDTH, NBullets.HEIGHT);
    WorldScene ws = world1.getEmptyScene();
    ws = gun.place(ws);
    return wc.show() && wc.drawScene(ws);
  }
  
  boolean __testBulletsPlace(Tester t) {
    WorldCanvas wc = new WorldCanvas(NBullets.WIDTH, NBullets.HEIGHT);
    WorldScene ws = world1.getEmptyScene();
    ws = lob1.foldr(new Func2IMovablePlace(), ws);
    ws = lob1Moved.foldr(new Func2IMovablePlace(), ws);
    return wc.show() && wc.drawScene(ws);
  }
  
  /*
   * Single object
   */
  boolean testIMovableMove(Tester t) {
    return t.checkExpect(bullet1.move(),
        bullet1Moved
        );
  }
  
  boolean testIMovableIsOffscreen(Tester t) {
    return t.checkExpect(bullet1.isOffscreen(), false)
        && t.checkExpect(bullet3.isOffscreen(), false)
        && t.checkExpect(bullet4.isOffscreen(), true)
        && t.checkExpect(bullet5.isOffscreen(), false)
        && t.checkExpect(bullet6.isOffscreen(), true)
        && t.checkExpect(bullet7.isOffscreen(), false)
        && t.checkExpect(bullet8.isOffscreen(), true)
        && t.checkExpect(bullet9.isOffscreen(), false)
        && t.checkExpect(bullet10.isOffscreen(), true);
  }
  
  /*
   * Multiple objects movement
   */
  
  boolean testFuncIMovableMove(Tester t) {
    return t.checkExpect(lob1.map(new FuncIMovableMove()),
        lob1Moved)
        && t.checkExpect(lob2.map(new FuncIMovableMove()),
            lob2Moved);
  }
  
  boolean testPredIMovableIsOffscreen(Tester t) {
    return t.checkExpect(lob3.remove(new PredIMovableIsOffscreen()),
        lob3RemovedOffscreen);
  }
  
  /*
   * Collision tests
   * 
   */
  boolean testCollide(Tester t) {
    return t.checkExpect(
        lob1.foldr(new CollideAndExplode(los1), new MtList<>()),
        new ConsList<>(bullet1Exploded0, new ConsList<>(bullet1Exploded1, new MtList<>()))
        )
        && t.checkExpect(
            lob1.foldr(new CollideAndExplode(new MtList<>()), new MtList<>()),
            lob1
            );
  }
  
  /*
   * GamePosn Tests
   */

  GamePosn pos11 = new GamePosn(0, 0);
  GamePosn pos21 = new GamePosn(3, 5);
  GamePosn pos33 = new GamePosn(10, 20);
  
  boolean testAdd(Tester t) {
    return t.checkExpect(pos11.add(pos21),
        new GamePosn(3, 5));
  }
}





