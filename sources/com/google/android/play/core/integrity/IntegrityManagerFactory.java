package com.google.android.play.core.integrity;

import android.content.Context;

/* loaded from: classes4.dex */
public class IntegrityManagerFactory {
    private IntegrityManagerFactory() {
    }

    public static IntegrityManager create(Context context) {
        return C1195v.m372a(context).m369a();
    }

    public static StandardIntegrityManager createStandard(Context context) {
        return C1150aj.m346a(context).m371a();
    }
}
