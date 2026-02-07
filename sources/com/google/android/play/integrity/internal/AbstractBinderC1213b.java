package com.google.android.play.integrity.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* renamed from: com.google.android.play.integrity.internal.b */
/* loaded from: classes4.dex */
public abstract class AbstractBinderC1213b extends Binder implements IInterface {
    protected AbstractBinderC1213b(String str) {
        attachInterface(this, str);
    }

    /* renamed from: a */
    protected abstract boolean mo406a(int i, Parcel parcel, Parcel parcel2, int i2);

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i <= 16777215) {
            parcel.enforceInterface(getInterfaceDescriptor());
        } else if (super.onTransact(i, parcel, parcel2, i2)) {
            return true;
        }
        return mo406a(i, parcel, parcel2, i2);
    }
}
