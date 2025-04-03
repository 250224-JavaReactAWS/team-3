package com.revature.services;

import com.revature.exceptions.custom.reservation.InvalidDatesException;
import com.revature.exceptions.custom.reservation.ResourceNotFoundException;
import com.revature.exceptions.custom.reservation.RoomNotAvailableException;
import com.revature.models.*;
import com.revature.repos.HotelDAO;
import com.revature.repos.ReservationDAO;
import com.revature.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        List<Room> rooms = findRoomsForReservation(reservationToBeMade, hotel.getRooms());
        reservationToBeMade.setUser(user);
        reservationToBeMade.setHotel(hotel);
        reservationToBeMade.setRooms(rooms);
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

    private List<Room> findRoomsForReservation(Reservation reservation, List<Room> inventory){
        List<Room> mutableInventory = new ArrayList<>(inventory);
        List<Room> foundRooms = new ArrayList<>();
        for(Room room : reservation.getRooms()){
            RoomType type = room.getType();
            List<Room> roomsOfSameType = findRoomsOfType(type, mutableInventory);
            Optional<Room> availableRoom = findAvailableRoom(reservation.getCheckInDate(), reservation.getCheckOutDate(), roomsOfSameType);
            if(availableRoom.isEmpty()){
                throw new RoomNotAvailableException("No available room found for the type: " + room.getType().toString());
            }
            foundRooms.add(availableRoom.get());
            mutableInventory.remove(availableRoom.get());
        }
        return foundRooms;
    }

    private List<Room> findRoomsOfType(RoomType type, List<Room> rooms){
        return rooms.stream().filter(room -> room.getType().equals(type)).toList();
    }

    private Optional<Room> findAvailableRoom(LocalDate checking, LocalDate checkout, List<Room> rooms){
        for(Room room : rooms){
            if(roomIsAvailable(room, checking, checkout)){
                return Optional.of(room);
            }
        }
        return Optional.empty();
    }

    private boolean roomIsAvailable(Room room, LocalDate checking, LocalDate checkout){
        List<Reservation> reservations = room.getReservations();
        for(Reservation reservation : reservations){
            if(reservationHasConflictWithDates(reservation, checking, checkout)){
                return false;
            }
        }
        return true;
    }
    private boolean reservationHasConflictWithDates(Reservation reservation, LocalDate checking,LocalDate checkout){
        LocalDate reservationChecking = reservation.getCheckInDate();
        LocalDate reservationCheckout = reservation.getCheckOutDate();
        return !(checkout.equals(reservationChecking) || checking.equals(reservationCheckout) || checkout.isBefore(reservationChecking) || checking.isAfter(reservationCheckout));
    }
}
