package com.google.android.play.core.integrity;

import android.os.Bundle;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.C1228q;

/* renamed from: com.google.android.play.core.integrity.av */
/* loaded from: classes4.dex */
final class BinderC1162av extends BinderC1160at {

    /* renamed from: c */
    private final C1228q f281c;

    BinderC1162av(C1164ax c1164ax, TaskCompletionSource taskCompletionSource) {
        super(c1164ax, taskCompletionSource);
        this.f281c = new C1228q("OnWarmUpIntegrityTokenCallback");
    }

    @Override // com.google.android.play.core.integrity.BinderC1160at, com.google.android.play.integrity.internal.InterfaceC1222k
    /* renamed from: e */
    public final void mo352e(Bundle bundle) {
        super.mo352e(bundle);
        this.f281c.m423c("onWarmUpExpressIntegrityToken", new Object[0]);
        int i = bundle.getInt(PluginsConstants.ERROR);
        if (i != 0) {
            this.f278a.trySetException(new StandardIntegrityException(i, null));
        } else {
            this.f278a.trySetResult(Long.valueOf(bundle.getLong("warm.up.sid")));
        }
    }
}
