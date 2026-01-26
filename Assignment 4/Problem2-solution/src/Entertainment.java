import tester.*;

interface IEntertainment {
  // compute the total price of this Entertainment
  double totalPrice();

  // computes the minutes of entertainment of this IEntertainment
  // Assume: a magazine provides 5 minutes of entertainment per page and TV series
  // and podcasts provide 50 minutes of entertainment per episode.
  int duration();

  // produce a String that shows the name and price of this IEntertainment
  String format();

  // is this IEntertainment the same as that one?
  boolean sameEntertainment(IEntertainment that);
  
  boolean isSameMagazine(Magazine mag);
  boolean isSameTVSeries(TVSeries tv);
  boolean isSamePodcast(Podcast pod);
}

abstract class AEntertainment implements IEntertainment {
  String name;
  double price; // represents price per issue
  int installments; // number of issues or episodes per year
  
  AEntertainment(String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments;
  }
  
  public abstract int installmentDuration();
  
  public double totalPrice() {
    return this.price * this.installments;
  }
  
  public int duration() {
    return this.installmentDuration() * this.installments;
  }
  
  // produce a String that shows the name and price of this Entertainment
  public String format() {
    return this.name + ", " + this.totalPrice() + ".";
  }
  
  public boolean isSameMagazine(Magazine mag) {
    return false;
  }
  
  public boolean isSameTVSeries(TVSeries tv) {
    return false;
  }
  
  public boolean isSamePodcast(Podcast pod) {
    return false;
  }
}

class Magazine extends AEntertainment {
  String genre;
  int pages;

  Magazine(String name, double price, String genre, int pages, int installments) {
    super(name, price, installments);
    this.genre = genre;
    this.pages = pages;
  }
  
  // Return the duration for this magazine
  public int installmentDuration() {
    return 5 * this.pages;
  }

  // is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.isSameMagazine(this);
  }

  public boolean isSameMagazine(Magazine that) {
    return this.name.equals(that.name)
        && this.price == that.price
        && this.genre.equals(that.genre)
        && this.pages == that.pages
        && this.installments == that.installments;
  }
}

class TVSeries extends AEntertainment {
  String corporation;

  TVSeries(String name, double price, int installments, String corporation) {
    super(name, price, installments);
    this.corporation = corporation;
  }

  // Return the duration for this tvseries
  public int installmentDuration() {
    return 50;
  }

  // is this TVSeries the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.isSameTVSeries(this);
  }

  public boolean isSameTVSeries(TVSeries that) {
    return this.name.equals(that.name)
        && this.price == that.price
        && this.installments == that.installments
        && this.corporation.equals(that.corporation);
  }
}

class Podcast extends AEntertainment {
  Podcast(String name, double price, int installments) {
    super(name, price, installments);
  }

  // Return the duration for this podcast
  public int installmentDuration() {
    return 50;
  }

  // is this Podcast the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.isSamePodcast(this);
  }

  public boolean isSamePodcast(Podcast that) {
    return this.name == that.name
        && this.price == that.price
        && this.installments == that.installments;
  }
}

class ExamplesEntertainment {
  // Magazines
  IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment timeMagazine = new Magazine("Time", 3.99, "News", 50, 52);

  // TV series
  IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment breakingBad = new TVSeries("Breaking Bad", 4.99, 62, "AMC");

  // Podcasts
  IEntertainment serial = new Podcast("Serial", 0.0, 8);
  IEntertainment theDaily = new Podcast("The Daily", 2.50, 100);

  // testing total price method
  boolean testTotalPrice(Tester t) {
    return t.checkInexact(this.rollingStone.totalPrice(), 2.55 * 12, .0001)
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25 * 13, .0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.timeMagazine.totalPrice(), 3.99 * 52, .0001)
        && t.checkInexact(this.breakingBad.totalPrice(), 4.99 * 62, .0001)
        && t.checkInexact(this.theDaily.totalPrice(), 2.50 * 100, .0001);
  }

  boolean testDuration(Tester t) {
    return t.checkExpect(rollingStone.duration(), 5 * 60 * 12)
        && t.checkExpect(breakingBad.duration(), 50 * 62)
        && t.checkExpect(serial.duration(), 50 * 8);
  }
  
  boolean testFormat(Tester t) {
    return t.checkExpect(houseOfCards.format(), "House of Cards, " + houseOfCards.totalPrice() + ".")
        && t.checkExpect(theDaily.format(), "The Daily, " + theDaily.totalPrice() + ".");
  }
  
  boolean testSameEntertainment(Tester t) {
    return t.checkExpect(rollingStone.sameEntertainment(rollingStone), true)
        && t.checkExpect(rollingStone.sameEntertainment(timeMagazine), false)
        && t.checkExpect(rollingStone.sameEntertainment(breakingBad), false)
        && t.checkExpect(rollingStone.sameEntertainment(theDaily), false)
        && t.checkExpect(houseOfCards.sameEntertainment(houseOfCards), true)
        && t.checkExpect(houseOfCards.sameEntertainment(breakingBad), false)
        && t.checkExpect(houseOfCards.sameEntertainment(timeMagazine), false)
        && t.checkExpect(houseOfCards.sameEntertainment(serial), false)
        && t.checkExpect(serial.sameEntertainment(serial), true)
        && t.checkExpect(serial.sameEntertainment(breakingBad), false)
        && t.checkExpect(serial.sameEntertainment(timeMagazine), false)
        && t.checkExpect(serial.sameEntertainment(theDaily), false);
  }
}




