package com.github.everything.core.interceptor;

import com.github.everything.core.model.Thing;

public interface ThingInterceptor {

    void apply(Thing thing);
}
