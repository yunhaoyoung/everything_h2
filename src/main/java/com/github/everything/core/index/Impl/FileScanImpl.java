package com.github.everything.core.index.Impl;

import com.github.everything.config.EverythingConfig;
import com.github.everything.core.index.FileScan;
import com.github.everything.core.interceptor.FileInterceptor;

import java.io.File;
import java.util.LinkedList;
import java.util.Set;

public class FileScanImpl implements FileScan {

    private final LinkedList<FileInterceptor> interceptors = new LinkedList<>();

    private EverythingConfig config = EverythingConfig.getInstance();
    @Override
    public void index(String path) {
        Set<String> excludePaths = config.getHandlerPath().getExcludePath();
        for (String excludepath : excludePaths){
            if(path.startsWith(excludepath)){
                return;
            }
        }
        File file = new File(path);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null){
                for (File f : files){
                    index(f.getAbsolutePath());
                }
            }
        }
        for (FileInterceptor interceptor : this.interceptors){
            interceptor.apply(file);
        }
    }

    @Override
    public void interceptor(FileInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }
}
