package cadence_api.user.service;

import cadence_api.user.dto.UserRequest;
import cadence_api.user.dto.UserResponse;
import cadence_api.user.model.User;


public interface UserService {
    UserResponse createUser(UserRequest userRequest);
}
