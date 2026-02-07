package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.core.integrity.StandardIntegrityManager;
import com.google.android.play.integrity.internal.C1228q;

/* renamed from: com.google.android.play.core.integrity.bb */
/* loaded from: classes4.dex */
final class C1169bb extends StandardIntegrityManager.StandardIntegrityToken {

    /* renamed from: a */
    private final String f293a;

    /* renamed from: b */
    private final C1194u f294b;

    C1169bb(String str, C1228q c1228q, PendingIntent pendingIntent) {
        this.f293a = str;
        this.f294b = new C1194u(c1228q, pendingIntent);
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityToken
    public final String token() {
        return this.f293a;
    }
}
