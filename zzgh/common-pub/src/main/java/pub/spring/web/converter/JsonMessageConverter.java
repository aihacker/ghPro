package pub.spring.web.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2015/6/22
 */
public class JsonMessageConverter extends MappingJackson2HttpMessageConverter {

    public JsonMessageConverter() {
        super(buildObjectMapper());
    }

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .failOnUnknownProperties(false).build();
        return objectMapper;
    }

}
