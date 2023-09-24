package crqlar.thesis.service.user;

import crqlar.thesis.dto.LoginCredentialsDTO;
import crqlar.thesis.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    void login(LoginCredentialsDTO loginCredentialsDTO);
}
