import javalib.funworld.*;
import javalib.worldimages.*;

interface IMovable {
  // To draw this object
  WorldImage draw();
  
  // To place this object image to the scene
  WorldScene place(WorldScene scene);
  
  // To move this object one tick
  AMovable move();
  
  // Determine whether this object is offscreen;
  /*boolean isOffscreen(int width, int height);*/
  boolean isOffscreen();
  
  // Whether this object hits the given other object
  boolean hits(AMovable other);

  // Explode this object into pieces
  IList<IMovable> explode();
}

abstract class AMovable implements IMovable {
  GamePosn location;
  GamePosn velocity;
  int radius;
  
  AMovable(GamePosn location, GamePosn velocity, int radius) {
    this.location = location;
    this.velocity = velocity;
    this.radius = radius;
  }
  
  public abstract WorldImage draw();
  
  public AMovable move() {
    this.location = this.location.add(this.velocity);
    return this;
  }
  
  
  public WorldScene place(WorldScene scene) {
    return scene.placeImageXY(this.draw(),
        this.location.x,
        this.location.y);
  }
  
  public boolean isOffscreen() {
    return this.location.x + this.radius <= 0
        || this.location.x - this.radius >= NBullets.WIDTH
        || this.location.y + this.radius <= 0
        || this.location.y - this.radius >= NBullets.HEIGHT;
  }
  
  public abstract IList<IMovable> explode();
  
  public boolean hits(AMovable other) {
    return Math.abs(this.location.x - other.location.x) <= this.radius + other.radius
        && Math.abs(this.location.y - other.location.y) <= this.radius + other.radius; 
  }
}

// To render movable onto the scene
class Func2IMovablePlace implements IFunction2<IMovable, WorldScene, WorldScene> {
  public WorldScene apply(IMovable obj, WorldScene scene) {
    return obj.place(scene);
  }
}

// To move movable
class FuncIMovableMove implements IFunction<IMovable, IMovable> {
  public IMovable apply(IMovable obj) {
    return obj.move();
  }
}

class PredIMovableIsOffscreen implements IPred<IMovable> {
  public boolean apply(IMovable obj) {
    return obj.isOffscreen();
  }
}

class MovableHit implements IPred<IMovable> {
  IMovable other;
  MovableHit(IMovable other) {
    this.other = other;
  }
  
  public boolean apply(IMovable obj) {
    return obj.hits((AMovable) other);
  }
}

class CollideAndExplode implements IFunction2<IMovable, IList<IMovable>, IList<IMovable>> {
  IList<IMovable> lom;
  CollideAndExplode(IList<IMovable> lom) {
    this.lom = lom;
  }
  public IList<IMovable> apply(IMovable obj, IList<IMovable> lob) {
    if (lom.some(new MovableHit(obj))) {
      return lob.append(obj.explode());
    }
    return new ConsList<IMovable>(obj, lob);
  }
}





