package com.blade.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebsocketSessionManager {

    private static Logger logger = LoggerFactory.getLogger(WebsocketSessionManager.class);

    private static ConcurrentHashMap<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    public static void add(String key, WebSocketSession session) {
        SESSIONS.put(key, session);
    }

    public static void remove(String key) {
        Optional.ofNullable(SESSIONS.get(key))
                .ifPresent(webSocketSession -> {
                    try {
                        webSocketSession.close();
                    } catch (IOException e) {
                        logger.error("Fail to close the session for {}, message:{}", key, e.getMessage());
                    } finally {
                        SESSIONS.remove(key);
                    }
                }
        );
    }

    public static Collection<WebSocketSession> getCurrentSessions() {
        return SESSIONS.values();
    }

}
