package crqlar.thesis.controller;

import crqlar.thesis.dto.LoginCredentialsDTO;
import crqlar.thesis.dto.UserDTO;
import crqlar.thesis.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@RequestBody LoginCredentialsDTO loginCredentialsDTO){
        try{
            userService.login(loginCredentialsDTO);
            return ResponseEntity.ok("Success");
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "get-users")
    public ResponseEntity<?> getUsers(){
        try{
            List<UserDTO> usersDTO = userService.getUsers();
            return ResponseEntity.ok(usersDTO);
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
