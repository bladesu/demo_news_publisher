package com.blade.demo.schedule.task;

import com.blade.demo.DemoApplication;
import com.blade.demo.model.third.parties.google.GoogleNewsApiResp;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    classes = DemoApplication.class
)
@DirtiesContext
class GoogleNewsFetcherTest {

    private final static Logger logger = LoggerFactory.getLogger(GoogleNewsFetcherTest.class);

    @Autowired
    GoogleNewsFetcher googleNewsFetcher;

    // Not perfect test case because of dependence to google news service
    // just for easier check about the function for developer.
    @Test
    public void getTopHeadlines_whenNormal_thenSuccess() {
    ResponseEntity<GoogleNewsApiResp> responseEntity = googleNewsFetcher.getResponseFromTopHeadlines();
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    responseEntity.getBody().getArticles().forEach(e -> logger.info(e.toString()));
    }

}