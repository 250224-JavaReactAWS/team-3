package com.revature.services;

import com.revature.exceptions.custom.hotel.*;
import com.revature.exceptions.custom.user.ForbiddenActionException;
import com.revature.exceptions.custom.user.OwnerDoesNotExistsException;
import com.revature.models.Hotel;
import com.revature.repos.HotelDAO;
import com.revature.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class HotelService {

    private final HotelDAO hotelDAO;
    private final UserDAO userDAO;

    @Autowired
    public HotelService(HotelDAO hotelDAO, UserDAO userDAO) {
        this.hotelDAO = hotelDAO;
        this.userDAO = userDAO;
    }

    //TODO Get hotel details including images, location, price, amenities, and user reviews.
    public Hotel getHotelById (int hotelId){
        return hotelDAO.findById(hotelId).orElseThrow( () ->
                new HotelNotFoundException("Hotel with Id " + hotelId + "is not found")
        );
    }

    public List<Hotel> getAllHotels (){
        return hotelDAO.findAll();
    }

    public List<Hotel> getAllHotelsByUserId (int ownerId){
        return hotelDAO.getAllByOwnerId(ownerId);
    }

    //TODO Manage hotels

    //Method to add new hotel
    public Optional<Hotel> addNewHotel (Hotel newHotel,int ownerId){
        validateHotelData(newHotel, ownerId);
        newHotel.setOwner(userDAO.findById(ownerId).get());
        return Optional.of(hotelDAO.save(newHotel));
    }

    //Method to update a Hotel
    public Optional<Hotel> updateHotelById (int hotelId, Hotel updatedHotel, int ownerId){

        //Validate that the hotel exists
        Hotel hotelToUpdate = hotelDAO.findById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel with Id " + hotelId + "is not found")
        );

        //If exists, validate the data
        validateHotelData(updatedHotel,ownerId);

        //Validate the Hotel belongs to the Owner
        validateHotelBelongsToOwner(hotelToUpdate, ownerId);


        //Set new values
        hotelToUpdate.setName(updatedHotel.getName().isEmpty() ? hotelToUpdate.getName() : updatedHotel.getName());
        hotelToUpdate.setAddress(updatedHotel.getAddress().isEmpty() ? hotelToUpdate.getAddress() : updatedHotel.getAddress());
        hotelToUpdate.setCellphoneNumber(updatedHotel.getCellphoneNumber().isEmpty() ? hotelToUpdate.getCellphoneNumber() : updatedHotel.getCellphoneNumber());
        hotelToUpdate.setDescription(updatedHotel.getDescription().isEmpty() ? hotelToUpdate.getDescription() : updatedHotel.getDescription());


        return Optional.of(hotelDAO.save(hotelToUpdate));
    }

    //Method to delete a Hotel
    public void deleteHotelById (int hotelId){
        //Validate that the hotel exists
        if (hotelDAO.findById(hotelId).isEmpty()){
            throw new HotelNotFoundException("Hotel with Id " + hotelId + "is not found");
        }
        hotelDAO.deleteById(hotelId);
    }


    //Method to validate the hotel data
    public void validateHotelData(Hotel hotel, int ownerId){
        final String PHONE_NUMBER_REGEX = "\\d{10}";

        if (userDAO.findById(ownerId).isEmpty()){
            throw new OwnerDoesNotExistsException("The owner does not exists");
        }
        if (hotel.getName().length()<8){
            throw new InvalidHotelNameException("Hotel name must have at least 8 characters");
        }
        if (hotel.getAddress().length()<10){
            throw new InvalidHotelAddressException("Hotel address must have at least 20 characters");
        }
        if (!Pattern.matches(PHONE_NUMBER_REGEX,hotel.getCellphoneNumber())){
            throw new InvalidHotelPhoneNumberException("Hotel phone number is not valid");
        }
        if (hotel.getDescription().length()<20){
            throw new InvalidHotelDescriptionException("Hotel description must have at least 20 characters");
        }
    }

    //Method to validate that the hotel belongs to the current logged owner
    public void validateHotelBelongsToOwner (Hotel hotel, int ownerId){
        if (hotel.getOwner().getUserId() != ownerId){
            throw new ForbiddenActionException("You cannot access this endpoint");
        }
    }

    public void validateHotelBelongsToOwner (int hotelId, int ownerId){

        Hotel hotel = hotelDAO.findById(hotelId).orElseThrow(
                () -> new HotelNotFoundException("Hotel Not Found")
        );

        if (hotel.getOwner().getUserId() != ownerId){
            throw new ForbiddenActionException("You cannot access this endpoint");
        }
    }



}
