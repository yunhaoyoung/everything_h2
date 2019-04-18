package com.github.everything.core;

import com.github.everything.config.EverythingConfig;
import com.github.everything.config.HandlerPath;
import com.github.everything.core.dao.DataSourceFactory;
import com.github.everything.core.dao.FileIndexDao;
import com.github.everything.core.dao.impl.FileIndexDaoImpl;
import com.github.everything.core.index.FileScan;
import com.github.everything.core.index.Impl.FileScanImpl;
import com.github.everything.core.interceptor.Impl.FileIndexInterceptor;
import com.github.everything.core.interceptor.Impl.FilePrintInterceptor;
import com.github.everything.core.model.Condition;
import com.github.everything.core.model.Thing;
import com.github.everything.core.serach.ThingSearch;
import com.github.everything.core.serach.impl.ThingSearchImpl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EverythingManager {
    private static volatile EverythingManager manager;

    private FileScan fileScan;

    private ThingSearch thingSearch;

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

    private EverythingConfig config = EverythingConfig.getInstance();

    private EverythingManager(){
        this.fileScan = new FileScanImpl();
        //this.fileScan.interceptor(new FilePrintInterceptor());
        FileIndexDao fileIndexDao = new FileIndexDaoImpl(DataSourceFactory.getInstance());
        this.fileScan.interceptor(new FileIndexInterceptor(fileIndexDao));

        this.thingSearch = new ThingSearchImpl(fileIndexDao);
    }

    public static EverythingManager getInstance(){
        if(manager == null){
            synchronized (EverythingManager.class){
                if(manager == null){
                    manager = new EverythingManager();
                }
            }
        }
        return manager;
    }

    /**
     * 构建索引
     */
    public void buildIndex(){

        DataSourceFactory.databaseInit(true);
        HandlerPath handlerPath = config.getHandlerPath();
        Set<String> includePaths = handlerPath.getIncludePath();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Build Index Started ...");
                final CountDownLatch countDownLatch = new CountDownLatch(includePaths.size());
                for (String path : includePaths){
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            fileScan.index(path);
                            countDownLatch.countDown();
                        }
                    });
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Build Index complete ...");
            }
        }).start();

        System.out.println("Build complete");
    }
    /**
     * 检索功能
     */
    public List<Thing> search(Condition condition){
        condition.setLimit(config.getMaxReturn());
        condition.setOrderByDepthAsc(!config.getOrderByDesc());
        return this.thingSearch.search(condition);
    }

    /**
     * 帮助功能
     */
    public void help(){
        System.out.println("命令列表：");
        System.out.println("退出：quit");
        System.out.println("帮助：help");
        System.out.println("索引：index");
        System.out.println("搜索：search <name> [<file_Type> img | doc | bin | archive | other]");

    }

    /**
     * 退出功能
     */
    public void quit(){
        System.out.println("欢迎使用，再见");
        System.exit(0);
    }
}
