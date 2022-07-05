package com.blade.demo.schedule;

import com.blade.demo.misc.ScheduledJobInfo;
import com.blade.demo.model.third.parties.google.GoogleNewsApiResp;
import com.blade.demo.schedule.task.loader.GoogleNewsFetcher;
import com.blade.demo.schedule.task.publisher.GoogleNewsPublisher;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
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
    private final GoogleNewsPublisher googleNewsPublisher;

    public ScheduledJobs(
            GoogleNewsFetcher googleNewsFetcher,
            GoogleNewsPublisher googleNewsPublisher
    ) {
        this.googleNewsFetcher = googleNewsFetcher;
        this.googleNewsPublisher = googleNewsPublisher;
    }

    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(
            name= ScheduledJobInfo.LOCK_KEY_GOOGLE_NEWS_PUBLISHER,
            lockAtMostFor = "4s",
            lockAtLeastFor = "1s"
    )
    @Async
    public void fetchGoogleNews() {
        logger.info("fetch google news");
        Optional<List<GoogleNewsApiResp.Article>> articleOptional = googleNewsFetcher.getArticlesFromTopHeadlines();
        logger.info("Fetched news count:{}", articleOptional.map(List::size).orElse(0));
        logger.info("Publish news to message:");
        articleOptional.ifPresent(articles -> articles.stream().forEach(googleNewsPublisher::publish));
        logger.info("Fetched news count:{}", articleOptional.map(List::size).orElse(0));
    }

}
