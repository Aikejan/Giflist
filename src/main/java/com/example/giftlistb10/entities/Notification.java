package com.example.giftlistb10.entities;

import com.example.giftlistb10.enums.Request;
import com.example.giftlistb10.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "notifications")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_gen")
    @SequenceGenerator(
            name = "notification_gen",
            sequenceName = "notification_seq",
            allocationSize = 1,
            initialValue = 23)
    private Long id;
    private LocalDate localDate;
    private boolean seen;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Request request;
    @ElementCollection
    private List<Long> seenUserId;
    @OneToOne(mappedBy = "notification", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private Complain complain;
    @OneToOne(mappedBy = "notification",cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private Wish wish;
    @OneToOne(mappedBy = "notification", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private Charity charity;
    @ManyToMany(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<User> toUser;
    @ManyToMany(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<User> fromUser;
}