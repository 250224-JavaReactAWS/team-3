package com.revature.services;

import com.revature.exceptions.custom.user.*;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.repos.UserDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  private final UserDAO userDAO;

  @Autowired
  public UserService(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  public Optional<User> Register(User newUser) {
    Optional<User> user = userDAO.findUserByEmail(newUser.getEmail());

    if (user.isPresent()) {
      throw new EmailAlreadyRegisteredException("There is an account with that email address.");
    }

    if (!newUser.getEmail().matches(EMAIL_REGEX)) {
      throw new InvalidEmailFormatException("Your email is invalid. An example of valid email is: exampleEmail1@domain.com");
    }

    if (!newUser.getPassword().matches(PASSWORD_REGEX)) {
      throw new InvalidPasswordException("Invalid Password. Must be at least 8 characters long and need to contain one uppercase letter (A-Z)" +
              "one lowercase letter (a-z), one number (0-9), and one special character(@$!%*?&)");
    }

    return Optional.of(userDAO.save(newUser));
  }

  public Optional<User> login(User userCredentials) {
    Optional<User> user = userDAO.findUserByEmail(userCredentials.getEmail());
    User userToLogin;

    if (user.isEmpty()) {
      throw new WrongEmailException("There is no account with that email address.");
    } else {
      userToLogin = user.get();
    }

    if (!userToLogin.getPassword().equals(userCredentials.getPassword())) {
      throw new WrongPasswordException("Incorrect Password");
    }

    return Optional.of(userToLogin);
  }

  //Method to verify if a user is already logged in
  public void validateUserIsAuthenticated (HttpSession session){
      if (session.getAttribute("userId") == null){
        throw new UnauthenticatedException("You must be logged in to access this page");
      }
  }

  //Method to verify if the logged user is an owner
  public void validateUserIsOwner (HttpSession session){
      if (session.getAttribute("role") != UserRole.OWNER){
        throw new ForbiddenActionException("You cannot access to this page");
      }
  }



}
