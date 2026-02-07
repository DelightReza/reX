package com.google.firebase.remoteconfig.internal.rollouts;

import com.google.firebase.remoteconfig.internal.ConfigCacheClient;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Executor;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class RolloutsStateSubscriptionsHandler {
    private ConfigCacheClient activatedConfigsCache;
    private Executor executor;
    private RolloutsStateFactory rolloutsStateFactory;
    private Set subscribers = Collections.newSetFromMap(new ConcurrentHashMap());

    public RolloutsStateSubscriptionsHandler(ConfigCacheClient configCacheClient, RolloutsStateFactory rolloutsStateFactory, Executor executor) {
        this.activatedConfigsCache = configCacheClient;
        this.rolloutsStateFactory = rolloutsStateFactory;
        this.executor = executor;
    }
}
