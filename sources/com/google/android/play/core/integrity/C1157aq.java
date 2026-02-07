package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractC1207ag;
import com.google.android.play.integrity.internal.AbstractRunnableC1229r;

/* renamed from: com.google.android.play.core.integrity.aq */
/* loaded from: classes4.dex */
final class C1157aq extends AbstractRunnableC1229r {

    /* renamed from: a */
    final /* synthetic */ Context f268a;

    /* renamed from: b */
    final /* synthetic */ C1164ax f269b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1157aq(C1164ax c1164ax, TaskCompletionSource taskCompletionSource, Context context) {
        super(taskCompletionSource);
        this.f269b = c1164ax;
        this.f268a = context;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: b */
    protected final void mo339b() {
        this.f269b.f286d.trySetResult(Boolean.valueOf(AbstractC1207ag.m402a(this.f268a)));
    }
}
