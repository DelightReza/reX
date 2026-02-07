package com.google.android.play.integrity.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* renamed from: com.google.android.play.integrity.internal.ab */
/* loaded from: classes4.dex */
final class ServiceConnectionC1202ab implements ServiceConnection {

    /* renamed from: a */
    final /* synthetic */ C1203ac f334a;

    /* synthetic */ ServiceConnectionC1202ab(C1203ac c1203ac, AbstractC1201aa abstractC1201aa) {
        this.f334a = c1203ac;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f334a.f337c.m423c("ServiceConnectionImpl.onServiceConnected(%s)", componentName);
        this.f334a.m395c().post(new C1236y(this, iBinder));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.f334a.f337c.m423c("ServiceConnectionImpl.onServiceDisconnected(%s)", componentName);
        this.f334a.m395c().post(new C1237z(this));
    }
}
