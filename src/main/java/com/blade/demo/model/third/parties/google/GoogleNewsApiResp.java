package com.blade.demo.model.third.parties.google;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
public class GoogleNewsApiResp {

    private String status;
    private Integer totalResults;
    private List<Article> articles;

    @Data
    public static class Article {
        Map<String, String> source;
        String author;
        String title;
        String description;
        String url;
        String urlToImage;
        Timestamp publishedAt;

    }

}
