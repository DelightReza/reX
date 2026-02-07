package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* renamed from: com.google.android.play.integrity.internal.a */
/* loaded from: classes4.dex */
public abstract class AbstractC1200a implements IInterface {

    /* renamed from: a */
    private final IBinder f332a;

    /* renamed from: b */
    private final String f333b;

    protected AbstractC1200a(IBinder iBinder, String str) {
        this.f332a = iBinder;
        this.f333b = str;
    }

    /* renamed from: a */
    protected final Parcel m374a() {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInterfaceToken(this.f333b);
        return parcelObtain;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f332a;
    }

    /* renamed from: b */
    protected final void m375b(int i, Parcel parcel) {
        try {
            this.f332a.transact(i, parcel, null, 1);
        } finally {
            parcel.recycle();
        }
    }
}
