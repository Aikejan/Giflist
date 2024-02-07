package com.example.giftlistb10.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mailingLists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailingList {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mailingList_gen")
    @SequenceGenerator(
            name = "mailingList_gen",
            sequenceName = "mailingList_seq",
            allocationSize = 1,
            initialValue = 4)
    private Long id;
    @Column(length = 10000)
    private String image;
    private String nameMailing;
    private String text;
    private LocalDate createdAt;
    @ManyToMany(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<User> users;
}