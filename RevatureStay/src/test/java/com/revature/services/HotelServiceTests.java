package com.revature.services;

import com.revature.exceptions.custom.hotel.HotelNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
