package com.example.giftlistb10.entities;

import com.example.giftlistb10.enums.StatusComplaint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "complains")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Complain {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "complain_gen")
    @SequenceGenerator(name = "complain_gen",
            sequenceName = "complain_seq",
            allocationSize = 1,
            initialValue = 7)
    private Long id;
    private String textComplain;
    @ManyToOne( cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE},fetch=FetchType.LAZY)
    private Wish wish;
    @OneToOne( cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE})
    private Notification notification;
    @ManyToOne( cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE},fetch=FetchType.LAZY)
    private Charity charity;
    @ManyToMany(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<User> fromUser;
    @Column(name = "select_complain")
    @Enumerated(EnumType.STRING)
    private StatusComplaint statusComplaint;
    private LocalDate createdAt;
}