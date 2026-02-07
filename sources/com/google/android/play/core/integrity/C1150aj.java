package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1205ae;

/* renamed from: com.google.android.play.core.integrity.aj */
/* loaded from: classes4.dex */
final class C1150aj {

    /* renamed from: a */
    private static C1192s f259a;

    /* renamed from: a */
    static synchronized C1192s m346a(Context context) {
        try {
            if (f259a == null) {
                C1190q c1190q = new C1190q(null);
                c1190q.m370a(AbstractC1205ae.m400a(context));
                f259a = c1190q.mo345b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f259a;
    }
}
