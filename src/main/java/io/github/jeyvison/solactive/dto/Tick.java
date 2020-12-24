package io.github.jeyvison.solactive.dto;


public class Tick {

    private String instrument;
    private double price;
    private long timestamp;

    public Tick(String instrument, double price, long timestamp){
        this.instrument = instrument;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getInstrument() {
        return instrument;
    }

    public double getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
