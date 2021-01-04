package io.github.jeyvison.solactive.dto;

public class Statistic {

    long count;
    private double average;
    private double max;
    private double min;

    public Statistic(double average, double max, double min, long count) {
        this.average = average;
        this.count = count;
        this.max = max;
        this.min = min;
    }

    public double getAverage() {
        return average;
    }

    public long getCount() {
        return count;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

}
