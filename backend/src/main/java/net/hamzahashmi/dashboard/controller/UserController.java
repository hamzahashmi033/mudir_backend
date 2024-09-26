package net.hamzahashmi.dashboard.controller;


import net.hamzahashmi.dashboard.entity.User;
import net.hamzahashmi.dashboard.service.ProjectService;
import net.hamzahashmi.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.saveUser(user);
        String successMessage = "User created successfully";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @GetMapping("/find-user/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable String email){
        try{
            User user = userService.findUserByEmail(email);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
