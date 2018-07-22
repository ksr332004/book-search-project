package com.seran.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Strings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    
    private String title;
    private String contents;
    private String url;
    private String isbn;
    private String datetime;
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
    
    public String getDatetimeString() {
        if ("".equals(Strings.isNullOrEmpty(datetime))) {
            return "";  
        } else {
            DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime date = LocalDateTime.parse(datetime, format);
            return date.format(dTF);
        }
    }
    
}
