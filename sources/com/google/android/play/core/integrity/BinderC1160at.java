package com.google.android.play.core.integrity;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1221j;

/* renamed from: com.google.android.play.core.integrity.at */
/* loaded from: classes4.dex */
class BinderC1160at extends AbstractBinderC1221j {

    /* renamed from: a */
    final TaskCompletionSource f278a;

    /* renamed from: b */
    final /* synthetic */ C1164ax f279b;

    BinderC1160at(C1164ax c1164ax, TaskCompletionSource taskCompletionSource) {
        this.f279b = c1164ax;
        this.f278a = taskCompletionSource;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1222k
    /* renamed from: b */
    public final void mo349b(Bundle bundle) {
        this.f279b.f283a.m399v(this.f278a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1222k
    /* renamed from: c */
    public void mo350c(Bundle bundle) {
        this.f279b.f283a.m399v(this.f278a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1222k
    /* renamed from: d */
    public final void mo351d(Bundle bundle) {
        this.f279b.f283a.m399v(this.f278a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1222k
    /* renamed from: e */
    public void mo352e(Bundle bundle) {
        this.f279b.f283a.m399v(this.f278a);
    }
}
