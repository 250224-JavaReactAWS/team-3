package com.revature.services;

import com.revature.exceptions.custom.reservation.*;
import com.revature.exceptions.custom.user.ForbiddenActionException;
import com.revature.models.*;
import com.revature.repos.HotelDAO;
import com.revature.repos.ReservationDAO;
import com.revature.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Reservation makeReservation(Reservation reservationToBeMade, int userId){
        User user = findUser(userId);
        Hotel hotel = findHotel(reservationToBeMade);
        validateReservationDates(reservationToBeMade.getCheckInDate(), reservationToBeMade.getCheckOutDate());
        List<Room> rooms = findRoomsForReservation(reservationToBeMade, hotel.getRooms());
        reservationToBeMade.setUser(user);
        reservationToBeMade.setHotel(hotel);
        reservationToBeMade.setRooms(rooms);
        return reservationDAO.save(reservationToBeMade);
    }

    public List<Reservation> viewMyReservations(int userId){
        User user = findUser(userId);
        if(user.getRole().equals(UserRole.CUSTOMER)){
            return user.getReservations();
        }
        List<Hotel> hotels = user.getOwnerHotels();
        List<Reservation> reservations = new ArrayList<>();
        for(Hotel hotel : hotels){
            reservations.addAll(hotel.getReservations());
        }
        return reservations;
    }

    public Reservation viewReservation(int userId, int reservationId){
        User user = findUser(userId);
        Reservation reservation = findReservation(reservationId);
        if(reservation.getUser().getUserId() != userId && reservation.getHotel().getOwner().getUserId() != userId){
            throw new ForbiddenActionException("You can not access this resource");
        }
        return reservation;
    }

    @Transactional
    public Reservation updateReservation(Reservation reservation, int userId){
        User user = findUser(userId);
        Reservation storedReservation = findReservation(reservation.getReservationId());
        validateThatReservationBelongsToUser(storedReservation, userId);
        Hotel hotel = storedReservation.getHotel();
        if(datesOfReservationNeedToBeUpdated(reservation)){
            validateReservationDates(reservation.getCheckInDate(), reservation.getCheckOutDate());
            storedReservation.setCheckInDate(reservation.getCheckInDate());
            storedReservation.setCheckOutDate(reservation.getCheckOutDate());
            List<Room> availableRooms = findRoomsForReservation(storedReservation,hotel.getRooms());
            storedReservation.setRooms(availableRooms);
        }
        if(reservation.getNumGuests()>0){
            storedReservation.setNumGuests(reservation.getNumGuests());
        }
        return reservationDAO.save(storedReservation);
    }

    @Transactional
    public boolean deleteRoomFromReservation(int userId, int reservationId, int roomId){
        User user = findUser(userId);
        Reservation reservation = findReservation(reservationId);
        validateThatReservationBelongsToUser(reservation, userId);
        List<Room> newRooms = reservation.getRooms().stream().filter(room -> room.getRoomId() != roomId).toList();
        reservation.setRooms(newRooms);
        List<Room> newSavedRooms = reservationDAO.save(reservation).getRooms();
        return newSavedRooms.stream().filter(room -> room.getRoomId() == roomId).toList().isEmpty();
    }

    @Transactional
    public Room addRoomToReservation(int userId, int reservationId, Room room){
        User user = findUser(userId);
        Reservation reservation = findReservation(reservationId);
        validateThatReservationBelongsToUser(reservation, userId);
        List<Room> roomsOfSameType = findRoomsOfType(room.getType(), reservation.getHotel().getRooms());
        Optional<Room> availableRoom = findAvailableRoom(reservation.getCheckInDate(), reservation.getCheckOutDate(), roomsOfSameType);
        if(availableRoom.isEmpty()){
            throw new RoomNotAvailableException("No available room found for the type: " + room.getType().toString());
        }
        List<Room> rooms = new ArrayList<>(reservation.getRooms());
        rooms.add(availableRoom.get());
        reservation.setRooms(rooms);
        Optional<Room> savedNewRoom = reservationDAO.save(reservation).getRooms().stream().filter(r -> r.getRoomId() == availableRoom.get().getRoomId()).findFirst();
        if(savedNewRoom.isEmpty()){
            throw new InternalException("Something went wrong while adding Room to Reservation");
        }
        return savedNewRoom.get();
    }

    @Transactional
    public Reservation updateReservationStatus(int userId, Reservation reservation){
        User user = findUser(userId);
        Reservation savedReservation = findReservation(reservation.getReservationId());
        if(user.getRole().equals(UserRole.CUSTOMER)){
            validateThatReservationBelongsToUser(savedReservation, userId);
            if(!reservation.getStatus().equals(ReservationStatus.CANCELLED)){
                throw new ForbiddenActionException("You can not set the status "+ reservation.getStatus() + " on this reservation");
            }
        }else{
            List<Hotel> userHotels = user.getOwnerHotels();
            Hotel reservationHotel = savedReservation.getHotel();
            if(!userHotels.contains(reservationHotel)){
                throw new ForbiddenActionException("This reservation does not belong to any of your hotels");
            }
            if(reservation.getStatus().equals(ReservationStatus.ACCEPTED)){
                validateReservationRoomsAvailability(savedReservation);
            }
        }
        savedReservation.setStatus(reservation.getStatus());
        return reservationDAO.save(savedReservation);
    }

    @Transactional
    public Room updateRoomFromReservation(int userId, int reservationId, int roomId, Room room){
        User user = findUser(userId);
        Reservation reservation = findReservation(reservationId);
        if(!user.getRole().equals(UserRole.OWNER) || reservation.getHotel().getOwner().getUserId() != userId){
            throw new ForbiddenActionException("You are not allowed to perform this operation");
        }
        Optional<Room> savedRoom = reservation.getHotel().getRooms().stream().filter(r -> r.getRoomId() == room.getRoomId()).findFirst();
        if(savedRoom.isEmpty()){
            throw new ResourceNotFoundException("Room with id: "+room.getRoomId() +" was not found");
        }
        if(!roomIsAvailable(savedRoom.get(), reservation.getCheckInDate(), reservation.getCheckOutDate())){
            throw new RoomNotAvailableException("Room with id: " + room.getRoomId() + "is not available for given dates");
        }
        List<Room> newRooms = new ArrayList<>(reservation.getRooms().stream().filter( r -> r.getRoomId() != roomId).toList());
        newRooms.add(savedRoom.get());
        reservation.setRooms(newRooms);
        Optional<Room> addedRoom = reservationDAO.save(reservation).getRooms().stream().filter(r -> r.getRoomId() == room.getRoomId()).findFirst();
        if (addedRoom.isEmpty()){
            throw new InternalException("Something went wrong!");
        }
        return addedRoom.get();
    }

    private User findUser(int userID){
        Optional<User> user = usersDAO.findById(userID);
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

    private Reservation findReservation(int reservationId){
        Optional<Reservation> reservation = reservationDAO.findById(reservationId);
        if(reservation.isEmpty()){
            throw new ResourceNotFoundException("Reservation not found");
        }
        return reservation.get();
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
        if (reservation.getReservationId() != 0){
            mutableInventory = cleanListOfRoomForUpdatingReservation(reservation.getReservationId(), mutableInventory);
        }
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
            if(reservationHasConflictWithDates(reservation, checking, checkout) && reservationIsAccepted(reservation)){
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
    private boolean reservationIsAccepted(Reservation reservation){
        return reservation.getStatus().equals(ReservationStatus.ACCEPTED);
    }

    private boolean datesOfReservationNeedToBeUpdated(Reservation newReservation){
        return newReservation.getCheckInDate() != null && newReservation.getCheckOutDate() != null;
    }

    private List<Room> cleanListOfRoomForUpdatingReservation(int reservationId, List<Room> rooms){
        List<Room> cleaned = new ArrayList<>();
        for (Room room :rooms){
            room.setReservations(
                    room.getReservations()
                            .stream()
                            .filter(reservation -> reservation.getReservationId() != reservationId)
                            .toList()
            );
            cleaned.add(room);
        }
        return cleaned;
    }

    private void validateThatReservationBelongsToUser(Reservation reservation, int userId){
        if(reservation.getUser().getUserId() != userId){
            throw new ForbiddenActionException("This reservation does not belong to given user");
        }
    }

    private void validateReservationRoomsAvailability(Reservation reservation){
        List<Room> rooms = reservation.getRooms();
        for(Room room : rooms){
            List <Reservation> reservations = room.getReservations()
                    .stream()
                    .filter(r -> r.getReservationId() != reservation.getReservationId())
                    .toList();
            room.setReservations(reservations);
            if(!roomIsAvailable(room, reservation.getCheckInDate(), reservation.getCheckOutDate())){
                throw new RoomNotAvailableException("Room with ID: " + room.getRoomId() + " is not available. Please replace with an available Room and try again.");
            }
        }
    }

}
