package com.revature.services;

import com.revature.exceptions.custom.hotel.*;
import com.revature.exceptions.custom.user.OwnerDoesNotExistsException;
import com.revature.exceptions.custom.user.UnauthenticatedException;
import com.revature.models.*;
import com.revature.repos.HotelDAO;
import com.revature.repos.UserDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith( MockitoExtension.class )
public class HotelServiceTests {
  @Mock
  private HotelDAO mockedHotelDAO;
  @Mock
  private UserDAO mockedUserDAO;

  @InjectMocks
  private HotelService hotelService;
  // getHotelById()
  @Test public void givenHotelIdThatDoesNotExist_whenGetHotelById_thenThrowHotelNotFoundException () {
    // Arrange
    int hotelId = 1;
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.empty());

    // Assert
    assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(hotelId));
  }

  @Test
  public void givenHotelIdThatDoesExist_whenGetHotelByyId_thenReturnHotel () {
    // Arrange
    int hotelId = 1;
    Hotel hotel = new Hotel(hotelId, "Test", "Test", "55-3322-7788",
                            "Test", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                            new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>());
    when(mockedHotelDAO.findById(any())).thenReturn(Optional.of(hotel));

    // Act
    Hotel obtainedHotel = hotelService.getHotelById(hotelId);

    // Assert
    assertEquals(hotel, obtainedHotel);
  }

  // getAllHotels()
  @Test public void givenZeroHotels_whenGetAllHotels_thenReturnListHotelsEmpty () {
    // Arrange
    when(mockedHotelDAO.findAll()).thenReturn(new ArrayList<Hotel>());

    // Act
    List<Hotel> hotels = hotelService.getAllHotels();

    // Assert
    assertEquals(0, hotels.size());
  }

  @Test public void givenHotels_whenGetAllHotels_thenReturnListHotels () {
    // Arrange
    List<Hotel> hotels = new ArrayList<>(List.of(
            new Hotel(1, "Test1", "Test Address1", "55-5456-5342",
                      "Test Description1", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(2, "Test2", "Test Address2", "55-9709-3182",
                      "Test Description2", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(3, "Test3", "Test Address3", "55-5493-3722",
                      "Test Description3", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(4, "Test4", "Test Address4", "55-'3709-8212",
                      "Test Description4", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>())
    ));
    when(mockedHotelDAO.findAll()).thenReturn(hotels);

    // Act
    List<Hotel> hotelsObtained = hotelService.getAllHotels();

    // Assert
    assertEquals(hotels.size(), hotelsObtained.size());
  }

  // getAllHotelsByUserId()
  @Test public void givenUserIdThatDoesNotExist_whenGetAllHotelsByUserId_thenThrowsHotelNotFoundException () {
    // Arrange
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.CUSTOMER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));


    // Assert
    assertThrows(UnauthenticatedException.class, () -> hotelService.getAllHotelsByUserId(userId));
  }

  @Test public void givenThatHotelDoesExists_whenGetAllHotelsByUserId_thenReturnListOfRooms () {
    // Arrange
    int userId = 1;

    User user = new User(userId, "Test", "Test", "test@test.com", "Passwor1*",
                         UserRole.OWNER, new ArrayList<Reservation>(), new ArrayList<Review>(),
                         new ArrayList<Hotel>(), new ArrayList<Hotel>());


    List<Hotel> hotels = new ArrayList<>(List.of(
            // new Hotel(1, "Name1", "Address1", "55-6434-6542", "Description1", user, new ArrayList<>())
            new Hotel(1, "Name1", "Address1", "55-5643-3546",
                      "Description1", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(2, "Name2", "Address2", "55-4323-5123",
                      "Description2", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(3, "Name3", "Address3", "55-7587-6723",
                      "Description3", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(4, "Name4", "Address4", "55-9573-4920",
                      "Description4", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(5, "Name5", "Address5", "55-9083-5729",
                      "Description5", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(6, "Name6", "Address6", "55-5428-4920",
                      "Description6", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>())
    ));

    List<Hotel> hotelsToGet = new ArrayList<>(List.of(
            // new Hotel(1, "Name1", "Address1", "55-6434-6542", "Description1", user, new ArrayList<>())
            new Hotel(1, "Name1", "Address1", "55-5643-3546",
                      "Description1", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(3, "Name3", "Address3", "55-7587-6723",
                      "Description3", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(4, "Name4", "Address4", "55-9573-4920",
                      "Description4", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>()),
            new Hotel(6, "Name6", "Address6", "55-5428-4920",
                      "Description6", user, new ArrayList<Room>(), new ArrayList<Image>(),
                      new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>())
    ));

    user.setHotels(hotelsToGet);

    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));
    when(mockedHotelDAO.getAllByOwnerId(userId)).thenReturn(hotelsToGet);

    // Act
    List<Hotel> hotelsObtained = hotelService.getAllHotelsByUserId(userId);

    // Assert
    assertEquals(hotelsToGet.size(), hotelsObtained.size());
    assertEquals(hotelsToGet, hotelsObtained);
    verify(mockedHotelDAO).getAllByOwnerId(userId);
  }

  // addNewHotel()
  @Test public void givenInvalidUser_whenAddNewHotel_thenThrowOwnerDoesNotExistsException () {
    // Arrange
    int hotelId = 1;
    User invalidUser = new User();
    Hotel newInvalidHotel = new Hotel(hotelId,
                                    "test",
                                    "test address",
                                    "55-1234-6789",
                                    "test description",
                                    invalidUser,
                                    new ArrayList<Room>(),
                                    new ArrayList<Image>(),
                                    new ArrayList<Reservation>(),
                                    new ArrayList<Review>(),
                                    new ArrayList<User>());
    when(mockedUserDAO.findById(anyInt())).thenReturn(Optional.empty());

    // Act & Assert
    OwnerDoesNotExistsException exception = assertThrows(OwnerDoesNotExistsException.class, () -> {
      hotelService.addNewHotel(newInvalidHotel, hotelId);
    });

    assertEquals("The owner does not exists", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelName_whenAddNewHotel_thenThrowInvalidHotelNameException () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                                "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                                new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel newHotelWithInvalidName = new Hotel(hotelId,
                                      "test",
                                      "test address",
                                      "5512346789",
                                      "test description of the hotel",
                                      user,
                                      new ArrayList<Room>(),
                                      new ArrayList<Image>(),
                                      new ArrayList<Reservation>(),
                                      new ArrayList<Review>(),
                                      new ArrayList<User>());

    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));

    // Act & Assert
    InvalidHotelNameException exception = assertThrows(InvalidHotelNameException.class, () -> {
      hotelService.addNewHotel(newHotelWithInvalidName, userId);
    });

    assertEquals("Hotel name must have at least 8 characters", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelAddress_whenAddNewHotel_thenThrowInvalidHotelAddressException () {
    // Arrange
    int hotelId = 1;    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel newHotelWithInvalidAddress = new Hotel(hotelId,
                                     "test name",
                                     "test",
                                     "5512346789",
                                     "test description for the hotel",
                                     user,
                                     new ArrayList<Room>(),
                                     new ArrayList<Image>(),
                                     new ArrayList<Reservation>(),
                                     new ArrayList<Review>(),
                                     new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));

    // Act & Assert
    InvalidHotelAddressException exception = assertThrows(InvalidHotelAddressException.class, () -> {
      hotelService.addNewHotel(newHotelWithInvalidAddress, hotelId);
    });

    assertEquals("Hotel address must have at least 20 characters", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelPhoneNumber_whenAddNewHotel_thenThrowInvalidHotelPhoneNumberException () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel newRoomWithInvalidPhoneNumber = new Hotel(hotelId,
                                     "name of the hotel",
                                     "Av. Address 123, Street street 123, 34214",
                                     "55-1234-6789",
                                     "description of the hotel",
                                     user,
                                     new ArrayList<Room>(),
                                     new ArrayList<Image>(),
                                     new ArrayList<Reservation>(),
                                     new ArrayList<Review>(),
                                     new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));

    // Act & Assert
    InvalidHotelPhoneNumberException exception = assertThrows(InvalidHotelPhoneNumberException.class, () -> {
      hotelService.addNewHotel(newRoomWithInvalidPhoneNumber, hotelId);
    });

    assertEquals("Hotel phone number is not valid", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelDescription_whenAddNewHotel_thenThrowInvalidHotelDescriptionException () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel newRoomWithInvalidDescription = new Hotel(hotelId,
                                                    "name of the hotel",
                                                    "Av. Address 123, Street street 123, 34214",
                                                    "5512346789",
                                                    "description",
                                                    user,
                                                    new ArrayList<Room>(),
                                                    new ArrayList<Image>(),
                                                    new ArrayList<Reservation>(),
                                                    new ArrayList<Review>(),
                                                    new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));

    // Act & Assert
    InvalidHotelDescriptionException exception = assertThrows(InvalidHotelDescriptionException.class, () -> {
      hotelService.addNewHotel(newRoomWithInvalidDescription, hotelId);
    });

    assertEquals("Hotel description must have at least 20 characters", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test
  public void givenValidUserAndHotelData_whenAddNewHotel_thenShouldSaveAndReturned () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel newHotel = new Hotel(hotelId,
                               "name of the hotel",
                               "Av. Address 123, Street street 123, 34214",
                               "5512346789",
                               "description of the hotel that give us info",
                                null,
                                new ArrayList<Room>(),
                                new ArrayList<Image>(),
                                new ArrayList<Reservation>(),
                                new ArrayList<Review>(),
                                new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));
    when(mockedHotelDAO.save(any(Hotel.class))).thenAnswer(invocation -> invocation.getArgument(0) );

    // Act
    Optional<Hotel> saveHotel = hotelService.addNewHotel(newHotel, userId);

    // Assert
    assertTrue(saveHotel.isPresent());
    assertEquals(newHotel, saveHotel.get());
    verify(mockedUserDAO, times(2)).findById(userId);
    verify(mockedHotelDAO).save(any(Hotel.class));
  }

  // updateHotelById()
  @Test
  public void givenNonExistentHotel_whenUpdateHotelById_thenThrowHotelNotFoundException() {
    // Arrange
    Hotel updateHotel = new Hotel("test", "test address", "55-1234-6789",
                                  "test description", new User(), new ArrayList<Room>(), new ArrayList<Image>(),
                                  new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>());
    when(mockedHotelDAO.findById(anyInt())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(HotelNotFoundException.class, () -> {hotelService.updateHotelById(1, updateHotel, 1);});
    verify(mockedHotelDAO).findById(anyInt());
  }

  @Test public void givenInvalidUser_whenUpdateHotelById_thenThrowOwnerDoesNotExistsException () {
    // Arrange
    int hotelId = 1;
    final int userId = 1;
    Hotel existingHotel = new Hotel(hotelId, "name of the hotel", "Av. Address 123, Street street 123, 34214",
                  "5512346789", "description of the hotel that give us info", null,
                                    new ArrayList<Room>(), new ArrayList<Image>(), new ArrayList<Reservation>(),
                                    new ArrayList<Review>(), new ArrayList<User>());
    Hotel updatedHotel = new Hotel(hotelId,
                                   "name of the hotel",
                                   "Av. Address 123, Street street 123, 34214",
                                   "5512346789",
                                   "description of the hotel that give us info",
                                   null,
                                   new ArrayList<Room>(),
                                   new ArrayList<Image>(),
                                   new ArrayList<Reservation>(),
                                   new ArrayList<Review>(),
                                   new ArrayList<User>());

    when(mockedUserDAO.findById(userId)).thenReturn(Optional.<User>empty());
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    OwnerDoesNotExistsException exception = assertThrows(OwnerDoesNotExistsException.class, () -> {
      hotelService.updateHotelById(hotelId, updatedHotel, userId);
    });

    assertEquals("The owner does not exists", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelName_whenUpdateHotelById_thenThrowInvalidHotelNameException () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    Hotel existingHotel = new Hotel(hotelId, "name of the hotel", "Av. Address 123, Street street 123, 34214",
                                    "5512346789", "description of the hotel that give us info", null,
                                    new ArrayList<Room>(), new ArrayList<Image>(), new ArrayList<Reservation>(),
                                    new ArrayList<Review>(), new ArrayList<User>());
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel updateHotelWithInvalidName = new Hotel("name",
                                              "Av. Address 123, Street street 123, 34214",
                                              "5512346789",
                                              "description of the hotel that give us info",
                                              null,
                                              new ArrayList<Room>(),
                                              new ArrayList<Image>(),
                                              new ArrayList<Reservation>(),
                                              new ArrayList<Review>(),
                                              new ArrayList<User>());

    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    InvalidHotelNameException exception = assertThrows(InvalidHotelNameException.class, () -> {
      hotelService.updateHotelById(hotelId, updateHotelWithInvalidName, userId);
    });

    assertEquals("Hotel name must have at least 8 characters", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelAddress_whenUpdateHotelById_thenThrowInvalidHotelAddressException () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel existingHotel = new Hotel(hotelId, "name of the hotel", "Av. Address 123, Street street 123, 34214",
                                    "5512346789", "description of the hotel that give us info", null,
                                    new ArrayList<Room>(), new ArrayList<Image>(), new ArrayList<Reservation>(),
                                    new ArrayList<Review>(), new ArrayList<User>());
    Hotel updateHotelWithInvalidName = new Hotel("name of the hotel",
                                                 "avenue",
                                                 "5512346789",
                                                 "description of the hotel that give us info",
                                                 null,
                                                 new ArrayList<Room>(),
                                                 new ArrayList<Image>(),
                                                 new ArrayList<Reservation>(),
                                                 new ArrayList<Review>(),
                                                 new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    InvalidHotelAddressException exception = assertThrows(InvalidHotelAddressException.class, () -> {
      hotelService.updateHotelById(hotelId, updateHotelWithInvalidName, userId);
    });

    assertEquals("Hotel address must have at least 20 characters", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelPhoneNumber_whenUpdateHotelById_thenThrowInvalidHotelPhoneNumberException () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel existingHotel = new Hotel(hotelId, "name of the hotel", "Av. Address 123, Street street 123, 34214",
                                    "5512346789", "description of the hotel that give us info", null,
                                    new ArrayList<Room>(), new ArrayList<Image>(), new ArrayList<Reservation>(),
                                    new ArrayList<Review>(), new ArrayList<User>());
    Hotel updateHotelWithInvalidPhoneNumber = new Hotel("name of the hotel",
                                                 "Av. Address 123, Street street 123, 34214",
                                                 "7792",
                                                 "description of the hotel that give us info",
                                                 null,
                                                 new ArrayList<Room>(),
                                                 new ArrayList<Image>(),
                                                 new ArrayList<Reservation>(),
                                                 new ArrayList<Review>(),
                                                 new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    InvalidHotelPhoneNumberException exception = assertThrows(InvalidHotelPhoneNumberException.class, () -> {
      hotelService.updateHotelById(hotelId, updateHotelWithInvalidPhoneNumber, userId);
    });

    assertEquals("Hotel phone number is not valid", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test public void givenInvalidHotelDescription_whenUpdateHotelById_thenThrowInvalidHotelDescriptionException () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel existingHotel = new Hotel(hotelId, "name of the hotel", "Av. Address 123, Street street 123, 34214",
                                    "5512346789", "description of the hotel that give us moreinfo", null,
                                    new ArrayList<Room>(), new ArrayList<Image>(), new ArrayList<Reservation>(),
                                    new ArrayList<Review>(), new ArrayList<User>());
    final Hotel updateHotelWithInvalidDescription = new Hotel("name of the hotel", "Av. Address 123, Street street 123, 34214",
                                                              "5512346789",
                                                              "description",
                                                              null, new ArrayList<Room>(), new ArrayList<Image>(),
                                                              new ArrayList<Reservation>(), new ArrayList<Review>(), new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    InvalidHotelDescriptionException exception = assertThrows(InvalidHotelDescriptionException.class, () -> {
      hotelService.updateHotelById(hotelId, updateHotelWithInvalidDescription, userId);
    });

    assertEquals("Hotel description must have at least 20 characters", exception.getMessage());
    verify(mockedUserDAO).findById(anyInt());
    verify(mockedHotelDAO, never()).save(any());
  }

  @Test
  public void givenValidUserAndHotelData_whenUpdateHotelById_thenShouldSaveAndReturned () {
    // Arrange
    int hotelId = 1;
    int userId = 1;
    User user = new User(userId, "Test", "Test", "test@test.com",
                         "Password1*", UserRole.OWNER, new ArrayList<Reservation>(),
                         new ArrayList<Review>(), new ArrayList<Hotel>(), new ArrayList<Hotel>());
    Hotel existingHotel = new Hotel(hotelId, "name of the hotel", "Av. Address 123, Street street 123, 34214",
                                    "5512346789", "description of the hotel that give us info", user,
                                    new ArrayList<Room>(), new ArrayList<Image>(), new ArrayList<Reservation>(),
                                    new ArrayList<Review>(), new ArrayList<User>());
    Hotel updateHotelWithValidValues = new Hotel("Hote Loyal Inn",
                                                 "Av. Kafka 123, Street Rusia 567, 57462",
                                                 "5675643232",
                                                 "Enjoy the best luxury experience in the city",
                                                 user,
                                                 new ArrayList<Room>(),
                                                 new ArrayList<Image>(),
                                                 new ArrayList<Reservation>(),
                                                 new ArrayList<Review>(),
                                                 new ArrayList<User>());
    when(mockedUserDAO.findById(userId)).thenReturn(Optional.of(user));
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));
    when(mockedHotelDAO.save(any(Hotel.class))).thenAnswer(invocation -> invocation.getArgument(0) );

    // Act
    Optional<Hotel> saveHotel = hotelService.updateHotelById(hotelId, updateHotelWithValidValues, userId);

    // Assert
    assertTrue(saveHotel.isPresent());
    assertEquals(updateHotelWithValidValues.getName(), saveHotel.get().getName());
    verify(mockedUserDAO).findById(userId);
    verify(mockedHotelDAO).save(any(Hotel.class));
  }

  // deleteHotelById()
}
