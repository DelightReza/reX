package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1228q;

/* renamed from: com.google.android.play.core.integrity.ah */
/* loaded from: classes4.dex */
final class C1148ah extends IntegrityTokenResponse {

    /* renamed from: a */
    private final String f257a;

    /* renamed from: b */
    private final C1194u f258b;

    C1148ah(String str, C1228q c1228q, PendingIntent pendingIntent) {
        this.f257a = str;
        this.f258b = new C1194u(c1228q, pendingIntent);
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenResponse
    public final String token() {
        return this.f257a;
    }
}
