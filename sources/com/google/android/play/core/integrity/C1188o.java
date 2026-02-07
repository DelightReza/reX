package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1208ah;
import com.google.android.play.integrity.internal.C1210aj;
import com.google.android.play.integrity.internal.InterfaceC1209ai;
import com.google.android.play.integrity.internal.InterfaceC1212al;

/* renamed from: com.google.android.play.core.integrity.o */
/* loaded from: classes4.dex */
final class C1188o {

    /* renamed from: a */
    private final C1188o f314a = this;

    /* renamed from: b */
    private final InterfaceC1212al f315b;

    /* renamed from: c */
    private final InterfaceC1212al f316c;

    /* renamed from: d */
    private final InterfaceC1212al f317d;

    /* renamed from: e */
    private final InterfaceC1212al f318e;

    /* synthetic */ C1188o(Context context, C1187n c1187n) {
        InterfaceC1209ai interfaceC1209aiM404b = C1210aj.m404b(context);
        this.f315b = interfaceC1209aiM404b;
        InterfaceC1212al interfaceC1212alM403b = C1208ah.m403b(C1198y.f331a);
        this.f316c = interfaceC1212alM403b;
        InterfaceC1212al interfaceC1212alM403b2 = C1208ah.m403b(new C1146af(interfaceC1209aiM404b, interfaceC1212alM403b));
        this.f317d = interfaceC1212alM403b2;
        this.f318e = C1208ah.m403b(new C1197x(interfaceC1212alM403b2));
    }

    /* renamed from: a */
    public final IntegrityManager m369a() {
        return (IntegrityManager) this.f318e.mo344a();
    }
}
