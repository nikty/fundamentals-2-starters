import tester.*;

class BagelRecipe {
  // weights of the ingredients as ounces
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    if (new Utils().equalDoubles(flour, water, 0.001)) {
      this.flour = flour;
      this.water = water;
    }
    else {
      throw new IllegalArgumentException(
          "the weight of the flour should be equal to the weight of the water");
    }
    if (new Utils().equalDoubles(yeast, malt, 0.001)) {
      this.yeast = yeast;
      this.malt = malt;
    }
    else {
      throw new IllegalArgumentException(
          "the weight of the yeast should be equal the weight of the malt");
    }
    if (new Utils().equalDoubles((salt + yeast), flour / 20, 0.001)) {
      this.salt = salt;
    }
    else {
      throw new IllegalArgumentException(
          "the weight of the salt + yeast should be 1/20th the weight of the flour");
    }
  }

  // Given flour and yeast weights, calculate the rest of weights and produce
  // perfect recipe
  BagelRecipe(double flour, double yeast) {
    this(flour, flour, yeast, flour / 20 - yeast, yeast);
  }

  // Volumes instead of weights
  // Flour and water volumes are measured in cups, while yeast, salt, and malt
  // volumes are measured in teaspoons.
  // Conversions:
  // 48 teaspoons = 1 cup
  // 1 cup of yeast = 5 ounces
  // 1 cup of salt = 10 ounces
  // 1 cup of malt = 11 ounces
  // 1 cup of water = 8 ounces
  // 1 cup of flour = 4 and 1⁄4 ounces
  BagelRecipe(double flour, double yeast, double salt) {
    this((new Utils()).flourCupsToOunces(flour), (new Utils()).flourCupsToOunces(flour),
        (new Utils()).yeastTeaspoonsToOunces(yeast), (new Utils()).saltTeaspoonsToOunces(salt), // we
                                                                                                // don't
                                                                                                // actually
                                                                                                // need
                                                                                                // salt
                                                                                                // here
                                                                                                // for
                                                                                                // the
                                                                                                // perfect
                                                                                                // recipe
        (new Utils()).yeastTeaspoonsToOunces(yeast));
  }

  // Produce true if the same ingredients of this recipe and the given recipe have
  // the same weights to within 0.001 ounces
  boolean sameRecipe(BagelRecipe other) {
    return new Utils().equalDoubles(this.flour, other.flour, 0.001)
        && new Utils().equalDoubles(this.water, other.water, 0.001)
        && new Utils().equalDoubles(this.yeast, other.yeast, 0.001)
        && new Utils().equalDoubles(this.salt, other.salt, 0.001)
        && new Utils().equalDoubles(this.malt, other.malt, 0.001);
  }
}

// Utility class
class Utils {
  double flourCupsToOunces(double cups) {
    return cups * 4.25;
  }

  double yeastTeaspoonsToOunces(double tspns) {
    return tspns * (5. / 48);
  }

  double saltTeaspoonsToOunces(double tspns) {
    return tspns * (10. / 48);
  }

  // Return true if the absolute difference between given doubles is smaller than
  // the given tolerance
  boolean equalDoubles(double a, double b, double eps) {
    return Math.abs(a - b) < eps;
  }
}

class Examples {
  
  BagelRecipe bagel1 = new BagelRecipe(100.0, 5.0);
  BagelRecipe bagel2 = new BagelRecipe(100.0, 4.9991);
  BagelRecipe bagel3 = new BagelRecipe(100.0, 3.0);
  
  boolean testNewBagelRecipe(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException(
            "the weight of the flour should be equal to the weight of the water"),
        "BagelRecipe", 1.0, 2.0, .0, .0, .0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "the weight of the yeast should be equal the weight of the malt"),
            "BagelRecipe", 1.0, 1.0, 5.0, .0, 3.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "the weight of the salt + yeast should be 1/20th the weight of the flour"),
            "BagelRecipe", 1.0, 1.0, 5.0, .0, 5.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "the weight of the salt + yeast should be 1/20th the weight of the flour"),
            "BagelRecipe", 1.0, 1.0, 5.0, .0, 5.0)
        && t.checkConstructorNoException("Test creating valid recipe", "BagelRecipe", 100.0, 100.0,
            4.0, 1.0, 4.0);
  }

  boolean testNewBagelRecipeSimpleInterface(Tester t) {
    return t.checkConstructorNoException("Test creating valid recipe", "BagelRecipe", 100.0, 50.0);
  }

  boolean testNewBagelRecipeVolumes(Tester t) {
    return
    // 20.0 cups of flour == 85.0 ounces
    // salt + yeast == 4.25 ounces
    // 3.0 ounces of yeast == .6 cups of yeast == 28.8 teaspoons of yeast
    // 1.25 ounces of salt == .125 cups of salt == 6.0 teaspoons of salt
    t.checkConstructorNoException("Test creating valid recipe", "BagelRecipe", 20.0, 28.8, 6.0);
  }
  
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(bagel1.sameRecipe(bagel2), true)
        && t.checkExpect(bagel1.sameRecipe(bagel3), false);
  }
}
