package io.github.jeyvison.solactive.engine;

import io.github.jeyvison.solactive.model.StatisticModel;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;

public class StatisticModelEngine {

    private AtomicReferenceArray<StatisticModel> statistics = new AtomicReferenceArray<>(60);
    private volatile StatisticModel statisticModel = null;

    public StatisticModelEngine(){
        Timer timer = new Timer(true);
        timer.schedule(new StatisticCalculator(), 1000L,1000L);

    }

    public void compute(double price, long timestamp){
       int seconds = Math.abs((int)Duration.of(timestamp, ChronoUnit.MILLIS).get(ChronoUnit.SECONDS));

       int index = seconds % statistics.length();

        statistics.updateAndGet(index, (statisticModel) -> {
           if(statisticModel == null){
               return new StatisticModel(seconds, price, price, price, price, 1);
           }

           if(statisticModel.getSecond() <= seconds){
               double sum = statisticModel.getSum() + price;
               double max = Math.max(statisticModel.getMax(), price);
               double min = Math.min(statisticModel.getMin(), price);
               long count = statisticModel.getCount() + 1;
               double average = sum / count;

               return new StatisticModel(seconds, average, sum , max, min, count);
           }

           return statisticModel;

       });
       recalculate();
    }

    public StatisticModel getStatisticModel(){
        return statisticModel;
    }

    private class StatisticCalculator extends TimerTask {

        @Override
        public void run() {
            recalculate();
        }
    }


    private void recalculate(){

        int from = (int)Duration.of(System.currentTimeMillis(), ChronoUnit.MILLIS).minus(59, ChronoUnit.SECONDS).get(ChronoUnit.SECONDS);
        int to = (int)Duration.of(System.currentTimeMillis(), ChronoUnit.MILLIS).get(ChronoUnit.SECONDS);

        StatisticModel baseStatisticModel = new StatisticModel(0,0,0,0,0,0);

        statisticModel = IntStream.rangeClosed(from, to)
                .mapToObj(index -> statistics.get(index % statistics.length()))
                .filter(Objects::nonNull)
                .reduce(baseStatisticModel,(result, statisticModel) -> {
                    if(result == baseStatisticModel){
                        return statisticModel;
                    }

                    double sum = result.getSum() + statisticModel.getSum();
                    double max = Math.max(result.getMax(), statisticModel.getMax());
                    double min = Math.min(result.getMin(), statisticModel.getMin());
                    long count = result.getCount() + statisticModel.getCount();
                    double average = sum / count;

                    return new StatisticModel(0, average, sum, max, min, count);
                });
    }
}
