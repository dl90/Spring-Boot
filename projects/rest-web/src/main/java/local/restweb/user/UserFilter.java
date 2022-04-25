package local.restweb.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


public class UserFilter {

    public enum Filters {
        NO_FILTER,
        EXCLUDE_MSG1,
        EXCLUDE_MSG2
    }

    public static User filter(User user, Filters filters) {

        SimpleBeanPropertyFilter simpleFilter = SimpleBeanPropertyFilter.serializeAllExcept("msg1");

        switch (filters) {
            case NO_FILTER -> simpleFilter = SimpleBeanPropertyFilter.serializeAll();
            case EXCLUDE_MSG1 -> simpleFilter = SimpleBeanPropertyFilter.serializeAllExcept("msg1");
            case EXCLUDE_MSG2 -> simpleFilter = SimpleBeanPropertyFilter.serializeAllExcept("msg2");
        }

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("MsgFilter", simpleFilter);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        ObjectWriter writer = mapper.writer(filterProvider);

        try {
            String writeValueAsString = writer.writeValueAsString(user);
            User filteredUser = mapper.readValue(writeValueAsString, User.class);
            return filteredUser;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
