package com.google.android.gms.auth.api.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/* loaded from: classes4.dex */
public final class zbs extends com.google.android.gms.internal.p024authapi.zba implements IInterface {
    zbs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.signin.internal.ISignInService");
    }

    public final void zbc(zbr zbrVar, GoogleSignInOptions googleSignInOptions) {
        Parcel parcelZba = zba();
        com.google.android.gms.internal.p024authapi.zbc.zbd(parcelZba, zbrVar);
        com.google.android.gms.internal.p024authapi.zbc.zbc(parcelZba, googleSignInOptions);
        zbb(103, parcelZba);
    }

    public final void zbd(zbr zbrVar, GoogleSignInOptions googleSignInOptions) {
        Parcel parcelZba = zba();
        com.google.android.gms.internal.p024authapi.zbc.zbd(parcelZba, zbrVar);
        com.google.android.gms.internal.p024authapi.zbc.zbc(parcelZba, googleSignInOptions);
        zbb(102, parcelZba);
    }
}
