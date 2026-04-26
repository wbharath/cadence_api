package cadence_api.habit.repository;

import cadence_api.habit.model.Habit;
import cadence_api.habit.model.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    List<HabitLog> findByHabitAndLoggedDateBetween(Habit habit, LocalDate start, LocalDate end);
}
