package com.google.android.gms.internal.clearcut;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
final class zzdu implements zzef {
    private final zzdo zzmn;
    private final boolean zzmo;
    private final zzex zzmx;
    private final zzbu zzmy;

    private zzdu(zzex zzexVar, zzbu zzbuVar, zzdo zzdoVar) {
        this.zzmx = zzexVar;
        this.zzmo = zzbuVar.zze(zzdoVar);
        this.zzmy = zzbuVar;
        this.zzmn = zzdoVar;
    }

    static zzdu zza(zzex zzexVar, zzbu zzbuVar, zzdo zzdoVar) {
        return new zzdu(zzexVar, zzbuVar, zzdoVar);
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean equals(Object obj, Object obj2) {
        if (!this.zzmx.zzq(obj).equals(this.zzmx.zzq(obj2))) {
            return false;
        }
        if (this.zzmo) {
            return this.zzmy.zza(obj).equals(this.zzmy.zza(obj2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final int hashCode(Object obj) {
        int iHashCode = this.zzmx.zzq(obj).hashCode();
        return this.zzmo ? (iHashCode * 53) + this.zzmy.zza(obj).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final Object newInstance() {
        return this.zzmn.zzbd().zzbi();
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zza(Object obj, zzfr zzfrVar) {
        Iterator it = this.zzmy.zza(obj).iterator();
        if (it.hasNext()) {
            WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(((Map.Entry) it.next()).getKey());
            throw null;
        }
        zzex zzexVar = this.zzmx;
        zzexVar.zzc(zzexVar.zzq(obj), zzfrVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0064 A[EDGE_INSN: B:51:0x0064->B:28:0x0064 BREAK  A[LOOP:1: B:14:0x0034->B:54:0x0034], SYNTHETIC] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(java.lang.Object r8, byte[] r9, int r10, int r11, com.google.android.gms.internal.clearcut.zzay r12) throws com.google.android.gms.internal.clearcut.zzco {
        /*
            r7 = this;
            com.google.android.gms.internal.clearcut.zzcg r8 = (com.google.android.gms.internal.clearcut.zzcg) r8
            com.google.android.gms.internal.clearcut.zzey r0 = r8.zzjp
            com.google.android.gms.internal.clearcut.zzey r1 = com.google.android.gms.internal.clearcut.zzey.zzea()
            if (r0 != r1) goto L10
            com.google.android.gms.internal.clearcut.zzey r0 = com.google.android.gms.internal.clearcut.zzey.zzeb()
            r8.zzjp = r0
        L10:
            r4 = r0
        L11:
            if (r10 >= r11) goto L71
            int r2 = com.google.android.gms.internal.clearcut.zzax.zza(r9, r10, r12)
            int r0 = r12.zzfd
            r8 = 11
            r10 = 2
            if (r0 == r8) goto L2f
            r8 = r0 & 7
            r1 = r9
            r3 = r11
            r5 = r12
            if (r8 != r10) goto L2a
            int r10 = com.google.android.gms.internal.clearcut.zzax.zza(r0, r1, r2, r3, r4, r5)
            goto L11
        L2a:
            int r10 = com.google.android.gms.internal.clearcut.zzax.zza(r0, r1, r2, r3, r5)
            goto L11
        L2f:
            r1 = r9
            r3 = r11
            r5 = r12
            r8 = 0
            r9 = 0
        L34:
            if (r2 >= r3) goto L63
            int r11 = com.google.android.gms.internal.clearcut.zzax.zza(r1, r2, r5)
            int r12 = r5.zzfd
            int r0 = r12 >>> 3
            r2 = r12 & 7
            if (r0 == r10) goto L51
            r6 = 3
            if (r0 == r6) goto L46
            goto L5a
        L46:
            if (r2 != r10) goto L5a
            int r2 = com.google.android.gms.internal.clearcut.zzax.zze(r1, r11, r5)
            java.lang.Object r9 = r5.zzff
            com.google.android.gms.internal.clearcut.zzbb r9 = (com.google.android.gms.internal.clearcut.zzbb) r9
            goto L34
        L51:
            if (r2 != 0) goto L5a
            int r2 = com.google.android.gms.internal.clearcut.zzax.zza(r1, r11, r5)
            int r8 = r5.zzfd
            goto L34
        L5a:
            r0 = 12
            if (r12 == r0) goto L64
            int r2 = com.google.android.gms.internal.clearcut.zzax.zza(r12, r1, r11, r3, r5)
            goto L34
        L63:
            r11 = r2
        L64:
            if (r9 == 0) goto L6c
            int r8 = r8 << 3
            r8 = r8 | r10
            r4.zzb(r8, r9)
        L6c:
            r10 = r11
            r9 = r1
            r11 = r3
            r12 = r5
            goto L11
        L71:
            r3 = r11
            if (r10 != r3) goto L75
            return
        L75:
            com.google.android.gms.internal.clearcut.zzco r8 = com.google.android.gms.internal.clearcut.zzco.zzbo()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzdu.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.clearcut.zzay):void");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(Object obj) {
        this.zzmx.zzc(obj);
        this.zzmy.zzc(obj);
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(Object obj, Object obj2) {
        zzeh.zza(this.zzmx, obj, obj2);
        if (this.zzmo) {
            zzeh.zza(this.zzmy, obj, obj2);
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final int zzm(Object obj) {
        zzex zzexVar = this.zzmx;
        int iZzr = zzexVar.zzr(zzexVar.zzq(obj));
        return this.zzmo ? iZzr + this.zzmy.zza(obj).zzat() : iZzr;
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final boolean zzo(Object obj) {
        return this.zzmy.zza(obj).isInitialized();
    }
}
