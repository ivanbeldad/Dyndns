package rackian.com.model.json;

public interface JsonParser <T> {
    
    String objectToJson(T object);
    
    T jsonToObject(String json, Class c);
    
}
