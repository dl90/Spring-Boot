package com.example.SpringMVC;

import java.util.concurrent.Executor;

import com.example.SpringMVC.api.ProductRepository;
import com.example.SpringMVC.api.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@SpringBootApplication
@EnableAsync
@EnableCaching
public class SpringMvcApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);
    }

    @Bean
    // async multithreading
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("sample-");
        executor.initialize();
        return executor;
    }

    @Bean
    // runtime initial exec
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            System.out.println("inspect the beans provided by Spring Boot");
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
        };
    }

    @Override
    // db seeding
    public void run(String... args) throws Exception {
        productRepository.save(new Product("MBP-13", "Electronics"));
        productRepository.save(new Product("MBP-14", "Electronics"));
        productRepository.save(new Product("MBP-16", "Electronics"));
        productRepository.save(new Product("S21", "Phones"));
        productRepository.save(new Product("Iphone 13", "Phones"));
    }

}
