package com.google.firebase.remoteconfig.internal.rollouts;

import com.google.firebase.remoteconfig.internal.ConfigGetParameterHandler;

/* loaded from: classes.dex */
public class RolloutsStateFactory {
    ConfigGetParameterHandler getParameterHandler;

    RolloutsStateFactory(ConfigGetParameterHandler configGetParameterHandler) {
        this.getParameterHandler = configGetParameterHandler;
    }

    public static RolloutsStateFactory create(ConfigGetParameterHandler configGetParameterHandler) {
        return new RolloutsStateFactory(configGetParameterHandler);
    }
}
