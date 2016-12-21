package com.rackian.model.persistence;

import java.io.*;

public class BasicFiler implements Filer {
    
    private File file;
    
    public BasicFiler(String filePath) {
        this.file = new File(filePath);
    }
    
    public BasicFiler(File file) {
        this.file = file;
    }
    
    @Override
    public boolean save(String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public String getContent() {
        try {
            String content = "";
            String line;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                content += line;
            }
            return content;
        } catch (Exception ex) {
            return null;
        }
    }
    
}
