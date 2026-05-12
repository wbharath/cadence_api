package cadence_api;

import cadence_api.common.exception.UserNotFoundException;
import cadence_api.habit.dto.HabitRequest;
import cadence_api.habit.dto.HabitResponse;
import cadence_api.habit.model.Habit;
import cadence_api.habit.repository.HabitRepository;
import cadence_api.habit.service.impl.HabitServiceImpl;
import cadence_api.user.model.User;
import cadence_api.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HabitServiceImpl habitService;


    @Test
    void createHabit_Success(){
        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setUserId(1L);
        habitRequest.setName("Morning meditation");
        habitRequest.setColor("#6366f1");

        User mockUser = User.builder()
                .id(1L)
                .name("John")
                .email("john@test.com")
                .build();

        Habit mockSavedHabit = Habit.builder()
                .id(1L)
                .name("morning meditation")
                .color("#6366f1")
                .user(mockUser)
                .isActive(true)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(habitRepository.save(ArgumentMatchers.any(Habit.class))).thenReturn(mockSavedHabit);

        HabitResponse habitResponse = habitService.createHabit(habitRequest);

        assertNotNull(habitResponse);
        assertEquals("morning meditation", habitResponse.getName());
        assertEquals(1L, habitResponse.getId());
        assertTrue(habitResponse.getIsActive());
        assertEquals("#6366f1", habitResponse.getColor());

        verify(userRepository, times(1)).findById(1L);
        verify(habitRepository, times(1)).save(ArgumentMatchers.any(Habit.class));

    }
    @Test
    void createHabit_ThrowsUserNotFoundException_WhenUserNotFound() {


        HabitRequest request = new HabitRequest();
        request.setUserId(99L);
        request.setName("Morning Meditation");

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());


        assertThrows(UserNotFoundException.class, () -> {
            habitService.createHabit(request);
        });


        verify(habitRepository, never()).save(ArgumentMatchers.any(Habit.class));
    }

    @Test
    void getHabitsByUser_ReturnsCorrectList() {


        User mockUser = User.builder().id(1L).build();

        Habit habit1 = Habit.builder()
                .id(1L)
                .name("Meditation")
                .user(mockUser)
                .isActive(true)
                .build();

        Habit habit2 = Habit.builder()
                .id(2L)
                .name("Running")
                .user(mockUser)
                .isActive(true)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(mockUser));

        when(habitRepository.findByUserId(1L))
                .thenReturn(List.of(habit1, habit2));


        List<HabitResponse> responses = habitService.getHabitsByUser(1L);


        assertEquals(2, responses.size());
        assertEquals("Meditation", responses.get(0).getName());
        assertEquals("Running", responses.get(1).getName());
    }

    @Test
    void deleteHabit_SetsActiveToFalse() {


        User mockUser = User.builder().id(1L).build();

        Habit habit = Habit.builder()
                .id(1L)
                .name("Meditation")
                .user(mockUser)
                .isActive(true)
                .build();

        when(habitRepository.findById(1L))
                .thenReturn(Optional.of(habit));
        when(habitRepository.save(ArgumentMatchers.any(Habit.class)))
                .thenReturn(habit);


        habitService.deleteHabit(1L);


        assertFalse(habit.isActive());
        verify(habitRepository, times(1)).save(habit);
    }
}
