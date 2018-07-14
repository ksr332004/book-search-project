package com.seran.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "bookmark", sequenceName = "bookmark_id", initialValue = 1, allocationSize = 1)
@Table(name = "bookmark")
public class Bookmark {
    
    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmark")
    private Integer id;
    
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "key_barcode")
    private String keyBarcode;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "contents")
    private String contents;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "isbn")
    private String isbn;
    
    @Column(name = "create_date")
    private OffsetDateTime createDate;
    
    @Column(name = "authors")
    private String authors;
    
    @Column(name = "publisher")
    private String publisher;
    
    @Column(name = "translators")
    private String translators;
    
    @Column(name = "price")
    private Integer price;
    
    @Column(name = "sale_price")
    private Integer salePrice;

    @Column(name = "sale_yn")
    private String saleYN;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "thumbnail")
    private String thumbnail;
    
    @Column(name = "barcode")
    private String barcode;
    
    @Column(name = "ebook_barcode")
    private String ebookBarcode;

    @Column(name = "status")
    private String status;
    
    @Column(name = "new_date")
    private LocalDateTime newDate = LocalDateTime.now();
    
}