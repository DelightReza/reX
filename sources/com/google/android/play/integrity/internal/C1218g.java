package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* renamed from: com.google.android.play.integrity.internal.g */
/* loaded from: classes4.dex */
public final class C1218g extends AbstractC1200a implements InterfaceC1220i {
    C1218g(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.integrity.protocol.IExpressIntegrityService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1220i
    /* renamed from: c */
    public final void mo415c(Bundle bundle, InterfaceC1222k interfaceC1222k) {
        Parcel parcelM374a = m374a();
        AbstractC1214c.m409c(parcelM374a, bundle);
        parcelM374a.writeStrongBinder(interfaceC1222k);
        m375b(3, parcelM374a);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1220i
    /* renamed from: d */
    public final void mo416d(Bundle bundle, InterfaceC1222k interfaceC1222k) {
        Parcel parcelM374a = m374a();
        AbstractC1214c.m409c(parcelM374a, bundle);
        parcelM374a.writeStrongBinder(interfaceC1222k);
        m375b(2, parcelM374a);
    }
}
