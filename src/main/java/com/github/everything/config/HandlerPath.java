package com.github.everything.config;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
public class HandlerPath {
    private Set<String> includePath = new HashSet<>();

    private Set<String> excludePath = new HashSet<>();

    private HandlerPath() {

    }

    public void addIncludePath(String path){
        this.includePath.add(path);
    }

    public void addExcludePath(String path){
        this.excludePath.add(path);
    }

    public static HandlerPath getDefaultHandlerPath(){
        HandlerPath handlerPath = new HandlerPath();
        Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();
        paths.forEach(path -> {
            handlerPath.addIncludePath(path.toString());
        });

        String systemName = System.getProperty("os.name");
        if (systemName.contains("Windows")){
            handlerPath.addExcludePath("C:\\Windows");
            handlerPath.addExcludePath("C:\\Program Files");
            handlerPath.addExcludePath("C:\\Program Files(x86)");
        } else {
            handlerPath.addExcludePath("/root");
            handlerPath.addExcludePath("/temp");
        }
        return handlerPath;
    }

    public static void main(String[] args) {
        System.out.println(HandlerPath.getDefaultHandlerPath());
    }
}
