package cadence_api.user.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String timezone;
}
