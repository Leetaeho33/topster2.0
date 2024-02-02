package com.sparta.topster.domain.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class NotificationRepository {
    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
}
