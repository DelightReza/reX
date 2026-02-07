package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.Iterator;

/* renamed from: com.google.android.play.integrity.internal.y */
/* loaded from: classes4.dex */
final class C1236y extends AbstractRunnableC1229r {

    /* renamed from: a */
    final /* synthetic */ IBinder f368a;

    /* renamed from: b */
    final /* synthetic */ ServiceConnectionC1202ab f369b;

    C1236y(ServiceConnectionC1202ab serviceConnectionC1202ab, IBinder iBinder) {
        this.f369b = serviceConnectionC1202ab;
        this.f368a = iBinder;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: b */
    public final void mo339b() throws RemoteException {
        C1203ac c1203ac = this.f369b.f334a;
        c1203ac.f349o = (IInterface) c1203ac.f344j.mo337a(this.f368a);
        C1203ac.m391r(this.f369b.f334a);
        this.f369b.f334a.f342h = false;
        Iterator it = this.f369b.f334a.f339e.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.f369b.f334a.f339e.clear();
    }
}
