package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1228q;

/* renamed from: com.google.android.play.core.integrity.a */
/* loaded from: classes4.dex */
final class C1140a extends AbstractC1147ag {

    /* renamed from: a */
    private String f239a;

    /* renamed from: b */
    private C1228q f240b;

    /* renamed from: c */
    private PendingIntent f241c;

    C1140a() {
    }

    @Override // com.google.android.play.core.integrity.AbstractC1147ag
    /* renamed from: a */
    final AbstractC1147ag mo333a(PendingIntent pendingIntent) {
        this.f241c = pendingIntent;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1147ag
    /* renamed from: b */
    final AbstractC1147ag mo334b(C1228q c1228q) {
        if (c1228q == null) {
            throw new NullPointerException("Null logger");
        }
        this.f240b = c1228q;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1147ag
    /* renamed from: c */
    final AbstractC1147ag mo335c(String str) {
        this.f239a = str;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1147ag
    /* renamed from: d */
    final C1148ah mo336d() {
        C1228q c1228q;
        String str = this.f239a;
        if (str != null && (c1228q = this.f240b) != null) {
            return new C1148ah(str, c1228q, this.f241c);
        }
        StringBuilder sb = new StringBuilder();
        if (this.f239a == null) {
            sb.append(" token");
        }
        if (this.f240b == null) {
            sb.append(" logger");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
