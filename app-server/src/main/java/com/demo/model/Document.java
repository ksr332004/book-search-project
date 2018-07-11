package com.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    
    private String title;
    private String contents;
    private String url;
    private String isbn;
    private LocalDateTime datetime;
    @JsonProperty("authors")
    private List<String> authors = new ArrayList<>();
    private String publisher;
    @JsonProperty("translators")
    private List<String> translators = new ArrayList<>();
    private String price;
    private String sale_price;
    private String sale_yn;
    private String category;
    private String thumbnail;
    private String barcode;
    private String ebook_barcode;
    private String status;
    
}
