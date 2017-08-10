package training.openweatherapp;

/**
 * Created by hindahmed on 10/08/17.
 */

public class Temps {
    private String day;
    private String max;
    private String min;

    public Temps(String day, String max, String min) {
        this.day = day;
        this.max = max;
        this.min = min;
    }

    public String getDay() {
        return day;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }
}