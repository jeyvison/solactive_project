package io.github.jeyvison.solactive.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jeyvison.solactive.dto.Tick;
import io.github.jeyvison.solactive.service.StatisticModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TickController.class)
class TickControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticModelService statisticModelService;

    @Test
    void postTick() throws Exception {

        Tick tick = new Tick("IBM.N", 1234.12, System.currentTimeMillis());

        mockMvc.perform(MockMvcRequestBuilders.post("/ticks")
                .content(_mapper.writeValueAsString(tick))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void postTickTooOld() throws Exception {

        Tick tick = new Tick("IBM.N", 1234.12, Duration.ofMillis(System.currentTimeMillis()).minus(2, ChronoUnit.MINUTES).toMillis());

        mockMvc.perform(MockMvcRequestBuilders.post("/ticks")
                .content(_mapper.writeValueAsString(tick))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    private final ObjectMapper _mapper = new ObjectMapper();
}