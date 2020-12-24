package io.github.jeyvison.solactive.service;

import io.github.jeyvison.solactive.model.StatisticModel;
import io.github.jeyvison.solactive.engine.StatisticModelEngine;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StatisticModelService {

    private Map<String, StatisticModelEngine> statisticsByInstrument = new ConcurrentHashMap<>();
    private StatisticModelEngine statisticModelEngine = new StatisticModelEngine();

    public void addStatisticModel(String instrument, double price, long timestamp) {
        statisticModelEngine.compute(price, timestamp);

        statisticsByInstrument.compute(instrument, (key, statisticModelEngine) -> {
            if (statisticModelEngine == null) {
                statisticModelEngine = new StatisticModelEngine();
            }

            statisticModelEngine.compute(price, timestamp);

            return statisticModelEngine;
        });
    }

    public StatisticModel getStatisticModel() {
        return statisticModelEngine.getStatisticModel();
    }

    public StatisticModel getStatisticModel(String instrument) {

        StatisticModelEngine statisticModelEngine = statisticsByInstrument.get(instrument);

        if (statisticModelEngine == null) {
            return null;
        }

        return statisticModelEngine.getStatisticModel();
    }
}
