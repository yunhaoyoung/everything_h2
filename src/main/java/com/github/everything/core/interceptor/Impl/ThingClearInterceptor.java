package com.github.everything.core.interceptor.Impl;

import com.github.everything.core.dao.FileIndexDao;
import com.github.everything.core.interceptor.ThingInterceptor;
import com.github.everything.core.model.Thing;

import java.util.Queue;

public class ThingClearInterceptor implements ThingInterceptor , Runnable{

    private final FileIndexDao fileIndexDao;

    private final Queue<Thing> thingQueue;

    public ThingClearInterceptor(FileIndexDao fileIndexDao, Queue<Thing> thingQueue) {
        this.fileIndexDao = fileIndexDao;
        this.thingQueue = thingQueue;
    }

    @Override
    public void apply(Thing thing) {
        this.fileIndexDao.delete(thing);
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thing thing = this.thingQueue.poll();
            if(thing != null){
                this.apply(thing);
            }
        }
    }
}
