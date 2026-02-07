package com.google.android.gms.internal.measurement;

import java.util.List;
import p017j$.util.DesugarCollections;

/* loaded from: classes4.dex */
public final class zzfs extends zzkx implements zzmj {
    public final int zza() {
        return ((zzft) this.zza).zzb();
    }

    public final long zzb() {
        return ((zzft) this.zza).zzc();
    }

    public final long zzc() {
        return ((zzft) this.zza).zzd();
    }

    public final zzfs zzd(Iterable iterable) {
        zzaH();
        zzft.zzm((zzft) this.zza, iterable);
        return this;
    }

    public final zzfs zze(zzfw zzfwVar) {
        zzaH();
        zzft.zzk((zzft) this.zza, (zzfx) zzfwVar.zzaD());
        return this;
    }

    public final zzfs zzf(zzfx zzfxVar) {
        zzaH();
        zzft.zzk((zzft) this.zza, zzfxVar);
        return this;
    }

    public final zzfs zzg() {
        zzaH();
        ((zzft) this.zza).zze = zzlb.zzbH();
        return this;
    }

    public final zzfs zzh(int i) {
        zzaH();
        zzft.zzo((zzft) this.zza, i);
        return this;
    }

    public final zzfs zzi(String str) {
        zzaH();
        zzft.zzp((zzft) this.zza, str);
        return this;
    }

    public final zzfs zzj(int i, zzfw zzfwVar) {
        zzaH();
        zzft.zzj((zzft) this.zza, i, (zzfx) zzfwVar.zzaD());
        return this;
    }

    public final zzfs zzk(int i, zzfx zzfxVar) {
        zzaH();
        zzft.zzj((zzft) this.zza, i, zzfxVar);
        return this;
    }

    public final zzfs zzl(long j) {
        zzaH();
        zzft.zzr((zzft) this.zza, j);
        return this;
    }

    public final zzfs zzm(long j) {
        zzaH();
        zzft.zzq((zzft) this.zza, j);
        return this;
    }

    public final zzfx zzn(int i) {
        return ((zzft) this.zza).zzg(i);
    }

    public final String zzo() {
        return ((zzft) this.zza).zzh();
    }

    public final List zzp() {
        return DesugarCollections.unmodifiableList(((zzft) this.zza).zzi());
    }

    public final boolean zzq() {
        return ((zzft) this.zza).zzu();
    }

    /* synthetic */ zzfs(zzfk zzfkVar) {
        super(zzft.zza);
    }
}
