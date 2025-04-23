package com.revature.controllers;


import com.revature.models.Hotel;

import com.revature.services.HotelService;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("hotels")
public class HotelController {

    private final HotelService hotelService;
    private final UserService userService;

    @Autowired
    public HotelController(HotelService hotelService, UserService userService) {
        this.hotelService = hotelService;
        this.userService = userService;
    }
    //Method to get all hotels
    @GetMapping
    public List<Hotel> getAllHotelsHandler(){
        return hotelService.getAllHotels();
    }

    @GetMapping("my-hotels")
    //Method to get all hotels that belongs to a user
    public List<Hotel> getAllHotelsByOwnerIdHandler(HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        int ownerId = (int) session.getAttribute("userId");
        return hotelService.getAllHotelsByUserId(ownerId);
    }

    //Method to get a specific hotel
    @GetMapping("{hotelId}")
    public Hotel getHotelByIdHandler (@PathVariable int hotelId){
        return hotelService.getHotelById(hotelId);
    }

    //Method to add new Hotel (OWNER ONLY)
    @PostMapping
    public Optional<Hotel> addNewHotelHandler(@RequestBody Hotel newHotel, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        int ownerId = (int) session.getAttribute("userId");
        return hotelService.addNewHotel(newHotel, ownerId);
    }

    //Method to update a specific hotel (OWNER ONLY)
    @PutMapping("{hotelId}")
    public Optional<Hotel> updateHotelByIdHandler (@PathVariable int hotelId, @RequestBody Hotel newHotel, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        int ownerId = (int) session.getAttribute("userId");
        return hotelService.updateHotelById(hotelId, newHotel, ownerId);
    }

    //Method to delete a specific hotel (OWNER ONLY)
    @DeleteMapping("{hotelId}")
    public void deleteHotelHandler (@PathVariable int hotelId, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Delete hotel
        hotelService.deleteHotelById(hotelId);

    }


}
