package cadence_api.habit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CalendarResponse {

    private Long habitId;

    private String habitName;

    private String color;
    private Integer year;
    private Integer month;
    private List<LocalDate> loggedDates;
}
