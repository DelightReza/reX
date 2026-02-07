package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1208ah;
import com.google.android.play.integrity.internal.C1210aj;
import com.google.android.play.integrity.internal.InterfaceC1209ai;
import com.google.android.play.integrity.internal.InterfaceC1212al;

/* renamed from: com.google.android.play.core.integrity.s */
/* loaded from: classes4.dex */
final class C1192s {

    /* renamed from: a */
    private final C1192s f320a = this;

    /* renamed from: b */
    private final InterfaceC1212al f321b;

    /* renamed from: c */
    private final InterfaceC1212al f322c;

    /* renamed from: d */
    private final InterfaceC1212al f323d;

    /* renamed from: e */
    private final InterfaceC1212al f324e;

    /* renamed from: f */
    private final InterfaceC1212al f325f;

    /* synthetic */ C1192s(Context context, C1191r c1191r) {
        InterfaceC1209ai interfaceC1209aiM404b = C1210aj.m404b(context);
        this.f321b = interfaceC1209aiM404b;
        InterfaceC1212al interfaceC1212alM403b = C1208ah.m403b(C1154an.f266a);
        this.f322c = interfaceC1212alM403b;
        InterfaceC1212al interfaceC1212alM403b2 = C1208ah.m403b(new C1166az(interfaceC1209aiM404b, interfaceC1212alM403b));
        this.f323d = interfaceC1212alM403b2;
        InterfaceC1212al interfaceC1212alM403b3 = C1208ah.m403b(new C1172be(interfaceC1212alM403b2));
        this.f324e = interfaceC1212alM403b3;
        this.f325f = C1208ah.m403b(new C1153am(interfaceC1212alM403b2, interfaceC1212alM403b3));
    }

    /* renamed from: a */
    public final StandardIntegrityManager m371a() {
        return (StandardIntegrityManager) this.f325f.mo344a();
    }
}
