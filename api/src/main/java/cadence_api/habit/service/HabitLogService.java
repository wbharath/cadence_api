package cadence_api.habit.service;

import cadence_api.habit.dto.CalendarResponse;
import cadence_api.habit.dto.LogRequest;
import cadence_api.habit.dto.LogResponse;

public interface HabitLogService {

    LogResponse logHabit(LogRequest logRequest);
    void undoLog(Long logId);
    CalendarResponse getCalendar(Long habitId, int year, int month);

}
