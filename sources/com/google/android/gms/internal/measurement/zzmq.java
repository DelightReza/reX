package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentMap;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
final class zzmq {
    private static final zzmq zza = new zzmq();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzmu zzb = new zzma();

    private zzmq() {
    }

    public static zzmq zza() {
        return zza;
    }

    public final zzmt zzb(Class cls) {
        zzlj.zzc(cls, "messageType");
        zzmt zzmtVar = (zzmt) this.zzc.get(cls);
        if (zzmtVar != null) {
            return zzmtVar;
        }
        zzmt zzmtVarZza = this.zzb.zza(cls);
        zzlj.zzc(cls, "messageType");
        zzlj.zzc(zzmtVarZza, "schema");
        zzmt zzmtVar2 = (zzmt) this.zzc.putIfAbsent(cls, zzmtVarZza);
        return zzmtVar2 == null ? zzmtVarZza : zzmtVar2;
    }
}
