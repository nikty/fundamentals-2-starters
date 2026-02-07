import javalib.worldimages.OutlineMode;
import tester.Tester;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

class Ship extends AMovable {
  Color color = NBullets.SHIP_COLOR;
  
  Ship(GamePosn location, GamePosn velocity, int radius) {
    super(location, velocity, radius);
  }
  
  // Ship with default size
  Ship(GamePosn location, GamePosn velocity) {
    super(location, velocity, NBullets.SHIP_SIZE);
  }
  
  public WorldImage draw() {
    return new CircleImage(this.radius, OutlineMode.SOLID, this.color);
  }
  
  public IList<IMovable> explode() {
    return new MtList<IMovable>();
  }
}








