package com.hossain.context.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.util.context.ContextView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ContextExampleController {

    @GetMapping("/test-context")
    public Mono<Map<String, Object>> testContext() {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();
        return Mono.just(true)
                .doOnEach((Signal signal) -> {
                    if (signal.isOnNext()) {
                        ContextView context = signal.getContextView();
                        //do whatever you want to do with context.
                        dataMap.put("fromContext", context.get("testKey"));
                    }
                }).flatMap(value -> Mono.just(dataMap));
    }
}
