package com.example.giftlistb10.repositories;

import com.example.giftlistb10.entities.MailingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingRepository extends JpaRepository<MailingList,Long> {
}