package cadence_api.habit.service;

import cadence_api.habit.dto.HabitRequest;
import cadence_api.habit.dto.HabitResponse;
import cadence_api.habit.model.Habit;
import cadence_api.habit.repository.HabitRepository;
import cadence_api.user.model.User;
import cadence_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @Override
    public HabitResponse createHabit(HabitRequest habitRequest) {

        User user = userRepository.findById(habitRequest.getUserId()).orElseThrow(()->new RuntimeException("User not found"));

        Habit habit = Habit.builder()
                .name(habitRequest.getName())
                .description(habitRequest.getDescription())
                .user(user)
                .type(habitRequest.getType())
                .frequency(habitRequest.getFrequency())
                .color(habitRequest.getColor())
                .isActive(true)
                .build();

        Habit saved = habitRepository.save(habit);
        return mapToResponse(saved);

    }

    @Override
    public List<HabitResponse> getHabitsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        return habitRepository.findByUserId(user.getId()).stream().map(habit -> mapToResponse(habit)).collect(Collectors.toList());

    }

    @Override
    public HabitResponse updateHabit(Long habitId, HabitRequest habitRequest) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(()->new RuntimeException("Habit not found"));
        habit.setName(habitRequest.getName());
        habit.setDescription(habitRequest.getDescription());
        habit.setType(habitRequest.getType());
        habit.setFrequency(habitRequest.getFrequency());
        habit.setColor(habitRequest.getColor());

        Habit saved = habitRepository.save(habit);
        return mapToResponse(saved);
    }

    @Override
    public void deleteHabit(Long habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(()->new RuntimeException("Habit not found"));
        habit.setActive(false);
        habitRepository.save(habit);

    }

    private HabitResponse mapToResponse(Habit habit) {
        return HabitResponse.builder()
                .id(habit.getId())
                .name(habit.getName())
                .color(habit.getColor())
                .isActive(habit.isActive())
                .description(habit.getDescription())
                .type(habit.getType())
                .frequency(habit.getFrequency())
                .userId(habit.getUser().getId())
                .createdAt(habit.getCreatedAt())
                .updatedAt(habit.getUpdatedAt())
                .build();
    }
}
