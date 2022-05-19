package beans.annotations;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "beans.annotations")
public class Config {

    @Bean
    public Developer developer() {
        return new Developer("write code");
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Designer designer() {
        return new Designer("test");
    }

    @Bean
    public static Lifecycle lifecycle() {
        return new Lifecycle();
    }
}
