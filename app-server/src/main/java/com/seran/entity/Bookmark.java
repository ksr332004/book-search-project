package com.seran.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "key_barcode", length = 50, nullable = false)
    private String keyBarcode;
    
    @Column(name = "title", length = 256)
    private String title;
    
    @Column(name = "contents", length = 256)
    private String contents;
    
    @Column(name = "url", length = 256)
    private String url;
    
    @Column(name = "isbn", length = 100)
    private String isbn;
    
    @Column(name = "create_date")
    private OffsetDateTime createDate;
    
    @Column(name = "authors", length = 256)
    private String authors;
    
    @Column(name = "publisher", length = 200)
    private String publisher;
    
    @Column(name = "translators", length = 256)
    private String translators;
    
    @Column(name = "price")
    private Integer price;
    
    @Column(name = "sale_price")
    private Integer salePrice;

    @Column(name = "sale_yn", length = 3)
    private String saleYN;
    
    @Column(name = "category", length = 50)
    private String category;
    
    @Column(name = "thumbnail", length = 256)
    private String thumbnail;
    
    @Column(name = "barcode", length = 20)
    private String barcode;
    
    @Column(name = "ebook_barcode", length = 20)
    private String ebookBarcode;

    @Column(name = "status", length = 20)
    private String status;
    
    @Column(name = "new_date")
    private LocalDateTime newDate = LocalDateTime.now();
    
}