package io.github.jeyvison.solactive.model;


public class StatisticModel {

    int second;
    private double average;
    private double sum;
    private double max;
    private double min;
    long count;

    public StatisticModel(int second, double average, double sum, double max, double min, long count) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
        this.second = second;
        this.average = average;
    }

    public double getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    public int getSecond() {
        return second;
    }

    public double getAverage() {
        return average;
    }
}
