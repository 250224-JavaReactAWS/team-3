package com.revature.services;

import com.revature.exceptions.custom.hotel.HotelNotFoundException;
import com.revature.exceptions.custom.room.RoomNotFoundException;
import com.revature.models.*;
import com.revature.repos.HotelDAO;
import com.revature.repos.RoomDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith( MockitoExtension.class)
public class RoomServiceTests {
  @Mock private RoomDAO mockedRoomDAO;
  @Mock private HotelDAO mockedHotelDAO;

  @InjectMocks private RoomService roomService;
  @InjectMocks private HotelService hotelService;

  // changeRoomAvailability()
  @Test public void givenRoomDoesNotExist_whenChangeRoomAvailability_thenThrowsRoomNotFoundException () {
    // Arrange
    int roomId = 1;
    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.empty());

    // Assert
    assertThrows(RoomNotFoundException.class, () -> roomService.changeRoomAvailability(roomId));
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenRoomExistsWithAvailabilityTrue_whenChangeRoomAvailability_thenAvailabilityToggledFalse () {
    // Arrange
    int roomId = 1;
    Room roomToUpdate = new Room(1, RoomType.SINGLE, 1, 1, 55.32, true, new Hotel());
    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(roomToUpdate));
    when(mockedRoomDAO.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    Optional<Room> updateRoomAvailability = roomService.changeRoomAvailability(1);

    // Assert
    assertTrue(updateRoomAvailability.isPresent());
    Room updateRoom = updateRoomAvailability.get();
    assertFalse(updateRoom.isAvailable());
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO).save(roomToUpdate);
  }

  @Test public void givenRoomExistsWithAvailabilityTrue_whenChangeRoomAvailability_thenAvailabilityToggledTrue () {
    // Arrange
    int roomId = 1;
    Room roomToUpdate = new Room(1, RoomType.SINGLE, 1, 1, 55.32, false, new Hotel());
    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(roomToUpdate));
    when(mockedRoomDAO.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    Optional<Room> updateRoomAvailability = roomService.changeRoomAvailability(1);

    // Assert
    assertTrue(updateRoomAvailability.isPresent());
    Room updateRoom = updateRoomAvailability.get();
    assertTrue(updateRoom.isAvailable());
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO).save(roomToUpdate);
  }

  // getRoomById()
  @Test public void givenThatRoomDoesNotExists_whenSearchById_thenThrowsRoomNotFoundException () {
    // Arrange
    int roomId = 1;
    when(mockedRoomDAO.findById(any())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RoomNotFoundException.class, () -> roomService.getRoomById(roomId));
  }

  @Test public void givenThatRoomDoesExists_whenSearchById_thenReturnRoom () {
    // Arrange
    int roomId = 1;
    Room roomToUpdate = new Room(1, RoomType.SINGLE, 1, 1, 55.32, true, new Hotel());
    when(mockedRoomDAO.findById(any())).thenReturn(Optional.of(roomToUpdate));

    // Act
    Room obtainedRoom = roomService.getRoomById(roomId);

    // Assert
    assertEquals(obtainedRoom, roomToUpdate);
  }

  // getAllAvailableRooms()
  @Test public void givenThatHotelDoesNotExists_whenGetAllAvailableRooms_thenThrowsHotelNotFoundException () {
    // Arrange
    int hotelId = 1;
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(HotelNotFoundException.class, () -> roomService.getAllAvailableRooms(hotelId));
  }

  @Test public void givenThatHotelDoesExists_whenGetAllAvailableRooms_thenReturnListOfAvailableRooms () {
    // Arrange
    int hotelId = 1;

    Hotel existHotel = new Hotel(hotelId,
                                 "test",
                                 "test test",
                                 "55-5555-5555",
                                 "test description",
                                 new User(),
                                 new ArrayList<Room>(),
                                 new ArrayList<Image>(),
                                 new ArrayList<Reservation>(),
                                 new ArrayList<Review>(),
                                 new ArrayList<User>());

    List<Room> rooms = new ArrayList<>(List.of(new Room(1, RoomType.SINGLE, 2, 2, 43.24, true, existHotel),
                                               new Room(2, RoomType.DOUBLE, 1, 1, 55.32, false, existHotel),
                                               new Room(3, RoomType.TRIPLE, 1, 2, 64.21, true, existHotel),
                                               new Room(4, RoomType.SUITE, 3, 2, 95.65, false, existHotel)));

    List<Room> availableRooms = new ArrayList<>(List.of(new Room(1, RoomType.SINGLE, 2, 2, 43.24, true, existHotel), new Room(3, RoomType.TRIPLE, 1, 2, 64.21, true, existHotel)));

    existHotel.setRooms(rooms);

    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existHotel));
    when(mockedRoomDAO.getAllAvailableRoomsByHotelId(hotelId)).thenReturn(availableRooms);

    // Act
    List<Room> availableRoomsObtained = roomService.getAllAvailableRooms(hotelId);

    // Assert
    assertEquals(availableRooms.size(), availableRoomsObtained.size());
    assertEquals(availableRooms, availableRoomsObtained);
    verify(mockedHotelDAO).findById(hotelId);
  }

  // getAllRoomsByHotelId()
  @Test public void givenThatHotelDoesNotExists_whenGetAllRoomsById_thenThrowsHotelNotFoundException () {
    // Arrange
    int hotelId = 1;
    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(HotelNotFoundException.class, () -> roomService.getAllRoomsByHotelId(hotelId));
  }

  @Test public void givenThatHotelDoesExists_whenGetAllRoomsById_thenReturnListOfRooms () {
    // Arrange
    int hotelId = 1;

    Hotel existHotel = new Hotel(hotelId,
                                 "test",
                                 "test test",
                                 "55-5555-5555",
                                 "test description",
                                 new User(),
                                 new ArrayList<Room>(),
                                 new ArrayList<Image>(),
                                 new ArrayList<Reservation>(),
                                 new ArrayList<Review>(),
                                 new ArrayList<User>());

    List<Room> rooms = new ArrayList<>(List.of(new Room(1, RoomType.SINGLE, 2, 2, 43.24, true, existHotel),
                                               new Room(2, RoomType.DOUBLE, 1, 1, 55.32, false, existHotel),
                                               new Room(3, RoomType.TRIPLE, 1, 2, 64.21, true, existHotel),
                                               new Room(4, RoomType.SUITE, 3, 2, 95.65, false, existHotel)));

    existHotel.setRooms(rooms);

    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existHotel));

    // Act
    List<Room> roomsObtained = roomService.getAllRoomsByHotelId(hotelId);

    // Assert
    assertEquals(rooms.size(), roomsObtained.size());
    assertEquals(rooms, roomsObtained);
    verify(mockedHotelDAO).findById(hotelId);
  }

  // addNewRoom()
  @Test public void givenInvalidTypeValue_whenAddNewRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int hotelId = 1;
    Hotel existingHotel = new Hotel(hotelId,
                                    "test",
                                    "test address",
                                    "55-1234-6789",
                                    "test description",
                                    new User(),
                                    new ArrayList<Room>(),
                                    new ArrayList<Image>(),
                                    new ArrayList<Reservation>(),
                                    new ArrayList<Review>(),
                                    new ArrayList<User>());
    Room newInvalidRoom = new Room(null, 2, 2, 64.43, true, existingHotel);

    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.addNewRoom(newInvalidRoom, hotelId);
    });

    assertEquals("Room type cannot be null", exception.getMessage());
    verify(mockedHotelDAO).findById(hotelId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenInvalidBedsValue_whenAddNewRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int hotelId = 1;
    Hotel existingHotel = new Hotel(hotelId,
                                    "test",
                                    "test address",
                                    "55-1234-6789",
                                    "test description",
                                    new User(),
                                    new ArrayList<Room>(),
                                    new ArrayList<Image>(),
                                    new ArrayList<Reservation>(),
                                    new ArrayList<Review>(),
                                    new ArrayList<User>());
    Room newInvalidRoom = new Room(RoomType.SINGLE, -2, 2, 64.43, true, existingHotel);

    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.addNewRoom(newInvalidRoom, hotelId);
    });

    assertEquals("There must be at least one bed", exception.getMessage());
    verify(mockedHotelDAO).findById(hotelId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenInvalidBathsValue_whenAddNewRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int hotelId = 1;
    Hotel existingHotel = new Hotel(hotelId,
                                    "test",
                                    "test address",
                                    "55-1234-6789",
                                    "test description",
                                    new User(),
                                    new ArrayList<Room>(),
                                    new ArrayList<Image>(),
                                    new ArrayList<Reservation>(),
                                    new ArrayList<Review>(),
                                    new ArrayList<User>());
    Room newInvalidRoom = new Room(RoomType.DOUBLE, 2, -2, 64.43, true, existingHotel);

    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.addNewRoom(newInvalidRoom, hotelId);
    });

    assertEquals("There must be at least one bath", exception.getMessage());
    verify(mockedHotelDAO).findById(hotelId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenInvalidPriceValue_whenAddNewRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int hotelId = 1;
    Hotel existingHotel = new Hotel(hotelId,
                                    "test",
                                    "test address",
                                    "55-1234-6789",
                                    "test description",
                                    new User(),
                                    new ArrayList<Room>(),
                                    new ArrayList<Image>(),
                                    new ArrayList<Reservation>(),
                                    new ArrayList<Review>(),
                                    new ArrayList<User>());
    // RoomType.TRIPLE, 3, 1, -55,.34, true, existingHotel
    Room newInvalidRoom = new Room(RoomType.TRIPLE, 3, 1, -55.34, true, existingHotel);

    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.addNewRoom(newInvalidRoom, hotelId);
    });

    assertEquals("Price must be positive", exception.getMessage());
    verify(mockedHotelDAO).findById(hotelId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenValidRoomAndExistingHotel_whenAddNewRoom_thenShouldSaveAndReturned () {
    // Arrange
    int hotelId = 1;
    Hotel existingHotel = new Hotel(hotelId,
                                    "test",
                                    "test address",
                                    "55-1234-6789",
                                    "test description",
                                    new User(),
                                    new ArrayList<Room>(),
                                    new ArrayList<Image>(),
                                    new ArrayList<Reservation>(),
                                    new ArrayList<Review>(),
                                    new ArrayList<User>());
    Room newRoom = new Room(RoomType.TRIPLE, 2, 3, 75.42, true, existingHotel);

    Room savedRoom = new Room(1, RoomType.TRIPLE, 2, 3, 75.42, true, existingHotel);

    when(mockedHotelDAO.findById(hotelId)).thenReturn(Optional.of(existingHotel));
    when(mockedRoomDAO.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    Optional<Room> result = roomService.addNewRoom(newRoom, hotelId);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(newRoom, result.get());
    verify(mockedHotelDAO).findById(hotelId);
    verify(mockedRoomDAO).save(newRoom);
  }

  // updateRoomById()
  @Test
  public void givenInvalidTypeValue_whenUpdateRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int roomId = 1;
    Room room = new Room(1,  RoomType.TRIPLE, 3, 2,
                                   87.23, true, new Hotel());

    Room roomWithInvalidType = new Room(1, null, 3, 2,
                               87.23, true, new Hotel());

    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(room));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.updateRoomById(1, roomWithInvalidType);
    });

    assertEquals("Room type cannot be null", exception.getMessage());
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test
  public void givenInvalidBedsValue_whenUpdateRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int roomId = 1;
    Room room = new Room(1,  RoomType.TRIPLE, 3, 2,
                         87.23, true, new Hotel());

    Room roomWithInValidBeds = new Room(1, RoomType.TRIPLE, -3, 2,
                               87.23, true, new Hotel());

    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(room));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.updateRoomById(roomId, roomWithInValidBeds);
    });

    assertEquals("There must be at least one bed", exception.getMessage());
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenInvalidBathsValue_whenUpdateRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int roomId = 1;
    Room room = new Room(1,  RoomType.TRIPLE, 3, 2,
                         87.23, true, new Hotel());

    Room roomWithInvalidBaths = new Room(1, RoomType.TRIPLE, 3, -2,
                               87.23, true, new Hotel());

    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(room));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.updateRoomById(roomId, roomWithInvalidBaths);
    });

    assertEquals("There must be at least one bath", exception.getMessage());
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenInvalidPriceValue_whenUpdateRoom_thenThrowIllegalArgumentException () {
    // Arrange
    int roomId = 1;
    Room existingRoom = new Room(roomId,  RoomType.TRIPLE, 3, 2,
                         87.23, true, new Hotel());

    Room roomWithInvalidPrice = new Room(roomId, RoomType.TRIPLE, 3, 2,
                               -35.43, true, new Hotel());

    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(existingRoom));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      roomService.updateRoomById(roomId, roomWithInvalidPrice);
    });

    assertEquals("Price must be positive", exception.getMessage());
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO, never()).save(any());
  }

  @Test public void givenValidValues_whenUpdateRoom_thenShouldUpdateAndReturned () {
    // Arrange
    int roomId = 1;
    Room room = new Room(1,  RoomType.TRIPLE, 3, 2,
                         87.23, true, new Hotel());

    Room updateRoom = new Room(1, RoomType.SUITE, 4, 4,
                               99.99, true, new Hotel());

    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(room));
    when(mockedRoomDAO.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    Optional<Room> updatedRoomResult = roomService.updateRoomById(roomId, updateRoom);

    // Assert
    assertTrue(updatedRoomResult.isPresent());
    assertEquals(updateRoom, updatedRoomResult.get());
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO).save(updateRoom);
  }

  // deleteRoom()
  @Test
  public void givenThatRoomDoesNotExists_whenDeleteRoom_thenThrowsRoomNotFoundException() {
    // Arrange
    int roomId = 1;
    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RoomNotFoundException.class, () -> roomService.deleteRoom(roomId));
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO, never()).delete(any());
  }

  @Test
  public void givenThatRoomDoesExists_whenDeleteRoom_thenReturnVoid() {
    // Arrange
    int roomId = 1;
    Room room = new Room(roomId, RoomType.TRIPLE, 2, 3, 75.42, true, new Hotel());

    when(mockedRoomDAO.findById(roomId)).thenReturn(Optional.of(room));

    //  Act
    roomService.deleteRoom(roomId);

    // Act & Assert
    verify(mockedRoomDAO).findById(roomId);
    verify(mockedRoomDAO).deleteById(roomId);
  }
}
