package com.seran.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "user_history", sequenceName = "history_id", initialValue = 1, allocationSize = 1)
public class History {

    @Id
    @Column(name = "history_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_history")
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "search")
    @NotEmpty(message = "*Please provide your search word.")
    private String search;
    
    @Column(name = "new_date")
    private LocalDateTime newDate = LocalDateTime.now();
    
}
