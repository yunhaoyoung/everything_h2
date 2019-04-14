package com.github.everything.core.common;

import com.github.everything.core.model.FileType;
import com.github.everything.core.model.Thing;

import java.io.File;

public class FileConvertThing {
    public static Thing convert(File file){
        Thing thing = new Thing();
        String name = file.getName();
        thing.setName(name);
        thing.setPath(file.getAbsolutePath());
        int index = name.lastIndexOf(".");
        String extend = "*";
        if(index != -1 && (index+1) < name.length()){
            name.substring(index+1);
        }
        thing.setFileType(FileType.lookupByExtend(extend));
        thing.setDepth(computePathDepth(file.getAbsolutePath()));

        return thing;
    }

    private static int computePathDepth(String path){
        int depth = 0;
        for(char c: path.toCharArray()){
            if(c == File.separatorChar){
                depth += 1;
            }
        }
        return depth;
    }
}
