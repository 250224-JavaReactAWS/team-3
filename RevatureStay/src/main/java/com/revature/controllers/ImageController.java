package com.revature.controllers;

import com.revature.exceptions.custom.user.ForbiddenActionException;
import com.revature.exceptions.custom.user.UnauthenticatedException;
import com.revature.models.Hotel;
import com.revature.models.Image;
import com.revature.models.UserRole;
import com.revature.services.HotelService;
import com.revature.services.ImageService;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("hotels/{hotelId}/images")
@CrossOrigin(origins = {"http://localhost:5173"}, allowCredentials = "true")
public class ImageController {

    private final ImageService imageService;
    private final HotelService hotelService;
    private final UserService userService;

    public ImageController(ImageService imageService, HotelService hotelService, UserService userService) {
        this.imageService = imageService;
        this.hotelService = hotelService;
        this.userService = userService;
    }


    //Method to get all images of a hotel
    @GetMapping
    public List<Image> getAllImagesHandler (@PathVariable int hotelId) {
        return imageService.getAllImagesByHotelId(hotelId);
    }

    //Method to add new image to a specific hotel
    @PostMapping
    public Optional<Image> addNewImageHandler (@PathVariable int hotelId, @RequestBody Image image , HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Save the image
        return imageService.addNewImage(image, hotelId);
    }

    //Method to update an image by id
    @PutMapping("{imageId}")
    public Optional<Image> updateImageByIdHandler (@PathVariable int hotelId, @PathVariable int imageId, @RequestBody Image image, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Update the image
        return imageService.updateImage(image,hotelId,imageId);
    }

    //Method to delete an image by id
    @DeleteMapping("{imageId}")
    public void deleteImageByIdHandler (@PathVariable int imageId, @PathVariable int hotelId, HttpSession session){
        //Validate that the user is logged in and is an owner
        userService.validateUserIsAuthenticated(session);
        userService.validateUserIsOwner(session);

        //Validate that the hotel belongs to the current logged owner
        int ownerId = (int) session.getAttribute("userId");
        hotelService.validateHotelBelongsToOwner(hotelId,ownerId);

        //Delete Image
        imageService.deleteImage(imageId);
    }

}
