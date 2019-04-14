package com.github.everything.core.index;

import com.github.everything.core.interceptor.FileInterceptor;

public interface FileScan {
    /**
     * 将path路径下所有目录和文件递归扫描
     * 索引到数据库
     * @param path
     */
    void index(String path);

    void interceptor(FileInterceptor interceptor);
}
