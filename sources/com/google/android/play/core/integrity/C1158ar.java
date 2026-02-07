package com.google.android.play.core.integrity;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.InterfaceC1220i;

/* renamed from: com.google.android.play.core.integrity.ar */
/* loaded from: classes4.dex */
final class C1158ar extends AbstractC1163aw {

    /* renamed from: a */
    final /* synthetic */ long f270a;

    /* renamed from: b */
    final /* synthetic */ TaskCompletionSource f271b;

    /* renamed from: c */
    final /* synthetic */ C1164ax f272c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1158ar(C1164ax c1164ax, TaskCompletionSource taskCompletionSource, long j, TaskCompletionSource taskCompletionSource2) {
        super(c1164ax, taskCompletionSource);
        this.f272c = c1164ax;
        this.f270a = j;
        this.f271b = taskCompletionSource2;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: b */
    protected final void mo339b() {
        if (C1164ax.m357g(this.f272c)) {
            super.mo338a(new StandardIntegrityException(-2, null));
            return;
        }
        try {
            C1164ax c1164ax = this.f272c;
            ((InterfaceC1220i) c1164ax.f283a.m396e()).mo416d(C1164ax.m354b(c1164ax, this.f270a), new BinderC1162av(this.f272c, this.f271b));
        } catch (RemoteException e) {
            this.f272c.f284b.m422b(e, "warmUpIntegrityToken(%s)", Long.valueOf(this.f270a));
            this.f271b.trySetException(new StandardIntegrityException(-100, e));
        }
    }
}
