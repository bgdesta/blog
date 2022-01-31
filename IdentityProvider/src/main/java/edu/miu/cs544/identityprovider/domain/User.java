package edu.miu.cs544.identityprovider.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String imageUrl;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    private UserSecurity userSecurity;

    public void setUserSecurity(UserSecurity userSecurity) {
        this.userSecurity = userSecurity;
        userSecurity.setUser(this);
    }
}
