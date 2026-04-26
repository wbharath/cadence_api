package cadence_api.habit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long habitId;

    private String note;

    @NotNull
    private LocalDate loggedDate;


}
