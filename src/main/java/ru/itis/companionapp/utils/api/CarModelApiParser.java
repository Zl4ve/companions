package ru.itis.companionapp.utils.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CarModelApiParser {

    private final ObjectMapper objectMapper;

    public CarModelApiParser() {
        this.objectMapper = new ObjectMapper();
    }

    public List<String> parse(String json) {

        try {
            Set<String> models = new HashSet<>();
            JsonNode res = objectMapper.readTree(json);
            if (res.size() > 0) {
                for (JsonNode object : res) {
                    models.add(object.get("model").asText());
                }
                return new ArrayList<>(models);
            }
        } catch (JsonProcessingException e) {
        }

        return new ArrayList<>();
    }
}
