package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.Parcel;

/* renamed from: com.google.android.play.integrity.internal.j */
/* loaded from: classes4.dex */
public abstract class AbstractBinderC1221j extends AbstractBinderC1213b implements InterfaceC1222k {
    public AbstractBinderC1221j() {
        super("com.google.android.play.core.integrity.protocol.IExpressIntegrityServiceCallback");
    }

    @Override // com.google.android.play.integrity.internal.AbstractBinderC1213b
    /* renamed from: a */
    protected final boolean mo406a(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 2) {
            Bundle bundle = (Bundle) AbstractC1214c.m407a(parcel, Bundle.CREATOR);
            AbstractC1214c.m408b(parcel);
            mo352e(bundle);
            return true;
        }
        if (i == 3) {
            Bundle bundle2 = (Bundle) AbstractC1214c.m407a(parcel, Bundle.CREATOR);
            AbstractC1214c.m408b(parcel);
            mo350c(bundle2);
            return true;
        }
        if (i == 4) {
            Bundle bundle3 = (Bundle) AbstractC1214c.m407a(parcel, Bundle.CREATOR);
            AbstractC1214c.m408b(parcel);
            mo351d(bundle3);
            return true;
        }
        if (i != 5) {
            return false;
        }
        Bundle bundle4 = (Bundle) AbstractC1214c.m407a(parcel, Bundle.CREATOR);
        AbstractC1214c.m408b(parcel);
        mo349b(bundle4);
        return true;
    }
}
