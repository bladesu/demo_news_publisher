package com.blade.demo.schedule;

import com.blade.demo.model.third.parties.google.GoogleNewsApiResp;
import com.blade.demo.schedule.task.GoogleNewsFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduledJobs {

    private final static Logger logger = LoggerFactory.getLogger(ScheduledJobs.class);
    private final GoogleNewsFetcher googleNewsFetcher;

    public ScheduledJobs(
            GoogleNewsFetcher googleNewsFetcher
    ) {
        this.googleNewsFetcher = googleNewsFetcher;
    }

    @Scheduled(fixedDelay = 5000)
    @Async
    public void fetchGoogleNews() {
        logger.info("fetch google news");
        Optional<List<GoogleNewsApiResp.Article>> articleOptional = googleNewsFetcher.getArticlesFromTopHeadlines();
        logger.info("Fetched news count:{}", articleOptional.map(List::size).orElse(0));
    }
}
