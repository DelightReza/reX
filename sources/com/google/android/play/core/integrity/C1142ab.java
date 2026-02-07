package com.google.android.play.core.integrity;

import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractRunnableC1229r;
import com.google.android.play.integrity.internal.C1204ad;
import com.google.android.play.integrity.internal.InterfaceC1225n;

/* renamed from: com.google.android.play.core.integrity.ab */
/* loaded from: classes4.dex */
final class C1142ab extends AbstractRunnableC1229r {

    /* renamed from: a */
    final /* synthetic */ byte[] f243a;

    /* renamed from: b */
    final /* synthetic */ Long f244b;

    /* renamed from: c */
    final /* synthetic */ TaskCompletionSource f245c;

    /* renamed from: d */
    final /* synthetic */ IntegrityTokenRequest f246d;

    /* renamed from: e */
    final /* synthetic */ C1144ad f247e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1142ab(C1144ad c1144ad, TaskCompletionSource taskCompletionSource, byte[] bArr, Long l, Parcelable parcelable, TaskCompletionSource taskCompletionSource2, IntegrityTokenRequest integrityTokenRequest) {
        super(taskCompletionSource);
        this.f247e = c1144ad;
        this.f243a = bArr;
        this.f244b = l;
        this.f245c = taskCompletionSource2;
        this.f246d = integrityTokenRequest;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: a */
    public final void mo338a(Exception exc) {
        if (exc instanceof C1204ad) {
            super.mo338a(new IntegrityServiceException(-9, exc));
        } else {
            super.mo338a(exc);
        }
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: b */
    protected final void mo339b() {
        try {
            ((InterfaceC1225n) this.f247e.f251a.m396e()).mo418c(C1144ad.m341a(this.f247e, this.f243a, this.f244b, null), new BinderC1143ac(this.f247e, this.f245c));
        } catch (RemoteException e) {
            this.f247e.f252b.m422b(e, "requestIntegrityToken(%s)", this.f246d);
            this.f245c.trySetException(new IntegrityServiceException(-100, e));
        }
    }
}
