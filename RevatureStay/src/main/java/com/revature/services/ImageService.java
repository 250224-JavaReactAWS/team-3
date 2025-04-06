package com.revature.services;

import com.revature.exceptions.custom.hotel.HotelNotFoundException;
import com.revature.exceptions.custom.image.ImageNotFoundException;
import com.revature.models.Hotel;
import com.revature.models.Image;
import com.revature.models.Room;
import com.revature.repos.HotelDAO;
import com.revature.repos.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageDAO imageDAO;
    private final HotelDAO hotelDAO;

    @Autowired
    public ImageService(ImageDAO imageDAO, HotelDAO hotelDAO) {
        this.imageDAO = imageDAO;
        this.hotelDAO = hotelDAO;
    }

    //Method to get all images that belongs to a specific hotel
    public List<Image> getAllImagesByHotelId (int hotelId){
        //Validate that the hotel exists
        Hotel hotel = hotelDAO.findById(hotelId).orElseThrow(() ->
                new HotelNotFoundException("Hotel Not Found"));

        return hotel.getImages();
    }

    //Method to add new image
    public Optional<Image> addNewImage (Image newImage, int hotelId){
        //Validate that the hotel exists
        Hotel hotel = hotelDAO.findById(hotelId).orElseThrow(
                () ->  new HotelNotFoundException("Hotel Not Found")
        );
        //Set the hotel to the Image
        newImage.setHotel(hotel);

        //Validate Data
        validateImageData(newImage);
        //Save the image
        return Optional.of(imageDAO.save(newImage));
    }

    //Method to update an Image
    public Optional<Image> updateImage (Image updatedImage, int hotelId, int imageId){

        //Validate that the image exists
        Image imageToUpdate = imageDAO.findById(imageId).orElseThrow(
                () -> new ImageNotFoundException("There is not any image with the Id:" + imageId)
        );

        //Set new values
        imageToUpdate.setUrl(updatedImage.getUrl().isEmpty() ? imageToUpdate.getUrl() : updatedImage.getUrl());
        imageToUpdate.setAlt(updatedImage.getAlt().isEmpty() ? imageToUpdate.getAlt() : updatedImage.getAlt());

        //Validate the data
        validateImageData(imageToUpdate);

        return Optional.of(imageDAO.save(imageToUpdate));

    }

    //Method to delete an Image
    public void deleteImage (int imageId){
        //Validate that the image exists
        if (imageDAO.findById(imageId).isEmpty()){
            throw new ImageNotFoundException("There is not any image with the Id:" + imageId);
        }

        //Delete Image
        imageDAO.deleteById(imageId);
    }




    //Validate the image data
    public void validateImageData (Image image){
        if (image.getAlt().isEmpty()){
            throw new IllegalArgumentException("The image alt text must not be empty");
        }
        if (image.getUrl().isEmpty()){
            throw new IllegalArgumentException("The image url must not be empty");
        }
        if (image.getHotel() == null){
            throw new IllegalArgumentException("The hotel must not be empty"); //Check later
        }
    }
}
