package com.google.android.play.core.integrity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.android.play.integrity.internal.AbstractBinderC1224m;
import com.google.android.play.integrity.internal.AbstractC1207ag;
import com.google.android.play.integrity.internal.AbstractC1215d;
import com.google.android.play.integrity.internal.C1203ac;
import com.google.android.play.integrity.internal.C1228q;
import com.google.android.play.integrity.internal.InterfaceC1235x;
import java.util.ArrayList;

/* renamed from: com.google.android.play.core.integrity.ad */
/* loaded from: classes4.dex */
final class C1144ad {

    /* renamed from: a */
    final C1203ac f251a;

    /* renamed from: b */
    private final C1228q f252b;

    /* renamed from: c */
    private final String f253c;

    C1144ad(Context context, C1228q c1228q) {
        this.f253c = context.getPackageName();
        this.f252b = c1228q;
        if (AbstractC1207ag.m402a(context)) {
            this.f251a = new C1203ac(context, c1228q, "IntegrityService", C1145ae.f254a, new InterfaceC1235x() { // from class: com.google.android.play.core.integrity.aa
                @Override // com.google.android.play.integrity.internal.InterfaceC1235x
                /* renamed from: a */
                public final Object mo337a(IBinder iBinder) {
                    return AbstractBinderC1224m.m419b(iBinder);
                }
            }, null);
        } else {
            c1228q.m421a("Phonesky is not installed.", new Object[0]);
            this.f251a = null;
        }
    }

    /* renamed from: a */
    static /* bridge */ /* synthetic */ Bundle m341a(C1144ad c1144ad, byte[] bArr, Long l, Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1144ad.f253c);
        bundle.putByteArray("nonce", bArr);
        bundle.putInt("playcore.integrity.version.major", 1);
        bundle.putInt("playcore.integrity.version.minor", 2);
        bundle.putInt("playcore.integrity.version.patch", 0);
        if (l != null) {
            bundle.putLong("cloud.prj", l.longValue());
        }
        ArrayList arrayList = new ArrayList();
        AbstractC1215d.m411b(3, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1215d.m410a(arrayList)));
        return bundle;
    }

    /* renamed from: b */
    public final Task m343b(IntegrityTokenRequest integrityTokenRequest) {
        if (this.f251a == null) {
            return Tasks.forException(new IntegrityServiceException(-2, null));
        }
        try {
            byte[] bArrDecode = Base64.decode(integrityTokenRequest.nonce(), 10);
            Long lCloudProjectNumber = integrityTokenRequest.cloudProjectNumber();
            if (Build.VERSION.SDK_INT >= 23) {
                integrityTokenRequest.mo330a();
            }
            this.f252b.m423c("requestIntegrityToken(%s)", integrityTokenRequest);
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            this.f251a.m397t(new C1142ab(this, taskCompletionSource, bArrDecode, lCloudProjectNumber, null, taskCompletionSource, integrityTokenRequest), taskCompletionSource);
            return taskCompletionSource.getTask();
        } catch (IllegalArgumentException e) {
            return Tasks.forException(new IntegrityServiceException(-13, e));
        }
    }
}
