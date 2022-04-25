package local.restweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan
public class RestWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWebApplication.class, args);
    }
}
