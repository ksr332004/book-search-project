package com.seran.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "user_history", sequenceName = "history_id", initialValue = 1, allocationSize = 1)
public class History {

    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_history")
    private Integer id;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "search")
    private String search;
    
    @Column(name = "new_date")
    private LocalDateTime newDate;
    
}
