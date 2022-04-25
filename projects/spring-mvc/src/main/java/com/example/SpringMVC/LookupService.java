package com.example.SpringMVC;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class LookupService {

    private static final Logger logger = LoggerFactory.getLogger(LookupService.class);
    private static final String URL = "https://api.github.com/users/%s";
    private final RestTemplate restTemplate;

    public LookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<User> getInfo(String username) throws InterruptedException {
        logger.info("Start: {}", username);
        String url = String.format(URL, username);
        User user = restTemplate.getForObject(url, User.class);
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(user);
    }
}
