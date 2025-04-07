package com.revature.repos;

import com.revature.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDAO extends JpaRepository<Room,Integer> {

    @Query("SELECT r FROM Hotel h JOIN h.rooms r WHERE h.hotelId = :hotelId AND r.isAvailable = true")
    List<Room> getAllAvailableRoomsByHotelId(@Param("hotelId") int hotelId);

}
