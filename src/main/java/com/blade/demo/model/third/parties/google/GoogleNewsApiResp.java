package com.blade.demo.model.third.parties.google;

import com.blade.demo.model.message.News;
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
        private ArticleSource source;
        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private Timestamp publishedAt;

    }

    @Data
    public static class ArticleSource {
        private String id;
        private String name;
    }

}
