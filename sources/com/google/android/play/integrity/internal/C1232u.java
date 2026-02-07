package com.google.android.play.integrity.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* renamed from: com.google.android.play.integrity.internal.u */
/* loaded from: classes4.dex */
final class C1232u extends AbstractRunnableC1229r {

    /* renamed from: a */
    final /* synthetic */ TaskCompletionSource f364a;

    /* renamed from: b */
    final /* synthetic */ AbstractRunnableC1229r f365b;

    /* renamed from: c */
    final /* synthetic */ C1203ac f366c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1232u(C1203ac c1203ac, TaskCompletionSource taskCompletionSource, TaskCompletionSource taskCompletionSource2, AbstractRunnableC1229r abstractRunnableC1229r) {
        super(taskCompletionSource);
        this.f366c = c1203ac;
        this.f364a = taskCompletionSource2;
        this.f365b = abstractRunnableC1229r;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: b */
    public final void mo339b() {
        synchronized (this.f366c.f341g) {
            try {
                C1203ac.m388o(this.f366c, this.f364a);
                if (this.f366c.f347m.getAndIncrement() > 0) {
                    this.f366c.f337c.m423c("Already connected to the service.", new Object[0]);
                }
                C1203ac.m390q(this.f366c, this.f365b);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
