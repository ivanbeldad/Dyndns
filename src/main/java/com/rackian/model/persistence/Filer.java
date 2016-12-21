package com.rackian.model.persistence;

public interface Filer {
    
    boolean save(String content);
    
    String getContent();
    
}
