package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.Parcel;

/* renamed from: com.google.android.play.integrity.internal.o */
/* loaded from: classes4.dex */
public abstract class AbstractBinderC1226o extends AbstractBinderC1213b implements InterfaceC1227p {
    public AbstractBinderC1226o() {
        super("com.google.android.play.core.integrity.protocol.IIntegrityServiceCallback");
    }

    @Override // com.google.android.play.integrity.internal.AbstractBinderC1213b
    /* renamed from: a */
    protected final boolean mo406a(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 2) {
            return false;
        }
        Bundle bundle = (Bundle) AbstractC1214c.m407a(parcel, Bundle.CREATOR);
        AbstractC1214c.m408b(parcel);
        mo340b(bundle);
        return true;
    }
}
