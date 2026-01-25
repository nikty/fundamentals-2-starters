// to represent dates

import tester.*;

class Date {
  int year;
  int month;
  int day;
  Date(int year, int month, int day) {
    this.year = new Utils().checkRange(year, 1500, 2100,
        "Invalid year: " + Integer.toString(year));
    this.month = new Utils().checkRange(month, 1, 12,
        "Invalid month: " + Integer.toString(month));
    this.day = new Utils().checkRange(day, 1, 31,
        "Invalid day: " + Integer.toString(day));
  }
  
  // Implicit year
  Date(int month, int day) {
    this(2022, month, day);
  }
}

class Utils {
  int checkRange(int val, int min, int max, String msg) {
    if (val >= min && val <= max) {
      return val;
    } else {
      throw new IllegalArgumentException(msg);
    }
  }
}
	
class ExamplesDates {
  //Good dates
  Date d20100228 = new Date(2010, 2, 28);   // Feb 28, 2010
  Date d20091012 = new Date(2009, 10, 12);  // Oct 12, 2009
  Date d20220101 = new Date(1, 1);

  // Bad date
  //Date dn303323 = new Date(-30, 33, 23); // ???
  boolean testNewDate(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Invalid year: -30"), "Date",
            -30, 33, 23)
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid month: 14"), "Date",
            14, -30);
  }
  
  boolean testUtilsCheckRange(Tester t) {
    return t.checkException(new IllegalArgumentException("too great"),
        new Utils(), "checkRange", 1000, 10, 20, "too great");
  }
}