package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1228q;
import com.google.android.play.integrity.internal.InterfaceC1209ai;
import com.google.android.play.integrity.internal.InterfaceC1212al;

/* renamed from: com.google.android.play.core.integrity.af */
/* loaded from: classes4.dex */
public final class C1146af implements InterfaceC1209ai {

    /* renamed from: a */
    private final InterfaceC1212al f255a;

    /* renamed from: b */
    private final InterfaceC1212al f256b;

    public C1146af(InterfaceC1212al interfaceC1212al, InterfaceC1212al interfaceC1212al2) {
        this.f255a = interfaceC1212al;
        this.f256b = interfaceC1212al2;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1212al
    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo344a() {
        return new C1144ad((Context) this.f255a.mo344a(), (C1228q) this.f256b.mo344a());
    }
}
