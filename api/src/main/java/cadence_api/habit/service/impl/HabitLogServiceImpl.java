package cadence_api.habit.service.impl;

import cadence_api.common.exception.DuplicateLogException;
import cadence_api.common.exception.HabitNotFoundException;
import cadence_api.common.exception.LogNotFoundException;
import cadence_api.common.exception.UserNotFoundException;
import cadence_api.habit.dto.CalendarResponse;
import cadence_api.habit.dto.LogRequest;
import cadence_api.habit.dto.LogResponse;
import cadence_api.habit.model.Habit;
import cadence_api.habit.model.HabitLog;
import cadence_api.habit.repository.HabitLogRepository;
import cadence_api.habit.repository.HabitRepository;
import cadence_api.habit.service.HabitLogService;
import cadence_api.user.model.User;
import cadence_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class HabitLogServiceImpl implements HabitLogService {
    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public LogResponse logHabit(LogRequest logRequest) {
        User user = userRepository.findById(logRequest.getUserId()).orElseThrow(()->new UserNotFoundException(logRequest.getUserId()));
        Habit habit = habitRepository.findById(logRequest.getHabitId()).orElseThrow(()->new HabitNotFoundException(logRequest.getHabitId()));

        boolean alreadyLogged = habitLogRepository.existsByHabitAndLoggedDate(habit,logRequest.getLoggedDate());

        if(alreadyLogged){
            throw new DuplicateLogException(
                    logRequest.getHabitId(),
                    logRequest.getLoggedDate().toString()
            );
        }

        HabitLog habitLog = HabitLog.builder()
                .habit(habit)
                .user(user)
                .loggedDate(logRequest.getLoggedDate())
                .note(logRequest.getNote())
                .build();

        HabitLog saved = habitLogRepository.save(habitLog);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void undoLog(Long logId) {
        HabitLog habitLog = habitLogRepository.findById(logId).orElseThrow(()->new LogNotFoundException(logId));
        habitLogRepository.delete(habitLog);
    }

    @Override
    @Transactional(readOnly = true)
    public CalendarResponse getCalendar(Long habitId, int year, int month) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(()->new HabitNotFoundException(habitId));
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        List<HabitLog> logs = habitLogRepository.findByHabitAndLoggedDateBetween(habit, start, end);

        List<LocalDate> loggedDates = logs.stream()
                .map(HabitLog::getLoggedDate)
                .collect(Collectors.toList());

        return CalendarResponse.builder()
                .habitId(habit.getId())
                .habitName(habit.getName())
                .color(habit.getColor())
                .year(year)
                .month(month)
                .loggedDates(loggedDates)

                .build();
    }

    private LogResponse mapToResponse(HabitLog habitLog) {
        return LogResponse.builder()
                .userId(habitLog.getUser().getId())
                .id(habitLog.getId())
                .habitId(habitLog.getHabit().getId())
                .loggedDate(habitLog.getLoggedDate())
                .note(habitLog.getNote())
                .createdAt(habitLog.getCreatedAt())
                .build();

    }
}
