package io.github.jeyvison.solactive.controller;

import io.github.jeyvison.solactive.service.StatisticModelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticController.class)
class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticModelService statisticModelService;

    @Test
    void getStatisticForInvalidInstrument() throws Exception {

        Mockito.when(
                statisticModelService.getStatisticModel(
                        Mockito.anyString()
                )
        ).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/statistics/{instrument_identifier}", "non.existent.instrument")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}