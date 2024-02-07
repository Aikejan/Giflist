package com.example.giftlistb10.entities;

import com.example.giftlistb10.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_gen")
    @SequenceGenerator(name = "user_gen",
            sequenceName = "user_seq",
            allocationSize = 1,
            initialValue = 10)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isAgree;
    private boolean isBlock;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.REFRESH})
    private List<Charity> charities;
    @OneToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.PERSIST})
    private UserInfo userInfo;
    @ManyToMany(mappedBy = "users", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<MailingList> mailingList;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE})
    private List<Holiday> holidays;
    @ElementCollection
    private List<Long> friends;
    @ManyToMany(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<User> request;
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REMOVE})
    private List<Wish> wishes;
    @ManyToMany(mappedBy = "toUser",cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<Notification> notifications;
    @ManyToMany(mappedBy = "fromUser",cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    private List<Complain> complains;

    public void addRequest(User user) {
        request.add(user);
    }

    public void addFriends(User user) {
        friends.add(user.getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}