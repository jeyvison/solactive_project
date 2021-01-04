package io.github.jeyvison.solactive.controller;

import io.github.jeyvison.solactive.dto.Tick;
import io.github.jeyvison.solactive.service.StatisticModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class TickController {

    @Autowired
    private StatisticModelService statisticModelService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/ticks")
    public ResponseEntity postTick(@RequestBody Tick tick) {
        long last60 = Duration.ofMillis(System.currentTimeMillis()).minusMinutes(1).toMillis();

        if (tick.getTimestamp() < last60) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        statisticModelService.addStatisticModel(tick.getInstrument(), tick.getPrice(), tick.getTimestamp());

        return new ResponseEntity(HttpStatus.CREATED);

    }
}
