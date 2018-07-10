package com.demo.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
     
     @Column(name = "user_id")
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
     private Integer id;
     
     @Email(message = "*Please provide a valid Email")
 	 @NotEmpty(message = "*Please provide an email")
     private String email;

     @Column(name = "password")
     @Length(min = 6, message = "*Your password must have at least 5 characters")
 	 @NotEmpty(message = "*Please provide your password")
     @Transient
     private String password;

     @Column(name = "name")
     @NotEmpty(message = "*Please provide your name")
     private String name;

     @Column(name = "registration_date")
     private LocalDateTime registrationDate;
     
     @ManyToMany(cascade = CascadeType.ALL)
     @JoinTable(name = "user_item", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
     private Set<Item> items;
     
}
