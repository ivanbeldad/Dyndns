package com.rackian.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonParser<T> implements JsonParser<T> {
    
    public String objectToJson(T object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public T jsonToObject(String json, Class c) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T) mapper.readValue(json, c);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
