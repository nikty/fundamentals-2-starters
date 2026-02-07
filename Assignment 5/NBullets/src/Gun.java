import javalib.funworld.*;
import javalib.worldimages.*;

class Gun {
  GamePosn location;
  int size = 20; 
  
  Gun() {
    this.location = new GamePosn(NBullets.WIDTH/2, NBullets.HEIGHT);
  }
  
  WorldImage draw() {
    return new RectangleImage(this.size, this.size, OutlineMode.SOLID, NBullets.GUN_COLOR);
  }
  
  WorldScene place(WorldScene scene) {
    return scene.placeImageXY(this.draw(),
        this.location.x,
        this.location.y - this.size/2);
  }
  
  // Shoot new bullet from the location of the gun
  public Bullet shoot() {
    return new Bullet(this.location, new GamePosn(0, - NBullets.BULLET_SPEED),
        NBullets.BULLET_INITIAL_SIZE, 0);
  }
}
