package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1226o;
import com.google.android.play.integrity.internal.C1228q;

/* renamed from: com.google.android.play.core.integrity.ac */
/* loaded from: classes4.dex */
final class BinderC1143ac extends AbstractBinderC1226o {

    /* renamed from: a */
    final /* synthetic */ C1144ad f248a;

    /* renamed from: b */
    private final C1228q f249b = new C1228q("OnRequestIntegrityTokenCallback");

    /* renamed from: c */
    private final TaskCompletionSource f250c;

    BinderC1143ac(C1144ad c1144ad, TaskCompletionSource taskCompletionSource) {
        this.f248a = c1144ad;
        this.f250c = taskCompletionSource;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1227p
    /* renamed from: b */
    public final void mo340b(Bundle bundle) {
        this.f248a.f251a.m399v(this.f250c);
        this.f249b.m423c("onRequestIntegrityToken", new Object[0]);
        int i = bundle.getInt(PluginsConstants.ERROR);
        if (i != 0) {
            this.f250c.trySetException(new IntegrityServiceException(i, null));
            return;
        }
        String string = bundle.getString("token");
        if (string == null) {
            this.f250c.trySetException(new IntegrityServiceException(-100, null));
            return;
        }
        PendingIntent pendingIntent = Build.VERSION.SDK_INT >= 33 ? (PendingIntent) bundle.getParcelable("dialog.intent", PendingIntent.class) : (PendingIntent) bundle.getParcelable("dialog.intent");
        TaskCompletionSource taskCompletionSource = this.f250c;
        C1140a c1140a = new C1140a();
        c1140a.mo335c(string);
        c1140a.mo334b(this.f249b);
        c1140a.mo333a(pendingIntent);
        taskCompletionSource.trySetResult(c1140a.mo336d());
    }
}
