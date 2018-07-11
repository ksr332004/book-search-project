package com.demo.model;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class Parameter {
    
    private String query;
    private String sort;
    private Integer page;
    private Integer size;
    private String target;
    private Integer category;
    
    public String getUrl(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("query", getQuery());
        if (!getSort().isEmpty()) {
            builder.queryParam("sort", getSort());
        }
        if (getPage() != null && getPage() > 0) {
            builder.queryParam("page", getPage());
        }
        if (getSize() != null && getSize() > 0) {
            builder.queryParam("size", getSize());
        }
        if (!getTarget().isEmpty()) {
            builder.queryParam("target", getTarget());
        }
        if (getCategory() != null && getCategory() > 0) {
            builder.queryParam("category", getCategory());
        }
        return builder.build().toString();
    }

}
