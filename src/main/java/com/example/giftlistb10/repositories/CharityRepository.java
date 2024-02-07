package com.example.giftlistb10.repositories;

import com.example.giftlistb10.dto.booking.CharityBookingResponse;
import com.example.giftlistb10.entities.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Long> {
    List<Charity> getAllByReservoirId(Long id);
    @Query("select new com.example.giftlistb10.dto.booking.CharityBookingResponse(c.id," +
            "c.nameCharity, " +
            "c.condition, " +
            "c.createdAt, " +
            "c.image, " +
            "c.status, " +
            "c.owner.id, " +
            "concat(c.owner.firstName,' ',c.owner.lastName), " +
            "c.owner.userInfo.image," +
            "c.reservoir.id) from Charity c where c.reservoir.email=?1")
    List<CharityBookingResponse> getAllReservedCharity(String email);
}