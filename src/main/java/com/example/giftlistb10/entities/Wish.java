package com.example.giftlistb10.entities;

import com.example.giftlistb10.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wish {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "wish_gen")
    @SequenceGenerator(name = "wish_gen",
            sequenceName = "wish_seq",
            allocationSize = 1,
            initialValue = 10)
    private Long id;
    private String nameWish;
    @Column(length = 3000)
    private String linkToGift;
    private String image;
    private boolean isBlock;
    private String description;
    private LocalDate dateOfHoliday;
    @ElementCollection
    private List<Long> isAllReadyInWishList = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status statusWish;
    @ManyToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE},fetch=FetchType.LAZY)
    private User reservoir;
    @ManyToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE},fetch=FetchType.LAZY)
    private User owner;
    @ManyToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST},fetch=FetchType.LAZY)
    private Holiday holiday;
    @OneToMany(mappedBy = "wish", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE})
    private List<Complain> complains;
    @OneToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE})
    private Notification notification;
}