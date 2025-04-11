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
import java.util.Objects;
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

    //TODO Manage availability of a Room
    //Method to change the availability of a Room
    public Optional<Room> changeRoomAvailability (int roomId){
        //Validate that the room exists
        Room roomToUpdate = roomDAO.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("There is not any room with the Id: "+ roomId)
        );

        //Change the availability
        roomToUpdate.setAvailable(!roomToUpdate.isAvailable());

        //Save room
        return Optional.of(roomDAO.save(roomToUpdate));
    }

    //Method to get room by id
    public Room getRoomById (int roomId){
        return roomDAO.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("There is not any room with the Id: "+ roomId));
    }

    //Method to get all available rooms of a hotel
    public List<Room> getAllAvailableRooms (int hotelId){
        Hotel hotel = hotelDAO.findById(hotelId).orElseThrow(() ->
                new HotelNotFoundException("Hotel Not Found"));

        return roomDAO.getAllAvailableRoomsByHotelId(hotelId);
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

    //Method to update a specific Room
    public Optional<Room> updateRoomById (int roomId, Room updatedRoom){
        //Validate that the room exists
        Room roomToUpdate = roomDAO.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("There is not any room with the Id: "+ roomId)
        );

        //If exists, validate the data
        validateRoomData(updatedRoom);

        //Set new values
        roomToUpdate.setType(updatedRoom.getType() == null ? roomToUpdate.getType() : updatedRoom.getType());
        roomToUpdate.setBeds(updatedRoom.getBeds() == 0 ? roomToUpdate.getBeds() : updatedRoom.getBeds());
        roomToUpdate.setBaths(updatedRoom.getBaths() == 0 ? roomToUpdate.getBaths() : updatedRoom.getBaths());
        roomToUpdate.setPrice(updatedRoom.getPrice() ==0 ? roomToUpdate.getPrice() : updatedRoom.getPrice());

        return Optional.of(roomDAO.save(roomToUpdate));
    }

    //Method to delete a room by id
    public void deleteRoom (int roomId){
        //Validate that the room exists
        if(roomDAO.findById(roomId).isEmpty()){
            throw new RoomNotFoundException("There is not any room with the Id: "+ roomId);
        }
        roomDAO.deleteById(roomId);
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

    @Override public boolean equals ( Object o ) {
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        RoomService that = ( RoomService ) o;
        return Objects.equals(roomDAO, that.roomDAO);
    }

    @Override public int hashCode () {
        return Objects.hashCode(roomDAO);
    }
}
