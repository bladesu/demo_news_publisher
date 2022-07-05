package com.blade.demo.schedule.task.publisher;

import com.blade.demo.misc.KafkaMessageInfo;
import com.blade.demo.model.third.parties.google.GoogleNewsApiResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * With binding topic and data model, this component can publish data to kafka service.
 */
@Component
public class GoogleNewsConsumer {

    private final static Logger logger = LoggerFactory.getLogger(GoogleNewsConsumer.class);

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public GoogleNewsConsumer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = KafkaMessageInfo.COMMON_TOPIC_NEWS,
            groupId = KafkaMessageInfo.COMMON_GROUP_ID
    )
    public void listen(String message) {
        logger.info("Received Message in group foo: " + message);
    }
}
