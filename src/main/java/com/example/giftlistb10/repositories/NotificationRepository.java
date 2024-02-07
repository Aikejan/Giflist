package com.example.giftlistb10.repositories;

import com.example.giftlistb10.entities.Notification;
import com.example.giftlistb10.entities.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n " +
            "FROM User u JOIN u.notifications n " +
            "JOIN n.fromUser fr " +
            "JOIN u.userInfo ui " +
            "WHERE u.email = :email")
    List<Notification> getAllNotify(@Param("email") String email);
}