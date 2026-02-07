package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class zzas extends AbstractSafeParcelable implements Iterable {
    public static final Parcelable.Creator<zzas> CREATOR = new zzat();
    private final Bundle zza;

    zzas(Bundle bundle) {
        this.zza = bundle;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new zzar(this);
    }

    public final String toString() {
        return this.zza.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, zzc(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final int zza() {
        return this.zza.size();
    }

    public final Bundle zzc() {
        return new Bundle(this.zza);
    }

    final Double zzd(String str) {
        return Double.valueOf(this.zza.getDouble("value"));
    }

    final Long zze(String str) {
        return Long.valueOf(this.zza.getLong("value"));
    }

    final Object zzf(String str) {
        return this.zza.get(str);
    }

    final String zzg(String str) {
        return this.zza.getString(str);
    }
}
