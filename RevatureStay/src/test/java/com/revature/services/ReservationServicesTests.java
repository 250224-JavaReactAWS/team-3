package com.revature.services;

import com.revature.exceptions.custom.reservation.ForbbidenOperationException;
import com.revature.exceptions.custom.reservation.InvalidDatesException;
import com.revature.exceptions.custom.reservation.ResourceNotFoundException;
import com.revature.exceptions.custom.reservation.RoomNotAvailableException;
import com.revature.models.*;
import com.revature.repos.HotelDAO;
import com.revature.repos.ReservationDAO;
import com.revature.repos.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServicesTests {
    @InjectMocks
    private ReservationServices reservationServices;
    @Mock
    private ReservationDAO mockedReservationDAO;
    @Mock
    private UserDAO mockedUserDAO;
    @Mock
    private HotelDAO mockedHotelDAO;

    private Reservation reservationToTest, mockedSavedReservation;
    private User mockedUser;
    private Hotel mockedHotel;

    private final LocalDate checking = LocalDate.of(2025, 11, 9);
    private final LocalDate checkout = LocalDate.of(2025, 11, 10);
    private final int savedReservationId = 1;
    @BeforeEach
    public void setUp(){
        int guests = 2;
        mockedUser = new User();
        mockedUser.setUserId(1);
        mockedUser.setRole(UserRole.CUSTOMER);
        mockedHotel = new Hotel();
        mockedHotel.setHotelId(1);
        mockedHotel.setRooms(createListOfRooms());
        Room room1 = new Room();
        Room room2 = new Room();
        room1.setType(RoomType.DOUBLE);
        room2.setType(RoomType.SUITE);
        reservationToTest = new Reservation(checking, checkout, guests, ReservationStatus.PENDING,mockedUser, mockedHotel, List.of(room1, room2));
        mockedSavedReservation = new Reservation(savedReservationId, checking, checkout, guests, ReservationStatus.PENDING, mockedUser, mockedHotel, List.of(mockedHotel.getRooms().get(0),mockedHotel.getRooms().get(1)) );
    }

    @Test
    public void makeReservationWithCorrectValuesShouldReturnAReservationModel(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedHotelDAO.findById(1)).thenReturn(Optional.of(mockedHotel));
        when(mockedReservationDAO.save(any())).thenReturn(reservationToTest);
        assertInstanceOf(Reservation.class, reservationServices.makeReservation(reservationToTest, 1));
    }

    @Test
    public void makeReservationWithInvalidDatesShouldThrowAnInvalidDatesException(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedHotelDAO.findById(1)).thenReturn(Optional.of(mockedHotel));
        LocalDate invalidCheckOut = LocalDate.of(2025, 11, 8);
        reservationToTest.setCheckOutDate(invalidCheckOut);
        assertThrows(InvalidDatesException.class, ()->{
            reservationServices.makeReservation(reservationToTest, 1);
        });
    }

    @Test
    public void makeReservationShouldThrowAnExceptionWhenUserIsNotFound(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()->{
           reservationServices.makeReservation(reservationToTest, 1);
        });
    }

    @Test
    public void makeReservationShouldThrowAnExceptionWhenHotelIsNotFound(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedHotelDAO.findById(any())).thenReturn((Optional.empty()));
        assertThrows(ResourceNotFoundException.class, ()->{
            reservationServices.makeReservation(reservationToTest, 1);
        });
    }

    @Test
    public void makeReservationShouldReturnAnExceptionWhenARoomIsNotAvailable(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedHotelDAO.findById(1)).thenReturn(Optional.of(mockedHotel));
        Room notAvailableRoom = new Room();
        notAvailableRoom.setType(RoomType.SINGLE);
        reservationToTest.setRooms(List.of(notAvailableRoom));
        assertThrows(RoomNotAvailableException.class, ()->{
           reservationServices.makeReservation(reservationToTest, 1);
        });
    }

    @Test
    public void updateReservationShouldThrowAnExceptionWhenAStrangeTryToUpdate(){
        setMocksBehaviorToPerformRetrievals();
        Reservation reservationWithNewValues = new Reservation();
        reservationWithNewValues.setUser(mockedUser);
        assertThrows(ForbbidenOperationException.class, () ->{
           reservationServices.updateReservation(reservationWithNewValues, 5);
        });
    }

    @Test
    public void updateReservationShouldReturnAUpdatedReservationWhenThereAreAvailableRoomsInNewDates(){
        setMocksBehaviorToPerformUpdates();
        Reservation reservationWithNewValues = new Reservation();
        reservationWithNewValues.setUser(mockedUser);
        reservationWithNewValues.setCheckInDate(checkout.plusDays(2));
        reservationWithNewValues.setCheckOutDate(checkout.plusDays(3));
        Reservation result = reservationServices.updateReservation(reservationWithNewValues, mockedUser.getUserId());
        assertEquals(reservationWithNewValues.getCheckInDate(), result.getCheckInDate());
        assertEquals(reservationWithNewValues.getCheckOutDate(), result.getCheckOutDate());
    }
    @Test
    public void deleteRoomFromReservationShouldReturnTrue(){
        setMocksBehaviorToPerformUpdates();
        List<Room> updatedListOfRooms = reservationServices.deleteRoomFromReservation(mockedUser.getUserId(), savedReservationId, 2);
        assertFalse(updatedListOfRooms.contains(mockedHotel.getRooms().get(1)));
    }

    @Test
    public void deleteRoomFromReservationShouldThrowAnExceptionWhenReservationDoesNotBelongToUser(){
        setMocksBehaviorToPerformRetrievals();
        int differentUserId = 8;
        assertThrows(ForbbidenOperationException.class, () -> {
            List<Room> updatedListOfRooms = reservationServices.deleteRoomFromReservation(differentUserId, savedReservationId, 2);
        });
    }

    @Test
    public void addRoomToReservationShouldReturnANewListContainingTheAddedRoom(){
        setMocksBehaviorToPerformUpdates();
        Room roomToAdd = new Room();
        roomToAdd.setType(RoomType.TRIPLE);
        List<Room> beforeUpdateRooms = mockedSavedReservation.getRooms();
        List<Room> roomsUpdated = reservationServices.addRoomToReservation(mockedUser.getUserId(), savedReservationId, roomToAdd);
        assertEquals(beforeUpdateRooms.size() + 1, roomsUpdated.size());
    }

    @Test
    public void updateReservationStatusShouldReturnAReservationWhitNewStatus(){
        setMocksBehaviorToPerformUpdates();
        ReservationStatus newStatus = ReservationStatus.CANCELLED;
        Reservation reservationWithNewStatus = new Reservation();
        reservationWithNewStatus.setReservationId(savedReservationId);
        reservationWithNewStatus.setStatus(newStatus);
        Reservation updatedReservation = reservationServices.updateReservationStatus(mockedUser.getUserId(), reservationWithNewStatus);
        assertEquals(newStatus, updatedReservation.getStatus());
    }

    @Test
    public void updateReservationStatusShouldReturnExceptionWhenUserIsNotAllowedToSetGivenStatus(){
        setMocksBehaviorToPerformRetrievals();
        ReservationStatus newStatus = ReservationStatus.ACCEPTED;
        Reservation reservationWithNewStatus = new Reservation();
        reservationWithNewStatus.setReservationId(savedReservationId);
        reservationWithNewStatus.setStatus(newStatus);
        assertThrows(ForbbidenOperationException.class, () -> {
            Reservation updatedReservation = reservationServices.updateReservationStatus(mockedUser.getUserId(), reservationWithNewStatus);
        });
    }
    @Test
    public void updateReservationStatusShouldReturnExceptionWhenReservationsDoesNotBelongToHotelOfOwner(){
        User owner = setOwner();
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(owner));
        when(mockedReservationDAO.findById(any())).thenReturn(Optional.of(mockedSavedReservation));
        ReservationStatus newStatus = ReservationStatus.ACCEPTED;
        Reservation reservationWithNewStatus = new Reservation();
        reservationWithNewStatus.setReservationId(savedReservationId);
        reservationWithNewStatus.setStatus(newStatus);
        assertThrows(ForbbidenOperationException.class, () -> {
            Reservation updatedReservation = reservationServices.updateReservationStatus(owner.getUserId(), reservationWithNewStatus);
        });
    }

    @Test
    public void updateReservationStatusShouldReturnUpdatedReservationAfterOwnerAcceptIt(){
        User owner = setOwner();
        owner.setOwnerHotels(List.of(mockedHotel));
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(owner));
        when(mockedReservationDAO.findById(any())).thenReturn(Optional.of(mockedSavedReservation));
        when(mockedReservationDAO.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ReservationStatus newStatus = ReservationStatus.ACCEPTED;
        Reservation reservationWithNewStatus = new Reservation();
        reservationWithNewStatus.setReservationId(savedReservationId);
        reservationWithNewStatus.setStatus(newStatus);
        Reservation updatedReservation = reservationServices.updateReservationStatus(owner.getUserId(), reservationWithNewStatus);
        assertEquals(newStatus, updatedReservation.getStatus());
    }

    private List<Room> createListOfRooms(){
        Reservation reservation1 = new Reservation();
        reservation1.setCheckInDate(checking.minusDays(4));
        reservation1.setCheckOutDate(checking);
        reservation1.setStatus(ReservationStatus.ACCEPTED);
        Reservation reservation2 = new Reservation();
        reservation2.setCheckInDate(checking.minusDays(1));
        reservation2.setCheckOutDate(checkout);
        reservation2.setStatus(ReservationStatus.ACCEPTED);
        Reservation reservation3 = new Reservation();
        reservation3.setCheckInDate(checkout);
        reservation3.setCheckOutDate(checkout.plusDays(2));
        reservation3.setStatus(ReservationStatus.ACCEPTED);
        Reservation reservation4 = new Reservation();
        reservation4.setCheckInDate(checking);
        reservation4.setCheckOutDate(checkout);
        reservation4.setStatus(ReservationStatus.PENDING);
        Reservation reservation5 = new Reservation();
        reservation5.setCheckInDate(checking);
        reservation5.setCheckOutDate(checkout);
        reservation5.setStatus(ReservationStatus.REJECTED);
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();
        Room room4 = new Room();
        room1.setRoomId(1);
        room1.setType(RoomType.DOUBLE);
        room1.setReservations(List.of(reservation1, reservation3));
        room2.setRoomId(2);
        room2.setType(RoomType.SINGLE);
        room2.setReservations(List.of(reservation2));
        room3.setRoomId(3);
        room3.setType(RoomType.SUITE);
        room3.setReservations(List.of(reservation4, reservation5));
        room4.setRoomId(4);
        room4.setType(RoomType.TRIPLE);
        room4.setReservations(List.of());
        return List.of(room1, room2, room3, room4);
    }

    private void setMocksBehaviorToPerformUpdates(){
        setMocksBehaviorToPerformRetrievals();
        when(mockedReservationDAO.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private void setMocksBehaviorToPerformRetrievals(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedReservationDAO.findById(any())).thenReturn(Optional.of(mockedSavedReservation));
    }

    private User setOwner(){
        User user = new User();
        user.setRole(UserRole.OWNER);
        user.setUserId(5);
        Hotel hotel = new Hotel();
        hotel.setHotelId(9);
        user.setOwnerHotels(List.of(hotel));
        return user;
    }
}
