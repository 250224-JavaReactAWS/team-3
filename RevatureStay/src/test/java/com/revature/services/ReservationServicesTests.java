package com.revature.services;

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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Reservation reservationToTest;
    private User mockedUser;
    private Hotel mockedHotel;

    private final LocalDate checking = LocalDate.of(2025, 11, 9);
    private final LocalDate checkout = LocalDate.of(2025, 11, 10);
    @BeforeEach
    public void setUp(){
        int guests = 2;
        mockedUser = new User();
        mockedUser.setUserId(1);
        mockedHotel = new Hotel();
        mockedHotel.setHotelId(1);
        mockedHotel.setRooms(createListOfRooms());
        Room room1 = new Room();
        Room room2 = new Room();
        room1.setType(RoomType.DOUBLE);
        room2.setType(RoomType.SUITE);
        reservationToTest = new Reservation(checking, checkout, guests, ReservationStatus.PENDING,mockedUser, mockedHotel, List.of(room1, room2));
    }

    @Test
    public void makeReservationWithCorrectValuesShouldReturnAReservationModel(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedHotelDAO.findById(1)).thenReturn(Optional.of(mockedHotel));
        when(mockedReservationDAO.save(any())).thenReturn(reservationToTest);
        assertInstanceOf(Reservation.class, reservationServices.makeReservation(reservationToTest));
    }

    @Test
    public void makeReservationWithInvalidDatesShouldThrowAnInvalidDatesException(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedHotelDAO.findById(1)).thenReturn(Optional.of(mockedHotel));
        LocalDate invalidCheckOut = LocalDate.of(2025, 11, 8);
        reservationToTest.setCheckOutDate(invalidCheckOut);
        assertThrows(InvalidDatesException.class, ()->{
            reservationServices.makeReservation(reservationToTest);
        });
    }

    @Test
    public void makeReservationShouldThrowAnExceptionWhenUserIsNotFound(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()->{
           reservationServices.makeReservation(reservationToTest);
        });
    }

    @Test
    public void makeReservationShouldThrowAnExceptionWhenHotelIsNotFound(){
        when(mockedUserDAO.findById(any())).thenReturn(Optional.of(mockedUser));
        when(mockedHotelDAO.findById(any())).thenReturn((Optional.empty()));
        assertThrows(ResourceNotFoundException.class, ()->{
            reservationServices.makeReservation(reservationToTest);
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
           reservationServices.makeReservation(reservationToTest);
        });
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
}
