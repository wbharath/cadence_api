package cadence_api;

import cadence_api.common.enums.FrequencyType;
import cadence_api.common.enums.HabitType;
import cadence_api.habit.controller.HabitController;
import cadence_api.habit.dto.HabitRequest;
import cadence_api.habit.dto.HabitResponse;
import cadence_api.habit.service.HabitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HabitController.class)
public class HabitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HabitService habitService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createHabit_ReturnsCreated() throws Exception {

        // ARRANGE
        HabitRequest request = new HabitRequest();
        request.setUserId(1L);
        request.setName("Meditation");
        request.setType(HabitType.MENTAL);
        request.setFrequency(FrequencyType.DAILY);
        request.setColor("#6366f1");

        HabitResponse response = new HabitResponse();
        response.setName("Meditation");
        response.setUserId(1L);

        when(habitService.createHabit(any(HabitRequest.class)))
                .thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/habits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Meditation"))
                .andExpect(jsonPath("$.userId").value(1L));
    }
}