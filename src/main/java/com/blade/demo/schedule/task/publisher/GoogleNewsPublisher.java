package com.blade.demo.schedule.task.publisher;

import com.blade.demo.component.NewsHelper;
import com.blade.demo.misc.KafkaMessageInfo;
import com.blade.demo.model.message.News;
import com.blade.demo.model.third.parties.google.GoogleNewsApiResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * With binding topic and data model, this component can publish data to kafka service.
 */
@Component
public class GoogleNewsPublisher {

    private final static Logger logger = LoggerFactory.getLogger(GoogleNewsPublisher.class);

//    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final NewsHelper newsHelper;

    public GoogleNewsPublisher(
//            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            NewsHelper newsHelper
    ) {
//        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.newsHelper = newsHelper;
    }

    public void publish(GoogleNewsApiResp.Article rawData) {
        Optional<String> jsonOpt;
        try {
            String sourceData = String.format("%s/%s", rawData.getSource().getName(), rawData.getSource().getId());
            News news = newsHelper.create(sourceData, rawData.getAuthor(), rawData.getTitle(), rawData.getDescription());
            String json = objectMapper.writeValueAsString(news);
            logger.debug("Parsed json data:{}", json);
            jsonOpt = Optional.ofNullable(json);
        } catch (JsonProcessingException e) {
            jsonOpt = Optional.empty();
            if (Objects.nonNull(rawData)) {
                logger.error("Fail to transfer data:{}", rawData.toString());
            } else {
                logger.error("Fail to transfer null data");
            }
        }
//        jsonOpt.ifPresent(json -> kafkaTemplate.send(KafkaMessageInfo.COMMON_TOPIC_NEWS, json));
    }

}
