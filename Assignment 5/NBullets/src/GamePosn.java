import javalib.worldimages.Posn;

class GamePosn extends Posn {
  GamePosn(int x, int y) {
    super(x, y);
  }

  // Add two positions
  GamePosn add(GamePosn other) {
    return new GamePosn(this.x + other.x, this.y + other.y);
  }
}