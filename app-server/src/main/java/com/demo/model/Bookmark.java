package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_item")
public class Bookmark {

    @Id
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "item_id")
    private Integer itemId;
}
