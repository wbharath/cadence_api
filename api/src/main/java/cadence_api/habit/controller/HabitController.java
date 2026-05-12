package cadence_api.habit.controller;


import cadence_api.common.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<HabitResponse>> createHabit(@RequestBody @Valid HabitRequest habitRequest) {
        HabitResponse habitResponse = habitService.createHabit(habitRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Habit created successfully", habitResponse));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<HabitResponse>>> getAllHabits(@PathVariable Long userId) {
        List<HabitResponse> habitResponse = habitService.getHabitsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success("Habit fetched successfully",habitResponse));
    }

    @PatchMapping("/{habitId}")
    public ResponseEntity<ApiResponse<HabitResponse>> updateHabit(@PathVariable Long habitId, @RequestBody @Valid HabitRequest habitRequest) {
        HabitResponse habitResponse = habitService.updateHabit(habitId, habitRequest);
        return ResponseEntity.ok(ApiResponse.success("Habit updated successfully",habitResponse));
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<ApiResponse<Void>> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return ResponseEntity
                .ok(ApiResponse.success("Habit deleted successfully", null));
    }
}