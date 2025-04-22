package com.revature.controllers;

import com.revature.exceptions.custom.user.ForbiddenActionException;
import com.revature.exceptions.custom.user.UnauthenticatedException;
import com.revature.models.Room;
import com.revature.models.UserRole;
import com.revature.services.HotelService;
import com.revature.services.RoomService;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("hotels/{hotelId}/rooms")
@CrossOrigin(origins = {"http://revature-example-bucket.s3-website-us-east-1.amazonaws.com"}, allowCredentials = "true")
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;
    private final UserService userService;

    @Autowired
    public RoomController(RoomService roomService, HotelService hotelService, UserService userService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
        this.userService = userService;
    }

    //Method to get all rooms
    @GetMapping
    public List<Room> getAllHotelRoomsHandler (@PathVariable int hotelId){
        return roomService.getAllRoomsByHotelId(hotelId);
    }

    //Method to get all available rooms
    @GetMapping("available")
    public List<Room> getAllAvailableRoomsHandler (@PathVariable int hotelId){
        return roomService.getAllAvailableRooms(hotelId);
    }

    //Method to addNewRoom
    @PostMapping
    public Optional<Room> addNewRoomHandler (@RequestBody Room newRoom, @PathVariable int hotelId, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Save the room
        return roomService.addNewRoom(newRoom, hotelId);
    }

    //Method to update a specific Room
    @PutMapping("{roomId}")
    public Optional<Room> updateRoomByIdHandler (@PathVariable int hotelId, @PathVariable int roomId, @RequestBody Room updatedRoom, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Update Hotel
        return roomService.updateRoomById(roomId,updatedRoom);
    }

    //Method to delete a room
    @DeleteMapping("{roomId}")
    public void deleteRoomHandler (@PathVariable int hotelId, @PathVariable int roomId, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Delete room
        roomService.deleteRoom(roomId);
    }

    //Method to change the availability of a room
    @PatchMapping("{roomId}/availability")
    public Optional<Room> changeRoomAvailabilityHandler (@PathVariable int hotelId, @PathVariable int roomId, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Change Availability
        return roomService.changeRoomAvailability(roomId);
    }




}
