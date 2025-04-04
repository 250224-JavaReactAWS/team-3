package com.revature.controllers;

import com.revature.exceptions.custom.user.ForbiddenActionException;
import com.revature.exceptions.custom.user.UnauthenticatedException;
import com.revature.models.Hotel;
import com.revature.models.Room;
import com.revature.models.RoomType;
import com.revature.models.UserRole;
import com.revature.services.HotelService;
import com.revature.services.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("hotels/{hotelId}/rooms")
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;

    @Autowired
    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    //Method to addNewRoom
    @PostMapping("create")
    public Optional<Room> addNewRoomHandler (@RequestBody Room newRoom, @PathVariable int hotelId, HttpSession session){
        //Validate that the user is logged in and is an owner
        if (session.getAttribute("userId") == null){
            throw new UnauthenticatedException("You must be logged in to access this page");
        }
        if (session.getAttribute("role") != UserRole.OWNER){
            throw new ForbiddenActionException("You must be logged in to access this page");
        }

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Save the room
        return roomService.addNewRoom(newRoom, hotelId);
    }

    //Method to get all rooms
    @GetMapping
    public List<Room> getAllHotelRoomsHandler (@PathVariable int hotelId){
        return roomService.getAllRoomsByHotelId(hotelId);
    }

}
