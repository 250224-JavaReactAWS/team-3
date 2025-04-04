package com.revature.services;

import com.revature.exceptions.custom.hotel.HotelNotFoundException;
import com.revature.exceptions.custom.room.RoomNotFoundException;
import com.revature.models.Hotel;
import com.revature.models.Room;
import com.revature.models.RoomType;
import com.revature.repos.HotelDAO;
import com.revature.repos.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomDAO roomDAO;
    private final HotelDAO hotelDAO;

    @Autowired
    public RoomService(RoomDAO roomDAO, HotelDAO hotelDAO) {
        this.roomDAO = roomDAO;
        this.hotelDAO = hotelDAO;
    }

    //TODO Check Availability of a Room

    //Method to verify the availability of a room
    public boolean isRoomAvailable(int roomId){
        Room room = getRoomById(roomId);
        return room.isAvailable();
    }
    //TODO Manage availability of a Room

    //Method to change the availability of a Room
    public Optional<Room> changeRoomAvailability (int roomId){
        //First check the availability of a Room
        if (isRoomAvailable(roomId)){
            //Then change to Occupied or Not available
            Room storedRoom = getRoomById(roomId);
            storedRoom.setAvailable(false);
            return Optional.of(storedRoom);
        }
        return Optional.empty();
    }

    //Method to get room by id
    public Room getRoomById (int roomId){
        return roomDAO.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("There is not any room with the Id: "+ roomId));
    }

    //TODO Manage Inventory of a Room
    //Method to get all rooms of a specific hotel
    public List<Room> getAllRoomsByHotelId (int hotelId){
        Hotel hotel = hotelDAO.findById(hotelId).orElseThrow(() ->
                new HotelNotFoundException("Hotel Not Found"));
        return hotel.getRooms();
    }


    //Method to add a new Room
    public Optional<Room> addNewRoom (Room newRoom, int hotelId){
        newRoom.setHotel(hotelDAO.findById(hotelId).get());
        //Validate the data
        validateRoomData(newRoom);
        return Optional.of(roomDAO.save(newRoom));
    }

    //Method to delete a specific Room
    public boolean deleteRoom (int roomId){
        //Find the room and delete it if present
        if (roomDAO.findById(roomId).isPresent()){
            roomDAO.deleteById(roomId);
            return true;
        }
        return false;
    }

    //Method to update a specific Room
    public Optional<Room> updateRoom (int roomId, Room newRoom){
        //Validate that the room exists
        if (roomDAO.findById(roomId).isPresent()){
            return Optional.of(roomDAO.save(newRoom));
        }
        return Optional.empty();
    }

    //Method to validate room data
    public void validateRoomData (Room room){
        if (room.getType() == null){
            throw new IllegalArgumentException("Room type cannot be null");
        }
        if (room.getBeds() < 1){
            throw new IllegalArgumentException("There must be at least one bed");
        }
        if (room.getBaths() < 1){
            throw new IllegalArgumentException("There must be at least one bath");
        }
        if (room.getPrice() <= 0){
            throw new IllegalArgumentException("Price must be positive");
        }
        if (room.getHotel() == null){
            throw new IllegalArgumentException("Hotel cannot be null");
        }
    }

}
