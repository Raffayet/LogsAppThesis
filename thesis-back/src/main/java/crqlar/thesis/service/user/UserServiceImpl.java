package crqlar.thesis.service.user;

import crqlar.thesis.dto.UserDTO;
import crqlar.thesis.model.User;
import crqlar.thesis.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
