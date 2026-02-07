package com.google.android.recaptcha.internal;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
final class zzki implements zzkr {
    private final zzke zza;
    private final zzll zzb;
    private final boolean zzc;
    private final zzif zzd;

    private zzki(zzll zzllVar, zzif zzifVar, zzke zzkeVar) {
        this.zzb = zzllVar;
        this.zzc = zzifVar.zzj(zzkeVar);
        this.zzd = zzifVar;
        this.zza = zzkeVar;
    }

    static zzki zzc(zzll zzllVar, zzif zzifVar, zzke zzkeVar) {
        return new zzki(zzllVar, zzifVar, zzkeVar);
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final int zza(Object obj) {
        zzll zzllVar = this.zzb;
        int iZzb = zzllVar.zzb(zzllVar.zzd(obj));
        return this.zzc ? iZzb + this.zzd.zzb(obj).zzb() : iZzb;
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final int zzb(Object obj) {
        int iHashCode = this.zzb.zzd(obj).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zzb(obj).zza.hashCode() : iHashCode;
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final Object zze() {
        zzke zzkeVar = this.zza;
        return zzkeVar instanceof zzit ? ((zzit) zzkeVar).zzs() : zzkeVar.zzW().zzk();
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final void zzf(Object obj) {
        this.zzb.zzm(obj);
        this.zzd.zzf(obj);
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final void zzg(Object obj, Object obj2) {
        zzkt.zzr(this.zzb, obj, obj2);
        if (this.zzc) {
            zzkt.zzq(this.zzd, obj, obj2);
        }
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final void zzh(Object obj, zzkq zzkqVar, zzie zzieVar) {
        boolean zZzO;
        zzll zzllVar = this.zzb;
        Object objZzc = zzllVar.zzc(obj);
        zzif zzifVar = this.zzd;
        zzij zzijVarZzc = zzifVar.zzc(obj);
        while (zzkqVar.zzc() != Integer.MAX_VALUE) {
            try {
                int iZzd = zzkqVar.zzd();
                if (iZzd != 11) {
                    if ((iZzd & 7) == 2) {
                        Object objZzd = zzifVar.zzd(zzieVar, this.zza, iZzd >>> 3);
                        if (objZzd != null) {
                            zzifVar.zzg(zzkqVar, objZzd, zzieVar, zzijVarZzc);
                        } else {
                            zZzO = zzllVar.zzr(objZzc, zzkqVar);
                        }
                    } else {
                        zZzO = zzkqVar.zzO();
                    }
                    if (!zZzO) {
                        break;
                    }
                } else {
                    Object objZzd2 = null;
                    zzgw zzgwVarZzp = null;
                    int iZzj = 0;
                    while (zzkqVar.zzc() != Integer.MAX_VALUE) {
                        int iZzd2 = zzkqVar.zzd();
                        if (iZzd2 == 16) {
                            iZzj = zzkqVar.zzj();
                            objZzd2 = zzifVar.zzd(zzieVar, this.zza, iZzj);
                        } else if (iZzd2 == 26) {
                            if (objZzd2 != null) {
                                zzifVar.zzg(zzkqVar, objZzd2, zzieVar, zzijVarZzc);
                            } else {
                                zzgwVarZzp = zzkqVar.zzp();
                            }
                        } else if (!zzkqVar.zzO()) {
                            break;
                        }
                    }
                    if (zzkqVar.zzd() != 12) {
                        throw zzje.zzb();
                    }
                    if (zzgwVarZzp != null) {
                        if (objZzd2 != null) {
                            zzifVar.zzh(zzgwVarZzp, objZzd2, zzieVar, zzijVarZzc);
                        } else {
                            zzllVar.zzk(objZzc, iZzj, zzgwVarZzp);
                        }
                    }
                }
            } finally {
                zzllVar.zzn(obj, objZzc);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x008f, code lost:
    
        if (r12 == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0091, code lost:
    
        r6.zzj((r11 << 3) | 2, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0097, code lost:
    
        r12 = r13;
        r11 = r3;
        r13 = r5;
        r14 = r7;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x008f A[EDGE_INSN: B:58:0x008f->B:35:0x008f BREAK  A[LOOP:1: B:18:0x0050->B:63:0x0050], SYNTHETIC] */
    @Override // com.google.android.recaptcha.internal.zzkr
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzi(java.lang.Object r10, byte[] r11, int r12, int r13, com.google.android.recaptcha.internal.zzgj r14) throws com.google.android.recaptcha.internal.zzje {
        /*
            r9 = this;
            r0 = r10
            com.google.android.recaptcha.internal.zzit r0 = (com.google.android.recaptcha.internal.zzit) r0
            com.google.android.recaptcha.internal.zzlm r1 = r0.zzc
            com.google.android.recaptcha.internal.zzlm r2 = com.google.android.recaptcha.internal.zzlm.zzc()
            if (r1 != r2) goto L11
            com.google.android.recaptcha.internal.zzlm r1 = com.google.android.recaptcha.internal.zzlm.zzf()
            r0.zzc = r1
        L11:
            r6 = r1
            com.google.android.recaptcha.internal.zzip r10 = (com.google.android.recaptcha.internal.zzip) r10
            r10.zzi()
            r10 = 0
            r0 = r10
        L19:
            if (r12 >= r13) goto L9d
            int r4 = com.google.android.recaptcha.internal.zzgk.zzi(r11, r12, r14)
            int r2 = r14.zza
            r12 = 11
            r1 = 2
            if (r2 == r12) goto L4b
            r12 = r2 & 7
            if (r12 != r1) goto L43
            com.google.android.recaptcha.internal.zzif r12 = r9.zzd
            com.google.android.recaptcha.internal.zzie r0 = r14.zzd
            com.google.android.recaptcha.internal.zzke r1 = r9.zza
            int r3 = r2 >>> 3
            java.lang.Object r0 = r12.zzd(r0, r1, r3)
            if (r0 != 0) goto L40
            r3 = r11
            r5 = r13
            r7 = r14
            int r12 = com.google.android.recaptcha.internal.zzgk.zzh(r2, r3, r4, r5, r6, r7)
            goto L19
        L40:
            int r11 = com.google.android.recaptcha.internal.zzkn.zza
            throw r10
        L43:
            r3 = r11
            r5 = r13
            r7 = r14
            int r12 = com.google.android.recaptcha.internal.zzgk.zzo(r2, r3, r4, r5, r7)
            goto L19
        L4b:
            r3 = r11
            r5 = r13
            r7 = r14
            r11 = 0
            r12 = r10
        L50:
            if (r4 >= r5) goto L8e
            int r13 = com.google.android.recaptcha.internal.zzgk.zzi(r3, r4, r7)
            int r14 = r7.zza
            int r2 = r14 >>> 3
            r4 = r14 & 7
            if (r2 == r1) goto L72
            r8 = 3
            if (r2 == r8) goto L62
            goto L85
        L62:
            if (r0 != 0) goto L6f
            if (r4 != r1) goto L85
            int r4 = com.google.android.recaptcha.internal.zzgk.zza(r3, r13, r7)
            java.lang.Object r12 = r7.zzc
            com.google.android.recaptcha.internal.zzgw r12 = (com.google.android.recaptcha.internal.zzgw) r12
            goto L50
        L6f:
            int r11 = com.google.android.recaptcha.internal.zzkn.zza
            throw r10
        L72:
            if (r4 != 0) goto L85
            int r4 = com.google.android.recaptcha.internal.zzgk.zzi(r3, r13, r7)
            int r11 = r7.zza
            com.google.android.recaptcha.internal.zzif r13 = r9.zzd
            com.google.android.recaptcha.internal.zzie r14 = r7.zzd
            com.google.android.recaptcha.internal.zzke r0 = r9.zza
            java.lang.Object r0 = r13.zzd(r14, r0, r11)
            goto L50
        L85:
            r2 = 12
            if (r14 == r2) goto L8f
            int r4 = com.google.android.recaptcha.internal.zzgk.zzo(r14, r3, r13, r5, r7)
            goto L50
        L8e:
            r13 = r4
        L8f:
            if (r12 == 0) goto L97
            int r11 = r11 << 3
            r11 = r11 | r1
            r6.zzj(r11, r12)
        L97:
            r12 = r13
            r11 = r3
            r13 = r5
            r14 = r7
            goto L19
        L9d:
            r5 = r13
            if (r12 != r5) goto La1
            return
        La1:
            com.google.android.recaptcha.internal.zzje r10 = com.google.android.recaptcha.internal.zzje.zzg()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.recaptcha.internal.zzki.zzi(java.lang.Object, byte[], int, int, com.google.android.recaptcha.internal.zzgj):void");
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final void zzj(Object obj, zzmd zzmdVar) {
        Iterator itZzf = this.zzd.zzb(obj).zzf();
        while (itZzf.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzf.next();
            zzii zziiVar = (zzii) entry.getKey();
            if (zziiVar.zze() != zzmc.MESSAGE) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            zziiVar.zzg();
            zziiVar.zzf();
            if (entry instanceof zzjh) {
                zzmdVar.zzw(zziiVar.zza(), ((zzjh) entry).zza().zzb());
            } else {
                zzmdVar.zzw(zziiVar.zza(), entry.getValue());
            }
        }
        zzll zzllVar = this.zzb;
        zzllVar.zzp(zzllVar.zzd(obj), zzmdVar);
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final boolean zzk(Object obj, Object obj2) {
        zzll zzllVar = this.zzb;
        if (!zzllVar.zzd(obj).equals(zzllVar.zzd(obj2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zzb(obj).equals(this.zzd.zzb(obj2));
        }
        return true;
    }

    @Override // com.google.android.recaptcha.internal.zzkr
    public final boolean zzl(Object obj) {
        return this.zzd.zzb(obj).zzk();
    }
}
