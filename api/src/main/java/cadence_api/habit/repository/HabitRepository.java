package cadence_api.habit.repository;

import cadence_api.habit.dto.HabitResponse;
import cadence_api.habit.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    public List<Habit> findByUserId(Long userId);

}
