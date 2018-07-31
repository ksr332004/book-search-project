package com.seran.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="history", uniqueConstraints={ @UniqueConstraint(columnNames = {"user_id", "search"})})
public class History implements Serializable {

    private static final long serialVersionUID = -7916976906969394345L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "search")
    @NotEmpty(message = "*Please provide your search word.")
    private String search;

    @Column(name = "target")
    private String target;

    @Column(name = "new_date")
    private LocalDateTime newDate = LocalDateTime.now();
    
}
