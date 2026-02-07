package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzfr extends zzlb implements zzmj {
    private static final zzfr zza;
    private int zzd;
    private int zze;
    private long zzf;

    static {
        zzfr zzfrVar = new zzfr();
        zza = zzfrVar;
        zzlb.zzbO(zzfr.class, zzfrVar);
    }

    private zzfr() {
    }

    public static zzfq zzc() {
        return (zzfq) zza.zzbA();
    }

    static /* synthetic */ void zze(zzfr zzfrVar, int i) {
        zzfrVar.zzd |= 1;
        zzfrVar.zze = i;
    }

    static /* synthetic */ void zzf(zzfr zzfrVar, long j) {
        zzfrVar.zzd |= 2;
        zzfrVar.zzf = j;
    }

    public final int zza() {
        return this.zze;
    }

    public final long zzb() {
        return this.zzf;
    }

    public final boolean zzg() {
        return (this.zzd & 2) != 0;
    }

    public final boolean zzh() {
        return (this.zzd & 1) != 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    protected final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzlb.zzbL(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002ဂ\u0001", new Object[]{"zzd", "zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzfr();
        }
        zzfk zzfkVar = null;
        if (i2 == 4) {
            return new zzfq(zzfkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zza;
    }
}
