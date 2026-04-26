package cadence_api.user.service.Impl;

import cadence_api.user.dto.UserRequest;
import cadence_api.user.dto.UserResponse;
import cadence_api.user.model.User;
import cadence_api.user.repository.UserRepository;
import cadence_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .timezone(userRequest.getTimezone())
                .build();

        User saved =  userRepository.save(user);
        return mapToUserResponse(saved);
    }


    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
