package com.seran.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "user", sequenceName = "user_id", initialValue = 1, allocationSize = 1)
public class User {
    
     @Id
     @Column(name = "user_id", nullable = false)
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
     private Integer id;
     
     @Column(name = "email", length = 200, nullable = false, unique = true)
     @Email(message = "*Please provide a valid email.")
     @NotEmpty(message = "*Please provide an email.")
     private String email;

     @Column(name = "password", length = 50, nullable = false)
     @Transient
     @Length(min = 7, message = "*Your password must have at least 7 characters.")
     @NotEmpty(message = "*Please provide your password.")
     private String password;

     @Column(name = "name", length = 100)
     @NotEmpty(message = "*Please provide your name.")
     private String name;

     @Column(name = "registration_date")
     private LocalDateTime registrationDate = LocalDateTime.now();
     
     @Column(name = "available", length = 3)
     private String available = "Y";
     
     @Column(name = "role", length = 20, nullable = false)
     @NotEmpty(message = "*Please provide user role.")
     private String role;
     
     @OneToMany
     @JoinColumn(name = "bookmark_id", referencedColumnName = "user_id")
     private List<Bookmark> bookmarks = new ArrayList<>();
     
     @OneToMany
     @JoinColumn(name = "history_id", referencedColumnName = "user_id")
     private List<History> histories = new ArrayList<History>();
     
}
