package com.seran.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Strings;

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
    private OffsetDateTime datetime;
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
    
    public String getAuthorString() {
        String author = "";
        if (!authors.isEmpty()) {
            if (authors.size() > 1) {
                author = authors.get(0) + " 외 " + (authors.size() - 1) + " 명 지음";
            } else {
                author = authors.get(0) + " 지음";
            }
        }
        return author;
    }

    public String getTranslatorString() {
        String translator = "";
        if (!translators.isEmpty()) {
            if (authors.size() > 1) {
                translator = translators.get(0) + " 외 " + (translators.size() - 1) + " 명 번역";
            } else {
                translator = translators.get(0) + " 번역";
            }
        }
        return translator;
    }
    
    public String getPublishDate() {
        if ("".equals(Strings.isNullOrEmpty(datetime.toString()))) {
            return "";  
        } else {
            DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(datetime.toString(), DateTimeFormatter.BASIC_ISO_DATE);
            return offsetDateTime.format(dTF);        
        }
    }
    
}
