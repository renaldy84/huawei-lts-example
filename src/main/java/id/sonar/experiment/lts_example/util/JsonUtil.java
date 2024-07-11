package id.sonar.experiment.lts_example.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.ReadContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

	public static Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	public static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	public static ObjectMapper objectMapperIntoSnakeCase =
				new Jackson2ObjectMapperBuilder()
				.featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, JsonParser.Feature.ALLOW_COMMENTS)
				.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
				.serializationInclusion(JsonInclude.Include.NON_NULL).modules(new JavaTimeModule())
				.build();

	public static <T> T fromJson(String json, Class<T> t) {
		return gson.fromJson(json, t);
	}
	
	public static <T> T fromJson(String json, Type t) {
		return gson.fromJson(json, t);
	}

	public static <T> String toJson(T t) {
		return gson.toJson(t);
	}

	public static <T> T mapToObject(Map<?, ?> map, Class<T> t) {
		return fromJson(toJson(map), t);
	}
	
	public static <T> T mapToObject(Map<?, ?> map, Type t) {
		return fromJson(toJson(map), t);
	}
	
	public static <T> List<T> listMapToObject(List<Map<?, ?>> listMap, Class<T> t){
		return listMap.stream().map(ls-> mapToObject(ls, t)).collect(Collectors.toList());
	}
	
	public static String minifyJson(String json) {
	    try {
			JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
			return jsonNode.toString();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return StringUtil.EMPTY;
	}
	

	public static <T> String toJsonJackson(T t) {
		try {
			return objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return StringUtil.EMPTY;
	}
	
	public static <T> T fromJsonJackson(String json, Class<T> t) {
		try {
			return objectMapper.readValue(json, t);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	public static <T> T fromJsonJacksonSnakeCase(String json, Class<T> t) {
		try {
			return objectMapperIntoSnakeCase.readValue(json, t);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> T fromJsonJacksonSnakeCase(String json, TypeReference<T> t) {
		try {
			return objectMapperIntoSnakeCase.readValue(json, t);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static <T> String toJsonJacksonSnakeCase(T t) {
		try {
			return objectMapperIntoSnakeCase.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return StringUtil.EMPTY;
	}
	
	@SuppressWarnings("unchecked")
	public static long getLongValue(ReadContext ctx, String filter) {
        try {
            Object obj = ctx.read(filter);
            if (obj instanceof List) {
                List<Number> list = (List<Number>)obj;
                if (list == null || list.isEmpty())
                    return 0l;
                return list.get(0).longValue();
            } else {
                Number num = (Number)obj;
                if (num==null)
                    return 0l;
                return num.longValue();
            }
        } catch (Exception e) {
            return 0l;
        }
    }

    public static String getStringValue(ReadContext ctx, String filter) {
        try {
            Object obj = ctx.read(filter);
            if (obj instanceof String)
                return ((String)obj);
            else
                return obj.toString();
        } catch (Exception e) {
            return null;
        }
    }
    
    
	public static Boolean checkExistsPath(ReadContext ctx, String filter) {
        try {
            Object obj = ctx.read(filter);
            return obj != null;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }


}
