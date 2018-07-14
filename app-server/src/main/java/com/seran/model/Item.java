package com.demo.model;

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
@SequenceGenerator(name = "item", sequenceName = "item_id", initialValue = 1, allocationSize = 1)
public class Item {

	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item")
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "contents")
    private String contents;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "isbn")
	private String isbn;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Column(name = "authors")
	private String authors;
	
	@Column(name = "publisher")
	private String publisher;
	
	@Column(name = "translators")
	private String translators;
	
	@Column(name = "price")
    private Integer price;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "thumbnail")
	private String thumbnail;

}
