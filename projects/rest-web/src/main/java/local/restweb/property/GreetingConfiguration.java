package local.restweb.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GreetingConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "greeting")
    Greeting createGreetingBean() {
        return new Greeting();
    }
}
