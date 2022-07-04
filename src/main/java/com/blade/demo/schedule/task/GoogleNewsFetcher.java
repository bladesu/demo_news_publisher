package com.blade.demo.schedule.task;

import com.blade.demo.model.third.parties.google.GoogleNewsApiResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class GoogleNewsFetcher {

    @Value("${third.parties.google.news.api.url}")
    private String apiUrl;
    @Value("${third.parties.google.news.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;


    public GoogleNewsFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<GoogleNewsApiResp> getResponseFromTopHeadlines() {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .path("/top-headlines")
                .queryParam("country", "tw")
                .queryParam("apiKey", apiKey)
                .build()
                .toUri();
        return restTemplate.getForEntity(uri, GoogleNewsApiResp.class);
    }

    public Optional<List<GoogleNewsApiResp.Article>> getArticlesFromTopHeadlines() {
        return Optional.ofNullable(getResponseFromTopHeadlines())
                .filter(e -> Objects.equals(e.getStatusCode(), HttpStatus.OK))
                .map(ResponseEntity::getBody)
                .map(GoogleNewsApiResp::getArticles);
    }
}