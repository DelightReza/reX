package com.google.firebase.abt;

import android.content.Context;
import com.google.firebase.inject.Provider;

/* loaded from: classes.dex */
public class FirebaseABTesting {
    private final Provider analyticsConnector;
    private Integer maxUserProperties = null;
    private final String originService;

    public FirebaseABTesting(Context context, Provider provider, String str) {
        this.analyticsConnector = provider;
        this.originService = str;
    }
}
