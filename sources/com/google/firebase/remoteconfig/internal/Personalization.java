package com.google.firebase.remoteconfig.internal;

import com.google.firebase.inject.Provider;
import java.util.HashMap;
import java.util.Map;
import p017j$.util.DesugarCollections;

/* loaded from: classes.dex */
public class Personalization {
    private final Provider analyticsConnector;
    private final Map loggedChoiceIds = DesugarCollections.synchronizedMap(new HashMap());

    public Personalization(Provider provider) {
        this.analyticsConnector = provider;
    }
}
