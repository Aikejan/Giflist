package com.example.giftlistb10.entities;

import com.example.giftlistb10.enums.Category;
import com.example.giftlistb10.enums.Condition;
import com.example.giftlistb10.enums.Status;
import com.example.giftlistb10.enums.SubCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "charities")
@Getter
@Setter
public class Charity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "charity_gen")
    @SequenceGenerator(name = "charity_gen",
            sequenceName = "charity_seq",
            allocationSize = 1,
            initialValue = 9)
    private Long id;
    private String nameCharity;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;
    private String description;
    private String image;
    private LocalDate createdAt;
    private boolean isBlock;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Condition condition;
    @ManyToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE},fetch=FetchType.LAZY)
    private User reservoir;
    @ManyToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE},fetch=FetchType.LAZY)
    private User owner;
    @OneToOne( cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.PERSIST})
    private Notification notification;
    @OneToMany(mappedBy = "charity", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE})
    private List<Complain> complains;
    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }
}