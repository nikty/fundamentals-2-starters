/*
 * World requirements:
 * - [x] Your world must have a constructor that just takes an integer,
 *       which represents the number of bullets a player has to shoot.
 *       That is how your graders will launch your world.
 * - [x] Your game must end when there are no more bullets to fire
 *       and there are no bullets left on the screen.
 * - [x] So long as there are bullets left to fire, the player should
 *       be able to press the space bar to fire a bullet from the center
 *       of the bottom of the screen.
 * - [x] As well as the bullets and the ships, the player must also be
 *       able to see how many bullets are left and how many ships have
 *       been destroyed so far.
 * Ships requirements:
 * - [x] Ships should not spawn at every tick. Ships should spawn with
 *       some fixed frequency, and a non-zero, random amount of them
 *       should spawn at the same time.
 * - [x] Ships should spawn at either the left or right ends of the
 *       screen, and then move across the screen.
 * - [x] Ships should not be able to spawn at the very top or very
 *       bottom of the screen, but their spawn point along the y-axis
 *       should be random.
 * - [x] Two or more identical ships spawning at the same time is fine.
 * - [x] Ships should move with what appears to be smooth motion,
 *       and move in a straight line across the screen.
 * - [x] All ships should have the same speed (the magnitude of their velocity).
 * - [x] Ships that have flown past the edge of the screen should be removed from the game.
 * - [x] Ships should be visually represented as a circle with a fixed color and radius.
 * - [ ] When a ship is hit by a bullet, it disappears.
 * - [ ] If a bullet hits two or more ships simultaneously, all of the ships should disappear.
 * 
 * Bullets requirements:
 * - [ ] Bullets should move with what appears to be smooth motion,
 *       and move in a straight line across the screen.
 * - [ ] All bullets should have the same speed (the magnitude of their velocity).
 *       It is fine if bullet speeds vary slightly due to rounding.
 * - [ ] Bullets that have flown past the edge of the screen should be
 *       removed from the game.
 * - [ ] Bullets should be visually represented as a circle with a fixed color;
 *       their size is discussed below.
 * - [ ] When a bullet collides with a ship, it disappears and "explodes" into
 *       many bullets. The initial position of all of them should be the same as
 *       that of the destroyed bullet.
 * - [ ] When a bullet a player fired hits a ship, it should explode into two bullets.
 *       When one of those bullets hits a ship, it should explode into three bullets, etc.
 * - [ ] In the nth explosion (where n is 1 in the explosion of the player-fired
 *       bullet), for each bullet i it explodes into, 0 <= i <= n, the bullet should
 *       fire off at i * (360 / (n + 1)) degrees.
 * - [ ] Bullets should grow in size along with n, so the higher up in the chain of
 *       explosions the bullet originated from, the bigger it should be. It should
 *       stop growing after some explosion, however, so it doesn’t take up too much of
 *       the screen.
 * - [ ] MY: Bullet hits a ship if their circles touch
 * - [ ] If two or more bullets hit the same ship simultaneously, all of the bullets
 *       should explode.
 */

import tester.*;
import javalib.funworld.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
import java.awt.Color;
import java.util.Random;

class NBullets extends World {
  /* Canvas size */
  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;

  /* Colors */
  private static final Color BACKGROUND_COLOR = new Color(0x25, 0x25, 0x25);
  public static int FONT_SIZE = 30;
  public static Color FONT_COLOR = Color.LIGHT_GRAY;
  public static final Color BULLET_COLOR = Color.RED;
  public static final Color SHIP_COLOR = Color.BLUE;
  public static final Color GUN_COLOR = Color.ORANGE;
  public static final Color GAME_OVER_COLOR = Color.RED;

  /* Clock */
  public static final double TICK_RATE = 1.0/28;
  
  /* Other stuff */
  private static final double SHIP_SPAWN_RATE = 1; // period in seconds (approximate)
  private static final int MAX_SHIPS_TO_SPAWN = 3; 
  
  public static final int BULLET_SPEED = 8; // pixels per tick
  public static final int SHIP_SPEED_TO_BULLET_SPEED = 2; // bullet speed to ship speed factor
  public static final int SHIP_SPEED = BULLET_SPEED/SHIP_SPEED_TO_BULLET_SPEED;
  public static final int NO_SHIP_FRACTION = 7; // top and bottom without ships == height divided by this
  public static final int HEIGHT_TO_SHIP_SIZE = 30; // ship size is smaller than world's height by this number
  public static final int SHIP_SIZE = HEIGHT/HEIGHT_TO_SHIP_SIZE; // ship size
  public static final int BULLET_INITIAL_SIZE = 8; // in pixels
  public static final int BULLET_SIZE_INCREMENT = 1; // in pixels
  public static final int BULLET_MAX_SIZE = 10; // in pixels
  public static final int BULLET_MAX_EXPLOSIONS = 10;
  

  
  int shipsDestroyed = 0; // Number of ships destroyed
  boolean paused = false;
  Gun gun = new Gun();
  IList<IMovable> bullets = new MtList<IMovable>(); // List of bullets on the screen
  IList<IMovable> ships = new MtList<IMovable>(); // List of ships in the game
  Random rand = new java.util.Random();
  int nBullets; // number of bullets a user has to shoot

  NBullets(int nBullets,
      Random rand) {
    this.nBullets = nBullets;
    this.rand = rand;
  }
  
  // Create a game with default settings and specified number of bullets
  NBullets(int nBullets) {
    this.nBullets = nBullets;
  }
  
  @Override
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    scene = this.placeBackground(scene);
    scene = this.gun.place(scene);
    scene = this.ships.foldr(new Func2IMovablePlace(), scene);
    scene = this.bullets.foldr(new Func2IMovablePlace(), scene);
    scene = this.placeInfo(scene);
    if (this.paused) {
      scene = scene.placeImageXY(new TextImage("PAUSED", FONT_SIZE, FONT_COLOR), WIDTH/2, HEIGHT/2);
    }
    return scene;
  }
  
  @Override
  public World onTick() {
    if (this.paused) {
      return this;
    }
    IList<IMovable> newBullets = this.bullets.foldr(new CollideAndExplode(ships), new MtList<>());
    IList<IMovable> newShips = this.ships.foldr(new CollideAndExplode(bullets), new MtList<>());

    this.shipsDestroyed = this.shipsDestroyed + this.ships.length() - newShips.length();
    this.ships = newShips;
    this.bullets = newBullets;
    
    return this.updateShips()
        .updateBullets()
        .spawnShips();
  }
  
  // Spawn up to MAX_SHIPS_TO_SPAWN ships (inclusive) every SHIP_SPAWN_RATE seconds, approximately
  private NBullets spawnShips() {
    // Use random to approximate spawning at the specified rate
    if (this.rand.nextInt((int) (SHIP_SPAWN_RATE * 1/TICK_RATE)) == 0) {
      // Random number of ships
      int nShipsToSpawn = this.rand.nextInt(MAX_SHIPS_TO_SPAWN) + 1;
      this.ships = this.ships.append(this.spawnNShips(nShipsToSpawn));
    }
    return this;
  }
  
  // Return a list of 1 up to N (inclusive) new ships
  private IList<IMovable> spawnNShips(int n) {
    if (n == 0) {
      return new MtList<IMovable>();
    }
    return new ConsList<IMovable>(this.spawnShip(), this.spawnNShips(n - 1));
  }
  
  
  // Spawn single ship
  private Ship spawnShip() {
    int x = this.rand.nextBoolean() ? 0 : WIDTH;
    int noShipsPart = HEIGHT/NO_SHIP_FRACTION;
    int y = noShipsPart + this.rand.nextInt((NO_SHIP_FRACTION - 1)*noShipsPart - noShipsPart);
    int direction = x == 0 ? 1 : -1;
    int shipSpeed = SHIP_SPEED;
    return new Ship(new GamePosn(x, y), new GamePosn(direction * shipSpeed, 0));
  }
  
  private NBullets updateShips() {
    this.ships = this.ships.map(new FuncIMovableMove());
    this.ships = this.ships.remove(new PredIMovableIsOffscreen());
    return this;
  }
  
  // Move all bullets and remove those that are offscreen
  private NBullets updateBullets() {
    this.bullets = this.bullets.map(new FuncIMovableMove());
    this.bullets = this.bullets.remove(new PredIMovableIsOffscreen());
    return this;
  }
  
  private WorldScene placeBackground(WorldScene scene) {
    WorldImage background = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, BACKGROUND_COLOR);
    return scene.placeImageXY(background,
        WIDTH/2,
        HEIGHT/2);
  }
  
  private WorldScene placeInfo(WorldScene scene) {
    scene = this.placeInfoScore(scene);
    scene = this.placeInfoBullets(scene);
    return scene;
  }
  
  private WorldScene placeInfoScore(WorldScene scene) {
    WorldImage scoreImage = new TextImage(
        Integer.toString(this.shipsDestroyed), FONT_SIZE, FONT_COLOR);
    return scene.placeImageXY(scoreImage,
        (int)(WIDTH - scoreImage.getWidth()/2),
        (int)scoreImage.getHeight()/2);
  }
  
  private WorldScene placeInfoBullets(WorldScene scene) {
    WorldImage scoreImage = new TextImage(
        Integer.toString(this.nBullets), FONT_SIZE, FONT_COLOR);
    return scene.placeImageXY(scoreImage,
        (int)(scoreImage.getWidth()/2),
        (int)scoreImage.getHeight()/2);
  }
  
  @Override
  public WorldEnd worldEnds() {
    if (this.gameOver()) {
      return new WorldEnd(true, this.makeALastScene());
    } else {
      return new WorldEnd(false, this.makeScene());
    }
  }
  
  // Keyboard handler
  @Override
  public NBullets onKeyEvent(String key) {
    //System.out.println(this.bullets.length());
    if (key.equals(" ") && this.nBullets > 0) {
      Bullet newBullet = this.gun.shoot();
      this.nBullets--;
      this.bullets = new ConsList<IMovable>(newBullet, this.bullets);
    }
    if (key.equals("p")) {
      this.paused = !this.paused;
    }
    return this;
  }
  
  // Return the last scene
  WorldScene makeALastScene() {
    WorldImage gameOver = new AboveImage( 
        new TextImage("GAME OVER",
            HEIGHT/5, FontStyle.BOLD, GAME_OVER_COLOR),
        new TextImage("SHIPS DESTROYED: " + Integer.toString(this.shipsDestroyed),
            HEIGHT/20, FontStyle.BOLD, GAME_OVER_COLOR)
        );
    return this.makeScene().placeImageXY(gameOver, WIDTH/2, HEIGHT/2);
  }
  
  // Return true if the game is over: if there are no more bullets to shoot
  boolean gameOver() {
    return this.nBullets == 0 && this.bullets.length() == 0;
  }
}

class NBulletsExamples {
  
  // To run a game
  boolean testPlayGame(Tester t) {
    NBullets game = new NBullets(10);
    return game.bigBang(NBullets.WIDTH, NBullets.HEIGHT, NBullets.TICK_RATE);
  }
  
  NBullets world1 = new NBullets(10, new Random(10)); 
}


