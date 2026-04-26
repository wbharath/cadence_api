package cadence_api.habit.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LogResponse {


    private Long userId;


    private Long habitId;

    private String note;

    private LocalDate loggedDate;

    private Long id;

    private LocalDateTime createdAt;

}
