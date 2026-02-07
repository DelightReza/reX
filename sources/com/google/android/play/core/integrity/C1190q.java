package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1211ak;

/* renamed from: com.google.android.play.core.integrity.q */
/* loaded from: classes4.dex */
final class C1190q implements InterfaceC1149ai {

    /* renamed from: a */
    private Context f319a;

    private C1190q() {
    }

    /* synthetic */ C1190q(C1189p c1189p) {
    }

    /* renamed from: a */
    public final C1190q m370a(Context context) {
        context.getClass();
        this.f319a = context;
        return this;
    }

    @Override // com.google.android.play.core.integrity.InterfaceC1149ai
    /* renamed from: b */
    public final C1192s mo345b() {
        AbstractC1211ak.m405a(this.f319a, Context.class);
        return new C1192s(this.f319a, null);
    }
}
