package com.revature.services;

import com.revature.exceptions.custom.reservation.InvalidDatesException;
import com.revature.exceptions.custom.reservation.ResourceNotFoundException;
import com.revature.models.Hotel;
import com.revature.models.Reservation;
import com.revature.models.User;
import com.revature.repos.HotelDAO;
import com.revature.repos.ReservationDAO;
import com.revature.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReservationServices {
    private final ReservationDAO reservationDAO;
    private final HotelDAO hotelDAO;
    private final UserDAO usersDAO;

    @Autowired
    public ReservationServices(ReservationDAO reservationDAO, HotelDAO hotelDAO, UserDAO usersDAO) {
        this.reservationDAO = reservationDAO;
        this.hotelDAO = hotelDAO;
        this.usersDAO = usersDAO;
    }

    public Reservation makeReservation(Reservation reservationToBeMade){
        User user = findUser(reservationToBeMade);
        Hotel hotel = findHotel(reservationToBeMade);
        validateReservationDates(reservationToBeMade.getCheckInDate(), reservationToBeMade.getCheckOutDate());
        reservationToBeMade.setUser(user);
        reservationToBeMade.setHotel(hotel);
        return reservationDAO.save(reservationToBeMade);
    }

    private User findUser(Reservation reservation){
        Optional<User> user = usersDAO.findById(reservation.getUser().getUserId());
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }
        return user.get();
    }

    private Hotel findHotel(Reservation reservation){
        Optional<Hotel> hotel = hotelDAO.findById(reservation.getHotel().getHotelId());
        if(hotel.isEmpty()){
            throw new ResourceNotFoundException("Hotel not found");
        }
        return hotel.get();
    }

    private void validateReservationDates(LocalDate checkIn, LocalDate checkOut){
        LocalDate now = LocalDate.now();
        if (checkIn.isAfter(checkOut)) {
            throw new InvalidDatesException("Check-out date can not be placed before check-in date");
        }
        if(checkIn.isBefore(now)){
            throw new InvalidDatesException("You cant make a reservation for past dates");
        }
    }
}
