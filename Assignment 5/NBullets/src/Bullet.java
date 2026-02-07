import java.awt.Color;
import javalib.worldimages.*;
import tester.*;

class Bullet extends AMovable {
  Color color = NBullets.BULLET_COLOR;
  int nExplosions; // Number of explosions so far
  
  Bullet(GamePosn location, GamePosn velocity, int radius, int nExplosions) {
    super(location, velocity, radius);
    this.nExplosions = nExplosions;
  }
  
  // Draw this bullet image
  public WorldImage draw() {
    return new CircleImage(this.radius, OutlineMode.SOLID, this.color);
  }
  
  // Return a list of bullets resulting from exploding this one
  public IList<IMovable> explode() {
    
    int count = Math.min(NBullets.BULLET_MAX_EXPLOSIONS, this.nExplosions + 2);
    double angle = 360.0/count;
    return explodeHelper(count, angle);
  }
  
  private IList<IMovable> explodeHelper(int n, double angle) {
    if (n == 0) {
      return new MtList<IMovable>();
    }
    int newRadius = this.radius;
    if (newRadius < NBullets.BULLET_MAX_SIZE) {
      newRadius = Math.min(NBullets.BULLET_MAX_SIZE, this.radius + NBullets.BULLET_SIZE_INCREMENT);
    }
    GamePosn newVelocity = new GamePosn(
        (int) (Math.cos(Math.toRadians(angle * n)) * NBullets.BULLET_SPEED),
        (int) (Math.sin(Math.toRadians(angle * n)) * NBullets.BULLET_SPEED)
        );
    
    return new ConsList<IMovable>(new Bullet(this.location, newVelocity, newRadius, this.nExplosions + 1),
        this.explodeHelper(n - 1, angle));
  }
}
        
class ExamplesBullet {
  /*
  NBullets world = new NBullets(10);
  GamePosn VELOCITY = new GamePosn(0, -5);
  IMovable bullet1 = new Bullet(new GamePosn(world.width/2, world.height/2),
      VELOCITY, world); // at the center of the screen
  IListOfMovable bullet1Exploded = new ConsMovable(
      new Bullet(new GamePosn(world.width/2, world.height/2),
          new GamePosn(1, 0), world.bulletSizeInitial + world.bulletSizeIncrement, 1, world),
      new ConsMovable(
          new Bullet(new GamePosn(world.width/2, world.height/2),
              new GamePosn(-1, 0), world.bulletSizeInitial + world.bulletSizeIncrement, 1, world),
          new MtMovable()));
      
      
  IMovable bullet2 = new Bullet(new GamePosn(world.width/3, world.height/3), VELOCITY,
      world.bulletSizeInitial, 0, world); // at the center of the screen
  IMovable bullet3 = new Bullet(new GamePosn(world.width/2, 0), VELOCITY,
      world.bulletSizeInitial, 0, world); // at the top of the screen
  IMovable bullet4 = new Bullet(new GamePosn(world.width/2, -11), VELOCITY,
      world.bulletSizeInitial, 0, world); // offscreen
  
  Ship ship1 = new Ship(new Posn(world.width/2, world.height/2), 5, 20); // hit with bullet1
  
  IMovable bullet1Moved = new Bullet(
      new GamePosn(world.width/2 + VELOCITY.x,
          world.height/2 + VELOCITY.y),
      VELOCITY, world);
  IMovable bullet2Moved = new Bullet(
      new GamePosn(world.width/3 + VELOCITY.x, world.height/3 + VELOCITY.y),
      VELOCITY, world);
  
  IListOfMovable bullets1 = new ConsMovable(bullet1,
      new ConsMovable(bullet2, new MtMovable()));
  IListOfMovable bullets1Moved = new ConsMovable(bullet1Moved,
      new ConsMovable(bullet2Moved, new MtMovable()));
  
  IListOfMovable bullets2 = new ConsMovable(bullet3,
      new ConsMovable(bullet4, bullets1));
  IListOfMovable bullets2RemoveOffscreen = new ConsMovable(bullet3, bullets1);
      
  boolean testBulletMove(Tester t) {
    return t.checkExpect(bullet1.move(),
        bullet1Moved);
  }
  
  boolean testBulletsMove(Tester t) {
    return t.checkExpect(bullets1.move(),
        bullets1Moved);
  }
  
  boolean testBulletsRemoveOffscreen(Tester t) {
    return t.checkExpect(bullets2.removeOffscreen(), bullets2RemoveOffscreen);
  }
  
  boolean testBulletHits(Tester t) {
    return t.checkExpect(bullet1.hits(ship1), true);
  }
  
  boolean testIMovableCollide(Tester t) {
    return t.checkExpect(bullets1.collide(new MtList<Ship>()), bullets1);
  }
  
  boolean testBulletExplode(Tester t) {
    return t.checkExpect(bullet1.explode(),
        bullet1Exploded);
  }
 */ 
}