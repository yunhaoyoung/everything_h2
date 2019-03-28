package com.github.everything.core.serach;

import com.github.everything.core.model.Condition;
import com.github.everything.core.model.Thing;

import java.util.List;

public interface ThingSearch {
    List<Thing> search(Condition condition);

}
