package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @PostMapping("register")
  public ResponseEntity<User> registerHandler(@RequestBody User user){
    Optional<User> userToBeRegister = userService.Register(user);

    userToBeRegister.ifPresent(value -> logger.info("A new user was created with the id: {}", value.getUserId()));

    return userToBeRegister.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                           .orElseGet(() -> ResponseEntity.badRequest().build() );
  }

  @PostMapping("login")
  public User loginHandler(@RequestBody User userCredentials, HttpSession session){
    Optional<User> userLogged = userService.login(userCredentials);

    if(userLogged.isPresent()) {
      session.setAttribute("userId", userLogged.get().getUserId());
      session.setAttribute("role", userLogged.get().getRole());

      return userLogged.get();
    }
    return null;
  }
}

