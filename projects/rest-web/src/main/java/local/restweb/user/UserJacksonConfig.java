package local.restweb.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserJacksonConfig {
    public UserJacksonConfig(ObjectMapper objectMapper) {
        objectMapper
                .setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false))
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
