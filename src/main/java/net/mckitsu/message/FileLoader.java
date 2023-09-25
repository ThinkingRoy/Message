package net.mckitsu.message;

import java.io.File;
import java.io.IOException;

public class FileLoader {
    public void createFile (File file) {

        try{
            file.createNewFile();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean deletedFile (File file){
        try{
            file.delete();
            return true;
        }catch (SecurityException securityException){
            throw new RuntimeException();
        }

    }
    public boolean exists (File file){
        return file.exists();
    }
    public String path (File file){
        file.getPath();
        return file.getPath();
    }
    public long size (File file){
        try{
            file.length();
            return file.length();
        }catch (SecurityException securityException){
            throw new RuntimeException();
        }
    }
    public String fileName (File file){
        file.getName();
        return file.getName();
    }

}
