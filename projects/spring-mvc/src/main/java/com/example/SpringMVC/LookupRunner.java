package com.example.SpringMVC;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class LookupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LookupRunner.class);

    @Autowired
    private LookupService lookupService;

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture<User> u_1 = lookupService.getInfo("Pytorch");
        CompletableFuture<User> u_2 = lookupService.getInfo("spring-boot");
        CompletableFuture<User> u_3 = lookupService.getInfo("spring-mvc");
        CompletableFuture<User> u_4 = lookupService.getInfo("spring-security");

        CompletableFuture.allOf(u_1, u_2, u_3, u_4).join();
        logger.info(u_1.get().toString());
        logger.info(u_2.get().toString());
        logger.info(u_3.get().toString());
        logger.info(u_4.get().toString());
    }
}
