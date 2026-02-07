package com.google.android.play.core.integrity;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.InterfaceC1220i;

/* renamed from: com.google.android.play.core.integrity.as */
/* loaded from: classes4.dex */
final class C1159as extends AbstractC1163aw {

    /* renamed from: a */
    final /* synthetic */ String f273a;

    /* renamed from: b */
    final /* synthetic */ long f274b;

    /* renamed from: c */
    final /* synthetic */ long f275c;

    /* renamed from: d */
    final /* synthetic */ TaskCompletionSource f276d;

    /* renamed from: e */
    final /* synthetic */ C1164ax f277e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1159as(C1164ax c1164ax, TaskCompletionSource taskCompletionSource, String str, long j, long j2, TaskCompletionSource taskCompletionSource2) {
        super(c1164ax, taskCompletionSource);
        this.f277e = c1164ax;
        this.f273a = str;
        this.f274b = j;
        this.f275c = j2;
        this.f276d = taskCompletionSource2;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: b */
    protected final void mo339b() {
        if (C1164ax.m357g(this.f277e)) {
            super.mo338a(new StandardIntegrityException(-2, null));
            return;
        }
        try {
            C1164ax c1164ax = this.f277e;
            ((InterfaceC1220i) c1164ax.f283a.m396e()).mo415c(C1164ax.m353a(c1164ax, this.f273a, this.f274b, this.f275c), new BinderC1161au(this.f277e, this.f276d));
        } catch (RemoteException e) {
            this.f277e.f284b.m422b(e, "requestExpressIntegrityToken(%s, %s)", this.f273a, Long.valueOf(this.f274b));
            this.f276d.trySetException(new StandardIntegrityException(-100, e));
        }
    }
}
