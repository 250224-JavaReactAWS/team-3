package com.revature.services;

import com.revature.exceptions.custom.reservation.InvalidDatesException;
import com.revature.exceptions.custom.reservation.ResourceNotFoundException;
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

    @BeforeEach
    public void setUp(){
        LocalDate checking = LocalDate.of(2025, 11, 9);
        LocalDate checkout = LocalDate.of(2025, 11, 10);
        int guests = 2;
        mockedUser = new User();
        mockedUser.setUserId(1);
        mockedHotel = new Hotel();
        mockedHotel.setHotelId(1);
        reservationToTest = new Reservation(checking, checkout, guests, ReservationStatus.PENDING,mockedUser, mockedHotel, null);
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
//    @Test
//    public void viewMyReservationsShouldReturnAListOfReservations(){
//        assertInstanceOf(Li)
//        reservationServices.viewMyReservations()
//    }
}
