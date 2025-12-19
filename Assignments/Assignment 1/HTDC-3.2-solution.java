/*
  Exercise 3.2

  Translate the data definition in figure 11 into classes. Also obtain
  examples of weather information and translate them into instances of
  the matching class.

*/

// Date class to represent a date
class Date {
    private int day;
    private int month;
    private int year;

    // Constructor
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
}

// TemperatureRange class to represent a range of temperatures
class TemperatureRange {
    private int high;
    private int low;

    // Constructor
    public TemperatureRange(int high, int low) {
        this.high = high;
        this.low = low;
    }
}

// WeatherRecord class to aggregate date and temperature ranges
class WeatherRecord {
    private Date d;
    private TemperatureRange today;
    private TemperatureRange normal;
    private TemperatureRange record;

    // Constructor
    public WeatherRecord(Date d, TemperatureRange today, TemperatureRange normal, TemperatureRange record) {
        this.d = d;
        this.today = today;
        this.normal = normal;
        this.record = record;
    }
}

class WeatherRecordExamples {
    WeatherRecordExamples() {}

    WeatherRecord ny = new WeatherRecord(new Date(19, 12, 2025),
                                         new TemperatureRange(5, -2),
                                         new TemperatureRange(6, -4),
                                         new TemperatureRange(13, -10));  // New York
    WeatherRecord la = new WeatherRecord(new Date(19, 12, 2025),
                                         new TemperatureRange(15, 6),
                                         new TemperatureRange(16, 7),
                                         new TemperatureRange(22, 2)); // Los Angeles
    WeatherRecord chicago = new WeatherRecord(new Date(19, 12, 2025),
                                              new TemperatureRange(10, 2),
                                              new TemperatureRange(9, -3),
                                              new TemperatureRange(20, -9)); // Chicago
    WeatherRecord miami = new WeatherRecord(new Date(19, 12, 2025),
                                            new TemperatureRange(25, 12),
                                            new TemperatureRange(26, 15),
                                            new TemperatureRange(30, 10)); // Miami
    WeatherRecord seattle = new WeatherRecord(new Date(19, 12, 2025),
                                              new TemperatureRange(3, -1),
                                              new TemperatureRange(6, -1),
                                              new TemperatureRange(12, -5)); // Seattle
    WeatherRecord denver = new WeatherRecord(new Date(19, 12, 2025),
                                             new TemperatureRange(-2, -8),
                                             new TemperatureRange(2, -7),
                                             new TemperatureRange(10, -15)); // Denver
}
