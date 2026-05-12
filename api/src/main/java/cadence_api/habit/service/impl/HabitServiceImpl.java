package cadence_api.habit.service.impl;

import cadence_api.common.exception.HabitNotFoundException;
import cadence_api.common.exception.UserNotFoundException;
import cadence_api.habit.dto.HabitRequest;
import cadence_api.habit.dto.HabitResponse;
import cadence_api.habit.model.Habit;
import cadence_api.habit.repository.HabitRepository;
import cadence_api.habit.service.HabitService;
import cadence_api.user.model.User;
import cadence_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @CacheEvict(value = "habits", key = "#habitRequest.userId")
    public HabitResponse createHabit(HabitRequest habitRequest) {

        User user = userRepository.findById(habitRequest.getUserId()).orElseThrow(()->new UserNotFoundException(habitRequest.getUserId()));

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
    @Transactional(readOnly=true)
    @Cacheable(value = "habits",key = "#userId")
    public List<HabitResponse> getHabitsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        return habitRepository.findByUserId(user.getId()).stream().map(habit -> mapToResponse(habit)).collect(Collectors.toList());

    }

    @Override
    @Transactional
    @CacheEvict(value = "habits", key = "#habitRequest.userId")
    public HabitResponse updateHabit(Long habitId, HabitRequest habitRequest) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(()->new HabitNotFoundException(habitId));
        habit.setName(habitRequest.getName());
        habit.setDescription(habitRequest.getDescription());
        habit.setType(habitRequest.getType());
        habit.setFrequency(habitRequest.getFrequency());
        habit.setColor(habitRequest.getColor());

        Habit saved = habitRepository.save(habit);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "habits", allEntries = true)
    public void deleteHabit(Long habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(()->new HabitNotFoundException((habitId)));
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
