package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.play.integrity.internal.C1228q;
import com.google.android.play.integrity.internal.InterfaceC1209ai;
import com.google.android.play.integrity.internal.InterfaceC1212al;

/* renamed from: com.google.android.play.core.integrity.az */
/* loaded from: classes4.dex */
public final class C1166az implements InterfaceC1209ai {

    /* renamed from: a */
    private final InterfaceC1212al f288a;

    /* renamed from: b */
    private final InterfaceC1212al f289b;

    public C1166az(InterfaceC1212al interfaceC1212al, InterfaceC1212al interfaceC1212al2) {
        this.f288a = interfaceC1212al;
        this.f289b = interfaceC1212al2;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1212al
    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo344a() {
        return new C1164ax((Context) this.f288a.mo344a(), (C1228q) this.f289b.mo344a());
    }
}
