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
}
