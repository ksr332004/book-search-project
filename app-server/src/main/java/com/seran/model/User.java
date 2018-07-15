package com.seran.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "user", sequenceName = "user_id", initialValue = 1, allocationSize = 1)
public class User {
    
     @Id
     @Column(name = "user_id")
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
     private Integer id;
     
     @Column(name = "email")
     private String email;

     @Column(name = "password")
     @Transient
     private String password;

     @Column(name = "name")
     private String name;

     @Column(name = "registration_date")
     private LocalDateTime registrationDate = LocalDateTime.now();
     
     @Column(name = "available")
     private String available = "Y";
     
     @Column(name = "role")
     private String role;
     
     @OneToMany
     @JoinColumn(name = "bookmark_id", referencedColumnName = "user_id")
     private List<Bookmark> bookmarks = new ArrayList<>();
     
     @OneToMany
     @JoinColumn(name = "history_id", referencedColumnName = "user_id")
     private List<History> histories = new ArrayList<History>();
     
}
