package id.altanovela.funding.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    
    public static String toJsonString(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            log.error("Error", e);
        }
        return null;
    }
    
    public static <T> T toObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Error", e);
        }
        return null;
    }
}
