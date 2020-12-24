package io.github.jeyvison.solactive.service;

import io.github.jeyvison.solactive.model.StatisticModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class StatisticModelServiceTest {

    @Test
    void getStatistic(){
        StatisticModelService statisticModelService = new StatisticModelService();

        statisticModelService.addStatisticModel("IBM.N", 100.0, System.currentTimeMillis() - 2000);
        statisticModelService.addStatisticModel("IBM.N", 100.0, System.currentTimeMillis() - 2000);
        statisticModelService.addStatisticModel("IBM.N", 200.0, System.currentTimeMillis() - 1000);

        statisticModelService.addStatisticModel("MS.N", 200.0, System.currentTimeMillis());
        statisticModelService.addStatisticModel("MS.N", 100.0, System.currentTimeMillis());

        StatisticModel statisticModel = statisticModelService.getStatisticModel();

        Assert.assertEquals(140.0, statisticModel.getAverage(),0);
        Assert.assertEquals(5,statisticModel.getCount());
        Assert.assertEquals(200.0, statisticModel.getMax(), 0);
        Assert.assertEquals(100.0, statisticModel.getMin(), 0);
    }

    @Test
    void getStatisticByInstrument(){
        StatisticModelService statisticModelService = new StatisticModelService();

        statisticModelService.addStatisticModel("IBM.N", 100, System.currentTimeMillis());
        statisticModelService.addStatisticModel("IBM.N", 100.0, System.currentTimeMillis());
        statisticModelService.addStatisticModel("IBM.N", 200.0, System.currentTimeMillis());

        StatisticModel statisticModel = statisticModelService.getStatisticModel("IBM.N");

        Assert.assertEquals(133.3, statisticModel.getAverage(), 0.1);

        statisticModelService.addStatisticModel("MS.N", 200.0, System.currentTimeMillis());
        statisticModelService.addStatisticModel("MS.N", 100.0, System.currentTimeMillis());

        statisticModel = statisticModelService.getStatisticModel("MS.N");

        Assert.assertEquals(150.0, statisticModel.getAverage(), 0.1);
    }

    @Test
    void getStatisticWithInvalidInstrument(){
        StatisticModelService statisticModelService = new StatisticModelService();

        StatisticModel statisticModel = statisticModelService.getStatisticModel("non.existent.instrument");

        Assert.assertNull(statisticModel);
    }
}