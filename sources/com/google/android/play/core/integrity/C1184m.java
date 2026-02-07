package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.AbstractC1211ak;

/* renamed from: com.google.android.play.core.integrity.m */
/* loaded from: classes4.dex */
final class C1184m implements InterfaceC1193t {

    /* renamed from: a */
    private Context f309a;

    private C1184m() {
    }

    /* synthetic */ C1184m(C1183l c1183l) {
    }

    /* renamed from: a */
    public final C1184m m365a(Context context) {
        context.getClass();
        this.f309a = context;
        return this;
    }

    @Override // com.google.android.play.core.integrity.InterfaceC1193t
    /* renamed from: b */
    public final C1188o mo366b() {
        AbstractC1211ak.m405a(this.f309a, Context.class);
        return new C1188o(this.f309a, null);
    }
}
