package com.revature.repos;

import com.revature.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelDAO extends JpaRepository<Hotel, Integer> {

    @Query("SELECT h FROM Hotel h WHERE h.owner.userId = :ownerId")
    List<Hotel> getAllByOwnerId(@Param("ownerId") int ownerId);
}
