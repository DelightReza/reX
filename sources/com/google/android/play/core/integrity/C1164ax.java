package com.google.android.play.core.integrity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1219h;
import com.google.android.play.integrity.internal.AbstractC1215d;
import com.google.android.play.integrity.internal.C1203ac;
import com.google.android.play.integrity.internal.C1228q;
import com.google.android.play.integrity.internal.InterfaceC1235x;
import java.util.ArrayList;

/* renamed from: com.google.android.play.core.integrity.ax */
/* loaded from: classes4.dex */
final class C1164ax {

    /* renamed from: a */
    final C1203ac f283a;

    /* renamed from: b */
    private final C1228q f284b;

    /* renamed from: c */
    private final String f285c;

    /* renamed from: d */
    private final TaskCompletionSource f286d;

    C1164ax(Context context, C1228q c1228q) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f286d = taskCompletionSource;
        this.f285c = context.getPackageName();
        this.f284b = c1228q;
        C1203ac c1203ac = new C1203ac(context, c1228q, "ExpressIntegrityService", C1165ay.f287a, new InterfaceC1235x() { // from class: com.google.android.play.core.integrity.ap
            @Override // com.google.android.play.integrity.internal.InterfaceC1235x
            /* renamed from: a */
            public final Object mo337a(IBinder iBinder) {
                return AbstractBinderC1219h.m417b(iBinder);
            }
        }, null);
        this.f283a = c1203ac;
        c1203ac.m395c().post(new C1157aq(this, taskCompletionSource, context));
    }

    /* renamed from: a */
    static /* bridge */ /* synthetic */ Bundle m353a(C1164ax c1164ax, String str, long j, long j2) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1164ax.f285c);
        bundle.putLong("cloud.prj", j);
        bundle.putString("nonce", str);
        bundle.putLong("warm.up.sid", j2);
        ArrayList arrayList = new ArrayList();
        AbstractC1215d.m411b(5, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1215d.m410a(arrayList)));
        return bundle;
    }

    /* renamed from: b */
    static /* bridge */ /* synthetic */ Bundle m354b(C1164ax c1164ax, long j) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1164ax.f285c);
        bundle.putLong("cloud.prj", j);
        ArrayList arrayList = new ArrayList();
        AbstractC1215d.m411b(4, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1215d.m410a(arrayList)));
        return bundle;
    }

    /* renamed from: g */
    static /* bridge */ /* synthetic */ boolean m357g(C1164ax c1164ax) {
        return c1164ax.f286d.getTask().isSuccessful() && !((Boolean) c1164ax.f286d.getTask().getResult()).booleanValue();
    }

    /* renamed from: c */
    public final Task m358c(String str, long j, long j2) {
        this.f284b.m423c("requestExpressIntegrityToken(%s)", Long.valueOf(j2));
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f283a.m397t(new C1159as(this, taskCompletionSource, str, j, j2, taskCompletionSource), taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    /* renamed from: d */
    public final Task m359d(long j) {
        this.f284b.m423c("warmUpIntegrityToken(%s)", Long.valueOf(j));
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f283a.m397t(new C1158ar(this, taskCompletionSource, j, taskCompletionSource), taskCompletionSource);
        return taskCompletionSource.getTask();
    }
}
