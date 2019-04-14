package com.github.everything.core.serach.impl;

import com.github.everything.core.dao.FileIndexDao;
import com.github.everything.core.interceptor.Impl.ThingClearInterceptor;
import com.github.everything.core.interceptor.ThingInterceptor;
import com.github.everything.core.model.Condition;
import com.github.everything.core.model.Thing;
import com.github.everything.core.serach.ThingSearch;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThingSearchImpl implements ThingSearch {
    private final FileIndexDao fileIndexDao;

    private final ThingClearInterceptor interceptor;

    private final Queue<Thing> thingQueue = new ArrayBlockingQueue<>(1024);

    public ThingSearchImpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
        this.interceptor = new ThingClearInterceptor(this.fileIndexDao, thingQueue);
        this.backgroundClearThread();
    }

    @Override
    public List<Thing> search(Condition condition) {
        List<Thing> things =  this.fileIndexDao.query(condition);
        Iterator<Thing> iterator = things.iterator();
        while (iterator.hasNext()){
            Thing thing = iterator.next();
            File file = new File(thing.getPath());
            if (file.exists()){
                iterator.remove();
                this.thingQueue.add(thing);
            }
        }
        return things;
    }

    private void backgroundClearThread(){
        Thread thread = new Thread(this.interceptor);
        thread.setName("Thread-Clear");
        thread.setDaemon(true);
        thread.start();
    }
}
