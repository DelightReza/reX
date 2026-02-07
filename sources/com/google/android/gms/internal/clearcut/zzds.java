package com.google.android.gms.internal.clearcut;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mvel2.asm.TypeReference;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class zzds implements zzef {
    private static final Unsafe zzmh = zzfd.zzef();
    private final int[] zzmi;
    private final Object[] zzmj;
    private final int zzmk;
    private final int zzml;
    private final int zzmm;
    private final zzdo zzmn;
    private final boolean zzmo;
    private final boolean zzmp;
    private final boolean zzmq;
    private final boolean zzmr;
    private final int[] zzms;
    private final int[] zzmt;
    private final int[] zzmu;
    private final zzdw zzmv;
    private final zzcy zzmw;
    private final zzex zzmx;
    private final zzbu zzmy;
    private final zzdj zzmz;

    private zzds(int[] iArr, Object[] objArr, int i, int i2, int i3, zzdo zzdoVar, boolean z, boolean z2, int[] iArr2, int[] iArr3, int[] iArr4, zzdw zzdwVar, zzcy zzcyVar, zzex zzexVar, zzbu zzbuVar, zzdj zzdjVar) {
        this.zzmi = iArr;
        this.zzmj = objArr;
        this.zzmk = i;
        this.zzml = i2;
        this.zzmm = i3;
        this.zzmp = zzdoVar instanceof zzcg;
        this.zzmq = z;
        this.zzmo = zzbuVar != null && zzbuVar.zze(zzdoVar);
        this.zzmr = false;
        this.zzms = iArr2;
        this.zzmt = iArr3;
        this.zzmu = iArr4;
        this.zzmv = zzdwVar;
        this.zzmw = zzcyVar;
        this.zzmx = zzexVar;
        this.zzmy = zzbuVar;
        this.zzmn = zzdoVar;
        this.zzmz = zzdjVar;
    }

    private static int zza(int i, byte[] bArr, int i2, int i3, Object obj, zzay zzayVar) {
        return zzax.zza(i, bArr, i2, i3, zzn(obj), zzayVar);
    }

    private static int zza(zzef zzefVar, int i, byte[] bArr, int i2, int i3, zzcn zzcnVar, zzay zzayVar) throws zzco {
        int iZza = zza(zzefVar, bArr, i2, i3, zzayVar);
        while (true) {
            zzcnVar.add(zzayVar.zzff);
            if (iZza >= i3) {
                break;
            }
            int iZza2 = zzax.zza(bArr, iZza, zzayVar);
            if (i != zzayVar.zzfd) {
                break;
            }
            iZza = zza(zzefVar, bArr, iZza2, i3, zzayVar);
        }
        return iZza;
    }

    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, int i3, zzay zzayVar) throws zzco {
        zzds zzdsVar = (zzds) zzefVar;
        Object objNewInstance = zzdsVar.newInstance();
        int iZza = zzdsVar.zza(objNewInstance, bArr, i, i2, i3, zzayVar);
        zzdsVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return iZza;
    }

    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, zzay zzayVar) throws zzco {
        int iZza = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZza = zzax.zza(i3, bArr, iZza, zzayVar);
            i3 = zzayVar.zzfd;
        }
        int i4 = iZza;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzco.zzbl();
        }
        Object objNewInstance = zzefVar.newInstance();
        int i5 = i4 + i3;
        zzefVar.zza(objNewInstance, bArr, i4, i5, zzayVar);
        zzefVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return i5;
    }

    private static int zza(zzex zzexVar, Object obj) {
        return zzexVar.zzm(zzexVar.zzq(obj));
    }

    private final int zza(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzay zzayVar) throws zzco {
        int i9;
        Object objValueOf;
        int i10;
        Object objValueOf2;
        int iZzb;
        long jZza;
        int iZzm;
        Object objValueOf3;
        Object object;
        Unsafe unsafe = zzmh;
        long j2 = this.zzmi[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                objValueOf = Double.valueOf(zzax.zze(bArr, i));
                unsafe.putObject(obj, j, objValueOf);
                iZzb = i9 + 8;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 52:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                objValueOf2 = Float.valueOf(zzax.zzf(bArr, i));
                unsafe.putObject(obj, j, objValueOf2);
                iZzb = i10 + 4;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzayVar.zzfe;
                objValueOf3 = Long.valueOf(jZza);
                unsafe.putObject(obj, j, objValueOf3);
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzayVar.zzfd;
                objValueOf3 = Integer.valueOf(iZzm);
                unsafe.putObject(obj, j, objValueOf3);
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 56:
            case 65:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                objValueOf = Long.valueOf(zzax.zzd(bArr, i));
                unsafe.putObject(obj, j, objValueOf);
                iZzb = i9 + 8;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 57:
            case 64:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                objValueOf2 = Integer.valueOf(zzax.zzc(bArr, i));
                unsafe.putObject(obj, j, objValueOf2);
                iZzb = i10 + 4;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                objValueOf3 = Boolean.valueOf(zzayVar.zzfe != 0);
                unsafe.putObject(obj, j, objValueOf3);
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzax.zza(bArr, i, zzayVar);
                int i11 = zzayVar.zzfd;
                if (i11 == 0) {
                    unsafe.putObject(obj, j, "");
                } else {
                    if ((i6 & 536870912) != 0 && !zzff.zze(bArr, iZza, iZza + i11)) {
                        throw zzco.zzbp();
                    }
                    unsafe.putObject(obj, j, new String(bArr, iZza, i11, zzci.UTF_8));
                    iZza += i11;
                }
                unsafe.putInt(obj, j2, i4);
                return iZza;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZza2 = zza(zzad(i8), bArr, i, i2, zzayVar);
                object = unsafe.getInt(obj, j2) == i4 ? unsafe.getObject(obj, j) : null;
                Object objZza = zzayVar.zzff;
                if (object != null) {
                    objZza = zzci.zza(object, objZza);
                }
                unsafe.putObject(obj, j, objZza);
                unsafe.putInt(obj, j2, i4);
                return iZza2;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                int iZza3 = zzax.zza(bArr, i, zzayVar);
                int i12 = zzayVar.zzfd;
                if (i12 == 0) {
                    unsafe.putObject(obj, j, zzbb.zzfi);
                } else {
                    unsafe.putObject(obj, j, zzbb.zzb(bArr, iZza3, i12));
                    iZza3 += i12;
                }
                unsafe.putInt(obj, j2, i4);
                return iZza3;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZza4 = zzax.zza(bArr, i, zzayVar);
                int i13 = zzayVar.zzfd;
                zzck zzckVarZzaf = zzaf(i8);
                if (zzckVarZzaf != null && zzckVarZzaf.zzb(i13) == null) {
                    zzn(obj).zzb(i3, Long.valueOf(i13));
                    return iZza4;
                }
                unsafe.putObject(obj, j, Integer.valueOf(i13));
                iZzb = iZza4;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzbk.zzm(zzayVar.zzfd);
                objValueOf3 = Integer.valueOf(iZzm);
                unsafe.putObject(obj, j, objValueOf3);
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case TypeReference.INSTANCEOF /* 67 */:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzbk.zza(zzayVar.zzfe);
                objValueOf3 = Long.valueOf(jZza);
                unsafe.putObject(obj, j, objValueOf3);
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case TypeReference.NEW /* 68 */:
                if (i5 == 3) {
                    iZzb = zza(zzad(i8), bArr, i, i2, (i3 & (-8)) | 4, zzayVar);
                    object = unsafe.getInt(obj, j2) == i4 ? unsafe.getObject(obj, j) : null;
                    objValueOf3 = zzayVar.zzff;
                    if (object != null) {
                        objValueOf3 = zzci.zza(object, objValueOf3);
                    }
                    unsafe.putObject(obj, j, objValueOf3);
                    unsafe.putInt(obj, j2, i4);
                    return iZzb;
                }
            default:
                return i;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0129, code lost:
    
        r4.add(com.google.android.gms.internal.clearcut.zzbb.zzb(r13, r14, r1));
        r14 = r14 + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0119, code lost:
    
        if (r1 == 0) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011b, code lost:
    
        r4.add(com.google.android.gms.internal.clearcut.zzbb.zzfi);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0121, code lost:
    
        r4.add(com.google.android.gms.internal.clearcut.zzbb.zzb(r13, r14, r1));
        r14 = r14 + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0129, code lost:
    
        if (r14 >= r15) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x012b, code lost:
    
        r1 = com.google.android.gms.internal.clearcut.zzax.zza(r13, r14, r25);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0131, code lost:
    
        if (r16 != r25.zzfd) goto L211;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0133, code lost:
    
        r14 = com.google.android.gms.internal.clearcut.zzax.zza(r13, r1, r25);
        r1 = r25.zzfd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0139, code lost:
    
        if (r1 != 0) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x013c, code lost:
    
        return r14;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0185  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:104:0x01c8 -> B:95:0x01a1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x0139 -> B:65:0x011b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:90:0x0193 -> B:83:0x0174). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zza(java.lang.Object r12, byte[] r13, int r14, int r15, int r16, int r17, int r18, int r19, long r20, int r22, long r23, com.google.android.gms.internal.clearcut.zzay r25) throws com.google.android.gms.internal.clearcut.zzco {
        /*
            Method dump skipped, instructions count: 822
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, int, int, int, int, long, int, long, com.google.android.gms.internal.clearcut.zzay):int");
    }

    private final int zza(Object obj, byte[] bArr, int i, int i2, int i3, int i4, long j, zzay zzayVar) throws zzco {
        Unsafe unsafe = zzmh;
        Object objZzae = zzae(i3);
        Object object = unsafe.getObject(obj, j);
        if (this.zzmz.zzi(object)) {
            Object objZzk = this.zzmz.zzk(objZzae);
            this.zzmz.zzb(objZzk, object);
            unsafe.putObject(obj, j, objZzk);
            object = objZzk;
        }
        this.zzmz.zzl(objZzae);
        this.zzmz.zzg(object);
        int iZza = zzax.zza(bArr, i, zzayVar);
        int i5 = zzayVar.zzfd;
        if (i5 < 0 || i5 > i2 - iZza) {
            throw zzco.zzbl();
        }
        throw null;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:18:0x0066. Please report as an issue. */
    private final int zza(Object obj, byte[] bArr, int i, int i2, int i3, zzay zzayVar) throws zzco {
        zzds zzdsVar;
        Object obj2;
        int i4;
        Unsafe unsafe;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        zzds zzdsVar2;
        zzck zzckVarZzaf;
        int i10;
        int i11;
        int i12;
        byte[] bArr2;
        Unsafe unsafe2;
        int i13;
        int i14;
        int i15;
        int i16;
        int iZza;
        int i17;
        zzay zzayVar2;
        int i18;
        long j;
        Object objZza;
        zzay zzayVar3;
        int iZzb;
        Unsafe unsafe3;
        int i19;
        zzds zzdsVar3 = this;
        Object obj3 = obj;
        byte[] bArr3 = bArr;
        int i20 = i2;
        zzay zzayVar4 = zzayVar;
        Unsafe unsafe4 = zzmh;
        int i21 = -1;
        int iZza2 = i;
        int i22 = 0;
        int i23 = -1;
        int i24 = 0;
        while (true) {
            if (iZza2 < i20) {
                int iZza3 = iZza2 + 1;
                int i25 = bArr3[iZza2];
                if (i25 < 0) {
                    iZza3 = zzax.zza(i25, bArr3, iZza3, zzayVar4);
                    i25 = zzayVar4.zzfd;
                }
                int i26 = i25;
                iZza2 = iZza3;
                int i27 = i26 >>> 3;
                int i28 = i26 & 7;
                int iZzai = zzdsVar3.zzai(i27);
                if (iZzai != i21) {
                    int[] iArr = zzdsVar3.zzmi;
                    int i29 = iArr[iZzai + 1];
                    int i30 = (i29 & 267386880) >>> 20;
                    long j2 = i29 & 1048575;
                    if (i30 <= 17) {
                        int i31 = iArr[iZzai + 2];
                        int i32 = 1 << (i31 >>> 20);
                        int i33 = i31 & 1048575;
                        i5 = -1;
                        if (i33 != i23) {
                            if (i23 != -1) {
                                unsafe4.putInt(obj3, i23, i24);
                            }
                            i23 = i33;
                            i24 = unsafe4.getInt(obj3, i33);
                        }
                        switch (i30) {
                            case 0:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i13 = i26;
                                i14 = i2;
                                i15 = i23;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 1) {
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    zzfd.zza(obj3, j2, zzax.zze(bArr2, i16));
                                    iZza = i16 + 8;
                                    i24 = i11 | i32;
                                    zzayVar4 = zzayVar;
                                    i22 = i13;
                                    i20 = i14;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 1:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i13 = i26;
                                i14 = i2;
                                i15 = i23;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 5) {
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    zzfd.zza(obj3, j2, zzax.zzf(bArr2, i16));
                                    iZza = i16 + 4;
                                    i24 = i11 | i32;
                                    zzayVar4 = zzayVar;
                                    i22 = i13;
                                    i20 = i14;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 2:
                            case 3:
                                unsafe2 = unsafe4;
                                zzayVar3 = zzayVar;
                                i15 = i23;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 0) {
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZzb = zzax.zzb(bArr, i16, zzayVar3);
                                    Object obj4 = obj3;
                                    unsafe2.putLong(obj4, j2, zzayVar3.zzfe);
                                    unsafe3 = unsafe2;
                                    obj3 = obj4;
                                    i24 = i11 | i32;
                                    i20 = i2;
                                    bArr3 = bArr;
                                    i22 = i26;
                                    i23 = i15;
                                    i21 = -1;
                                    unsafe4 = unsafe3;
                                    iZza2 = iZzb;
                                    zzayVar4 = zzayVar3;
                                }
                            case 4:
                            case 11:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i17 = i2;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 0) {
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = zzax.zza(bArr2, i16, zzayVar2);
                                    unsafe2.putInt(obj3, j2, zzayVar2.zzfd);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i22 = i26;
                                    zzayVar4 = zzayVar2;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 5:
                            case 14:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                if (i28 != 1) {
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    Object obj5 = obj3;
                                    unsafe2.putLong(obj5, j2, zzax.zzd(bArr2, i18));
                                    unsafe2 = unsafe2;
                                    obj3 = obj5;
                                    iZza = i18 + 8;
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i22 = i26;
                                    zzayVar4 = zzayVar2;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 6:
                            case 13:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                if (i28 != 5) {
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    unsafe2.putInt(obj3, j2, zzax.zzc(bArr2, i18));
                                    iZza = i18 + 4;
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i22 = i26;
                                    zzayVar4 = zzayVar2;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 7:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                if (i28 != 0) {
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = zzax.zzb(bArr2, i18, zzayVar2);
                                    zzfd.zza(obj3, j2, zzayVar2.zzfe != 0);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i22 = i26;
                                    zzayVar4 = zzayVar2;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 8:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                j = j2;
                                if (i28 != 2) {
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = (i29 & 536870912) == 0 ? zzax.zzc(bArr2, i18, zzayVar2) : zzax.zzd(bArr2, i18, zzayVar2);
                                    objZza = zzayVar2.zzff;
                                    unsafe2.putObject(obj3, j, objZza);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i22 = i26;
                                    zzayVar4 = zzayVar2;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 9:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                j = j2;
                                if (i28 != 2) {
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = zza(zzdsVar3.zzad(iZzai), bArr2, i18, i17, zzayVar2);
                                    objZza = (i11 & i32) == 0 ? zzayVar2.zzff : zzci.zza(unsafe2.getObject(obj3, j), zzayVar2.zzff);
                                    unsafe2.putObject(obj3, j, objZza);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i22 = i26;
                                    zzayVar4 = zzayVar2;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 10:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                j = j2;
                                if (i28 != 2) {
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = zzax.zze(bArr2, i18, zzayVar2);
                                    objZza = zzayVar2.zzff;
                                    unsafe2.putObject(obj3, j, objZza);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i22 = i26;
                                    zzayVar4 = zzayVar2;
                                    i23 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 12:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i23;
                                if (i28 != 0) {
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = zzax.zza(bArr2, i18, zzayVar2);
                                    int i34 = zzayVar2.zzfd;
                                    zzck zzckVarZzaf2 = zzdsVar3.zzaf(iZzai);
                                    if (zzckVarZzaf2 == null || zzckVarZzaf2.zzb(i34) != null) {
                                        unsafe2.putInt(obj3, j2, i34);
                                        i24 = i11 | i32;
                                        i20 = i17;
                                        i22 = i26;
                                        zzayVar4 = zzayVar2;
                                        i23 = i15;
                                        unsafe4 = unsafe2;
                                        iZza2 = iZza;
                                        bArr3 = bArr2;
                                        i21 = -1;
                                    } else {
                                        zzn(obj3).zzb(i26, Long.valueOf(i34));
                                        i20 = i17;
                                        i22 = i26;
                                        zzayVar4 = zzayVar2;
                                        i23 = i15;
                                        i24 = i11;
                                        unsafe4 = unsafe2;
                                        iZza2 = iZza;
                                        bArr3 = bArr2;
                                        i21 = -1;
                                    }
                                }
                                break;
                            case 15:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                if (i28 != 0) {
                                    i15 = i23;
                                    i11 = i24;
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = zzax.zza(bArr2, i18, zzayVar);
                                    unsafe2.putInt(obj3, j2, zzbk.zzm(zzayVar.zzfd));
                                    i24 |= i32;
                                    i20 = i2;
                                    i22 = i26;
                                    zzayVar4 = zzayVar;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            case 16:
                                i18 = iZza2;
                                if (i28 != 0) {
                                    unsafe2 = unsafe4;
                                    i15 = i23;
                                    i11 = i24;
                                    i16 = i18;
                                    i13 = i26;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZzb = zzax.zzb(bArr, i18, zzayVar);
                                    zzayVar3 = zzayVar;
                                    Object obj6 = obj3;
                                    Unsafe unsafe5 = unsafe4;
                                    unsafe5.putLong(obj6, j2, zzbk.zza(zzayVar.zzfe));
                                    unsafe3 = unsafe5;
                                    obj3 = obj6;
                                    i24 |= i32;
                                    i20 = i2;
                                    bArr3 = bArr;
                                    i22 = i26;
                                    i21 = -1;
                                    unsafe4 = unsafe3;
                                    iZza2 = iZzb;
                                    zzayVar4 = zzayVar3;
                                }
                            case 17:
                                if (i28 != 3) {
                                    unsafe2 = unsafe4;
                                    i15 = i23;
                                    i13 = i26;
                                    i16 = iZza2;
                                    i11 = i24;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i23 = i15;
                                    i24 = i11;
                                    break;
                                } else {
                                    iZza = zza(zzdsVar3.zzad(iZzai), bArr, iZza2, i2, (i27 << 3) | 4, zzayVar);
                                    bArr2 = bArr;
                                    unsafe4.putObject(obj3, j2, (i24 & i32) == 0 ? zzayVar.zzff : zzci.zza(unsafe4.getObject(obj3, j2), zzayVar.zzff));
                                    i24 |= i32;
                                    i20 = i2;
                                    zzayVar4 = zzayVar;
                                    i22 = i26;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                            default:
                                unsafe2 = unsafe4;
                                i15 = i23;
                                i13 = i26;
                                i16 = iZza2;
                                i11 = i24;
                                i6 = i3;
                                zzdsVar = zzdsVar3;
                                unsafe = unsafe2;
                                i12 = i13;
                                iZza2 = i16;
                                i23 = i15;
                                i24 = i11;
                                break;
                        }
                    } else {
                        Unsafe unsafe6 = unsafe4;
                        i5 = -1;
                        int i35 = i20;
                        if (i30 != 27) {
                            iZza2 = iZza2;
                            if (i30 <= 49) {
                                i11 = i24;
                                unsafe = unsafe6;
                                i10 = i23;
                                int iZza4 = zzdsVar3.zza(obj, bArr, iZza2, i2, i26, i27, i28, iZzai, i29, i30, j2, zzayVar);
                                i19 = i26;
                                if (iZza4 == iZza2) {
                                    zzdsVar = this;
                                    i6 = i3;
                                    iZza2 = iZza4;
                                    i12 = i19;
                                    i24 = i11;
                                    i23 = i10;
                                } else {
                                    zzdsVar3 = this;
                                    obj3 = obj;
                                    bArr3 = bArr;
                                    i20 = i2;
                                    zzayVar4 = zzayVar;
                                    iZza2 = iZza4;
                                    i22 = i19;
                                    unsafe4 = unsafe;
                                    i24 = i11;
                                    i21 = -1;
                                    i23 = i10;
                                }
                            } else {
                                unsafe = unsafe6;
                                i10 = i23;
                                i11 = i24;
                                i19 = i26;
                                if (i30 != 50) {
                                    int iZza5 = zza(obj, bArr, iZza2, i2, i19, i27, i28, i29, i30, j2, iZzai, zzayVar);
                                    zzdsVar = this;
                                    i12 = i19;
                                    if (iZza5 == iZza2) {
                                        i23 = i10;
                                        i6 = i3;
                                        iZza2 = iZza5;
                                        i24 = i11;
                                    } else {
                                        obj3 = obj;
                                        i23 = i10;
                                        i20 = i2;
                                        i22 = i12;
                                        iZza2 = iZza5;
                                        zzdsVar3 = zzdsVar;
                                        unsafe4 = unsafe;
                                        i24 = i11;
                                        i21 = -1;
                                        bArr3 = bArr;
                                        zzayVar4 = zzayVar;
                                    }
                                } else if (i28 == 2) {
                                    int iZza6 = zza(obj, bArr, iZza2, i2, iZzai, i27, j2, zzayVar);
                                    if (iZza6 == iZza2) {
                                        zzdsVar = this;
                                        i6 = i3;
                                        iZza2 = iZza6;
                                        i12 = i19;
                                        i24 = i11;
                                        i23 = i10;
                                    } else {
                                        zzdsVar3 = this;
                                        obj3 = obj;
                                        bArr3 = bArr;
                                        i20 = i2;
                                        zzayVar4 = zzayVar;
                                        iZza2 = iZza6;
                                        i22 = i19;
                                        unsafe4 = unsafe;
                                        i24 = i11;
                                        i21 = -1;
                                        i23 = i10;
                                    }
                                } else {
                                    zzdsVar = this;
                                    i12 = i19;
                                }
                            }
                        } else if (i28 == 2) {
                            zzcn zzcnVarZzi = (zzcn) unsafe6.getObject(obj3, j2);
                            if (!zzcnVarZzi.zzu()) {
                                int size = zzcnVarZzi.size();
                                zzcnVarZzi = zzcnVarZzi.zzi(size == 0 ? 10 : size << 1);
                                unsafe6.putObject(obj3, j2, zzcnVarZzi);
                            }
                            zzayVar4 = zzayVar;
                            unsafe4 = unsafe6;
                            i20 = i2;
                            iZza2 = zza(zzdsVar3.zzad(iZzai), i26, bArr, iZza2, i35, zzcnVarZzi, zzayVar4);
                            i22 = i26;
                            i21 = -1;
                            obj3 = obj;
                            bArr3 = bArr;
                        } else {
                            zzdsVar = zzdsVar3;
                            i12 = i26;
                            unsafe = unsafe6;
                            i10 = i23;
                            iZza2 = iZza2;
                            i11 = i24;
                        }
                    }
                    if (i12 == i6 || i6 == 0) {
                        int i36 = i12;
                        int iZza7 = zza(i36, bArr, iZza2, i2, obj, zzayVar);
                        i22 = i36;
                        obj3 = obj;
                        zzdsVar3 = zzdsVar;
                        unsafe4 = unsafe;
                        i21 = -1;
                        zzayVar4 = zzayVar;
                        i20 = i2;
                        iZza2 = iZza7;
                        bArr3 = bArr;
                    } else {
                        obj2 = obj;
                        i4 = i2;
                        i7 = i23;
                        i9 = i24;
                        i8 = i12;
                    }
                } else {
                    zzdsVar = zzdsVar3;
                    unsafe = unsafe4;
                    i10 = i23;
                    i11 = i24;
                    i12 = i26;
                    i5 = -1;
                }
                i23 = i10;
                i6 = i3;
                i24 = i11;
                if (i12 == i6) {
                }
                int i362 = i12;
                int iZza72 = zza(i362, bArr, iZza2, i2, obj, zzayVar);
                i22 = i362;
                obj3 = obj;
                zzdsVar3 = zzdsVar;
                unsafe4 = unsafe;
                i21 = -1;
                zzayVar4 = zzayVar;
                i20 = i2;
                iZza2 = iZza72;
                bArr3 = bArr;
            } else {
                zzdsVar = zzdsVar3;
                obj2 = obj3;
                i4 = i20;
                unsafe = unsafe4;
                int i37 = i24;
                i5 = -1;
                i6 = i3;
                i7 = i23;
                i8 = i22;
                i9 = i37;
            }
        }
        int i38 = iZza2;
        if (i7 != i5) {
            unsafe.putInt(obj2, i7, i9);
        }
        int[] iArr2 = zzdsVar.zzmt;
        if (iArr2 != null) {
            int length = iArr2.length;
            Object objZza2 = null;
            int i39 = 0;
            while (i39 < length) {
                int i40 = iArr2[i39];
                zzex zzexVar = zzdsVar.zzmx;
                int i41 = zzdsVar.zzmi[i40];
                Object objZzo = zzfd.zzo(obj2, zzdsVar.zzag(i40) & 1048575);
                if (objZzo == null || (zzckVarZzaf = zzdsVar.zzaf(i40)) == null) {
                    zzdsVar2 = zzdsVar;
                } else {
                    Map mapZzg = zzdsVar.zzmz.zzg(objZzo);
                    zzdsVar2 = zzdsVar;
                    objZza2 = zzdsVar2.zza(i40, i41, mapZzg, zzckVarZzaf, objZza2, zzexVar);
                }
                objZza2 = (zzey) objZza2;
                i39++;
                zzdsVar = zzdsVar2;
            }
            zzds zzdsVar4 = zzdsVar;
            if (objZza2 != null) {
                zzdsVar4.zzmx.zzf(obj2, objZza2);
            }
        }
        if (i6 == 0) {
            if (i38 != i4) {
                throw zzco.zzbo();
            }
        } else if (i38 > i4 || i8 != i6) {
            throw zzco.zzbo();
        }
        return i38;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x016b A[EDGE_INSN: B:76:0x016b->B:72:0x016b BREAK  A[LOOP:0: B:22:0x0066->B:71:0x0167], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.gms.internal.clearcut.zzds zza(java.lang.Class r23, com.google.android.gms.internal.clearcut.zzdm r24, com.google.android.gms.internal.clearcut.zzdw r25, com.google.android.gms.internal.clearcut.zzcy r26, com.google.android.gms.internal.clearcut.zzex r27, com.google.android.gms.internal.clearcut.zzbu r28, com.google.android.gms.internal.clearcut.zzdj r29) {
        /*
            Method dump skipped, instructions count: 396
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Class, com.google.android.gms.internal.clearcut.zzdm, com.google.android.gms.internal.clearcut.zzdw, com.google.android.gms.internal.clearcut.zzcy, com.google.android.gms.internal.clearcut.zzex, com.google.android.gms.internal.clearcut.zzbu, com.google.android.gms.internal.clearcut.zzdj):com.google.android.gms.internal.clearcut.zzds");
    }

    private final Object zza(int i, int i2, Map map, zzck zzckVar, Object obj, zzex zzexVar) {
        this.zzmz.zzl(zzae(i));
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (zzckVar.zzb(((Integer) entry.getValue()).intValue()) == null) {
                if (obj == null) {
                    obj = zzexVar.zzdz();
                }
                zzbg zzbgVarZzk = zzbb.zzk(zzdg.zza(null, entry.getKey(), entry.getValue()));
                try {
                    zzdg.zza(zzbgVarZzk.zzae(), null, entry.getKey(), entry.getValue());
                    zzexVar.zza(obj, i2, zzbgVarZzk.zzad());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return obj;
    }

    private static void zza(int i, Object obj, zzfr zzfrVar) {
        if (obj instanceof String) {
            zzfrVar.zza(i, (String) obj);
        } else {
            zzfrVar.zza(i, (zzbb) obj);
        }
    }

    private static void zza(zzex zzexVar, Object obj, zzfr zzfrVar) {
        zzexVar.zza(zzexVar.zzq(obj), zzfrVar);
    }

    private final void zza(zzfr zzfrVar, int i, Object obj, int i2) {
        if (obj != null) {
            this.zzmz.zzl(zzae(i2));
            zzfrVar.zza(i, (zzdh) null, this.zzmz.zzh(obj));
        }
    }

    private final void zza(Object obj, Object obj2, int i) {
        long jZzag = zzag(i) & 1048575;
        if (zza(obj2, i)) {
            Object objZzo = zzfd.zzo(obj, jZzag);
            Object objZzo2 = zzfd.zzo(obj2, jZzag);
            if (objZzo != null && objZzo2 != null) {
                objZzo2 = zzci.zza(objZzo, objZzo2);
            } else if (objZzo2 == null) {
                return;
            }
            zzfd.zza(obj, jZzag, objZzo2);
            zzb(obj, i);
        }
    }

    private final boolean zza(Object obj, int i) {
        if (!this.zzmq) {
            int iZzah = zzah(i);
            return (zzfd.zzj(obj, (long) (iZzah & 1048575)) & (1 << (iZzah >>> 20))) != 0;
        }
        int iZzag = zzag(i);
        long j = iZzag & 1048575;
        switch ((iZzag & 267386880) >>> 20) {
            case 0:
                return zzfd.zzn(obj, j) != 0.0d;
            case 1:
                return zzfd.zzm(obj, j) != 0.0f;
            case 2:
                return zzfd.zzk(obj, j) != 0;
            case 3:
                return zzfd.zzk(obj, j) != 0;
            case 4:
                return zzfd.zzj(obj, j) != 0;
            case 5:
                return zzfd.zzk(obj, j) != 0;
            case 6:
                return zzfd.zzj(obj, j) != 0;
            case 7:
                return zzfd.zzl(obj, j);
            case 8:
                Object objZzo = zzfd.zzo(obj, j);
                if (objZzo instanceof String) {
                    return !((String) objZzo).isEmpty();
                }
                if (objZzo instanceof zzbb) {
                    return !zzbb.zzfi.equals(objZzo);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzfd.zzo(obj, j) != null;
            case 10:
                return !zzbb.zzfi.equals(zzfd.zzo(obj, j));
            case 11:
                return zzfd.zzj(obj, j) != 0;
            case 12:
                return zzfd.zzj(obj, j) != 0;
            case 13:
                return zzfd.zzj(obj, j) != 0;
            case 14:
                return zzfd.zzk(obj, j) != 0;
            case 15:
                return zzfd.zzj(obj, j) != 0;
            case 16:
                return zzfd.zzk(obj, j) != 0;
            case 17:
                return zzfd.zzo(obj, j) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zza(Object obj, int i, int i2) {
        return zzfd.zzj(obj, (long) (zzah(i2) & 1048575)) == i;
    }

    private final boolean zza(Object obj, int i, int i2, int i3) {
        return this.zzmq ? zza(obj, i) : (i2 & i3) != 0;
    }

    private static boolean zza(Object obj, int i, zzef zzefVar) {
        return zzefVar.zzo(zzfd.zzo(obj, i & 1048575));
    }

    private final zzef zzad(int i) {
        int i2 = (i / 4) << 1;
        zzef zzefVar = (zzef) this.zzmj[i2];
        if (zzefVar != null) {
            return zzefVar;
        }
        zzef zzefVarZze = zzea.zzcm().zze((Class) this.zzmj[i2 + 1]);
        this.zzmj[i2] = zzefVarZze;
        return zzefVarZze;
    }

    private final Object zzae(int i) {
        return this.zzmj[(i / 4) << 1];
    }

    private final zzck zzaf(int i) {
        return (zzck) this.zzmj[((i / 4) << 1) + 1];
    }

    private final int zzag(int i) {
        return this.zzmi[i + 1];
    }

    private final int zzah(int i) {
        return this.zzmi[i + 2];
    }

    private final int zzai(int i) {
        int i2 = this.zzmk;
        if (i >= i2) {
            int i3 = this.zzmm;
            if (i < i3) {
                int i4 = (i - i2) << 2;
                if (this.zzmi[i4] == i) {
                    return i4;
                }
                return -1;
            }
            if (i <= this.zzml) {
                int i5 = i3 - i2;
                int length = (this.zzmi.length / 4) - 1;
                while (i5 <= length) {
                    int i6 = (length + i5) >>> 1;
                    int i7 = i6 << 2;
                    int i8 = this.zzmi[i7];
                    if (i == i8) {
                        return i7;
                    }
                    if (i < i8) {
                        length = i6 - 1;
                    } else {
                        i5 = i6 + 1;
                    }
                }
            }
        }
        return -1;
    }

    private final void zzb(Object obj, int i) {
        if (this.zzmq) {
            return;
        }
        int iZzah = zzah(i);
        long j = iZzah & 1048575;
        zzfd.zza(obj, j, zzfd.zzj(obj, j) | (1 << (iZzah >>> 20)));
    }

    private final void zzb(Object obj, int i, int i2) {
        zzfd.zza(obj, zzah(i2) & 1048575, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzb(java.lang.Object r19, com.google.android.gms.internal.clearcut.zzfr r20) {
        /*
            Method dump skipped, instructions count: 1290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzb(java.lang.Object, com.google.android.gms.internal.clearcut.zzfr):void");
    }

    private final void zzb(Object obj, Object obj2, int i) {
        int iZzag = zzag(i);
        int i2 = this.zzmi[i];
        long j = iZzag & 1048575;
        if (zza(obj2, i2, i)) {
            Object objZzo = zzfd.zzo(obj, j);
            Object objZzo2 = zzfd.zzo(obj2, j);
            if (objZzo != null && objZzo2 != null) {
                objZzo2 = zzci.zza(objZzo, objZzo2);
            } else if (objZzo2 == null) {
                return;
            }
            zzfd.zza(obj, j, objZzo2);
            zzb(obj, i2, i);
        }
    }

    private final boolean zzc(Object obj, Object obj2, int i) {
        return zza(obj, i) == zza(obj2, i);
    }

    private static List zzd(Object obj, long j) {
        return (List) zzfd.zzo(obj, j);
    }

    private static double zze(Object obj, long j) {
        return ((Double) zzfd.zzo(obj, j)).doubleValue();
    }

    private static float zzf(Object obj, long j) {
        return ((Float) zzfd.zzo(obj, j)).floatValue();
    }

    private static int zzg(Object obj, long j) {
        return ((Integer) zzfd.zzo(obj, j)).intValue();
    }

    private static long zzh(Object obj, long j) {
        return ((Long) zzfd.zzo(obj, j)).longValue();
    }

    private static boolean zzi(Object obj, long j) {
        return ((Boolean) zzfd.zzo(obj, j)).booleanValue();
    }

    private static zzey zzn(Object obj) {
        zzcg zzcgVar = (zzcg) obj;
        zzey zzeyVar = zzcgVar.zzjp;
        if (zzeyVar != zzey.zzea()) {
            return zzeyVar;
        }
        zzey zzeyVarZzeb = zzey.zzeb();
        zzcgVar.zzjp = zzeyVarZzeb;
        return zzeyVarZzeb;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(java.lang.Object r10, java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 612
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.equals(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00f4 A[PHI: r3
      0x00f4: PHI (r3v9 java.lang.Object) = (r3v6 java.lang.Object), (r3v10 java.lang.Object) binds: [B:74:0x0110, B:68:0x00f2] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int hashCode(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 474
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.hashCode(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final Object newInstance() {
        return this.zzmv.newInstance(this.zzmn);
    }

    /* JADX WARN: Removed duplicated region for block: B:192:0x04f6  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0533  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x05ae  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x05c1  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x05d6  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0030  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(java.lang.Object r14, com.google.android.gms.internal.clearcut.zzfr r15) {
        /*
            Method dump skipped, instructions count: 2752
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, com.google.android.gms.internal.clearcut.zzfr):void");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zza(Object obj, byte[] bArr, int i, int i2, zzay zzayVar) throws zzco {
        Unsafe unsafe;
        zzay zzayVar2;
        Object obj2;
        int i3;
        int i4;
        byte[] bArr2;
        int i5;
        int i6;
        int iZza;
        Unsafe unsafe2;
        Object obj3;
        Object objZza;
        Object obj4;
        int iZzm;
        zzds zzdsVar = this;
        byte[] bArr3 = bArr;
        int i7 = i2;
        zzay zzayVar3 = zzayVar;
        if (!zzdsVar.zzmq) {
            zza(obj, bArr, i, i7, 0, zzayVar);
            return;
        }
        Unsafe unsafe3 = zzmh;
        int iZza2 = i;
        while (iZza2 < i7) {
            int iZza3 = iZza2 + 1;
            int i8 = bArr3[iZza2];
            if (i8 < 0) {
                iZza3 = zzax.zza(i8, bArr3, iZza3, zzayVar3);
                i8 = zzayVar3.zzfd;
            }
            int i9 = i8;
            int i10 = iZza3;
            int i11 = i9 >>> 3;
            int i12 = i9 & 7;
            int iZzai = zzdsVar.zzai(i11);
            if (iZzai >= 0) {
                int i13 = zzdsVar.zzmi[iZzai + 1];
                int i14 = (267386880 & i13) >>> 20;
                long j = 1048575 & i13;
                if (i14 <= 17) {
                    switch (i14) {
                        case 0:
                            unsafe = unsafe3;
                            if (i12 == 1) {
                                zzfd.zza(obj, j, zzax.zze(bArr3, i10));
                                iZza2 = i10 + 8;
                                unsafe3 = unsafe;
                                break;
                            }
                            i6 = i10;
                            obj2 = obj;
                            bArr2 = bArr;
                            i4 = i6;
                            i5 = i9;
                            i3 = i2;
                            zzayVar2 = zzayVar;
                            iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                            zzdsVar = this;
                            bArr3 = bArr;
                            zzayVar3 = zzayVar;
                            i7 = i3;
                            unsafe3 = unsafe;
                        case 1:
                            unsafe = unsafe3;
                            if (i12 == 5) {
                                zzfd.zza(obj, j, zzax.zzf(bArr3, i10));
                                iZza2 = i10 + 4;
                                unsafe3 = unsafe;
                                break;
                            }
                            i6 = i10;
                            obj2 = obj;
                            bArr2 = bArr;
                            i4 = i6;
                            i5 = i9;
                            i3 = i2;
                            zzayVar2 = zzayVar;
                            iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                            zzdsVar = this;
                            bArr3 = bArr;
                            zzayVar3 = zzayVar;
                            i7 = i3;
                            unsafe3 = unsafe;
                        case 2:
                        case 3:
                            Unsafe unsafe4 = unsafe3;
                            if (i12 != 0) {
                                unsafe = unsafe4;
                                i6 = i10;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                int iZzb = zzax.zzb(bArr3, i10, zzayVar3);
                                unsafe3 = unsafe4;
                                unsafe3.putLong(obj, j, zzayVar3.zzfe);
                                iZza2 = iZzb;
                                break;
                            }
                        case 4:
                        case 11:
                            unsafe2 = unsafe3;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                iZza2 = zzax.zza(bArr3, i10, zzayVar3);
                                unsafe2.putInt(obj, j, zzayVar3.zzfd);
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 5:
                        case 14:
                            unsafe2 = unsafe3;
                            if (i12 != 1) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                unsafe2.putLong(obj, j, zzax.zzd(bArr3, i10));
                                iZza2 = i10 + 8;
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 6:
                        case 13:
                            unsafe2 = unsafe3;
                            if (i12 != 5) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                unsafe2.putInt(obj, j, zzax.zzc(bArr3, i10));
                                iZza2 = i10 + 4;
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 7:
                            unsafe2 = unsafe3;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                iZza2 = zzax.zzb(bArr3, i10, zzayVar3);
                                zzfd.zza(obj, j, zzayVar3.zzfe != 0);
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 8:
                            unsafe2 = unsafe3;
                            obj3 = obj;
                            if (i12 != 2) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                iZza2 = (536870912 & i13) == 0 ? zzax.zzc(bArr3, i10, zzayVar3) : zzax.zzd(bArr3, i10, zzayVar3);
                                objZza = zzayVar3.zzff;
                                unsafe2.putObject(obj3, j, objZza);
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 9:
                            unsafe2 = unsafe3;
                            obj3 = obj;
                            if (i12 != 2) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                iZza2 = zza(zzdsVar.zzad(iZzai), bArr3, i10, i7, zzayVar3);
                                Object object = unsafe2.getObject(obj3, j);
                                objZza = object == null ? zzayVar3.zzff : zzci.zza(object, zzayVar3.zzff);
                                unsafe2.putObject(obj3, j, objZza);
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 10:
                            unsafe2 = unsafe3;
                            obj3 = obj;
                            if (i12 != 2) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                iZza2 = zzax.zze(bArr3, i10, zzayVar3);
                                objZza = zzayVar3.zzff;
                                unsafe2.putObject(obj3, j, objZza);
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 12:
                            unsafe2 = unsafe3;
                            obj4 = obj;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                iZza2 = zzax.zza(bArr3, i10, zzayVar3);
                                iZzm = zzayVar3.zzfd;
                                unsafe2.putInt(obj4, j, iZzm);
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 15:
                            unsafe2 = unsafe3;
                            obj4 = obj;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                iZza2 = zzax.zza(bArr3, i10, zzayVar3);
                                iZzm = zzbk.zzm(zzayVar3.zzfd);
                                unsafe2.putInt(obj4, j, iZzm);
                                unsafe3 = unsafe2;
                                break;
                            }
                        case 16:
                            if (i12 != 0) {
                                unsafe2 = unsafe3;
                                i6 = i10;
                                unsafe = unsafe2;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                                break;
                            } else {
                                int iZzb2 = zzax.zzb(bArr3, i10, zzayVar3);
                                unsafe3.putLong(obj, j, zzbk.zza(zzayVar3.zzfe));
                                unsafe2 = unsafe3;
                                iZza2 = iZzb2;
                                unsafe3 = unsafe2;
                                break;
                            }
                    }
                } else {
                    unsafe = unsafe3;
                    if (i14 != 27) {
                        if (i14 <= 49) {
                            iZza = zzdsVar.zza(obj, bArr, i10, i2, i9, i11, i12, iZzai, i13, i14, j, zzayVar);
                            if (iZza == i10) {
                                obj2 = obj;
                                bArr2 = bArr;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                i4 = iZza;
                                i5 = i9;
                                iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                            }
                        } else {
                            if (i14 != 50) {
                                iZza = zza(obj, bArr, i10, i2, i9, i11, i12, i13, i14, j, iZzai, zzayVar);
                                if (iZza == i10) {
                                    obj2 = obj;
                                    bArr2 = bArr;
                                    i3 = i2;
                                    i5 = i9;
                                    i4 = iZza;
                                }
                            } else if (i12 == 2) {
                                int iZza4 = zza(obj, bArr, i10, i2, iZzai, i11, j, zzayVar);
                                if (iZza4 == i10) {
                                    obj2 = obj;
                                    bArr2 = bArr;
                                    i3 = i2;
                                    i4 = iZza4;
                                    i5 = i9;
                                } else {
                                    zzdsVar = this;
                                    bArr3 = bArr;
                                    i7 = i2;
                                    zzayVar3 = zzayVar;
                                    iZza2 = iZza4;
                                }
                            } else {
                                i6 = i10;
                                i9 = i9;
                                obj2 = obj;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                            }
                            zzayVar2 = zzayVar;
                            iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                            zzdsVar = this;
                            bArr3 = bArr;
                            zzayVar3 = zzayVar;
                            i7 = i3;
                        }
                        zzdsVar = this;
                        bArr3 = bArr;
                        i7 = i2;
                        zzayVar3 = zzayVar;
                        iZza2 = iZza;
                    } else if (i12 == 2) {
                        zzcn zzcnVarZzi = (zzcn) unsafe.getObject(obj, j);
                        if (!zzcnVarZzi.zzu()) {
                            int size = zzcnVarZzi.size();
                            zzcnVarZzi = zzcnVarZzi.zzi(size == 0 ? 10 : size << 1);
                            unsafe.putObject(obj, j, zzcnVarZzi);
                        }
                        iZza2 = zza(zzdsVar.zzad(iZzai), i9, bArr3, i10, i7, zzcnVarZzi, zzayVar3);
                        bArr3 = bArr;
                        i7 = i2;
                        zzayVar3 = zzayVar;
                    } else {
                        i9 = i9;
                        i6 = i10;
                        obj2 = obj;
                        bArr2 = bArr;
                        i4 = i6;
                        i5 = i9;
                        i3 = i2;
                        zzayVar2 = zzayVar;
                        iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
                        zzdsVar = this;
                        bArr3 = bArr;
                        zzayVar3 = zzayVar;
                        i7 = i3;
                    }
                    unsafe3 = unsafe;
                }
            }
            unsafe = unsafe3;
            i6 = i10;
            obj2 = obj;
            bArr2 = bArr;
            i4 = i6;
            i5 = i9;
            i3 = i2;
            zzayVar2 = zzayVar;
            iZza2 = zza(i5, bArr2, i4, i3, obj2, zzayVar2);
            zzdsVar = this;
            bArr3 = bArr;
            zzayVar3 = zzayVar;
            i7 = i3;
            unsafe3 = unsafe;
        }
        if (iZza2 != i7) {
            throw zzco.zzbo();
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(Object obj) {
        int[] iArr = this.zzmt;
        if (iArr != null) {
            for (int i : iArr) {
                long jZzag = zzag(i) & 1048575;
                Object objZzo = zzfd.zzo(obj, jZzag);
                if (objZzo != null) {
                    zzfd.zza(obj, jZzag, this.zzmz.zzj(objZzo));
                }
            }
        }
        int[] iArr2 = this.zzmu;
        if (iArr2 != null) {
            for (int i2 : iArr2) {
                this.zzmw.zza(obj, i2);
            }
        }
        this.zzmx.zzc(obj);
        if (this.zzmo) {
            this.zzmy.zzc(obj);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002b  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzc(java.lang.Object r7, java.lang.Object r8) {
        /*
            Method dump skipped, instructions count: 406
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzc(java.lang.Object, java.lang.Object):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x0369, code lost:
    
        if ((r4 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x0426, code lost:
    
        if (zza(r21, r15, r4) != false) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x0428, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, (com.google.android.gms.internal.clearcut.zzdo) r2.getObject(r21, r13), zzad(r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x045f, code lost:
    
        if (zza(r21, r15, r4) != false) goto L263;
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x0461, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzh(r15, 0L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x046a, code lost:
    
        if (zza(r21, r15, r4) != false) goto L266;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x046c, code lost:
    
        r8 = com.google.android.gms.internal.clearcut.zzbn.zzk(r15, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x0495, code lost:
    
        if (zza(r21, r15, r4) != false) goto L278;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x0497, code lost:
    
        r6 = (com.google.android.gms.internal.clearcut.zzbb) r2.getObject(r21, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x049d, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x04a6, code lost:
    
        if (zza(r21, r15, r4) != false) goto L282;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x04a8, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzeh.zzc(r15, r2.getObject(r21, r13), zzad(r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x04c1, code lost:
    
        if ((r6 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L287;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x04c3, code lost:
    
        r6 = (com.google.android.gms.internal.clearcut.zzbb) r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x04c6, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzb(r15, (java.lang.String) r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x04d2, code lost:
    
        if (zza(r21, r15, r4) != false) goto L291;
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x04d4, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x070d, code lost:
    
        if ((r12 & r19) != 0) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x0727, code lost:
    
        if ((r12 & r19) != 0) goto L263;
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x072d, code lost:
    
        if ((r12 & r19) != 0) goto L266;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0747, code lost:
    
        if ((r12 & r19) != 0) goto L278;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x074d, code lost:
    
        if ((r12 & r19) != 0) goto L282;
     */
    /* JADX WARN: Code restructure failed: missing block: B:429:0x075b, code lost:
    
        if ((r6 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L287;
     */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x0761, code lost:
    
        if ((r12 & r19) != 0) goto L291;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e2, code lost:
    
        if ((r4 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e4, code lost:
    
        r4 = (com.google.android.gms.internal.clearcut.zzbb) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e7, code lost:
    
        r3 = com.google.android.gms.internal.clearcut.zzbn.zzb(r3, (java.lang.String) r4);
     */
    /* JADX WARN: Removed duplicated region for block: B:319:0x056d A[PHI: r8
      0x056d: PHI (r8v70 int) = 
      (r8v32 int)
      (r8v35 int)
      (r8v38 int)
      (r8v41 int)
      (r8v44 int)
      (r8v47 int)
      (r8v50 int)
      (r8v53 int)
      (r8v56 int)
      (r8v59 int)
      (r8v62 int)
      (r8v65 int)
      (r8v68 int)
      (r8v73 int)
     binds: [B:384:0x065d, B:379:0x064b, B:374:0x0639, B:369:0x0627, B:364:0x0615, B:359:0x0603, B:354:0x05f1, B:349:0x05e0, B:344:0x05cf, B:339:0x05be, B:334:0x05ad, B:329:0x059c, B:324:0x058b, B:318:0x056b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x018b A[PHI: r4
      0x018b: PHI (r4v72 int) = 
      (r4v34 int)
      (r4v37 int)
      (r4v40 int)
      (r4v43 int)
      (r4v46 int)
      (r4v49 int)
      (r4v52 int)
      (r4v55 int)
      (r4v58 int)
      (r4v61 int)
      (r4v64 int)
      (r4v67 int)
      (r4v70 int)
      (r4v75 int)
     binds: [B:152:0x027b, B:147:0x0269, B:142:0x0257, B:137:0x0245, B:132:0x0233, B:127:0x0221, B:122:0x020f, B:117:0x01fe, B:112:0x01ed, B:107:0x01dc, B:102:0x01cb, B:97:0x01ba, B:92:0x01a9, B:86:0x0189] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zzm(java.lang.Object r21) {
        /*
            Method dump skipped, instructions count: 2300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzm(java.lang.Object):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00a7  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzo(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzo(java.lang.Object):boolean");
    }
}
