package com.revature.repos;

import com.revature.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDAO extends JpaRepository<Hotel, Integer> {

}
