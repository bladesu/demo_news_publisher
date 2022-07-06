package com.blade.demo.consumer;

import com.blade.demo.component.WebsocketSessionManager;
import com.blade.demo.misc.KafkaMessageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;

/**
 * With binding topic and data model, this component can publish data to kafka service.
 */
@Component
public class GoogleNewsConsumer {

    private final static Logger logger = LoggerFactory.getLogger(GoogleNewsConsumer.class);

    private final ObjectMapper objectMapper;


    public GoogleNewsConsumer(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = KafkaMessageInfo.COMMON_TOPIC_NEWS,
            // random group_id, that is each consumer should read message independently.
            groupId = "#{T(java.util.UUID).randomUUID().toString()}"
    )
    public void listen(String message) {
        logger.debug("Received Message in group foo: " + message);
        Collection<WebSocketSession> webSocketSessions = WebsocketSessionManager.getCurrentSessions();
        logger.debug("Broadcasting to client with count={}", webSocketSessions.stream().count());
        webSocketSessions.forEach(
                webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        logger.error("Fail to send message to websocket client.");
                    }
                }
        );

    }
}
