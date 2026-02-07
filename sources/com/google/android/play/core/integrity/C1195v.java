package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1205ae;

/* renamed from: com.google.android.play.core.integrity.v */
/* loaded from: classes4.dex */
final class C1195v {

    /* renamed from: a */
    private static C1188o f328a;

    /* renamed from: a */
    static synchronized C1188o m372a(Context context) {
        try {
            if (f328a == null) {
                C1184m c1184m = new C1184m(null);
                c1184m.m365a(AbstractC1205ae.m400a(context));
                f328a = c1184m.mo366b();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f328a;
    }
}
