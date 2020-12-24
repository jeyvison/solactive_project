package io.github.jeyvison.solactive.controller;

import io.github.jeyvison.solactive.dto.Statistic;
import io.github.jeyvison.solactive.model.StatisticModel;
import io.github.jeyvison.solactive.service.StatisticModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    @Autowired
    private StatisticModelService statisticModelService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/statistics")
    public Statistic getStatistic(){

        StatisticModel statisticModel = statisticModelService.getStatisticModel();

        return new Statistic(statisticModel.getAverage(), statisticModel.getMax(), statisticModel.getMin(), statisticModel.getCount());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/statistics/{instrument_identifier}")
    public ResponseEntity<Statistic> getStatistic(@PathVariable("instrument_identifier") String instrumentIdentifier){

        StatisticModel statisticModel = statisticModelService.getStatisticModel(instrumentIdentifier);

        if(statisticModel == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                new Statistic(statisticModel.getAverage(), statisticModel.getMax(),statisticModel.getMin(),
                        statisticModel.getCount()),
                HttpStatus.FOUND);
    }
}
