package com.seran.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="history")
public class History {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "search")
    @NotEmpty(message = "*Please provide your search word.")
    private String search;
    
    @Column(name = "new_date")
    private LocalDateTime newDate = LocalDateTime.now();
    
}
