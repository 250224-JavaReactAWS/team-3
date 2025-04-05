package com.revature.services;

import com.revature.repos.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageDAO imageDAO;

    @Autowired
    public ImageService(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    //TODO GET All IMAGES

    //TODO UPLOAD AN IMAGE

    //TODO UPDATE AN IMAGE

    //TODO DELETE AN IMAGE
}
