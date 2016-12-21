package com.rackian.model.json;

public interface JsonParser <T> {
    
    String objectToJson(T object);
    
    T jsonToObject(String json, Class c);
    
}
