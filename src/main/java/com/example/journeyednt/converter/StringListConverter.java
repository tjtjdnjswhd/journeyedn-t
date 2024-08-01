package com.example.journeyednt.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if (strings == null) {
            return "";
        }
        return mapper.writeValueAsString(strings);
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }
        TypeReference<List<String>> typeRef = new TypeReference<>() {};
        return mapper.readValue(s, typeRef);
    }
}