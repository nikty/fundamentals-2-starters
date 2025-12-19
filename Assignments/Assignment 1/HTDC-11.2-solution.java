/*
  Exercise 11.2

  Exercise 3.2 provides the data definition for a weather recording
  program. Design the following methods for the WeatherRecord class:

  1. withinRange, which determines whether today’s high and low were
  within the normal range;

  2. rainyDay, which determines whether the precipitation is higher
  than some given value;

  3. recordDay, which determines whether the temperature broke either
  the high or the low record.

*/

import tester.*;

// Date class to represent a date
class Date {
    int day;
    int month;
    int year;

    // Constructor
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
}

// TemperatureRange class to represent a range of temperatures
class TemperatureRange {
    int high;
    int low;

    // Constructor
    public TemperatureRange(int high, int low) {
        this.high = high;
        this.low = low;
    }

    // Determine whether this range's hign and low are within the given range
    boolean withinRange(TemperatureRange that) {
        return this.low >= that.low && this.high <= that.high;
    }

    // Determine whether this range's hign or low beats the given range
    boolean exceeds(TemperatureRange that) {
        return this.low < that.low || this.high > that.high;
    }
}

// WeatherRecord class to aggregate date and temperature ranges
class WeatherRecord {
    Date d;
    TemperatureRange today;
    TemperatureRange normal;
    TemperatureRange record;
    double precipitation;

    // Constructor
    public WeatherRecord(Date d, TemperatureRange today, TemperatureRange normal, TemperatureRange record, double precipitation) {
        this.d = d;
        this.today = today;
        this.normal = normal;
        this.record = record;
        this.precipitation = precipitation;
    }

    // Determine whether today’s high and low were within the normal range
    boolean withinRange() {
        return this.today.withinRange(this.normal);
    }

    // Determine whether the precipitation is higher than some given value;
    boolean rainyDay(double p) {
        return this.precipitation > p;
    }

    // Determine whether the temperature broke either the high or the low record.
    boolean recordDay() {
        return this.today.exceeds(this.record);
    }

}

class WeatherRecordExamples {
    WeatherRecordExamples() {}

    // Example WeatherRecord instances with realistic precipitation values
    WeatherRecord ny = new WeatherRecord(new Date(2025, 11, 19),  // December is month 11
                                         new TemperatureRange(5, -2),
                                         new TemperatureRange(6, -4),
                                         new TemperatureRange(13, -10), 
                                         5.0);  // New York - average winter precipitation

    WeatherRecord la = new WeatherRecord(new Date(2025, 11, 19),
                                         new TemperatureRange(15, 6),
                                         new TemperatureRange(16, 7),
                                         new TemperatureRange(22, 2), 
                                         0.0); // Los Angeles - very low winter precipitation

    WeatherRecord laRecord = new WeatherRecord(new Date(2025, 7, 19),
                                                new TemperatureRange(30, 6),
                                                new TemperatureRange(16, 7),
                                                new TemperatureRange(22, 2), 
                                                0.0); // Los Angeles - very low winter precipitation

    WeatherRecord chicago = new WeatherRecord(new Date(2025, 11, 19),
                                              new TemperatureRange(10, 2),
                                              new TemperatureRange(9, -3),
                                              new TemperatureRange(20, -9), 
                                              7.0); // Chicago - potential for snowy conditions

    WeatherRecord miami = new WeatherRecord(new Date(2025, 11, 19),
                                            new TemperatureRange(25, 12),
                                            new TemperatureRange(26, 15),
                                            new TemperatureRange(30, 10), 
                                            2.0); // Miami - typical December rain

    WeatherRecord seattle = new WeatherRecord(new Date(2025, 11, 19),
                                              new TemperatureRange(3, -1),
                                              new TemperatureRange(6, -1),
                                              new TemperatureRange(12, -5), 
                                              10.0); // Seattle - usual rainy weather

    WeatherRecord denver = new WeatherRecord(new Date(2025, 11, 19),
                                             new TemperatureRange(-2, -8),
                                             new TemperatureRange(2, -7),
                                             new TemperatureRange(10, -15), 
                                             3.0); // Denver - potential snow and rain

    boolean testWithinRange(Tester t){
        return t.checkExpect(ny.withinRange(), true)
            && t.checkExpect(chicago.withinRange(), false);
    }

    boolean testRainyDay(Tester t) {
        return t.checkExpect(miami.rainyDay(1.0), true)
            && t.checkExpect(la.rainyDay(1.0), false);
    }

    boolean testRecordDay(Tester t) {
        return t.checkExpect(la.recordDay(), false)
            && t.checkExpect(laRecord.recordDay(), true);
    }
}
