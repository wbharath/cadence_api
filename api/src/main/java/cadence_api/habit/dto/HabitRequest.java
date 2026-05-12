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

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Habit name is required")
    private String name;

    @NotNull(message = "Habit type is required")
    private HabitType type;

    @NotNull(message = "Frequency is required")
    private FrequencyType frequency;

    @NotBlank(message = "Color is required")
    private String color;

    private String description;


}
