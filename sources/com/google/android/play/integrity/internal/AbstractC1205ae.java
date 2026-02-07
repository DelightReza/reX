package com.google.android.play.integrity.internal;

import android.content.Context;

/* renamed from: com.google.android.play.integrity.internal.ae */
/* loaded from: classes4.dex */
public abstract class AbstractC1205ae {
    /* renamed from: a */
    public static Context m400a(Context context) {
        Context applicationContext = context.getApplicationContext();
        return applicationContext != null ? applicationContext : context;
    }
}
