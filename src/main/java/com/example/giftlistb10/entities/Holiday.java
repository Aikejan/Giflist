package com.example.giftlistb10.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "holidays")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "holiday_gen")
    @SequenceGenerator(name = "holiday_gen",
            sequenceName = "holiday_seq",
            allocationSize = 1,
            initialValue = 8)
    private Long id;
    private String nameHoliday;
    private LocalDate dateOfHoliday;
    private String image;
    @ManyToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE},fetch=FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "holiday", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE})
    private List<Wish> wishes;
}