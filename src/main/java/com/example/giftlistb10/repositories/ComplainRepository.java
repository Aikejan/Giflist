package com.example.giftlistb10.repositories;

import com.example.giftlistb10.dto.complaint.ComplaintUserResponse;
import com.example.giftlistb10.dto.complaint.ComplaintResponse;
import com.example.giftlistb10.entities.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {
    @Query("select new com.example.giftlistb10.dto.complaint.ComplaintResponse(" +
            "ow.id, " +
            "concat(ow.firstName,' ',ow.lastName), " +
            "ui.image, " +
            "ui.phoneNumber, " +
            "ch.id, " +
            "ch.image, " +
            "rui.image, " +
            "ch.description, " +
            "ch.nameCharity, " +
            "ch.category, " +
            "ch.subCategory, " +
            "ch.condition, " +
            "TO_CHAR(ch.createdAt, 'YYYY-MM-DD'), " +
            "ch.status, " +
            "ch.isBlock) " +
            "from Charity ch " +
            "join ch.owner ow " +
            "join ow.userInfo ui " +
            "join ch.reservoir r " +
            "join r.userInfo rui where ch.id = :charityId")
    Optional<ComplaintResponse> getComplainByCharityId(Long charityId);


    @Query("select new com.example.giftlistb10.dto.complaint.ComplaintUserResponse(" +
            "fru.id, " +
            "concat(fru.firstName, ' ', fru.lastName), " +
            "ui.image, " +
            "c.statusComplaint, " +
            "c.textComplain," +
            "c.createdAt) " +
            "from Complain c " +
            "join c.fromUser fru " +
            "join c.charity ch " +
            "join fru.userInfo ui where ch.id = :charityId")
    List<ComplaintUserResponse> getComplaintsCharity(Long charityId);

    @Query("select new com.example.giftlistb10.dto.complaint.ComplaintResponse(" +
            "ow.id, " +
            "concat(ow.firstName, ' ', ow.lastName), " +
            "ui.image, " +
            "ui.phoneNumber, " +
            "w.id, " +
            "w.nameWish, " +
            "w.image, " +
            "h.nameHoliday, " +
            "h.dateOfHoliday, " +
            "w.statusWish, " +
            "w.isBlock) " +
            "from Wish w " +
            "join w.owner ow " +
            "join ow.userInfo ui " +
            "left join w.holiday h " +
            "where w.id = :wishId")
    Optional<ComplaintResponse> getComplainByWishId(Long wishId);

    @Query("select new com.example.giftlistb10.dto.complaint.ComplaintResponse(" +
            "ow.id, " +
            "concat(ow.firstName, ' ', ow.lastName), " +
            "ui.image, " +
            "ch.id, " +
            "ch.image, " +
            "ch.nameCharity, " +
            "ch.condition, " +
            "u.image, " +
            "TO_CHAR(ch.createdAt, 'YYYY-MM-DD')) " +
            "from Charity ch " +
            "join ch.owner ow " +
            "join ow.userInfo ui " +
            "join ch.reservoir r " +
            "join r.userInfo u " +
            "left join ch.complains c " +
            "where c is not null")
    List<ComplaintResponse> getCharitiesWithComplaints();

    @Query("select new com.example.giftlistb10.dto.complaint.ComplaintUserResponse(" +
            "fru.id, " +
            "concat(fru.firstName, ' ', fru.lastName), " +
            "ui.image, " +
            "c.statusComplaint, " +
            "c.textComplain, " +
            "c.createdAt) " +
            "from Complain c " +
            "join c.fromUser fru " +
            "join c.wish w " +
            "join fru.userInfo ui where w.id = :wishId")
    List<ComplaintUserResponse> getComplaintsWish(Long wishId);
}