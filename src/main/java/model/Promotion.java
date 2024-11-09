package model;

import java.time.LocalDateTime;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    LocalDateTime start_date;
    LocalDateTime end_date;

    public Promotion(String name, int buy, int get, LocalDateTime start_date, LocalDateTime end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
