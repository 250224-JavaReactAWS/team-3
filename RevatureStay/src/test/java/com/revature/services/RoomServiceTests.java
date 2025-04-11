package com.revature.services;

import com.revature.exceptions.custom.room.RoomNotFoundException;
import com.revature.models.*;
import com.revature.repos.HotelDAO;
import com.revature.repos.RoomDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
