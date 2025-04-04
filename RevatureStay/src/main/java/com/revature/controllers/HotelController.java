package com.revature.controllers;

import com.revature.exceptions.custom.user.ForbiddenActionException;
import com.revature.exceptions.custom.user.UnauthenticatedException;
import com.revature.models.Hotel;
import com.revature.models.UserRole;
import com.revature.services.HotelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Optional;

@RestController
@RequestMapping("hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    //Method to get a specific hotel
    @GetMapping("{hotelId}")
    public Hotel getHotelByIdHandler (@PathVariable int hotelId){
        return hotelService.getHotelById(hotelId);
    }

    //Method to add new Hotel (OWNER ONLY)
    @PostMapping("create")
    public Optional<Hotel> addNewHotelHandler(@RequestBody Hotel newHotel, HttpSession session){
        //Validate that the user is logged in and is an owner
        if (session.getAttribute("userId") == null){
            throw new UnauthenticatedException("You must be logged in to access this page");
        }
        if (session.getAttribute("role") != UserRole.OWNER){
            throw new ForbiddenActionException("You must be logged in to access this page");
        }

        int ownerId = (int) session.getAttribute("userId");
        return hotelService.addNewHotel(newHotel, ownerId);
    }

    //Method to update a specific hotel (OWNER ONLY)
    @PutMapping("update/{hotelId}")
    public Optional<Hotel> updateHotelByIdHandler (@PathVariable int hotelId, @RequestBody Hotel newHotel, HttpSession session){
        //Validate that the user is logged in and is an owner
        if (session.getAttribute("userId") == null){
            throw new UnauthenticatedException("You must be logged in to access this page");
        }
        if (session.getAttribute("role") != UserRole.OWNER){
            throw new ForbiddenActionException("You must be logged in to access this page");
        }

        int ownerId = (int) session.getAttribute("userId");
        return hotelService.updateHotelById(hotelId, newHotel, ownerId);
    }

    //Method to delete a specific hotel (OWNER ONLY)
    @DeleteMapping("delete/{hotelId}")
    public void deleteHotelHandler (@PathVariable int hotelId, HttpSession session){
        //Validate that the user is logged in and is an owner
        if (session.getAttribute("userId") == null){
            throw new UnauthenticatedException("You must be logged in to access this page");
        }
        if (session.getAttribute("role") != UserRole.OWNER){
            throw new ForbiddenActionException("You must be logged in to access this page");
        }

        hotelService.deleteHotelById(hotelId);

    }


}
