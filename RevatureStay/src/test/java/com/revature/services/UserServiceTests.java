package com.revature.services;

import com.revature.exceptions.custom.user.EmailAlreadyRegisteredException;
import com.revature.exceptions.custom.user.InvalidEmailFormatException;
import com.revature.exceptions.custom.user.InvalidPasswordException;
import com.revature.models.*;
import com.revature.repos.UserDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	@Mock
	UserDAO mockedUserDAO;

	@InjectMocks
	UserService userService;


	// register()
	@Test
	public void givesUserWithEmailRegistered_whenRegister_thenThrowEmailAlreadyRegisteredException() {
		// Arrange
		String email = "johnDoe@mail.com";

		User newUser = new User();
		newUser.setFirstName("John");
		newUser.setLastName("Doe");
		newUser.setEmail("johnDoe@mail.com");
		newUser.setPassword("PaS$W0rd");
		newUser.setRole(UserRole.CUSTOMER);

		User userRegistered = new User(1, "first name", "last name",
																	 "johnDoe@mail.com", "Password1*", UserRole.CUSTOMER,
																	 new ArrayList<Reservation>(), new ArrayList<Review>(),
																	 new ArrayList<Hotel>(), new ArrayList<Hotel>());
		when(mockedUserDAO.findUserByEmail(email)).thenReturn(Optional.of(userRegistered));

		// Act & Assert
		assertThrows(EmailAlreadyRegisteredException.class, () -> userService.Register(newUser));
		verify(mockedUserDAO).findUserByEmail(email);
	}
	@Test
	public void givesUserWithInvalidEmail_whenRegister_thenThrowInvalidEmailFormatException() {		// Arrange
		String email = "josjdhjl@wwqo";

		User newUser = new User();
		newUser.setFirstName("John");
		newUser.setLastName("Doe");
		newUser.setEmail(email);
		newUser.setPassword("PaS$W0rd");
		newUser.setRole(UserRole.CUSTOMER);

		when(mockedUserDAO.findUserByEmail(email)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(InvalidEmailFormatException.class, () -> userService.Register(newUser));
		verify(mockedUserDAO).findUserByEmail(email);
	}

	@Test
	public void givesUserWithInvalidPassword_whenRegister_thenThrowInvalidPasswordException() {
		// Arrange
		String email = "johnDoe@mail.com";
		String password = "password*";

		User newUser = new User();
		newUser.setFirstName("John");
		newUser.setLastName("Doe");
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setRole(UserRole.CUSTOMER);

		when(mockedUserDAO.findUserByEmail(email)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(InvalidPasswordException.class, () -> userService.Register(newUser));
		verify(mockedUserDAO).findUserByEmail(email);
	}

	@Test
	public void givesValidEmailAndPassword_whenRegister_thenShouldSaveAndReturned() {
		// Arrange
		String email = "johnDoe@mail.com";
		String password = "Pa$sW0rD*";

		User newUser = new User();
		newUser.setFirstName("John");
		newUser.setLastName("Doe");
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setRole(UserRole.CUSTOMER);

		when(mockedUserDAO.findUserByEmail(email)).thenReturn(Optional.empty());
		when(mockedUserDAO.save(any(User.class))).thenAnswer( invocation -> invocation.getArgument(0) );

		// Act
		Optional<User> registeredUser = userService.Register(newUser);

		// Act & Assert
		assertTrue(registeredUser.isPresent());
		assertEquals(newUser, registeredUser.get());
		verify(mockedUserDAO).findUserByEmail(email);
		verify(mockedUserDAO).save(any(User.class));
	}

	/*
	// login()
	@Test
	public void givesUserUnregistered_whenLogin_thenThrowWrongEmailException() {
		// Arrange

		// Act

		// Assert
	}

	@Test
	public void givesIncorrectPassword_whenLogin_thenThrowWrongPasswordException() {
		// Arrange

		// Act

		// Assert
	}

	@Test
	public void givesCorrectCredentials_whenLogin_thenShouldBeReturned() {
		// Arrange

		// Act

		// Assert
	}

	@Test
	public void givesCorrectCredentials_whenLogin_thenShouldBeReturned() {
		// Arrange

		// Act

		// Assert
	}*/
}
