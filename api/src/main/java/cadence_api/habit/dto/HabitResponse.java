package cadence_api.habit.dto;

import cadence_api.common.enums.FrequencyType;
import cadence_api.common.enums.HabitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HabitResponse {


    private Long userId;


    private String name;

    private String description;


    private HabitType type;


    private FrequencyType frequency;


    private String color;


    private Long id;


    private Boolean isActive;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

}
