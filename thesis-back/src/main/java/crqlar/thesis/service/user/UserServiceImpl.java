package crqlar.thesis.service.user;

import crqlar.thesis.dto.LoginCredentialsDTO;
import crqlar.thesis.dto.UserDTO;
import crqlar.thesis.exception.BadCredentialsException;
import crqlar.thesis.exception.UserNotFoundException;
import crqlar.thesis.model.User;
import crqlar.thesis.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getUsers() {
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user: userRepository.findAll()){
            usersDTO.add(new UserDTO(user));
        }
        return usersDTO;
    }

    @Override
    public void login(LoginCredentialsDTO loginCredentialsDTO) {
        String email = loginCredentialsDTO.getEmail();
        User user = userRepository.findByUsername(email).orElseThrow(UserNotFoundException::new);
        if(!user.getPassword().equals(loginCredentialsDTO.getPassword())){
            throw new BadCredentialsException();
        }
    }
}
