package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.C1228q;

/* renamed from: com.google.android.play.core.integrity.au */
/* loaded from: classes4.dex */
final class BinderC1161au extends BinderC1160at {

    /* renamed from: c */
    private final C1228q f280c;

    BinderC1161au(C1164ax c1164ax, TaskCompletionSource taskCompletionSource) {
        super(c1164ax, taskCompletionSource);
        this.f280c = new C1228q("OnRequestIntegrityTokenCallback");
    }

    @Override // com.google.android.play.core.integrity.BinderC1160at, com.google.android.play.integrity.internal.InterfaceC1222k
    /* renamed from: c */
    public final void mo350c(Bundle bundle) {
        super.mo350c(bundle);
        this.f280c.m423c("onRequestExpressIntegrityToken", new Object[0]);
        int i = bundle.getInt(PluginsConstants.ERROR);
        if (i != 0) {
            this.f278a.trySetException(new StandardIntegrityException(i, null));
            return;
        }
        PendingIntent pendingIntent = Build.VERSION.SDK_INT >= 33 ? (PendingIntent) bundle.getParcelable("dialog.intent", PendingIntent.class) : (PendingIntent) bundle.getParcelable("dialog.intent");
        TaskCompletionSource taskCompletionSource = this.f278a;
        C1167b c1167b = new C1167b();
        c1167b.mo362c(bundle.getString("token"));
        c1167b.mo361b(this.f280c);
        c1167b.mo360a(pendingIntent);
        taskCompletionSource.trySetResult(c1167b.mo363d());
    }
}
