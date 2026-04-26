package cadence_api.habit.controller;


import cadence_api.habit.dto.HabitRequest;
import cadence_api.habit.dto.HabitResponse;
import cadence_api.habit.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;
    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@RequestBody @Valid HabitRequest habitRequest) {
        HabitResponse habitResponse = habitService.createHabit(habitRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<HabitResponse>> getAllHabits(@PathVariable Long userId) {
        List<HabitResponse> habitResponse = habitService.getHabitsByUser(userId);
        return ResponseEntity.ok(habitResponse);
    }

    @PatchMapping("/{habitId}")
    public ResponseEntity<HabitResponse> updateHabit(@PathVariable Long habitId, @RequestBody @Valid HabitRequest habitRequest) {
        HabitResponse habitResponse = habitService.updateHabit(habitId, habitRequest);
        return ResponseEntity.ok(habitResponse);
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return  ResponseEntity.noContent().build();
    }
}