import tester.Tester;

interface IBook {
  
  // Given the number that represents today in the
  // library date-recording system, produces the number of days this book is
  // overdue.
  // Assume: If the number is negative, the book can still be out for that many
  // days.
  int daysOverdue(int day);

  // Produces a boolean value that informs us whether the book is overdue on
  // the given day.
  boolean isOverdue(int day);

  // Produce the fine for this book, if the
  // book is returned on the given day.
  int computeFine(int day);
  
  // Return number of days the book can be taken
  int getBorrowDays();
}

abstract class ABook implements IBook {
  String title;
  int dayTaken;

  ABook(String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
  }

  public int daysOverdue(int day) {
    return day - this.dayTaken - this.getBorrowDays();
  }

  public boolean isOverdue(int day) {
    return this.daysOverdue(day) > 0;
  }
  
  public abstract int computeFine(int day);
  
  public abstract int getBorrowDays();
}

class Book extends ABook {
  String author;
  int borrowDays = 14;
  int overdueFee = 10; // in cents

  Book(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }
  
  public int getBorrowDays() {
    return this.borrowDays;
  }
  
  public int computeFine(int day) {
    if (this.isOverdue(day)) {
      return this.daysOverdue(day) * this.overdueFee;
    }
    return 0;
  }
}

class RefBook extends ABook {
  int borrowDays = 2;
  int overdueFee = 10; // in cents
  
  RefBook(String title, int dayTaken) {
    super(title, dayTaken);
  }
  
  public int getBorrowDays() {
    return this.borrowDays;
  }
  
  public int computeFine(int day) {
    if (this.isOverdue(day)) {
      return this.daysOverdue(day) * this.overdueFee;
    }
    return 0;
  }
}

class AudioBook extends ABook {
  String author;
  int borrowDays = 14;
  int overdueFee = 20; // in cents
  
  AudioBook(String title, String author, int dayTaken) {
    super(title, dayTaken);
    this.author = author;
  }
  
  public int getBorrowDays() {
    return this.borrowDays;
  }
  
  public int computeFine(int day) {
    if (this.isOverdue(day)) {
      return this.daysOverdue(day) * this.overdueFee;
    }
    return 0;
  }
}

class Examples {
  IBook book1 = new Book("A Philosophy of Software Design", "John Ousterhout", 5000);
  IBook book2 = new Book("Structure and Interpretation of Computer Programs",
      "Abraham S. Eisenberg, Gerald Jay Sussman", 7500);
  IBook book3 = new Book("Common Lisp: A Gentle Introduction to Symbolic Computation",
      "David S. Touretzky", 4800);
  IBook book4 = new RefBook("Common Lisp the Language", 6000);
  IBook book5 = new AudioBook("JavaScript: The Good Parts", "Douglas Crockford", 8000);
  IBook book6 = new Book("The Little Schemer", "Daniel P. Friedman, Matthias Felleisen", 5500);
  IBook book8 = new Book("On Lisp", "Paul Graham", 6200);

  boolean testDaysOverdue(Tester t) {
    return t.checkExpect(book1.daysOverdue(5001), -13)
        && t.checkExpect(book2.daysOverdue(5000), -2514)
        && t.checkExpect(book4.daysOverdue(6100), 98);
  }

  boolean testIsOverdue(Tester t) {
    return t.checkExpect(book1.isOverdue(5001), false)
        && t.checkExpect(book2.isOverdue(8000), true)
        && t.checkExpect(book4.isOverdue(6001), false)
        && t.checkExpect(book4.isOverdue(6003), true);
  }
  
  boolean testComputeFine(Tester t) {
    return t.checkExpect(book1.computeFine(5001), 0)
        && t.checkExpect(book2.computeFine(8000), 486 * 10)
        && t.checkExpect(book4.computeFine(6001), 0);
        //&& t.checkExpect(book4.computeFine(6003), 1 * 10);
  }
}