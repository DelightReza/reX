package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* renamed from: com.google.android.play.integrity.internal.l */
/* loaded from: classes4.dex */
public final class C1223l extends AbstractC1200a implements InterfaceC1225n {
    C1223l(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.integrity.protocol.IIntegrityService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1225n
    /* renamed from: c */
    public final void mo418c(Bundle bundle, InterfaceC1227p interfaceC1227p) {
        Parcel parcelM374a = m374a();
        AbstractC1214c.m409c(parcelM374a, bundle);
        parcelM374a.writeStrongBinder(interfaceC1227p);
        m375b(2, parcelM374a);
    }
}
