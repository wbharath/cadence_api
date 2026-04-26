package cadence_api.habit.dto;


import cadence_api.common.enums.FrequencyType;
import cadence_api.common.enums.HabitType;
import cadence_api.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HabitRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private HabitType type;

    @NotNull
    private FrequencyType frequency;

    @NotBlank
    private String color;

}
