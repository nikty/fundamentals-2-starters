// To represend a menu item
interface IMenuItem {
  
}

class Soup implements IMenuItem {
  String name;
  int price; // in cents
  boolean isVegetarian;
  Soup(String name, int price, boolean isVegetarian) {
    this.name = name;
    this.price = price;
    this.isVegetarian = isVegetarian;
  }
}

class Salad implements IMenuItem {
  String name;
  int price; // in cents
  boolean isVegetarian;
  String dressing;

  Salad(String name, int price, boolean isVegetarian, String dressing) {
      this.name = name;
      this.price = price;
      this.isVegetarian = isVegetarian;
      this.dressing = dressing;
  }
}

class Sandwich implements IMenuItem {
  String name;
  int price; // in cents
  String bread;
  Filling filling1;
  Filling filling2;

  Sandwich(String name, int price, String bread, Filling filling1, Filling filling2) {
      this.name = name;
      this.price = price;
      this.bread = bread;
      this.filling1 = filling1;
      this.filling2 = filling2;
  }
}

// To represent fillings
class Filling {
  String name;
  Filling(String name) {
    this.name = name;
  }
}

class Examples {
  // Soups
  IMenuItem soup1 = new Soup("Borscht", 300, false);
  IMenuItem soup2 = new Soup("Tomato Basil", 250, true);

  // Salads
  IMenuItem salad1 = new Salad("Caesar Salad", 350, false, "Caesar Dressing");
  IMenuItem salad2 = new Salad("Greek Salad", 300, true, "Olive Oil & Lemon");
  
  //Sandwiches
  Filling turkeyFilling = new Filling("turkey");
  Filling cheeseFilling = new Filling("cheese");
  IMenuItem sandwich1 = new Sandwich("Turkey Club", 450, "Whole Wheat", turkeyFilling, cheeseFilling);
 
 Filling veggieFilling1 = new Filling("peanut butter");
 Filling veggieFilling2 = new Filling("jelly");
 IMenuItem sandwich2 = new Sandwich("Veggie Delight", 400, "Multigrain", veggieFilling1, veggieFilling2);

}


