package com.github.everything.core.dao;

import com.github.everything.core.model.Condition;
import com.github.everything.core.model.Thing;

import java.util.List;

public interface FileIndexDao {
    void insert(Thing thing);
    void delete(Thing thing);
    List<Thing> query(Condition condition);
}
