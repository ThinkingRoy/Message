package net.mckitsu.message;

import java.io.File;
import java.util.List;

public class FileDistinguish {

    public List<File> directory;

    public List<File> file;

    public boolean isNull(File[] files) {
        return files == null;
    }

    public void distinguishFile(File[] files){

        if(isNull(files)){throw new NullPointerException();}

        for(File file : files){
            if(file.isFile()){
                this.file.add(file);
            }else if(file.isDirectory()){
                this.directory.add(file);
            }
        }
    }

    public boolean isName(File[] files, String name) {

        if(isNull(files)){throw new NullPointerException();}

        for (File file : files){
            return file.getName().equalsIgnoreCase(name);
        }
        return false;
    }
}
