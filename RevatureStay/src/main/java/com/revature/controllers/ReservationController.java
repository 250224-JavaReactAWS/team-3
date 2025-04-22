package com.revature.controllers;

import com.revature.exceptions.custom.user.UnauthenticatedException;
import com.revature.models.Reservation;
import com.revature.models.Room;
import com.revature.services.ReservationServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("reservations")
@CrossOrigin(origins = {"http://revature-example-bucket.s3-website-us-east-1.amazonaws.com"}, allowCredentials = "true")
public class ReservationController {
    private final ReservationServices reservationServices;

    @Autowired
    public ReservationController(ReservationServices reservationServices){
        this.reservationServices = reservationServices;
    }

    @GetMapping
    public List<Reservation> getReservationsHandler(HttpSession session){
        int userId = getUserIdFromSession(session);
        return reservationServices.viewMyReservations(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservationHandler(@RequestBody Reservation reservation, HttpSession session){
        int userId = getUserIdFromSession(session);
        return reservationServices.makeReservation(reservation, userId);
    }

    @GetMapping("{reservationId}")
    public Reservation getReservationHandler(@PathVariable int reservationId, HttpSession session){
        int userId = getUserIdFromSession(session);
        return reservationServices.viewReservation(userId, reservationId);
    }

    @PutMapping("{reservationId}")
    public Reservation updateReservationHandler(@RequestBody Reservation reservation, @PathVariable int reservationId, HttpSession session){
        int userId = getUserIdFromSession(session);
        reservation.setReservationId(reservationId);
        return reservationServices.updateReservation(reservation, userId);
    }

    @PatchMapping("{reservationId}")
    public Reservation updateStatusReservationHandler(@RequestBody Reservation reservation, @PathVariable int reservationId, HttpSession session){
        int userId = getUserIdFromSession(session);
        reservation.setReservationId(reservationId);
        return reservationServices.updateReservationStatus(userId, reservation);
    }

    @DeleteMapping("{reservationId}/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoomFromReservationHandler(@PathVariable int reservationId, @PathVariable int roomId, HttpSession session){
        int userId = getUserIdFromSession(session);
        boolean result = reservationServices.deleteRoomFromReservation(userId, reservationId, roomId);
        if(result){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("{reservationId}/rooms")
    public Room createRoomForReservationHandler(@PathVariable int reservationId, @RequestBody Room room, HttpSession session){
        int userId = getUserIdFromSession(session);
        return reservationServices.addRoomToReservation(userId, reservationId, room);
    }

    @PutMapping("{reservationId}/rooms/{roomId}")
    public Room updateRoomFromReservationHandler(@PathVariable int reservationId, @PathVariable int roomId,@RequestBody Room room, HttpSession session){
        int userId = getUserIdFromSession(session);
        return reservationServices.updateRoomFromReservation(userId, reservationId, roomId, room);
    }

    private int getUserIdFromSession(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if(userId == null){
            throw new UnauthenticatedException("You need to log in before access this feature");
        }
        return userId;
    }
}
