package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1228q;

/* renamed from: com.google.android.play.core.integrity.b */
/* loaded from: classes4.dex */
final class C1167b extends AbstractC1168ba {

    /* renamed from: a */
    private String f290a;

    /* renamed from: b */
    private C1228q f291b;

    /* renamed from: c */
    private PendingIntent f292c;

    C1167b() {
    }

    @Override // com.google.android.play.core.integrity.AbstractC1168ba
    /* renamed from: a */
    final AbstractC1168ba mo360a(PendingIntent pendingIntent) {
        this.f292c = pendingIntent;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1168ba
    /* renamed from: b */
    final AbstractC1168ba mo361b(C1228q c1228q) {
        if (c1228q == null) {
            throw new NullPointerException("Null logger");
        }
        this.f291b = c1228q;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1168ba
    /* renamed from: c */
    final AbstractC1168ba mo362c(String str) {
        if (str == null) {
            throw new NullPointerException("Null token");
        }
        this.f290a = str;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1168ba
    /* renamed from: d */
    final C1169bb mo363d() {
        C1228q c1228q;
        String str = this.f290a;
        if (str != null && (c1228q = this.f291b) != null) {
            return new C1169bb(str, c1228q, this.f292c);
        }
        StringBuilder sb = new StringBuilder();
        if (this.f290a == null) {
            sb.append(" token");
        }
        if (this.f291b == null) {
            sb.append(" logger");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
