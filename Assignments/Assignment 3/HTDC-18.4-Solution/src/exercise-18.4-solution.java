/*
  Exercise 18.4

  Determine whether it is possible to abstract in the class hierarchy
  of exercise 4.2 (page 31).
*/

class SalesItem {
    int originalPrice;

    SalesItem(int originalPrice) {
        this.originalPrice = originalPrice;
    }
}

class DeepDiscount extends SalesItem {
    DeepDiscount(int originalPrice) {
        super(originalPrice);
    }
}

class RegularDiscount extends SalesItem {
    int discountPercentage;

    RegularDiscount(int originalPrice, int discountPercentage) {
        super(originalPrice);
        this.discountPercentage = discountPercentage;
    }
}

