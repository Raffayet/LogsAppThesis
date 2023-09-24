package crqlar.thesis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginCredentialsDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
