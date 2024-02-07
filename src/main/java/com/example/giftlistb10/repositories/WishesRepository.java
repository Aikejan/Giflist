package com.example.giftlistb10.repositories;

import com.example.giftlistb10.entities.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishesRepository extends JpaRepository<Wish,Long> {
}