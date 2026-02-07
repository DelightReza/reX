package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractRunnableC1229r;
import com.google.android.play.integrity.internal.C1204ad;

/* renamed from: com.google.android.play.core.integrity.aw */
/* loaded from: classes4.dex */
abstract class AbstractC1163aw extends AbstractRunnableC1229r {

    /* renamed from: f */
    final /* synthetic */ C1164ax f282f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractC1163aw(C1164ax c1164ax, TaskCompletionSource taskCompletionSource) {
        super(taskCompletionSource);
        this.f282f = c1164ax;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: a */
    public final void mo338a(Exception exc) {
        if (!(exc instanceof C1204ad)) {
            super.mo338a(exc);
        } else if (C1164ax.m357g(this.f282f)) {
            super.mo338a(new StandardIntegrityException(-2, exc));
        } else {
            super.mo338a(new StandardIntegrityException(-9, exc));
        }
    }
}
