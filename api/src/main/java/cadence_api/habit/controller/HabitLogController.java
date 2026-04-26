package cadence_api.habit.controller;

import cadence_api.habit.dto.CalendarResponse;
import cadence_api.habit.dto.LogRequest;
import cadence_api.habit.dto.LogResponse;
import cadence_api.habit.service.HabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habits/logs")
@RequiredArgsConstructor
public class HabitLogController {

    private final HabitLogService habitLogService;

    @PostMapping
    public ResponseEntity<LogResponse> logHabit(@RequestBody LogRequest logRequest) {
        LogResponse logResponse = habitLogService.logHabit(logRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(logResponse);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long logId) {
        habitLogService.undoLog(logId);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{habitId}/calendar")
    public ResponseEntity<CalendarResponse> getCalendar(@PathVariable Long habitId, @RequestParam int year, @RequestParam int month) {
        CalendarResponse calendarResponse = habitLogService.getCalendar(habitId, year, month);
        return ResponseEntity.ok(calendarResponse);
    }

}