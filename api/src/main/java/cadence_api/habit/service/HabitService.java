package cadence_api.habit.service;

import cadence_api.habit.dto.HabitRequest;
import cadence_api.habit.dto.HabitResponse;


import java.util.List;

public interface HabitService {

    HabitResponse createHabit(HabitRequest habitRequest);
    List<HabitResponse> getHabitsByUser(Long userId);

    HabitResponse updateHabit(Long habitId, HabitRequest habitRequest);
    void deleteHabit(Long habitId);
}
