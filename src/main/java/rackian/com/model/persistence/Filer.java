package rackian.com.model.persistence;

public interface Filer {
    
    boolean save(String content);
    
    String getContent();
    
}
