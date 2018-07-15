package com.seran.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime newDate = LocalDateTime.now();
    
}
