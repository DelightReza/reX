package com.google.android.gms.internal.measurement;

import androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticNonNull0;
import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.mvel2.Operator;
import org.mvel2.asm.TypeReference;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class zzml implements zzmt {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zznu.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzmi zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final int[] zzj;
    private final int zzk;
    private final int zzl;
    private final zzlw zzm;
    private final zznk zzn;
    private final zzko zzo;
    private final zzmn zzp;
    private final zzmd zzq;

    private zzml(int[] iArr, Object[] objArr, int i, int i2, zzmi zzmiVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzmn zzmnVar, zzlw zzlwVar, zznk zznkVar, zzko zzkoVar, zzmd zzmdVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = z;
        boolean z3 = false;
        if (zzkoVar != null && zzkoVar.zzc(zzmiVar)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzj = iArr2;
        this.zzk = i3;
        this.zzl = i4;
        this.zzp = zzmnVar;
        this.zzm = zzlwVar;
        this.zzn = zznkVar;
        this.zzo = zzkoVar;
        this.zzg = zzmiVar;
        this.zzq = zzmdVar;
    }

    private final zzlf zzA(int i) {
        int i2 = i / 3;
        return (zzlf) this.zzd[i2 + i2 + 1];
    }

    private final zzmt zzB(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzmt zzmtVar = (zzmt) this.zzd[i3];
        if (zzmtVar != null) {
            return zzmtVar;
        }
        zzmt zzmtVarZzb = zzmq.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzmtVarZzb;
        return zzmtVarZzb;
    }

    private final Object zzC(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final Object zzD(Object obj, int i) {
        zzmt zzmtVarZzB = zzB(i);
        int iZzy = zzy(i) & 1048575;
        if (!zzP(obj, i)) {
            return zzmtVarZzB.zze();
        }
        Object object = zzb.getObject(obj, iZzy);
        if (zzS(object)) {
            return object;
        }
        Object objZze = zzmtVarZzB.zze();
        if (object != null) {
            zzmtVarZzB.zzg(objZze, object);
        }
        return objZze;
    }

    private final Object zzE(Object obj, int i, int i2) {
        zzmt zzmtVarZzB = zzB(i2);
        if (!zzT(obj, i, i2)) {
            return zzmtVarZzB.zze();
        }
        Object object = zzb.getObject(obj, zzy(i2) & 1048575);
        if (zzS(object)) {
            return object;
        }
        Object objZze = zzmtVarZzB.zze();
        if (object != null) {
            zzmtVarZzB.zzg(objZze, object);
        }
        return objZze;
    }

    private static Field zzF(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private static void zzG(Object obj) {
        if (!zzS(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(obj)));
        }
    }

    private final void zzH(Object obj, Object obj2, int i) {
        if (zzP(obj2, i)) {
            int iZzy = zzy(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzy;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzmt zzmtVarZzB = zzB(i);
            if (!zzP(obj, i)) {
                if (zzS(object)) {
                    Object objZze = zzmtVarZzB.zze();
                    zzmtVarZzB.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzJ(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzS(object2)) {
                Object objZze2 = zzmtVarZzB.zze();
                zzmtVarZzB.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzmtVarZzB.zzg(object2, object);
        }
    }

    private final void zzI(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzT(obj2, i2, i)) {
            int iZzy = zzy(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzy;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzmt zzmtVarZzB = zzB(i);
            if (!zzT(obj, i2, i)) {
                if (zzS(object)) {
                    Object objZze = zzmtVarZzB.zze();
                    zzmtVarZzB.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzK(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzS(object2)) {
                Object objZze2 = zzmtVarZzB.zze();
                zzmtVarZzB.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzmtVarZzB.zzg(object2, object);
        }
    }

    private final void zzJ(Object obj, int i) {
        int iZzv = zzv(i);
        long j = 1048575 & iZzv;
        if (j == 1048575) {
            return;
        }
        zznu.zzq(obj, j, (1 << (iZzv >>> 20)) | zznu.zzc(obj, j));
    }

    private final void zzK(Object obj, int i, int i2) {
        zznu.zzq(obj, zzv(i2) & 1048575, i);
    }

    private final void zzL(Object obj, int i, Object obj2) {
        zzb.putObject(obj, zzy(i) & 1048575, obj2);
        zzJ(obj, i);
    }

    private final void zzM(Object obj, int i, int i2, Object obj2) {
        zzb.putObject(obj, zzy(i2) & 1048575, obj2);
        zzK(obj, i, i2);
    }

    private final void zzN(zzoc zzocVar, int i, Object obj, int i2) {
        if (obj == null) {
            return;
        }
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzC(i2));
        throw null;
    }

    private final boolean zzO(Object obj, Object obj2, int i) {
        return zzP(obj, i) == zzP(obj2, i);
    }

    private final boolean zzP(Object obj, int i) {
        int iZzv = zzv(i);
        long j = iZzv & 1048575;
        if (j != 1048575) {
            return (zznu.zzc(obj, j) & (1 << (iZzv >>> 20))) != 0;
        }
        int iZzy = zzy(i);
        long j2 = iZzy & 1048575;
        switch (zzx(iZzy)) {
            case 0:
                return Double.doubleToRawLongBits(zznu.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zznu.zzb(obj, j2)) != 0;
            case 2:
                return zznu.zzd(obj, j2) != 0;
            case 3:
                return zznu.zzd(obj, j2) != 0;
            case 4:
                return zznu.zzc(obj, j2) != 0;
            case 5:
                return zznu.zzd(obj, j2) != 0;
            case 6:
                return zznu.zzc(obj, j2) != 0;
            case 7:
                return zznu.zzw(obj, j2);
            case 8:
                Object objZzf = zznu.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzka) {
                    return !zzka.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zznu.zzf(obj, j2) != null;
            case 10:
                return !zzka.zzb.equals(zznu.zzf(obj, j2));
            case 11:
                return zznu.zzc(obj, j2) != 0;
            case 12:
                return zznu.zzc(obj, j2) != 0;
            case 13:
                return zznu.zzc(obj, j2) != 0;
            case 14:
                return zznu.zzd(obj, j2) != 0;
            case 15:
                return zznu.zzc(obj, j2) != 0;
            case 16:
                return zznu.zzd(obj, j2) != 0;
            case 17:
                return zznu.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzQ(Object obj, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzP(obj, i) : (i3 & i4) != 0;
    }

    private static boolean zzR(Object obj, int i, zzmt zzmtVar) {
        return zzmtVar.zzk(zznu.zzf(obj, i & 1048575));
    }

    private static boolean zzS(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzlb) {
            return ((zzlb) obj).zzbR();
        }
        return true;
    }

    private final boolean zzT(Object obj, int i, int i2) {
        return zznu.zzc(obj, (long) (zzv(i2) & 1048575)) == i;
    }

    private static boolean zzU(Object obj, long j) {
        return ((Boolean) zznu.zzf(obj, j)).booleanValue();
    }

    private static final void zzV(int i, Object obj, zzoc zzocVar) {
        if (obj instanceof String) {
            zzocVar.zzF(i, (String) obj);
        } else {
            zzocVar.zzd(i, (zzka) obj);
        }
    }

    static zznl zzd(Object obj) {
        zzlb zzlbVar = (zzlb) obj;
        zznl zznlVar = zzlbVar.zzc;
        if (zznlVar != zznl.zzc()) {
            return zznlVar;
        }
        zznl zznlVarZzf = zznl.zzf();
        zzlbVar.zzc = zznlVarZzf;
        return zznlVarZzf;
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x038e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.gms.internal.measurement.zzml zzl(java.lang.Class r32, com.google.android.gms.internal.measurement.zzmf r33, com.google.android.gms.internal.measurement.zzmn r34, com.google.android.gms.internal.measurement.zzlw r35, com.google.android.gms.internal.measurement.zznk r36, com.google.android.gms.internal.measurement.zzko r37, com.google.android.gms.internal.measurement.zzmd r38) {
        /*
            Method dump skipped, instructions count: 1022
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzml.zzl(java.lang.Class, com.google.android.gms.internal.measurement.zzmf, com.google.android.gms.internal.measurement.zzmn, com.google.android.gms.internal.measurement.zzlw, com.google.android.gms.internal.measurement.zznk, com.google.android.gms.internal.measurement.zzko, com.google.android.gms.internal.measurement.zzmd):com.google.android.gms.internal.measurement.zzml");
    }

    private static double zzm(Object obj, long j) {
        return ((Double) zznu.zzf(obj, j)).doubleValue();
    }

    private static float zzn(Object obj, long j) {
        return ((Float) zznu.zzf(obj, j)).floatValue();
    }

    private final int zzo(Object obj) {
        int i;
        int iZzx;
        int iZzx2;
        int iZzy;
        int iZzx3;
        int iZzx4;
        int iZzx5;
        int iZzx6;
        int iZzn;
        int iZzx7;
        int iZzy2;
        int iZzx8;
        int iZzx9;
        Unsafe unsafe = zzb;
        int i2 = 0;
        int i3 = 1048575;
        int i4 = 0;
        for (int i5 = 0; i5 < this.zzc.length; i5 += 3) {
            int iZzy3 = zzy(i5);
            int[] iArr = this.zzc;
            int i6 = iArr[i5];
            int iZzx10 = zzx(iZzy3);
            if (iZzx10 <= 17) {
                int i7 = iArr[i5 + 2];
                int i8 = i7 & 1048575;
                int i9 = i7 >>> 20;
                if (i8 != i3) {
                    i4 = unsafe.getInt(obj, i8);
                    i3 = i8;
                }
                i = 1 << i9;
            } else {
                i = 0;
            }
            long j = iZzy3 & 1048575;
            switch (iZzx10) {
                case 0:
                    if ((i4 & i) != 0) {
                        iZzx = zzki.zzx(i6 << 3);
                        iZzn = iZzx + 8;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if ((i4 & i) != 0) {
                        iZzx2 = zzki.zzx(i6 << 3);
                        iZzn = iZzx2 + 4;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if ((i4 & i) != 0) {
                        iZzy = zzki.zzy(unsafe.getLong(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if ((i4 & i) != 0) {
                        iZzy = zzki.zzy(unsafe.getLong(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if ((i4 & i) != 0) {
                        iZzy = zzki.zzu(unsafe.getInt(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if ((i4 & i) != 0) {
                        iZzx = zzki.zzx(i6 << 3);
                        iZzn = iZzx + 8;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if ((i4 & i) != 0) {
                        iZzx2 = zzki.zzx(i6 << 3);
                        iZzn = iZzx2 + 4;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if ((i4 & i) != 0) {
                        iZzx4 = zzki.zzx(i6 << 3);
                        iZzn = iZzx4 + 1;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if ((i4 & i) != 0) {
                        Object object = unsafe.getObject(obj, j);
                        if (object instanceof zzka) {
                            int i10 = i6 << 3;
                            int i11 = zzki.$r8$clinit;
                            int iZzd = ((zzka) object).zzd();
                            iZzx5 = zzki.zzx(iZzd) + iZzd;
                            iZzx6 = zzki.zzx(i10);
                            iZzn = iZzx6 + iZzx5;
                            i2 += iZzn;
                            break;
                        } else {
                            iZzy = zzki.zzw((String) object);
                            iZzx3 = zzki.zzx(i6 << 3);
                            i2 += iZzx3 + iZzy;
                            break;
                        }
                    } else {
                        break;
                    }
                case 9:
                    if ((i4 & i) != 0) {
                        iZzn = zzmv.zzn(i6, unsafe.getObject(obj, j), zzB(i5));
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if ((i4 & i) != 0) {
                        zzka zzkaVar = (zzka) unsafe.getObject(obj, j);
                        int i12 = i6 << 3;
                        int i13 = zzki.$r8$clinit;
                        int iZzd2 = zzkaVar.zzd();
                        iZzx5 = zzki.zzx(iZzd2) + iZzd2;
                        iZzx6 = zzki.zzx(i12);
                        iZzn = iZzx6 + iZzx5;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if ((i4 & i) != 0) {
                        iZzy = zzki.zzx(unsafe.getInt(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if ((i4 & i) != 0) {
                        iZzy = zzki.zzu(unsafe.getInt(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if ((i4 & i) != 0) {
                        iZzx2 = zzki.zzx(i6 << 3);
                        iZzn = iZzx2 + 4;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if ((i4 & i) != 0) {
                        iZzx = zzki.zzx(i6 << 3);
                        iZzn = iZzx + 8;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if ((i4 & i) != 0) {
                        int i14 = unsafe.getInt(obj, j);
                        iZzx3 = zzki.zzx(i6 << 3);
                        iZzy = zzki.zzx((i14 >> 31) ^ (i14 + i14));
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if ((i & i4) != 0) {
                        long j2 = unsafe.getLong(obj, j);
                        iZzx7 = zzki.zzx(i6 << 3);
                        iZzy2 = zzki.zzy((j2 >> 63) ^ (j2 + j2));
                        iZzn = iZzx7 + iZzy2;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if ((i4 & i) != 0) {
                        iZzn = zzki.zzt(i6, (zzmi) unsafe.getObject(obj, j), zzB(i5));
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iZzn = zzmv.zzg(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 19:
                    iZzn = zzmv.zze(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 20:
                    iZzn = zzmv.zzl(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 21:
                    iZzn = zzmv.zzw(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 22:
                    iZzn = zzmv.zzj(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 23:
                    iZzn = zzmv.zzg(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 24:
                    iZzn = zzmv.zze(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 25:
                    iZzn = zzmv.zza(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 26:
                    iZzn = zzmv.zzt(i6, (List) unsafe.getObject(obj, j));
                    i2 += iZzn;
                    break;
                case 27:
                    iZzn = zzmv.zzo(i6, (List) unsafe.getObject(obj, j), zzB(i5));
                    i2 += iZzn;
                    break;
                case 28:
                    iZzn = zzmv.zzb(i6, (List) unsafe.getObject(obj, j));
                    i2 += iZzn;
                    break;
                case 29:
                    iZzn = zzmv.zzu(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 30:
                    iZzn = zzmv.zzc(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 31:
                    iZzn = zzmv.zze(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 32:
                    iZzn = zzmv.zzg(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 33:
                    iZzn = zzmv.zzp(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case 34:
                    iZzn = zzmv.zzr(i6, (List) unsafe.getObject(obj, j), false);
                    i2 += iZzn;
                    break;
                case Operator.PROJECTION /* 35 */:
                    iZzy = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i15 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i15);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.CONVERTABLE_TO /* 36 */:
                    iZzy = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i16 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i16);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.END_OF_STMT /* 37 */:
                    iZzy = zzmv.zzm((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i17 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i17);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.FOREACH /* 38 */:
                    iZzy = zzmv.zzx((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i18 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i18);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.f1408IF /* 39 */:
                    iZzy = zzmv.zzk((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i19 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i19);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.ELSE /* 40 */:
                    iZzy = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i20 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i20);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.WHILE /* 41 */:
                    iZzy = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i21 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i21);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.UNTIL /* 42 */:
                    List list = (List) unsafe.getObject(obj, j);
                    int i22 = zzmv.$r8$clinit;
                    iZzy = list.size();
                    if (iZzy > 0) {
                        int i23 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i23);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.FOR /* 43 */:
                    iZzy = zzmv.zzv((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i24 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i24);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.SWITCH /* 44 */:
                    iZzy = zzmv.zzd((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i25 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i25);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.f1407DO /* 45 */:
                    iZzy = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i26 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i26);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    iZzy = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i27 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i27);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    iZzy = zzmv.zzq((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i28 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i28);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    iZzy = zzmv.zzs((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i29 = i6 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i29);
                        iZzx3 = iZzx9 + iZzx8;
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    iZzn = zzmv.zzi(i6, (List) unsafe.getObject(obj, j), zzB(i5));
                    i2 += iZzn;
                    break;
                case 50:
                    zzmd.zza(i6, unsafe.getObject(obj, j), zzC(i5));
                    break;
                case 51:
                    if (zzT(obj, i6, i5)) {
                        iZzx = zzki.zzx(i6 << 3);
                        iZzn = iZzx + 8;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(obj, i6, i5)) {
                        iZzx2 = zzki.zzx(i6 << 3);
                        iZzn = iZzx2 + 4;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(obj, i6, i5)) {
                        iZzy = zzki.zzy(zzz(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(obj, i6, i5)) {
                        iZzy = zzki.zzy(zzz(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(obj, i6, i5)) {
                        iZzy = zzki.zzu(zzp(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(obj, i6, i5)) {
                        iZzx = zzki.zzx(i6 << 3);
                        iZzn = iZzx + 8;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(obj, i6, i5)) {
                        iZzx2 = zzki.zzx(i6 << 3);
                        iZzn = iZzx2 + 4;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(obj, i6, i5)) {
                        iZzx4 = zzki.zzx(i6 << 3);
                        iZzn = iZzx4 + 1;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzT(obj, i6, i5)) {
                        Object object2 = unsafe.getObject(obj, j);
                        if (object2 instanceof zzka) {
                            int i30 = i6 << 3;
                            int i31 = zzki.$r8$clinit;
                            int iZzd3 = ((zzka) object2).zzd();
                            iZzx5 = zzki.zzx(iZzd3) + iZzd3;
                            iZzx6 = zzki.zzx(i30);
                            iZzn = iZzx6 + iZzx5;
                            i2 += iZzn;
                            break;
                        } else {
                            iZzy = zzki.zzw((String) object2);
                            iZzx3 = zzki.zzx(i6 << 3);
                            i2 += iZzx3 + iZzy;
                            break;
                        }
                    } else {
                        break;
                    }
                case 60:
                    if (zzT(obj, i6, i5)) {
                        iZzn = zzmv.zzn(i6, unsafe.getObject(obj, j), zzB(i5));
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(obj, i6, i5)) {
                        zzka zzkaVar2 = (zzka) unsafe.getObject(obj, j);
                        int i32 = i6 << 3;
                        int i33 = zzki.$r8$clinit;
                        int iZzd4 = zzkaVar2.zzd();
                        iZzx5 = zzki.zzx(iZzd4) + iZzd4;
                        iZzx6 = zzki.zzx(i32);
                        iZzn = iZzx6 + iZzx5;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(obj, i6, i5)) {
                        iZzy = zzki.zzx(zzp(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(obj, i6, i5)) {
                        iZzy = zzki.zzu(zzp(obj, j));
                        iZzx3 = zzki.zzx(i6 << 3);
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(obj, i6, i5)) {
                        iZzx2 = zzki.zzx(i6 << 3);
                        iZzn = iZzx2 + 4;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(obj, i6, i5)) {
                        iZzx = zzki.zzx(i6 << 3);
                        iZzn = iZzx + 8;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzT(obj, i6, i5)) {
                        int iZzp = zzp(obj, j);
                        iZzx3 = zzki.zzx(i6 << 3);
                        iZzy = zzki.zzx((iZzp >> 31) ^ (iZzp + iZzp));
                        i2 += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzT(obj, i6, i5)) {
                        long jZzz = zzz(obj, j);
                        iZzx7 = zzki.zzx(i6 << 3);
                        iZzy2 = zzki.zzy((jZzz >> 63) ^ (jZzz + jZzz));
                        iZzn = iZzx7 + iZzy2;
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    if (zzT(obj, i6, i5)) {
                        iZzn = zzki.zzt(i6, (zzmi) unsafe.getObject(obj, j), zzB(i5));
                        i2 += iZzn;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zznk zznkVar = this.zzn;
        int iZza = i2 + zznkVar.zza(zznkVar.zzd(obj));
        if (!this.zzh) {
            return iZza;
        }
        this.zzo.zza(obj);
        throw null;
    }

    private static int zzp(Object obj, long j) {
        return ((Integer) zznu.zzf(obj, j)).intValue();
    }

    private final int zzq(Object obj, byte[] bArr, int i, int i2, int i3, long j, zzjn zzjnVar) {
        Unsafe unsafe = zzb;
        Object objZzC = zzC(i3);
        Object object = unsafe.getObject(obj, j);
        if (!((zzmc) object).zze()) {
            zzmc zzmcVarZzb = zzmc.zza().zzb();
            zzmd.zzb(zzmcVarZzb, object);
            unsafe.putObject(obj, j, zzmcVarZzb);
        }
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(objZzC);
        throw null;
    }

    private final int zzr(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzjn zzjnVar) throws zzll {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(obj, j, Double.valueOf(Double.longBitsToDouble(zzjo.zzp(bArr, i))));
                int i9 = i + 8;
                unsafe.putInt(obj, j2, i4);
                return i9;
            case 52:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(obj, j, Float.valueOf(Float.intBitsToFloat(zzjo.zzb(bArr, i))));
                int i10 = i + 4;
                unsafe.putInt(obj, j2, i4);
                return i10;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                int iZzm = zzjo.zzm(bArr, i, zzjnVar);
                unsafe.putObject(obj, j, Long.valueOf(zzjnVar.zzb));
                unsafe.putInt(obj, j2, i4);
                return iZzm;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                int iZzj = zzjo.zzj(bArr, i, zzjnVar);
                unsafe.putObject(obj, j, Integer.valueOf(zzjnVar.zza));
                unsafe.putInt(obj, j2, i4);
                return iZzj;
            case 56:
            case 65:
                if (i5 != 1) {
                    return i;
                }
                unsafe.putObject(obj, j, Long.valueOf(zzjo.zzp(bArr, i)));
                int i11 = i + 8;
                unsafe.putInt(obj, j2, i4);
                return i11;
            case 57:
            case 64:
                if (i5 != 5) {
                    return i;
                }
                unsafe.putObject(obj, j, Integer.valueOf(zzjo.zzb(bArr, i)));
                int i12 = i + 4;
                unsafe.putInt(obj, j2, i4);
                return i12;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                int iZzm2 = zzjo.zzm(bArr, i, zzjnVar);
                unsafe.putObject(obj, j, Boolean.valueOf(zzjnVar.zzb != 0));
                unsafe.putInt(obj, j2, i4);
                return iZzm2;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZzj2 = zzjo.zzj(bArr, i, zzjnVar);
                int i13 = zzjnVar.zza;
                if (i13 == 0) {
                    unsafe.putObject(obj, j, "");
                } else {
                    if ((i6 & 536870912) != 0 && !zznz.zze(bArr, iZzj2, iZzj2 + i13)) {
                        throw zzll.zzc();
                    }
                    unsafe.putObject(obj, j, new String(bArr, iZzj2, i13, zzlj.zzb));
                    iZzj2 += i13;
                }
                unsafe.putInt(obj, j2, i4);
                return iZzj2;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                Object objZzE = zzE(obj, i4, i8);
                int iZzo = zzjo.zzo(objZzE, zzB(i8), bArr, i, i2, zzjnVar);
                zzM(obj, i4, i8, objZzE);
                return iZzo;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzjo.zza(bArr, i, zzjnVar);
                unsafe.putObject(obj, j, zzjnVar.zzc);
                unsafe.putInt(obj, j2, i4);
                return iZza;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZzj3 = zzjo.zzj(bArr, i, zzjnVar);
                int i14 = zzjnVar.zza;
                zzlf zzlfVarZzA = zzA(i8);
                if (zzlfVarZzA != null && !zzlfVarZzA.zza(i14)) {
                    zzd(obj).zzj(i3, Long.valueOf(i14));
                    return iZzj3;
                }
                unsafe.putObject(obj, j, Integer.valueOf(i14));
                unsafe.putInt(obj, j2, i4);
                return iZzj3;
            case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                if (i5 != 0) {
                    return i;
                }
                int iZzj4 = zzjo.zzj(bArr, i, zzjnVar);
                unsafe.putObject(obj, j, Integer.valueOf(zzke.zzb(zzjnVar.zza)));
                unsafe.putInt(obj, j2, i4);
                return iZzj4;
            case TypeReference.INSTANCEOF /* 67 */:
                if (i5 != 0) {
                    return i;
                }
                int iZzm3 = zzjo.zzm(bArr, i, zzjnVar);
                unsafe.putObject(obj, j, Long.valueOf(zzke.zzc(zzjnVar.zzb)));
                unsafe.putInt(obj, j2, i4);
                return iZzm3;
            case TypeReference.NEW /* 68 */:
                if (i5 == 3) {
                    Object objZzE2 = zzE(obj, i4, i8);
                    int iZzn = zzjo.zzn(objZzE2, zzB(i8), bArr, i, i2, (i3 & (-8)) | 4, zzjnVar);
                    zzM(obj, i4, i8, objZzE2);
                    return iZzn;
                }
                break;
        }
        return i;
    }

    private final int zzs(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzjn zzjnVar) throws zzll {
        int iZzl;
        Unsafe unsafe = zzb;
        zzli zzliVarZzd = (zzli) unsafe.getObject(obj, j2);
        if (!zzliVarZzd.zzc()) {
            int size = zzliVarZzd.size();
            zzliVarZzd = zzliVarZzd.zzd(size == 0 ? 10 : size + size);
            unsafe.putObject(obj, j2, zzliVarZzd);
        }
        zzli zzliVar = zzliVarZzd;
        Object objZzA = null;
        switch (i7) {
            case 18:
            case Operator.PROJECTION /* 35 */:
                if (i5 != 2) {
                    if (i5 == 1) {
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzliVar);
                        Double.longBitsToDouble(zzjo.zzp(bArr, i));
                        throw null;
                    }
                    return i;
                }
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzliVar);
                int iZzj = zzjo.zzj(bArr, i, zzjnVar);
                int i8 = zzjnVar.zza + iZzj;
                if (iZzj < i8) {
                    Double.longBitsToDouble(zzjo.zzp(bArr, iZzj));
                    throw null;
                }
                if (iZzj == i8) {
                    return iZzj;
                }
                throw zzll.zzf();
            case 19:
            case Operator.CONVERTABLE_TO /* 36 */:
                if (i5 != 2) {
                    if (i5 == 5) {
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzliVar);
                        Float.intBitsToFloat(zzjo.zzb(bArr, i));
                        throw null;
                    }
                    return i;
                }
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzliVar);
                int iZzj2 = zzjo.zzj(bArr, i, zzjnVar);
                int i9 = zzjnVar.zza + iZzj2;
                if (iZzj2 < i9) {
                    Float.intBitsToFloat(zzjo.zzb(bArr, iZzj2));
                    throw null;
                }
                if (iZzj2 == i9) {
                    return iZzj2;
                }
                throw zzll.zzf();
            case 20:
            case 21:
            case Operator.END_OF_STMT /* 37 */:
            case Operator.FOREACH /* 38 */:
                if (i5 == 2) {
                    zzlx zzlxVar = (zzlx) zzliVar;
                    int iZzj3 = zzjo.zzj(bArr, i, zzjnVar);
                    int i10 = zzjnVar.zza + iZzj3;
                    while (iZzj3 < i10) {
                        iZzj3 = zzjo.zzm(bArr, iZzj3, zzjnVar);
                        zzlxVar.zzg(zzjnVar.zzb);
                    }
                    if (iZzj3 == i10) {
                        return iZzj3;
                    }
                    throw zzll.zzf();
                }
                if (i5 == 0) {
                    zzlx zzlxVar2 = (zzlx) zzliVar;
                    int iZzm = zzjo.zzm(bArr, i, zzjnVar);
                    zzlxVar2.zzg(zzjnVar.zzb);
                    while (iZzm < i2) {
                        int iZzj4 = zzjo.zzj(bArr, iZzm, zzjnVar);
                        if (i3 != zzjnVar.zza) {
                            return iZzm;
                        }
                        iZzm = zzjo.zzm(bArr, iZzj4, zzjnVar);
                        zzlxVar2.zzg(zzjnVar.zzb);
                    }
                    return iZzm;
                }
                return i;
            case 22:
            case 29:
            case Operator.f1408IF /* 39 */:
            case Operator.FOR /* 43 */:
                if (i5 == 2) {
                    return zzjo.zzf(bArr, i, zzliVar, zzjnVar);
                }
                if (i5 == 0) {
                    return zzjo.zzl(i3, bArr, i, i2, zzliVar, zzjnVar);
                }
                return i;
            case 23:
            case 32:
            case Operator.ELSE /* 40 */:
            case 46:
                if (i5 == 2) {
                    zzlx zzlxVar3 = (zzlx) zzliVar;
                    int iZzj5 = zzjo.zzj(bArr, i, zzjnVar);
                    int i11 = zzjnVar.zza + iZzj5;
                    while (iZzj5 < i11) {
                        zzlxVar3.zzg(zzjo.zzp(bArr, iZzj5));
                        iZzj5 += 8;
                    }
                    if (iZzj5 == i11) {
                        return iZzj5;
                    }
                    throw zzll.zzf();
                }
                if (i5 == 1) {
                    zzlx zzlxVar4 = (zzlx) zzliVar;
                    zzlxVar4.zzg(zzjo.zzp(bArr, i));
                    int i12 = i + 8;
                    while (i12 < i2) {
                        int iZzj6 = zzjo.zzj(bArr, i12, zzjnVar);
                        if (i3 != zzjnVar.zza) {
                            return i12;
                        }
                        zzlxVar4.zzg(zzjo.zzp(bArr, iZzj6));
                        i12 = iZzj6 + 8;
                    }
                    return i12;
                }
                return i;
            case 24:
            case 31:
            case Operator.WHILE /* 41 */:
            case Operator.f1407DO /* 45 */:
                if (i5 == 2) {
                    zzlc zzlcVar = (zzlc) zzliVar;
                    int iZzj7 = zzjo.zzj(bArr, i, zzjnVar);
                    int i13 = zzjnVar.zza + iZzj7;
                    while (iZzj7 < i13) {
                        zzlcVar.zzh(zzjo.zzb(bArr, iZzj7));
                        iZzj7 += 4;
                    }
                    if (iZzj7 == i13) {
                        return iZzj7;
                    }
                    throw zzll.zzf();
                }
                if (i5 == 5) {
                    zzlc zzlcVar2 = (zzlc) zzliVar;
                    zzlcVar2.zzh(zzjo.zzb(bArr, i));
                    int i14 = i + 4;
                    while (i14 < i2) {
                        int iZzj8 = zzjo.zzj(bArr, i14, zzjnVar);
                        if (i3 != zzjnVar.zza) {
                            return i14;
                        }
                        zzlcVar2.zzh(zzjo.zzb(bArr, iZzj8));
                        i14 = iZzj8 + 4;
                    }
                    return i14;
                }
                return i;
            case 25:
            case Operator.UNTIL /* 42 */:
                if (i5 != 2) {
                    if (i5 == 0) {
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzliVar);
                        zzjo.zzm(bArr, i, zzjnVar);
                        long j3 = zzjnVar.zzb;
                        throw null;
                    }
                    return i;
                }
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzliVar);
                int iZzj9 = zzjo.zzj(bArr, i, zzjnVar);
                int i15 = zzjnVar.zza + iZzj9;
                if (iZzj9 < i15) {
                    zzjo.zzm(bArr, iZzj9, zzjnVar);
                    throw null;
                }
                if (iZzj9 == i15) {
                    return iZzj9;
                }
                throw zzll.zzf();
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iZzj10 = zzjo.zzj(bArr, i, zzjnVar);
                        int i16 = zzjnVar.zza;
                        if (i16 < 0) {
                            throw zzll.zzd();
                        }
                        if (i16 == 0) {
                            zzliVar.add("");
                        } else {
                            zzliVar.add(new String(bArr, iZzj10, i16, zzlj.zzb));
                            iZzj10 += i16;
                        }
                        while (iZzj10 < i2) {
                            int iZzj11 = zzjo.zzj(bArr, iZzj10, zzjnVar);
                            if (i3 != zzjnVar.zza) {
                                return iZzj10;
                            }
                            iZzj10 = zzjo.zzj(bArr, iZzj11, zzjnVar);
                            int i17 = zzjnVar.zza;
                            if (i17 < 0) {
                                throw zzll.zzd();
                            }
                            if (i17 == 0) {
                                zzliVar.add("");
                            } else {
                                zzliVar.add(new String(bArr, iZzj10, i17, zzlj.zzb));
                                iZzj10 += i17;
                            }
                        }
                        return iZzj10;
                    }
                    int iZzj12 = zzjo.zzj(bArr, i, zzjnVar);
                    int i18 = zzjnVar.zza;
                    if (i18 < 0) {
                        throw zzll.zzd();
                    }
                    if (i18 == 0) {
                        zzliVar.add("");
                    } else {
                        int i19 = iZzj12 + i18;
                        if (!zznz.zze(bArr, iZzj12, i19)) {
                            throw zzll.zzc();
                        }
                        zzliVar.add(new String(bArr, iZzj12, i18, zzlj.zzb));
                        iZzj12 = i19;
                    }
                    while (iZzj12 < i2) {
                        int iZzj13 = zzjo.zzj(bArr, iZzj12, zzjnVar);
                        if (i3 != zzjnVar.zza) {
                            return iZzj12;
                        }
                        iZzj12 = zzjo.zzj(bArr, iZzj13, zzjnVar);
                        int i20 = zzjnVar.zza;
                        if (i20 < 0) {
                            throw zzll.zzd();
                        }
                        if (i20 == 0) {
                            zzliVar.add("");
                        } else {
                            int i21 = iZzj12 + i20;
                            if (!zznz.zze(bArr, iZzj12, i21)) {
                                throw zzll.zzc();
                            }
                            zzliVar.add(new String(bArr, iZzj12, i20, zzlj.zzb));
                            iZzj12 = i21;
                        }
                    }
                    return iZzj12;
                }
                return i;
            case 27:
                if (i5 == 2) {
                    return zzjo.zze(zzB(i6), i3, bArr, i, i2, zzliVar, zzjnVar);
                }
                return i;
            case 28:
                if (i5 == 2) {
                    int iZzj14 = zzjo.zzj(bArr, i, zzjnVar);
                    int i22 = zzjnVar.zza;
                    if (i22 < 0) {
                        throw zzll.zzd();
                    }
                    if (i22 > bArr.length - iZzj14) {
                        throw zzll.zzf();
                    }
                    if (i22 == 0) {
                        zzliVar.add(zzka.zzb);
                    } else {
                        zzliVar.add(zzka.zzl(bArr, iZzj14, i22));
                        iZzj14 += i22;
                    }
                    while (iZzj14 < i2) {
                        int iZzj15 = zzjo.zzj(bArr, iZzj14, zzjnVar);
                        if (i3 != zzjnVar.zza) {
                            return iZzj14;
                        }
                        iZzj14 = zzjo.zzj(bArr, iZzj15, zzjnVar);
                        int i23 = zzjnVar.zza;
                        if (i23 < 0) {
                            throw zzll.zzd();
                        }
                        if (i23 > bArr.length - iZzj14) {
                            throw zzll.zzf();
                        }
                        if (i23 == 0) {
                            zzliVar.add(zzka.zzb);
                        } else {
                            zzliVar.add(zzka.zzl(bArr, iZzj14, i23));
                            iZzj14 += i23;
                        }
                    }
                    return iZzj14;
                }
                return i;
            case 30:
            case Operator.SWITCH /* 44 */:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZzl = zzjo.zzl(i3, bArr, i, i2, zzliVar, zzjnVar);
                    }
                    return i;
                }
                iZzl = zzjo.zzf(bArr, i, zzliVar, zzjnVar);
                zzlf zzlfVarZzA = zzA(i6);
                zznk zznkVar = this.zzn;
                int i24 = zzmv.$r8$clinit;
                if (zzlfVarZzA != null) {
                    if (OnBackPressedDispatcher$$ExternalSyntheticNonNull0.m1m(zzliVar)) {
                        int size2 = zzliVar.size();
                        int i25 = 0;
                        for (int i26 = 0; i26 < size2; i26++) {
                            Integer num = (Integer) zzliVar.get(i26);
                            int iIntValue = num.intValue();
                            if (zzlfVarZzA.zza(iIntValue)) {
                                if (i26 != i25) {
                                    zzliVar.set(i25, num);
                                }
                                i25++;
                            } else {
                                objZzA = zzmv.zzA(obj, i4, iIntValue, objZzA, zznkVar);
                            }
                        }
                        if (i25 != size2) {
                            zzliVar.subList(i25, size2).clear();
                            return iZzl;
                        }
                    } else {
                        Iterator it = zzliVar.iterator();
                        while (it.hasNext()) {
                            int iIntValue2 = ((Integer) it.next()).intValue();
                            if (!zzlfVarZzA.zza(iIntValue2)) {
                                objZzA = zzmv.zzA(obj, i4, iIntValue2, objZzA, zznkVar);
                                it.remove();
                            }
                        }
                    }
                }
                return iZzl;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzlc zzlcVar3 = (zzlc) zzliVar;
                    int iZzj16 = zzjo.zzj(bArr, i, zzjnVar);
                    int i27 = zzjnVar.zza + iZzj16;
                    while (iZzj16 < i27) {
                        iZzj16 = zzjo.zzj(bArr, iZzj16, zzjnVar);
                        zzlcVar3.zzh(zzke.zzb(zzjnVar.zza));
                    }
                    if (iZzj16 == i27) {
                        return iZzj16;
                    }
                    throw zzll.zzf();
                }
                if (i5 == 0) {
                    zzlc zzlcVar4 = (zzlc) zzliVar;
                    int iZzj17 = zzjo.zzj(bArr, i, zzjnVar);
                    zzlcVar4.zzh(zzke.zzb(zzjnVar.zza));
                    while (iZzj17 < i2) {
                        int iZzj18 = zzjo.zzj(bArr, iZzj17, zzjnVar);
                        if (i3 != zzjnVar.zza) {
                            return iZzj17;
                        }
                        iZzj17 = zzjo.zzj(bArr, iZzj18, zzjnVar);
                        zzlcVar4.zzh(zzke.zzb(zzjnVar.zza));
                    }
                    return iZzj17;
                }
                return i;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzlx zzlxVar5 = (zzlx) zzliVar;
                    int iZzj19 = zzjo.zzj(bArr, i, zzjnVar);
                    int i28 = zzjnVar.zza + iZzj19;
                    while (iZzj19 < i28) {
                        iZzj19 = zzjo.zzm(bArr, iZzj19, zzjnVar);
                        zzlxVar5.zzg(zzke.zzc(zzjnVar.zzb));
                    }
                    if (iZzj19 == i28) {
                        return iZzj19;
                    }
                    throw zzll.zzf();
                }
                if (i5 == 0) {
                    zzlx zzlxVar6 = (zzlx) zzliVar;
                    int iZzm2 = zzjo.zzm(bArr, i, zzjnVar);
                    zzlxVar6.zzg(zzke.zzc(zzjnVar.zzb));
                    while (iZzm2 < i2) {
                        int iZzj20 = zzjo.zzj(bArr, iZzm2, zzjnVar);
                        if (i3 != zzjnVar.zza) {
                            return iZzm2;
                        }
                        iZzm2 = zzjo.zzm(bArr, iZzj20, zzjnVar);
                        zzlxVar6.zzg(zzke.zzc(zzjnVar.zzb));
                    }
                    return iZzm2;
                }
                return i;
            default:
                if (i5 == 3) {
                    zzmt zzmtVarZzB = zzB(i6);
                    int i29 = (i3 & (-8)) | 4;
                    int iZzc = zzjo.zzc(zzmtVarZzB, bArr, i, i2, i29, zzjnVar);
                    zzmt zzmtVar = zzmtVarZzB;
                    zzjn zzjnVar2 = zzjnVar;
                    zzliVar.add(zzjnVar2.zzc);
                    while (iZzc < i2) {
                        int iZzj21 = zzjo.zzj(bArr, iZzc, zzjnVar2);
                        if (i3 != zzjnVar2.zza) {
                            return iZzc;
                        }
                        zzmt zzmtVar2 = zzmtVar;
                        zzjn zzjnVar3 = zzjnVar2;
                        iZzc = zzjo.zzc(zzmtVar2, bArr, iZzj21, i2, i29, zzjnVar3);
                        zzliVar.add(zzjnVar3.zzc);
                        zzmtVar = zzmtVar2;
                        zzjnVar2 = zzjnVar3;
                    }
                    return iZzc;
                }
                return i;
        }
    }

    private final int zzt(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, 0);
    }

    private final int zzu(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, i2);
    }

    private final int zzv(int i) {
        return this.zzc[i + 2];
    }

    private final int zzw(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private static int zzx(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzy(int i) {
        return this.zzc[i + 1];
    }

    private static long zzz(Object obj, long j) {
        return ((Long) zznu.zzf(obj, j)).longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzmt
    public final int zza(Object obj) {
        int iZzx;
        int iZzx2;
        int iZzy;
        int iZzx3;
        int iZzx4;
        int iZzx5;
        int iZzx6;
        int iZzn;
        int iZzx7;
        int iZzy2;
        int iZzx8;
        int iZzx9;
        if (!this.zzi) {
            return zzo(obj);
        }
        Unsafe unsafe = zzb;
        int i = 0;
        for (int i2 = 0; i2 < this.zzc.length; i2 += 3) {
            int iZzy3 = zzy(i2);
            int iZzx10 = zzx(iZzy3);
            int i3 = this.zzc[i2];
            int i4 = iZzy3 & 1048575;
            if (iZzx10 >= zzkt.zzJ.zza() && iZzx10 <= zzkt.zzW.zza()) {
                int i5 = this.zzc[i2 + 2];
            }
            long j = i4;
            switch (iZzx10) {
                case 0:
                    if (zzP(obj, i2)) {
                        iZzx = zzki.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzP(obj, i2)) {
                        iZzx2 = zzki.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzP(obj, i2)) {
                        iZzy = zzki.zzy(zznu.zzd(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzP(obj, i2)) {
                        iZzy = zzki.zzy(zznu.zzd(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzP(obj, i2)) {
                        iZzy = zzki.zzu(zznu.zzc(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzP(obj, i2)) {
                        iZzx = zzki.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzP(obj, i2)) {
                        iZzx2 = zzki.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzP(obj, i2)) {
                        iZzx4 = zzki.zzx(i3 << 3);
                        iZzn = iZzx4 + 1;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzP(obj, i2)) {
                        Object objZzf = zznu.zzf(obj, j);
                        if (objZzf instanceof zzka) {
                            int i6 = i3 << 3;
                            int i7 = zzki.$r8$clinit;
                            int iZzd = ((zzka) objZzf).zzd();
                            iZzx5 = zzki.zzx(iZzd) + iZzd;
                            iZzx6 = zzki.zzx(i6);
                            iZzn = iZzx6 + iZzx5;
                            i += iZzn;
                            break;
                        } else {
                            iZzy = zzki.zzw((String) objZzf);
                            iZzx3 = zzki.zzx(i3 << 3);
                            i += iZzx3 + iZzy;
                            break;
                        }
                    } else {
                        break;
                    }
                case 9:
                    if (zzP(obj, i2)) {
                        iZzn = zzmv.zzn(i3, zznu.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (zzP(obj, i2)) {
                        zzka zzkaVar = (zzka) zznu.zzf(obj, j);
                        int i8 = i3 << 3;
                        int i9 = zzki.$r8$clinit;
                        int iZzd2 = zzkaVar.zzd();
                        iZzx5 = zzki.zzx(iZzd2) + iZzd2;
                        iZzx6 = zzki.zzx(i8);
                        iZzn = iZzx6 + iZzx5;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzP(obj, i2)) {
                        iZzy = zzki.zzx(zznu.zzc(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzP(obj, i2)) {
                        iZzy = zzki.zzu(zznu.zzc(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzP(obj, i2)) {
                        iZzx2 = zzki.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzP(obj, i2)) {
                        iZzx = zzki.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzP(obj, i2)) {
                        int iZzc = zznu.zzc(obj, j);
                        iZzx3 = zzki.zzx(i3 << 3);
                        iZzy = zzki.zzx((iZzc >> 31) ^ (iZzc + iZzc));
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzP(obj, i2)) {
                        long jZzd = zznu.zzd(obj, j);
                        iZzx7 = zzki.zzx(i3 << 3);
                        iZzy2 = zzki.zzy((jZzd >> 63) ^ (jZzd + jZzd));
                        iZzn = iZzx7 + iZzy2;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (zzP(obj, i2)) {
                        iZzn = zzki.zzt(i3, (zzmi) zznu.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    iZzn = zzmv.zzg(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 19:
                    iZzn = zzmv.zze(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 20:
                    iZzn = zzmv.zzl(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 21:
                    iZzn = zzmv.zzw(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 22:
                    iZzn = zzmv.zzj(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 23:
                    iZzn = zzmv.zzg(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 24:
                    iZzn = zzmv.zze(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 25:
                    iZzn = zzmv.zza(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 26:
                    iZzn = zzmv.zzt(i3, (List) zznu.zzf(obj, j));
                    i += iZzn;
                    break;
                case 27:
                    iZzn = zzmv.zzo(i3, (List) zznu.zzf(obj, j), zzB(i2));
                    i += iZzn;
                    break;
                case 28:
                    iZzn = zzmv.zzb(i3, (List) zznu.zzf(obj, j));
                    i += iZzn;
                    break;
                case 29:
                    iZzn = zzmv.zzu(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 30:
                    iZzn = zzmv.zzc(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 31:
                    iZzn = zzmv.zze(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 32:
                    iZzn = zzmv.zzg(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 33:
                    iZzn = zzmv.zzp(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case 34:
                    iZzn = zzmv.zzr(i3, (List) zznu.zzf(obj, j), false);
                    i += iZzn;
                    break;
                case Operator.PROJECTION /* 35 */:
                    iZzy = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i10 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i10);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.CONVERTABLE_TO /* 36 */:
                    iZzy = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i11 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i11);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.END_OF_STMT /* 37 */:
                    iZzy = zzmv.zzm((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i12 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i12);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.FOREACH /* 38 */:
                    iZzy = zzmv.zzx((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i13 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i13);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.f1408IF /* 39 */:
                    iZzy = zzmv.zzk((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i14 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i14);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.ELSE /* 40 */:
                    iZzy = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i15 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i15);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.WHILE /* 41 */:
                    iZzy = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i16 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i16);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.UNTIL /* 42 */:
                    List list = (List) unsafe.getObject(obj, j);
                    int i17 = zzmv.$r8$clinit;
                    iZzy = list.size();
                    if (iZzy > 0) {
                        int i18 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i18);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.FOR /* 43 */:
                    iZzy = zzmv.zzv((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i19 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i19);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.SWITCH /* 44 */:
                    iZzy = zzmv.zzd((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i20 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i20);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case Operator.f1407DO /* 45 */:
                    iZzy = zzmv.zzf((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i21 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i21);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    iZzy = zzmv.zzh((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i22 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i22);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    iZzy = zzmv.zzq((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i23 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i23);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    iZzy = zzmv.zzs((List) unsafe.getObject(obj, j));
                    if (iZzy > 0) {
                        int i24 = i3 << 3;
                        iZzx8 = zzki.zzx(iZzy);
                        iZzx9 = zzki.zzx(i24);
                        iZzx3 = iZzx9 + iZzx8;
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    iZzn = zzmv.zzi(i3, (List) zznu.zzf(obj, j), zzB(i2));
                    i += iZzn;
                    break;
                case 50:
                    zzmd.zza(i3, zznu.zzf(obj, j), zzC(i2));
                    break;
                case 51:
                    if (zzT(obj, i3, i2)) {
                        iZzx = zzki.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(obj, i3, i2)) {
                        iZzx2 = zzki.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzki.zzy(zzz(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzki.zzy(zzz(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzki.zzu(zzp(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(obj, i3, i2)) {
                        iZzx = zzki.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(obj, i3, i2)) {
                        iZzx2 = zzki.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(obj, i3, i2)) {
                        iZzx4 = zzki.zzx(i3 << 3);
                        iZzn = iZzx4 + 1;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzT(obj, i3, i2)) {
                        Object objZzf2 = zznu.zzf(obj, j);
                        if (objZzf2 instanceof zzka) {
                            int i25 = i3 << 3;
                            int i26 = zzki.$r8$clinit;
                            int iZzd3 = ((zzka) objZzf2).zzd();
                            iZzx5 = zzki.zzx(iZzd3) + iZzd3;
                            iZzx6 = zzki.zzx(i25);
                            iZzn = iZzx6 + iZzx5;
                            i += iZzn;
                            break;
                        } else {
                            iZzy = zzki.zzw((String) objZzf2);
                            iZzx3 = zzki.zzx(i3 << 3);
                            i += iZzx3 + iZzy;
                            break;
                        }
                    } else {
                        break;
                    }
                case 60:
                    if (zzT(obj, i3, i2)) {
                        iZzn = zzmv.zzn(i3, zznu.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(obj, i3, i2)) {
                        zzka zzkaVar2 = (zzka) zznu.zzf(obj, j);
                        int i27 = i3 << 3;
                        int i28 = zzki.$r8$clinit;
                        int iZzd4 = zzkaVar2.zzd();
                        iZzx5 = zzki.zzx(iZzd4) + iZzd4;
                        iZzx6 = zzki.zzx(i27);
                        iZzn = iZzx6 + iZzx5;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzki.zzx(zzp(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(obj, i3, i2)) {
                        iZzy = zzki.zzu(zzp(obj, j));
                        iZzx3 = zzki.zzx(i3 << 3);
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(obj, i3, i2)) {
                        iZzx2 = zzki.zzx(i3 << 3);
                        iZzn = iZzx2 + 4;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(obj, i3, i2)) {
                        iZzx = zzki.zzx(i3 << 3);
                        iZzn = iZzx + 8;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzT(obj, i3, i2)) {
                        int iZzp = zzp(obj, j);
                        iZzx3 = zzki.zzx(i3 << 3);
                        iZzy = zzki.zzx((iZzp >> 31) ^ (iZzp + iZzp));
                        i += iZzx3 + iZzy;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzT(obj, i3, i2)) {
                        long jZzz = zzz(obj, j);
                        iZzx7 = zzki.zzx(i3 << 3);
                        iZzy2 = zzki.zzy((jZzz >> 63) ^ (jZzz + jZzz));
                        iZzn = iZzx7 + iZzy2;
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    if (zzT(obj, i3, i2)) {
                        iZzn = zzki.zzt(i3, (zzmi) zznu.zzf(obj, j), zzB(i2));
                        i += iZzn;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zznk zznkVar = this.zzn;
        return i + zznkVar.zza(zznkVar.zzd(obj));
    }

    @Override // com.google.android.gms.internal.measurement.zzmt
    public final int zzb(Object obj) {
        int i;
        long jDoubleToLongBits;
        int i2;
        int iFloatToIntBits;
        int length = this.zzc.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iZzy = zzy(i4);
            int i5 = this.zzc[i4];
            long j = 1048575 & iZzy;
            int iHashCode = 37;
            switch (zzx(iZzy)) {
                case 0:
                    i = i3 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zznu.zza(obj, j));
                    byte[] bArr = zzlj.zzd;
                    i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 1:
                    i2 = i3 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zznu.zzb(obj, j));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 2:
                    i = i3 * 53;
                    jDoubleToLongBits = zznu.zzd(obj, j);
                    byte[] bArr2 = zzlj.zzd;
                    i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 3:
                    i = i3 * 53;
                    jDoubleToLongBits = zznu.zzd(obj, j);
                    byte[] bArr3 = zzlj.zzd;
                    i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 4:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzc(obj, j);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 5:
                    i = i3 * 53;
                    jDoubleToLongBits = zznu.zzd(obj, j);
                    byte[] bArr4 = zzlj.zzd;
                    i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 6:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzc(obj, j);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 7:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzlj.zza(zznu.zzw(obj, j));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 8:
                    i2 = i3 * 53;
                    iFloatToIntBits = ((String) zznu.zzf(obj, j)).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 9:
                    Object objZzf = zznu.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzf(obj, j).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 11:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzc(obj, j);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 12:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzc(obj, j);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 13:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzc(obj, j);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 14:
                    i = i3 * 53;
                    jDoubleToLongBits = zznu.zzd(obj, j);
                    byte[] bArr5 = zzlj.zzd;
                    i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 15:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzc(obj, j);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 16:
                    i = i3 * 53;
                    jDoubleToLongBits = zznu.zzd(obj, j);
                    byte[] bArr6 = zzlj.zzd;
                    i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 17:
                    Object objZzf2 = zznu.zzf(obj, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case Operator.PROJECTION /* 35 */:
                case Operator.CONVERTABLE_TO /* 36 */:
                case Operator.END_OF_STMT /* 37 */:
                case Operator.FOREACH /* 38 */:
                case Operator.f1408IF /* 39 */:
                case Operator.ELSE /* 40 */:
                case Operator.WHILE /* 41 */:
                case Operator.UNTIL /* 42 */:
                case Operator.FOR /* 43 */:
                case Operator.SWITCH /* 44 */:
                case Operator.f1407DO /* 45 */:
                case 46:
                case 47:
                case 48:
                case 49:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzf(obj, j).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 50:
                    i2 = i3 * 53;
                    iFloatToIntBits = zznu.zzf(obj, j).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 51:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzm(obj, j));
                        byte[] bArr7 = zzlj.zzd;
                        i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzn(obj, j));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr8 = zzlj.zzd;
                        i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr9 = zzlj.zzd;
                        i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr10 = zzlj.zzd;
                        i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzlj.zza(zzU(obj, j));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = ((String) zznu.zzf(obj, j)).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zznu.zzf(obj, j).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zznu.zzf(obj, j).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr11 = zzlj.zzd;
                        i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzp(obj, j);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzz(obj, j);
                        byte[] bArr12 = zzlj.zzd;
                        i3 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zznu.zzf(obj, j).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i3 * 53) + this.zzn.zzd(obj).hashCode();
        if (!this.zzh) {
            return iHashCode2;
        }
        this.zzo.zza(obj);
        throw null;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:23:0x0091. Please report as an issue. */
    final int zzc(Object obj, byte[] bArr, int i, int i2, int i3, zzjn zzjnVar) throws zzll {
        int i4;
        Unsafe unsafe;
        zzml zzmlVar;
        Object obj2;
        zzjn zzjnVar2;
        int i5;
        int i6;
        int i7;
        int iZzi;
        zzkn zzknVar;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        zzjn zzjnVar3;
        int i13;
        Unsafe unsafe2;
        int i14;
        int i15;
        int i16;
        byte[] bArr2;
        Unsafe unsafe3;
        byte[] bArr3;
        Object obj3;
        zzjn zzjnVar4;
        int i17;
        byte[] bArr4;
        int iZzm;
        byte[] bArr5;
        Unsafe unsafe4;
        int i18;
        zzjn zzjnVar5;
        zzml zzmlVar2 = this;
        Object obj4 = obj;
        byte[] bArr6 = bArr;
        int i19 = i2;
        zzjn zzjnVar6 = zzjnVar;
        zzG(obj4);
        Unsafe unsafe5 = zzb;
        int i20 = -1;
        int iZzj = i;
        int i21 = -1;
        int i22 = 0;
        int i23 = 1048575;
        int i24 = 0;
        int i25 = 0;
        while (true) {
            if (iZzj < i19) {
                int iZzk = iZzj + 1;
                int i26 = bArr6[iZzj];
                if (i26 < 0) {
                    iZzk = zzjo.zzk(i26, bArr6, iZzk, zzjnVar6);
                    i26 = zzjnVar6.zza;
                }
                int i27 = iZzk;
                i25 = i26;
                int i28 = i25 >>> 3;
                int iZzu = i28 > i21 ? zzmlVar2.zzu(i28, i22 / 3) : zzmlVar2.zzt(i28);
                if (iZzu == i20) {
                    zzjnVar2 = zzjnVar6;
                    i5 = i25;
                    i6 = i27;
                    i4 = i23;
                    unsafe = unsafe5;
                    i21 = i28;
                    i22 = 0;
                    zzmlVar = zzmlVar2;
                    obj2 = obj4;
                } else {
                    int i29 = i25 & 7;
                    int[] iArr = zzmlVar2.zzc;
                    int i30 = iArr[iZzu + 1];
                    int iZzx = zzx(i30);
                    long j = i30 & 1048575;
                    if (iZzx <= 17) {
                        int i31 = iArr[iZzu + 2];
                        int i32 = 1 << (i31 >>> 20);
                        int i33 = i31 & 1048575;
                        if (i33 != i23) {
                            i11 = i30;
                            if (i23 != 1048575) {
                                unsafe5.putInt(obj4, i23, i24);
                            }
                            i12 = i33;
                            i24 = unsafe5.getInt(obj4, i33);
                        } else {
                            i11 = i30;
                            i12 = i23;
                        }
                        switch (iZzx) {
                            case 0:
                                zzjnVar3 = zzjnVar6;
                                i13 = iZzu;
                                unsafe2 = unsafe5;
                                i14 = i24;
                                i15 = i25;
                                i16 = i27;
                                bArr2 = bArr;
                                if (i29 == 1) {
                                    zznu.zzo(obj4, j, Double.longBitsToDouble(zzjo.zzp(bArr2, i16)));
                                    iZzj = i16 + 8;
                                    i24 = i14 | i32;
                                    i25 = i15;
                                    i19 = i2;
                                    bArr6 = bArr2;
                                    i22 = i13;
                                    i21 = i28;
                                    unsafe5 = unsafe2;
                                    zzjnVar6 = zzjnVar3;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 1:
                                zzjnVar3 = zzjnVar6;
                                i13 = iZzu;
                                unsafe2 = unsafe5;
                                i14 = i24;
                                i15 = i25;
                                i16 = i27;
                                bArr2 = bArr;
                                if (i29 == 5) {
                                    zznu.zzp(obj4, j, Float.intBitsToFloat(zzjo.zzb(bArr2, i16)));
                                    iZzj = i16 + 4;
                                    i24 = i14 | i32;
                                    i25 = i15;
                                    i19 = i2;
                                    bArr6 = bArr2;
                                    i22 = i13;
                                    i21 = i28;
                                    unsafe5 = unsafe2;
                                    zzjnVar6 = zzjnVar3;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 2:
                            case 3:
                                zzjnVar3 = zzjnVar6;
                                i13 = iZzu;
                                unsafe3 = unsafe5;
                                i16 = i27;
                                bArr2 = bArr;
                                if (i29 == 0) {
                                    int iZzm2 = zzjo.zzm(bArr2, i16, zzjnVar3);
                                    Object obj5 = obj4;
                                    unsafe3.putLong(obj5, j, zzjnVar3.zzb);
                                    unsafe2 = unsafe3;
                                    obj4 = obj5;
                                    i24 |= i32;
                                    i19 = i2;
                                    iZzj = iZzm2;
                                    bArr6 = bArr2;
                                    i22 = i13;
                                    i21 = i28;
                                    unsafe5 = unsafe2;
                                    zzjnVar6 = zzjnVar3;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    unsafe2 = unsafe3;
                                    i14 = i24;
                                    i15 = i25;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 4:
                            case 11:
                                zzjnVar3 = zzjnVar6;
                                i13 = iZzu;
                                unsafe3 = unsafe5;
                                i16 = i27;
                                bArr3 = bArr;
                                if (i29 == 0) {
                                    iZzj = zzjo.zzj(bArr3, i16, zzjnVar3);
                                    unsafe3.putInt(obj4, j, zzjnVar3.zza);
                                    i24 |= i32;
                                    byte[] bArr7 = bArr3;
                                    unsafe5 = unsafe3;
                                    bArr6 = bArr7;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar3;
                                    i23 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i20 = -1;
                                    break;
                                } else {
                                    unsafe2 = unsafe3;
                                    i14 = i24;
                                    i15 = i25;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 5:
                            case 14:
                                Object obj6 = obj4;
                                i13 = iZzu;
                                if (i29 == 1) {
                                    Unsafe unsafe6 = unsafe5;
                                    bArr3 = bArr;
                                    zzjnVar3 = zzjnVar;
                                    unsafe6.putLong(obj6, j, zzjo.zzp(bArr, i27));
                                    unsafe3 = unsafe6;
                                    obj4 = obj6;
                                    iZzj = i27 + 8;
                                    i24 |= i32;
                                    byte[] bArr72 = bArr3;
                                    unsafe5 = unsafe3;
                                    bArr6 = bArr72;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar3;
                                    i23 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i20 = -1;
                                    break;
                                } else {
                                    obj4 = obj6;
                                    zzjnVar3 = zzjnVar;
                                    unsafe2 = unsafe5;
                                    i14 = i24;
                                    i15 = i25;
                                    i16 = i27;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 6:
                            case 13:
                                Object obj7 = obj4;
                                i13 = iZzu;
                                if (i29 == 5) {
                                    unsafe5.putInt(obj7, j, zzjo.zzb(bArr, i27));
                                    iZzj = i27 + 4;
                                    i24 |= i32;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar;
                                    bArr6 = bArr;
                                    obj4 = obj7;
                                    i23 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i20 = -1;
                                    break;
                                } else {
                                    i16 = i27;
                                    unsafe2 = unsafe5;
                                    i14 = i24;
                                    i15 = i25;
                                    obj4 = obj7;
                                    zzjnVar3 = zzjnVar;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 7:
                                obj3 = obj4;
                                zzjnVar4 = zzjnVar6;
                                i13 = iZzu;
                                i17 = i27;
                                bArr4 = bArr;
                                if (i29 == 0) {
                                    iZzm = zzjo.zzm(bArr4, i17, zzjnVar4);
                                    zznu.zzm(obj3, j, zzjnVar4.zzb != 0);
                                    i24 |= i32;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar4;
                                    i22 = i13;
                                    i21 = i28;
                                    iZzj = iZzm;
                                    bArr6 = bArr4;
                                    obj4 = obj3;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    i16 = i17;
                                    unsafe2 = unsafe5;
                                    i14 = i24;
                                    i15 = i25;
                                    obj4 = obj3;
                                    zzjnVar3 = zzjnVar4;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 8:
                                obj3 = obj4;
                                zzjnVar4 = zzjnVar6;
                                i13 = iZzu;
                                i17 = i27;
                                bArr4 = bArr;
                                if (i29 == 2) {
                                    iZzm = (i11 & 536870912) == 0 ? zzjo.zzg(bArr4, i17, zzjnVar4) : zzjo.zzh(bArr4, i17, zzjnVar4);
                                    unsafe5.putObject(obj3, j, zzjnVar4.zzc);
                                    i24 |= i32;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar4;
                                    i22 = i13;
                                    i21 = i28;
                                    iZzj = iZzm;
                                    bArr6 = bArr4;
                                    obj4 = obj3;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    i16 = i17;
                                    unsafe2 = unsafe5;
                                    i14 = i24;
                                    i15 = i25;
                                    obj4 = obj3;
                                    zzjnVar3 = zzjnVar4;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 9:
                                i13 = iZzu;
                                Unsafe unsafe7 = unsafe5;
                                zzjn zzjnVar7 = zzjnVar6;
                                if (i29 == 2) {
                                    Object obj8 = obj4;
                                    Object objZzD = zzmlVar2.zzD(obj8, i13);
                                    obj3 = obj8;
                                    unsafe5 = unsafe7;
                                    iZzm = zzjo.zzo(objZzD, zzmlVar2.zzB(i13), bArr, i27, i2, zzjnVar7);
                                    bArr4 = bArr;
                                    zzjnVar4 = zzjnVar7;
                                    zzmlVar2.zzL(obj3, i13, objZzD);
                                    i24 |= i32;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar4;
                                    i22 = i13;
                                    i21 = i28;
                                    iZzj = iZzm;
                                    bArr6 = bArr4;
                                    obj4 = obj3;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    obj3 = obj4;
                                    bArr4 = bArr;
                                    unsafe5 = unsafe7;
                                    i17 = i27;
                                    zzjnVar4 = zzjnVar7;
                                    i16 = i17;
                                    unsafe2 = unsafe5;
                                    i14 = i24;
                                    i15 = i25;
                                    obj4 = obj3;
                                    zzjnVar3 = zzjnVar4;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 10:
                                bArr5 = bArr;
                                i13 = iZzu;
                                unsafe4 = unsafe5;
                                i18 = i27;
                                zzjnVar5 = zzjnVar6;
                                if (i29 == 2) {
                                    iZzj = zzjo.zza(bArr5, i18, zzjnVar5);
                                    unsafe4.putObject(obj4, j, zzjnVar5.zzc);
                                    i24 |= i32;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar5;
                                    i22 = i13;
                                    i21 = i28;
                                    unsafe5 = unsafe4;
                                    bArr6 = bArr5;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    zzjnVar3 = zzjnVar5;
                                    unsafe2 = unsafe4;
                                    i16 = i18;
                                    i14 = i24;
                                    i15 = i25;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 12:
                                bArr5 = bArr;
                                i13 = iZzu;
                                unsafe4 = unsafe5;
                                i18 = i27;
                                zzjnVar5 = zzjnVar6;
                                if (i29 != 0) {
                                    zzjnVar3 = zzjnVar5;
                                    unsafe2 = unsafe4;
                                    i16 = i18;
                                    i14 = i24;
                                    i15 = i25;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                } else {
                                    iZzj = zzjo.zzj(bArr5, i18, zzjnVar5);
                                    int i34 = zzjnVar5.zza;
                                    zzlf zzlfVarZzA = zzmlVar2.zzA(i13);
                                    if (zzlfVarZzA == null || zzlfVarZzA.zza(i34)) {
                                        unsafe4.putInt(obj4, j, i34);
                                        i24 |= i32;
                                        i19 = i2;
                                        zzjnVar6 = zzjnVar5;
                                        i22 = i13;
                                        i21 = i28;
                                        unsafe5 = unsafe4;
                                        bArr6 = bArr5;
                                        i23 = i12;
                                        i20 = -1;
                                        break;
                                    } else {
                                        zzd(obj4).zzj(i25, Long.valueOf(i34));
                                        i19 = i2;
                                        zzjnVar6 = zzjnVar5;
                                        i22 = i13;
                                        i21 = i28;
                                        unsafe5 = unsafe4;
                                        bArr6 = bArr5;
                                        i23 = i12;
                                        i20 = -1;
                                    }
                                }
                                break;
                            case 15:
                                bArr5 = bArr;
                                i13 = iZzu;
                                unsafe4 = unsafe5;
                                i18 = i27;
                                zzjnVar5 = zzjnVar6;
                                if (i29 == 0) {
                                    iZzj = zzjo.zzj(bArr5, i18, zzjnVar5);
                                    unsafe4.putInt(obj4, j, zzke.zzb(zzjnVar5.zza));
                                    i24 |= i32;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar5;
                                    i22 = i13;
                                    i21 = i28;
                                    unsafe5 = unsafe4;
                                    bArr6 = bArr5;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    zzjnVar3 = zzjnVar5;
                                    unsafe2 = unsafe4;
                                    i16 = i18;
                                    i14 = i24;
                                    i15 = i25;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            case 16:
                                bArr5 = bArr;
                                zzjn zzjnVar8 = zzjnVar6;
                                i13 = iZzu;
                                i18 = i27;
                                if (i29 == 0) {
                                    int iZzm3 = zzjo.zzm(bArr5, i18, zzjnVar8);
                                    long jZzc = zzke.zzc(zzjnVar8.zzb);
                                    Object obj9 = obj4;
                                    Unsafe unsafe8 = unsafe5;
                                    zzjnVar5 = zzjnVar8;
                                    unsafe8.putLong(obj9, j, jZzc);
                                    unsafe4 = unsafe8;
                                    obj4 = obj9;
                                    i24 |= i32;
                                    i19 = i2;
                                    iZzj = iZzm3;
                                    zzjnVar6 = zzjnVar5;
                                    i22 = i13;
                                    i21 = i28;
                                    unsafe5 = unsafe4;
                                    bArr6 = bArr5;
                                    i23 = i12;
                                    i20 = -1;
                                    break;
                                } else {
                                    Unsafe unsafe9 = unsafe5;
                                    zzjnVar5 = zzjnVar8;
                                    unsafe4 = unsafe9;
                                    zzjnVar3 = zzjnVar5;
                                    unsafe2 = unsafe4;
                                    i16 = i18;
                                    i14 = i24;
                                    i15 = i25;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                            default:
                                if (i29 == 3) {
                                    Object objZzD2 = zzmlVar2.zzD(obj4, iZzu);
                                    zzjn zzjnVar9 = zzjnVar6;
                                    i13 = iZzu;
                                    iZzj = zzjo.zzn(objZzD2, zzmlVar2.zzB(iZzu), bArr, i27, i2, (i28 << 3) | 4, zzjnVar9);
                                    zzmlVar2.zzL(obj4, i13, objZzD2);
                                    i24 |= i32;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar9;
                                    bArr6 = bArr;
                                    i23 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i20 = -1;
                                    break;
                                } else {
                                    i13 = iZzu;
                                    zzjnVar3 = zzjnVar6;
                                    unsafe2 = unsafe5;
                                    i14 = i24;
                                    i15 = i25;
                                    i16 = i27;
                                    obj2 = obj4;
                                    i6 = i16;
                                    unsafe = unsafe2;
                                    i24 = i14;
                                    zzjnVar2 = zzjnVar3;
                                    i4 = i12;
                                    i22 = i13;
                                    i21 = i28;
                                    i5 = i15;
                                    zzmlVar = zzmlVar2;
                                    break;
                                }
                        }
                    } else {
                        Unsafe unsafe10 = unsafe5;
                        int i35 = i24;
                        int i36 = iZzu;
                        if (iZzx != 27) {
                            i8 = i27;
                            if (iZzx <= 49) {
                                unsafe = unsafe10;
                                i9 = i35;
                                i4 = i23;
                                int iZzs = zzmlVar2.zzs(obj, bArr, i8, i2, i25, i28, i29, i36, i30, iZzx, j, zzjnVar);
                                i25 = i25;
                                i36 = i36;
                                if (iZzs != i8) {
                                    zzmlVar2 = this;
                                    obj4 = obj;
                                    bArr6 = bArr;
                                    i19 = i2;
                                    zzjnVar6 = zzjnVar;
                                    iZzj = iZzs;
                                    i21 = i28;
                                } else {
                                    zzmlVar = this;
                                    i6 = iZzs;
                                    i21 = i28;
                                    i22 = i36;
                                    i5 = i25;
                                    i24 = i9;
                                    obj2 = obj;
                                    zzjnVar2 = zzjnVar;
                                }
                            } else {
                                i25 = i25;
                                unsafe = unsafe10;
                                i9 = i35;
                                i10 = i28;
                                i4 = i23;
                                if (iZzx != 50) {
                                    zzjnVar2 = zzjnVar;
                                    i21 = i10;
                                    i5 = i25;
                                    int iZzr = zzr(obj, bArr, i8, i2, i5, i21, i29, i30, iZzx, j, i36, zzjnVar2);
                                    zzmlVar = this;
                                    obj2 = obj;
                                    i36 = i36;
                                    if (iZzr != i8) {
                                        i25 = i5;
                                        zzjnVar6 = zzjnVar2;
                                        bArr6 = bArr;
                                        i19 = i2;
                                        iZzj = iZzr;
                                        zzmlVar2 = zzmlVar;
                                        obj4 = obj2;
                                    } else {
                                        i6 = iZzr;
                                        i22 = i36;
                                        i24 = i9;
                                    }
                                } else if (i29 == 2) {
                                    int iZzq = zzq(obj, bArr, i8, i2, i36, j, zzjnVar);
                                    if (iZzq != i8) {
                                        zzmlVar2 = this;
                                        obj4 = obj;
                                        bArr6 = bArr;
                                        i19 = i2;
                                        zzjnVar6 = zzjnVar;
                                        iZzj = iZzq;
                                        i21 = i10;
                                    } else {
                                        zzmlVar = this;
                                        obj2 = obj;
                                        i6 = iZzq;
                                        i21 = i10;
                                        i22 = i36;
                                        i5 = i25;
                                        i24 = i9;
                                        zzjnVar2 = zzjnVar;
                                    }
                                }
                            }
                            i22 = i36;
                            i23 = i4;
                            i24 = i9;
                            unsafe5 = unsafe;
                            i20 = -1;
                        } else if (i29 == 2) {
                            zzli zzliVarZzd = (zzli) unsafe10.getObject(obj4, j);
                            if (!zzliVarZzd.zzc()) {
                                int size = zzliVarZzd.size();
                                zzliVarZzd = zzliVarZzd.zzd(size == 0 ? 10 : size + size);
                                unsafe10.putObject(obj4, j, zzliVarZzd);
                            }
                            unsafe5 = unsafe10;
                            int iZze = zzjo.zze(zzmlVar2.zzB(i36), i25, bArr, i27, i2, zzliVarZzd, zzjnVar);
                            bArr6 = bArr;
                            i19 = i2;
                            iZzj = iZze;
                            i25 = i25;
                            i24 = i35;
                            i22 = i36;
                            i21 = i28;
                            i20 = -1;
                            obj4 = obj;
                            zzjnVar6 = zzjnVar;
                        } else {
                            i25 = i25;
                            i8 = i27;
                            i4 = i23;
                            unsafe = unsafe10;
                            i9 = i35;
                            i10 = i28;
                        }
                        zzmlVar = this;
                        obj2 = obj;
                        i6 = i8;
                        i21 = i10;
                        i22 = i36;
                        i5 = i25;
                        i24 = i9;
                        zzjnVar2 = zzjnVar;
                    }
                }
                if (i5 != i3 || i3 == 0) {
                    if (!zzmlVar.zzh || (zzknVar = zzjnVar2.zzd) == zzkn.zza) {
                        i7 = i5;
                        iZzi = zzjo.zzi(i7, bArr, i6, i2, zzd(obj2), zzjnVar);
                        i19 = i2;
                    } else {
                        zzknVar.zzb(zzmlVar.zzg, i21);
                        i7 = i5;
                        iZzi = zzjo.zzi(i7, bArr, i6, i2, zzd(obj2), zzjnVar2);
                        i19 = i2;
                    }
                    iZzj = iZzi;
                    bArr6 = bArr;
                    i25 = i7;
                    zzmlVar2 = zzmlVar;
                    obj4 = obj2;
                    i23 = i4;
                    unsafe5 = unsafe;
                    i20 = -1;
                    zzjnVar6 = zzjnVar;
                } else {
                    i19 = i2;
                    iZzj = i6;
                    i25 = i5;
                }
            } else {
                i4 = i23;
                unsafe = unsafe5;
                zzmlVar = zzmlVar2;
                obj2 = obj4;
            }
        }
        int i37 = i4;
        if (i37 != 1048575) {
            unsafe.putInt(obj2, i37, i24);
        }
        for (int i38 = zzmlVar.zzk; i38 < zzmlVar.zzl; i38++) {
            int i39 = zzmlVar.zzj[i38];
            int i40 = zzmlVar.zzc[i39];
            Object objZzf = zznu.zzf(obj2, zzmlVar.zzy(i39) & 1048575);
            if (objZzf != null && zzmlVar.zzA(i39) != null) {
                WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzmlVar.zzC(i39));
                throw null;
            }
        }
        if (i3 == 0) {
            if (iZzj != i19) {
                throw zzll.zze();
            }
        } else if (iZzj > i19 || i25 != i3) {
            throw zzll.zze();
        }
        return iZzj;
    }

    @Override // com.google.android.gms.internal.measurement.zzmt
    public final Object zze() {
        return ((zzlb) this.zzg).zzbD();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006d  */
    @Override // com.google.android.gms.internal.measurement.zzmt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzf(java.lang.Object r8) {
        /*
            r7 = this;
            boolean r0 = zzS(r8)
            if (r0 != 0) goto L8
            goto L91
        L8:
            boolean r0 = r8 instanceof com.google.android.gms.internal.measurement.zzlb
            r1 = 0
            if (r0 == 0) goto L1b
            r0 = r8
            com.google.android.gms.internal.measurement.zzlb r0 = (com.google.android.gms.internal.measurement.zzlb) r0
            r2 = 2147483647(0x7fffffff, float:NaN)
            r0.zzbP(r2)
            r0.zzb = r1
            r0.zzbN()
        L1b:
            int[] r0 = r7.zzc
            int r0 = r0.length
        L1e:
            if (r1 >= r0) goto L83
            int r2 = r7.zzy(r1)
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r3 = r3 & r2
            int r2 = zzx(r2)
            long r3 = (long) r3
            r5 = 9
            if (r2 == r5) goto L6d
            r5 = 60
            if (r2 == r5) goto L55
            r5 = 68
            if (r2 == r5) goto L55
            switch(r2) {
                case 17: goto L6d;
                case 18: goto L4f;
                case 19: goto L4f;
                case 20: goto L4f;
                case 21: goto L4f;
                case 22: goto L4f;
                case 23: goto L4f;
                case 24: goto L4f;
                case 25: goto L4f;
                case 26: goto L4f;
                case 27: goto L4f;
                case 28: goto L4f;
                case 29: goto L4f;
                case 30: goto L4f;
                case 31: goto L4f;
                case 32: goto L4f;
                case 33: goto L4f;
                case 34: goto L4f;
                case 35: goto L4f;
                case 36: goto L4f;
                case 37: goto L4f;
                case 38: goto L4f;
                case 39: goto L4f;
                case 40: goto L4f;
                case 41: goto L4f;
                case 42: goto L4f;
                case 43: goto L4f;
                case 44: goto L4f;
                case 45: goto L4f;
                case 46: goto L4f;
                case 47: goto L4f;
                case 48: goto L4f;
                case 49: goto L4f;
                case 50: goto L3d;
                default: goto L3c;
            }
        L3c:
            goto L80
        L3d:
            sun.misc.Unsafe r2 = com.google.android.gms.internal.measurement.zzml.zzb
            java.lang.Object r5 = r2.getObject(r8, r3)
            if (r5 == 0) goto L80
            r6 = r5
            com.google.android.gms.internal.measurement.zzmc r6 = (com.google.android.gms.internal.measurement.zzmc) r6
            r6.zzc()
            r2.putObject(r8, r3, r5)
            goto L80
        L4f:
            com.google.android.gms.internal.measurement.zzlw r2 = r7.zzm
            r2.zza(r8, r3)
            goto L80
        L55:
            int[] r2 = r7.zzc
            r2 = r2[r1]
            boolean r2 = r7.zzT(r8, r2, r1)
            if (r2 == 0) goto L80
            com.google.android.gms.internal.measurement.zzmt r2 = r7.zzB(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.measurement.zzml.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zzf(r3)
            goto L80
        L6d:
            boolean r2 = r7.zzP(r8, r1)
            if (r2 == 0) goto L80
            com.google.android.gms.internal.measurement.zzmt r2 = r7.zzB(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.measurement.zzml.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zzf(r3)
        L80:
            int r1 = r1 + 3
            goto L1e
        L83:
            com.google.android.gms.internal.measurement.zznk r0 = r7.zzn
            r0.zzg(r8)
            boolean r0 = r7.zzh
            if (r0 == 0) goto L91
            com.google.android.gms.internal.measurement.zzko r0 = r7.zzo
            r0.zzb(r8)
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzml.zzf(java.lang.Object):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzmt
    public final void zzg(Object obj, Object obj2) {
        zzG(obj);
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzy = zzy(i);
            int i2 = this.zzc[i];
            long j = 1048575 & iZzy;
            switch (zzx(iZzy)) {
                case 0:
                    if (zzP(obj2, i)) {
                        zznu.zzo(obj, j, zznu.zza(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzP(obj2, i)) {
                        zznu.zzp(obj, j, zznu.zzb(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzP(obj2, i)) {
                        zznu.zzr(obj, j, zznu.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzP(obj2, i)) {
                        zznu.zzr(obj, j, zznu.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzP(obj2, i)) {
                        zznu.zzq(obj, j, zznu.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzP(obj2, i)) {
                        zznu.zzr(obj, j, zznu.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzP(obj2, i)) {
                        zznu.zzq(obj, j, zznu.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzP(obj2, i)) {
                        zznu.zzm(obj, j, zznu.zzw(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzP(obj2, i)) {
                        zznu.zzs(obj, j, zznu.zzf(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzH(obj, obj2, i);
                    break;
                case 10:
                    if (zzP(obj2, i)) {
                        zznu.zzs(obj, j, zznu.zzf(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzP(obj2, i)) {
                        zznu.zzq(obj, j, zznu.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzP(obj2, i)) {
                        zznu.zzq(obj, j, zznu.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzP(obj2, i)) {
                        zznu.zzq(obj, j, zznu.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzP(obj2, i)) {
                        zznu.zzr(obj, j, zznu.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzP(obj2, i)) {
                        zznu.zzq(obj, j, zznu.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzP(obj2, i)) {
                        zznu.zzr(obj, j, zznu.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzH(obj, obj2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case Operator.PROJECTION /* 35 */:
                case Operator.CONVERTABLE_TO /* 36 */:
                case Operator.END_OF_STMT /* 37 */:
                case Operator.FOREACH /* 38 */:
                case Operator.f1408IF /* 39 */:
                case Operator.ELSE /* 40 */:
                case Operator.WHILE /* 41 */:
                case Operator.UNTIL /* 42 */:
                case Operator.FOR /* 43 */:
                case Operator.SWITCH /* 44 */:
                case Operator.f1407DO /* 45 */:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzm.zzb(obj, obj2, j);
                    break;
                case 50:
                    int i3 = zzmv.$r8$clinit;
                    zznu.zzs(obj, j, zzmd.zzb(zznu.zzf(obj, j), zznu.zzf(obj2, j)));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzT(obj2, i2, i)) {
                        zznu.zzs(obj, j, zznu.zzf(obj2, j));
                        zzK(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzI(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzT(obj2, i2, i)) {
                        zznu.zzs(obj, j, zznu.zzf(obj2, j));
                        zzK(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    zzI(obj, obj2, i);
                    break;
            }
        }
        zzmv.zzB(this.zzn, obj, obj2);
        if (this.zzh) {
            this.zzo.zza(obj2);
            throw null;
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:28:0x008f. Please report as an issue. */
    @Override // com.google.android.gms.internal.measurement.zzmt
    public final void zzh(Object obj, byte[] bArr, int i, int i2, zzjn zzjnVar) throws zzll {
        Object obj2;
        Unsafe unsafe;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        Object obj3;
        Unsafe unsafe2;
        int i9;
        Unsafe unsafe3;
        int i10;
        Object obj4;
        int i11;
        Unsafe unsafe4;
        int i12;
        int i13;
        int i14;
        int i15;
        zzml zzmlVar = this;
        Object obj5 = obj;
        byte[] bArr2 = bArr;
        int i16 = i2;
        zzjn zzjnVar2 = zzjnVar;
        if (!zzmlVar.zzi) {
            zzc(obj5, bArr, i, i16, 0, zzjnVar);
            return;
        }
        zzG(obj5);
        Unsafe unsafe5 = zzb;
        int i17 = -1;
        int iZzm = i;
        int i18 = -1;
        int i19 = 0;
        int i20 = 1048575;
        int i21 = 0;
        while (iZzm < i16) {
            int iZzk = iZzm + 1;
            int i22 = bArr2[iZzm];
            if (i22 < 0) {
                iZzk = zzjo.zzk(i22, bArr2, iZzk, zzjnVar2);
                i22 = zzjnVar2.zza;
            }
            int i23 = i22 >>> 3;
            int iZzu = i23 > i18 ? zzmlVar.zzu(i23, i19 / 3) : zzmlVar.zzt(i23);
            if (iZzu == i17) {
                obj2 = obj5;
                unsafe = unsafe5;
                i3 = i22;
                i4 = iZzk;
                i5 = i23;
                i6 = 0;
            } else {
                int i24 = i22 & 7;
                int[] iArr = zzmlVar.zzc;
                int i25 = iArr[iZzu + 1];
                int iZzx = zzx(i25);
                int i26 = i22;
                int i27 = iZzu;
                long j = i25 & 1048575;
                if (iZzx <= 17) {
                    int i28 = iArr[i27 + 2];
                    int i29 = 1 << (i28 >>> 20);
                    int i30 = i28 & 1048575;
                    if (i30 != i20) {
                        i7 = iZzk;
                        if (i20 != 1048575) {
                            unsafe5.putInt(obj5, i20, i21);
                        }
                        if (i30 != 1048575) {
                            i21 = unsafe5.getInt(obj5, i30);
                        }
                        i20 = i30;
                    } else {
                        i7 = iZzk;
                    }
                    switch (iZzx) {
                        case 0:
                            int i31 = i7;
                            i5 = i23;
                            i8 = i31;
                            obj3 = obj5;
                            unsafe2 = unsafe5;
                            i9 = i27;
                            if (i24 != 1) {
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                zznu.zzo(obj3, j, Double.longBitsToDouble(zzjo.zzp(bArr2, i8)));
                                iZzm = i8 + 8;
                                i21 |= i29;
                                i16 = i2;
                                zzjnVar2 = zzjnVar;
                                unsafe5 = unsafe2;
                                i19 = i9;
                                obj5 = obj3;
                                i18 = i5;
                                i17 = -1;
                                break;
                            }
                        case 1:
                            int i32 = i7;
                            i5 = i23;
                            i8 = i32;
                            obj3 = obj5;
                            unsafe2 = unsafe5;
                            i9 = i27;
                            if (i24 != 5) {
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                zznu.zzp(obj3, j, Float.intBitsToFloat(zzjo.zzb(bArr2, i8)));
                                iZzm = i8 + 4;
                                i21 |= i29;
                                i16 = i2;
                                zzjnVar2 = zzjnVar;
                                unsafe5 = unsafe2;
                                i19 = i9;
                                obj5 = obj3;
                                i18 = i5;
                                i17 = -1;
                                break;
                            }
                        case 2:
                        case 3:
                            Unsafe unsafe6 = unsafe5;
                            i8 = i7;
                            i9 = i27;
                            if (i24 != 0) {
                                obj3 = obj5;
                                unsafe2 = unsafe6;
                                i5 = i23;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                int iZzm2 = zzjo.zzm(bArr2, i8, zzjnVar2);
                                Object obj6 = obj5;
                                unsafe6.putLong(obj6, j, zzjnVar2.zzb);
                                i21 |= i29;
                                i16 = i2;
                                unsafe5 = unsafe6;
                                i19 = i9;
                                i18 = i23;
                                iZzm = iZzm2;
                                obj5 = obj6;
                                i17 = -1;
                                break;
                            }
                        case 4:
                        case 11:
                            unsafe3 = unsafe5;
                            i8 = i7;
                            i9 = i27;
                            if (i24 != 0) {
                                Unsafe unsafe7 = unsafe3;
                                obj3 = obj5;
                                unsafe2 = unsafe7;
                                i5 = i23;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                int iZzj = zzjo.zzj(bArr2, i8, zzjnVar2);
                                unsafe3.putInt(obj5, j, zzjnVar2.zza);
                                i21 |= i29;
                                i16 = i2;
                                iZzm = iZzj;
                                i19 = i9;
                                i18 = i23;
                                unsafe5 = unsafe3;
                                i17 = -1;
                                break;
                            }
                        case 5:
                        case 14:
                            int i33 = i7;
                            i9 = i27;
                            Object obj7 = obj5;
                            unsafe3 = unsafe5;
                            if (i24 != 1) {
                                obj5 = obj7;
                                i8 = i33;
                                Unsafe unsafe72 = unsafe3;
                                obj3 = obj5;
                                unsafe2 = unsafe72;
                                i5 = i23;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                unsafe3.putLong(obj7, j, zzjo.zzp(bArr2, i33));
                                obj5 = obj7;
                                iZzm = i33 + 8;
                                i21 |= i29;
                                i16 = i2;
                                i19 = i9;
                                i18 = i23;
                                unsafe5 = unsafe3;
                                i17 = -1;
                                break;
                            }
                        case 6:
                        case 13:
                            i10 = i7;
                            i9 = i27;
                            obj4 = obj5;
                            unsafe3 = unsafe5;
                            if (i24 != 5) {
                                i5 = i23;
                                unsafe2 = unsafe3;
                                obj3 = obj4;
                                i8 = i10;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                unsafe3.putInt(obj4, j, zzjo.zzb(bArr2, i10));
                                iZzm = i10 + 4;
                                i21 |= i29;
                                i16 = i2;
                                i19 = i9;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe3;
                                i17 = -1;
                                break;
                            }
                        case 7:
                            i10 = i7;
                            i9 = i27;
                            obj4 = obj5;
                            unsafe3 = unsafe5;
                            if (i24 != 0) {
                                i5 = i23;
                                unsafe2 = unsafe3;
                                obj3 = obj4;
                                i8 = i10;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                iZzm = zzjo.zzm(bArr2, i10, zzjnVar2);
                                zznu.zzm(obj4, j, zzjnVar2.zzb != 0);
                                i21 |= i29;
                                i16 = i2;
                                i19 = i9;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe3;
                                i17 = -1;
                                break;
                            }
                        case 8:
                            i10 = i7;
                            i9 = i27;
                            obj4 = obj5;
                            unsafe3 = unsafe5;
                            if (i24 != 2) {
                                i5 = i23;
                                unsafe2 = unsafe3;
                                obj3 = obj4;
                                i8 = i10;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                iZzm = (i25 & 536870912) == 0 ? zzjo.zzg(bArr2, i10, zzjnVar2) : zzjo.zzh(bArr2, i10, zzjnVar2);
                                unsafe3.putObject(obj4, j, zzjnVar2.zzc);
                                i21 |= i29;
                                i16 = i2;
                                i19 = i9;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe3;
                                i17 = -1;
                                break;
                            }
                        case 9:
                            obj4 = obj5;
                            unsafe3 = unsafe5;
                            i11 = i7;
                            i9 = i27;
                            if (i24 != 2) {
                                i5 = i23;
                                unsafe2 = unsafe3;
                                obj3 = obj4;
                                i8 = i11;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                Object objZzD = zzmlVar.zzD(obj4, i9);
                                int iZzo = zzjo.zzo(objZzD, zzmlVar.zzB(i9), bArr2, i11, i2, zzjnVar2);
                                zzmlVar.zzL(obj4, i9, objZzD);
                                i21 |= i29;
                                i16 = i2;
                                iZzm = iZzo;
                                i19 = i9;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe3;
                                i17 = -1;
                                break;
                            }
                        case 10:
                            i11 = i7;
                            obj4 = obj5;
                            unsafe4 = unsafe5;
                            if (i24 != 2) {
                                i5 = i23;
                                unsafe2 = unsafe4;
                                i9 = i27;
                                obj3 = obj4;
                                i8 = i11;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                iZzm = zzjo.zza(bArr2, i11, zzjnVar2);
                                unsafe4.putObject(obj4, j, zzjnVar2.zzc);
                                i21 |= i29;
                                i16 = i2;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe4;
                                i19 = i27;
                                i17 = -1;
                                break;
                            }
                        case 12:
                            i11 = i7;
                            obj4 = obj5;
                            unsafe4 = unsafe5;
                            if (i24 != 0) {
                                i5 = i23;
                                unsafe2 = unsafe4;
                                i9 = i27;
                                obj3 = obj4;
                                i8 = i11;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                iZzm = zzjo.zzj(bArr2, i11, zzjnVar2);
                                unsafe4.putInt(obj4, j, zzjnVar2.zza);
                                i21 |= i29;
                                i16 = i2;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe4;
                                i19 = i27;
                                i17 = -1;
                                break;
                            }
                        case 15:
                            i11 = i7;
                            obj4 = obj5;
                            unsafe4 = unsafe5;
                            if (i24 != 0) {
                                i5 = i23;
                                unsafe2 = unsafe4;
                                i9 = i27;
                                obj3 = obj4;
                                i8 = i11;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                iZzm = zzjo.zzj(bArr2, i11, zzjnVar2);
                                unsafe4.putInt(obj4, j, zzke.zzb(zzjnVar2.zza));
                                i21 |= i29;
                                i16 = i2;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe4;
                                i19 = i27;
                                i17 = -1;
                                break;
                            }
                        case 16:
                            if (i24 != 0) {
                                obj3 = obj5;
                                unsafe2 = unsafe5;
                                i8 = i7;
                                i9 = i27;
                                i5 = i23;
                                i3 = i26;
                                unsafe = unsafe2;
                                i6 = i9;
                                i4 = i8;
                                obj2 = obj3;
                                break;
                            } else {
                                int iZzm3 = zzjo.zzm(bArr2, i7, zzjnVar2);
                                Unsafe unsafe8 = unsafe5;
                                Object obj8 = obj5;
                                unsafe8.putLong(obj8, j, zzke.zzc(zzjnVar2.zzb));
                                unsafe4 = unsafe8;
                                obj4 = obj8;
                                i21 |= i29;
                                i16 = i2;
                                iZzm = iZzm3;
                                i18 = i23;
                                obj5 = obj4;
                                unsafe5 = unsafe4;
                                i19 = i27;
                                i17 = -1;
                                break;
                            }
                        default:
                            obj3 = obj5;
                            unsafe2 = unsafe5;
                            i8 = i7;
                            i9 = i27;
                            i5 = i23;
                            i3 = i26;
                            unsafe = unsafe2;
                            i6 = i9;
                            i4 = i8;
                            obj2 = obj3;
                            break;
                    }
                } else {
                    i5 = i23;
                    Object obj9 = obj5;
                    Unsafe unsafe9 = unsafe5;
                    int i34 = iZzk;
                    if (iZzx != 27) {
                        i6 = i27;
                        i15 = i34;
                        if (iZzx <= 49) {
                            long j2 = i25;
                            i14 = i21;
                            unsafe = unsafe9;
                            i13 = i20;
                            int iZzs = zzmlVar.zzs(obj9, bArr, i15, i2, i26, i5, i24, i6, j2, iZzx, j, zzjnVar);
                            i12 = i26;
                            if (iZzs != i15) {
                                zzmlVar = this;
                                obj5 = obj;
                                i16 = i2;
                                zzjnVar2 = zzjnVar;
                                iZzm = iZzs;
                                i20 = i13;
                                i18 = i5;
                                i19 = i6;
                                i21 = i14;
                                unsafe5 = unsafe;
                                i17 = -1;
                                bArr2 = bArr;
                            } else {
                                obj2 = obj;
                                i4 = iZzs;
                                i3 = i12;
                                i20 = i13;
                                i21 = i14;
                            }
                        } else {
                            i13 = i20;
                            i14 = i21;
                            unsafe = unsafe9;
                            i12 = i26;
                            if (iZzx != 50) {
                                i3 = i12;
                                int iZzr = zzr(obj, bArr, i15, i2, i3, i5, i24, i25, iZzx, j, i6, zzjnVar);
                                obj2 = obj;
                                if (iZzr != i15) {
                                    zzmlVar = this;
                                    i16 = i2;
                                    zzjnVar2 = zzjnVar;
                                    iZzm = iZzr;
                                    obj5 = obj2;
                                    i20 = i13;
                                    i18 = i5;
                                    i19 = i6;
                                    i21 = i14;
                                    unsafe5 = unsafe;
                                    i17 = -1;
                                    bArr2 = bArr;
                                } else {
                                    i4 = iZzr;
                                    i20 = i13;
                                    i21 = i14;
                                }
                            } else if (i24 == 2) {
                                int iZzq = zzq(obj, bArr, i15, i2, i6, j, zzjnVar);
                                if (iZzq != i15) {
                                    zzmlVar = this;
                                    obj5 = obj;
                                    bArr2 = bArr;
                                    i16 = i2;
                                    zzjnVar2 = zzjnVar;
                                    iZzm = iZzq;
                                    i20 = i13;
                                    i18 = i5;
                                    i19 = i6;
                                    i21 = i14;
                                    unsafe5 = unsafe;
                                    i17 = -1;
                                } else {
                                    obj2 = obj;
                                    i4 = iZzq;
                                    i3 = i12;
                                    i20 = i13;
                                    i21 = i14;
                                }
                            }
                        }
                    } else if (i24 == 2) {
                        zzli zzliVarZzd = (zzli) unsafe9.getObject(obj9, j);
                        if (!zzliVarZzd.zzc()) {
                            int size = zzliVarZzd.size();
                            zzliVarZzd = zzliVarZzd.zzd(size == 0 ? 10 : size + size);
                            unsafe9.putObject(obj9, j, zzliVarZzd);
                        }
                        zzmt zzmtVarZzB = zzmlVar.zzB(i27);
                        i27 = i27;
                        byte[] bArr3 = bArr2;
                        bArr2 = bArr;
                        i16 = i2;
                        zzjnVar2 = zzjnVar;
                        iZzm = zzjo.zze(zzmtVarZzB, i26, bArr3, i34, i2, zzliVarZzd, zzjnVar);
                        unsafe5 = unsafe9;
                        obj5 = obj9;
                        i18 = i5;
                        i19 = i27;
                        i17 = -1;
                    } else {
                        i12 = i26;
                        unsafe = unsafe9;
                        i6 = i27;
                        i13 = i20;
                        i14 = i21;
                        i15 = i34;
                    }
                    obj2 = obj;
                    i4 = i15;
                    i3 = i12;
                    i20 = i13;
                    i21 = i14;
                }
            }
            int iZzi = zzjo.zzi(i3, bArr, i4, i2, zzd(obj2), zzjnVar);
            bArr2 = bArr;
            zzjnVar2 = zzjnVar;
            i16 = i2;
            obj5 = obj2;
            i18 = i5;
            i19 = i6;
            unsafe5 = unsafe;
            i17 = -1;
            iZzm = iZzi;
            zzmlVar = this;
        }
        Object obj10 = obj5;
        Unsafe unsafe10 = unsafe5;
        int i35 = i16;
        int i36 = i20;
        int i37 = i21;
        if (i36 != 1048575) {
            unsafe10.putInt(obj10, i36, i37);
        }
        if (iZzm != i35) {
            throw zzll.zze();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzmt
    public final boolean zzj(Object obj, Object obj2) {
        boolean zZzV;
        int length = this.zzc.length;
        for (int i = 0; i < length; i += 3) {
            int iZzy = zzy(i);
            long j = iZzy & 1048575;
            switch (zzx(iZzy)) {
                case 0:
                    if (!zzO(obj, obj2, i) || Double.doubleToLongBits(zznu.zza(obj, j)) != Double.doubleToLongBits(zznu.zza(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 1:
                    if (!zzO(obj, obj2, i) || Float.floatToIntBits(zznu.zzb(obj, j)) != Float.floatToIntBits(zznu.zzb(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 2:
                    if (!zzO(obj, obj2, i) || zznu.zzd(obj, j) != zznu.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 3:
                    if (!zzO(obj, obj2, i) || zznu.zzd(obj, j) != zznu.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 4:
                    if (!zzO(obj, obj2, i) || zznu.zzc(obj, j) != zznu.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 5:
                    if (!zzO(obj, obj2, i) || zznu.zzd(obj, j) != zznu.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 6:
                    if (!zzO(obj, obj2, i) || zznu.zzc(obj, j) != zznu.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 7:
                    if (!zzO(obj, obj2, i) || zznu.zzw(obj, j) != zznu.zzw(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 8:
                    if (!zzO(obj, obj2, i) || !zzmv.zzV(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 9:
                    if (!zzO(obj, obj2, i) || !zzmv.zzV(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 10:
                    if (!zzO(obj, obj2, i) || !zzmv.zzV(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 11:
                    if (!zzO(obj, obj2, i) || zznu.zzc(obj, j) != zznu.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 12:
                    if (!zzO(obj, obj2, i) || zznu.zzc(obj, j) != zznu.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 13:
                    if (!zzO(obj, obj2, i) || zznu.zzc(obj, j) != zznu.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 14:
                    if (!zzO(obj, obj2, i) || zznu.zzd(obj, j) != zznu.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 15:
                    if (!zzO(obj, obj2, i) || zznu.zzc(obj, j) != zznu.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 16:
                    if (!zzO(obj, obj2, i) || zznu.zzd(obj, j) != zznu.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 17:
                    if (!zzO(obj, obj2, i) || !zzmv.zzV(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case Operator.PROJECTION /* 35 */:
                case Operator.CONVERTABLE_TO /* 36 */:
                case Operator.END_OF_STMT /* 37 */:
                case Operator.FOREACH /* 38 */:
                case Operator.f1408IF /* 39 */:
                case Operator.ELSE /* 40 */:
                case Operator.WHILE /* 41 */:
                case Operator.UNTIL /* 42 */:
                case Operator.FOR /* 43 */:
                case Operator.SWITCH /* 44 */:
                case Operator.f1407DO /* 45 */:
                case 46:
                case 47:
                case 48:
                case 49:
                    zZzV = zzmv.zzV(zznu.zzf(obj, j), zznu.zzf(obj2, j));
                    break;
                case 50:
                    zZzV = zzmv.zzV(zznu.zzf(obj, j), zznu.zzf(obj2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                case TypeReference.INSTANCEOF /* 67 */:
                case TypeReference.NEW /* 68 */:
                    long jZzv = zzv(i) & 1048575;
                    if (zznu.zzc(obj, jZzv) != zznu.zzc(obj2, jZzv) || !zzmv.zzV(zznu.zzf(obj, j), zznu.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                default:
            }
            if (!zZzV) {
                return false;
            }
        }
        if (!this.zzn.zzd(obj).equals(this.zzn.zzd(obj2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzo.zza(obj);
        this.zzo.zza(obj2);
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0094  */
    @Override // com.google.android.gms.internal.measurement.zzmt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzk(java.lang.Object r16) {
        /*
            Method dump skipped, instructions count: 223
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzml.zzk(java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.measurement.zzmt
    public final void zzi(Object obj, zzoc zzocVar) throws zzkg {
        int i;
        int i2 = 1048575;
        if (this.zzi) {
            if (this.zzh) {
                this.zzo.zza(obj);
                throw null;
            }
            int length = this.zzc.length;
            for (int i3 = 0; i3 < length; i3 += 3) {
                int iZzy = zzy(i3);
                int i4 = this.zzc[i3];
                switch (zzx(iZzy)) {
                    case 0:
                        if (zzP(obj, i3)) {
                            zzocVar.zzf(i4, zznu.zza(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (zzP(obj, i3)) {
                            zzocVar.zzo(i4, zznu.zzb(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (zzP(obj, i3)) {
                            zzocVar.zzt(i4, zznu.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (zzP(obj, i3)) {
                            zzocVar.zzJ(i4, zznu.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (zzP(obj, i3)) {
                            zzocVar.zzr(i4, zznu.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (zzP(obj, i3)) {
                            zzocVar.zzm(i4, zznu.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (zzP(obj, i3)) {
                            zzocVar.zzk(i4, zznu.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (zzP(obj, i3)) {
                            zzocVar.zzb(i4, zznu.zzw(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (zzP(obj, i3)) {
                            zzV(i4, zznu.zzf(obj, iZzy & 1048575), zzocVar);
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (zzP(obj, i3)) {
                            zzocVar.zzv(i4, zznu.zzf(obj, iZzy & 1048575), zzB(i3));
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (zzP(obj, i3)) {
                            zzocVar.zzd(i4, (zzka) zznu.zzf(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (zzP(obj, i3)) {
                            zzocVar.zzH(i4, zznu.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (zzP(obj, i3)) {
                            zzocVar.zzi(i4, zznu.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (zzP(obj, i3)) {
                            zzocVar.zzw(i4, zznu.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (zzP(obj, i3)) {
                            zzocVar.zzy(i4, zznu.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (zzP(obj, i3)) {
                            zzocVar.zzA(i4, zznu.zzc(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (zzP(obj, i3)) {
                            zzocVar.zzC(i4, zznu.zzd(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (zzP(obj, i3)) {
                            zzocVar.zzq(i4, zznu.zzf(obj, iZzy & 1048575), zzB(i3));
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        zzmv.zzF(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 19:
                        zzmv.zzJ(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 20:
                        zzmv.zzM(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 21:
                        zzmv.zzU(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 22:
                        zzmv.zzL(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 23:
                        zzmv.zzI(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 24:
                        zzmv.zzH(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 25:
                        zzmv.zzD(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 26:
                        zzmv.zzS(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar);
                        break;
                    case 27:
                        zzmv.zzN(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, zzB(i3));
                        break;
                    case 28:
                        zzmv.zzE(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar);
                        break;
                    case 29:
                        zzmv.zzT(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 30:
                        zzmv.zzG(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 31:
                        zzmv.zzO(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 32:
                        zzmv.zzP(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 33:
                        zzmv.zzQ(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case 34:
                        zzmv.zzR(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, false);
                        break;
                    case Operator.PROJECTION /* 35 */:
                        zzmv.zzF(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.CONVERTABLE_TO /* 36 */:
                        zzmv.zzJ(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.END_OF_STMT /* 37 */:
                        zzmv.zzM(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.FOREACH /* 38 */:
                        zzmv.zzU(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.f1408IF /* 39 */:
                        zzmv.zzL(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.ELSE /* 40 */:
                        zzmv.zzI(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.WHILE /* 41 */:
                        zzmv.zzH(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.UNTIL /* 42 */:
                        zzmv.zzD(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.FOR /* 43 */:
                        zzmv.zzT(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.SWITCH /* 44 */:
                        zzmv.zzG(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case Operator.f1407DO /* 45 */:
                        zzmv.zzO(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case 46:
                        zzmv.zzP(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case 47:
                        zzmv.zzQ(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case 48:
                        zzmv.zzR(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, true);
                        break;
                    case 49:
                        zzmv.zzK(i4, (List) zznu.zzf(obj, iZzy & 1048575), zzocVar, zzB(i3));
                        break;
                    case 50:
                        zzN(zzocVar, i4, zznu.zzf(obj, iZzy & 1048575), i3);
                        break;
                    case 51:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzf(i4, zzm(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzo(i4, zzn(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzt(i4, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzJ(i4, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzr(i4, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzm(i4, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzk(i4, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzb(i4, zzU(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (zzT(obj, i4, i3)) {
                            zzV(i4, zznu.zzf(obj, iZzy & 1048575), zzocVar);
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzv(i4, zznu.zzf(obj, iZzy & 1048575), zzB(i3));
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzd(i4, (zzka) zznu.zzf(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzH(i4, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzi(i4, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzw(i4, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzy(i4, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzA(i4, zzp(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case TypeReference.INSTANCEOF /* 67 */:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzC(i4, zzz(obj, iZzy & 1048575));
                            break;
                        } else {
                            break;
                        }
                    case TypeReference.NEW /* 68 */:
                        if (zzT(obj, i4, i3)) {
                            zzocVar.zzq(i4, zznu.zzf(obj, iZzy & 1048575), zzB(i3));
                            break;
                        } else {
                            break;
                        }
                }
            }
            zznk zznkVar = this.zzn;
            zznkVar.zzi(zznkVar.zzd(obj), zzocVar);
            return;
        }
        if (this.zzh) {
            this.zzo.zza(obj);
            throw null;
        }
        int length2 = this.zzc.length;
        Unsafe unsafe = zzb;
        int i5 = 0;
        int i6 = 1048575;
        int i7 = 0;
        while (i5 < length2) {
            int iZzy2 = zzy(i5);
            int[] iArr = this.zzc;
            int i8 = iArr[i5];
            int iZzx = zzx(iZzy2);
            if (iZzx <= 17) {
                int i9 = iArr[i5 + 2];
                int i10 = i9 & i2;
                if (i10 != i6) {
                    i7 = unsafe.getInt(obj, i10);
                    i6 = i10;
                }
                i = 1 << (i9 >>> 20);
            } else {
                i = 0;
            }
            long j = iZzy2 & i2;
            switch (iZzx) {
                case 0:
                    if ((i7 & i) != 0) {
                        zzocVar.zzf(i8, zznu.zza(obj, j));
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 1:
                    if ((i7 & i) != 0) {
                        zzocVar.zzo(i8, zznu.zzb(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 2:
                    if ((i7 & i) != 0) {
                        zzocVar.zzt(i8, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 3:
                    if ((i7 & i) != 0) {
                        zzocVar.zzJ(i8, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 4:
                    if ((i7 & i) != 0) {
                        zzocVar.zzr(i8, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 5:
                    if ((i7 & i) != 0) {
                        zzocVar.zzm(i8, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 6:
                    if ((i7 & i) != 0) {
                        zzocVar.zzk(i8, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 7:
                    if ((i7 & i) != 0) {
                        zzocVar.zzb(i8, zznu.zzw(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 8:
                    if ((i7 & i) != 0) {
                        zzV(i8, unsafe.getObject(obj, j), zzocVar);
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 9:
                    if ((i7 & i) != 0) {
                        zzocVar.zzv(i8, unsafe.getObject(obj, j), zzB(i5));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 10:
                    if ((i7 & i) != 0) {
                        zzocVar.zzd(i8, (zzka) unsafe.getObject(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 11:
                    if ((i7 & i) != 0) {
                        zzocVar.zzH(i8, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 12:
                    if ((i7 & i) != 0) {
                        zzocVar.zzi(i8, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 13:
                    if ((i7 & i) != 0) {
                        zzocVar.zzw(i8, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 14:
                    if ((i7 & i) != 0) {
                        zzocVar.zzy(i8, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 15:
                    if ((i7 & i) != 0) {
                        zzocVar.zzA(i8, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 16:
                    if ((i7 & i) != 0) {
                        zzocVar.zzC(i8, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 17:
                    if ((i7 & i) != 0) {
                        zzocVar.zzq(i8, unsafe.getObject(obj, j), zzB(i5));
                    } else {
                        continue;
                    }
                    i5 += 3;
                    i2 = 1048575;
                case 18:
                    zzmv.zzF(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 19:
                    zzmv.zzJ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 20:
                    zzmv.zzM(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 21:
                    zzmv.zzU(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 22:
                    zzmv.zzL(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 23:
                    zzmv.zzI(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 24:
                    zzmv.zzH(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 25:
                    zzmv.zzD(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 26:
                    zzmv.zzS(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar);
                    break;
                case 27:
                    zzmv.zzN(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, zzB(i5));
                    break;
                case 28:
                    zzmv.zzE(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar);
                    break;
                case 29:
                    zzmv.zzT(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    break;
                case 30:
                    zzmv.zzG(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 31:
                    zzmv.zzO(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 32:
                    zzmv.zzP(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 33:
                    zzmv.zzQ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case 34:
                    zzmv.zzR(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, false);
                    continue;
                    i5 += 3;
                    i2 = 1048575;
                case Operator.PROJECTION /* 35 */:
                    zzmv.zzF(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.CONVERTABLE_TO /* 36 */:
                    zzmv.zzJ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.END_OF_STMT /* 37 */:
                    zzmv.zzM(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.FOREACH /* 38 */:
                    zzmv.zzU(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.f1408IF /* 39 */:
                    zzmv.zzL(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.ELSE /* 40 */:
                    zzmv.zzI(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.WHILE /* 41 */:
                    zzmv.zzH(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.UNTIL /* 42 */:
                    zzmv.zzD(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.FOR /* 43 */:
                    zzmv.zzT(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.SWITCH /* 44 */:
                    zzmv.zzG(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case Operator.f1407DO /* 45 */:
                    zzmv.zzO(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case 46:
                    zzmv.zzP(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case 47:
                    zzmv.zzQ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case 48:
                    zzmv.zzR(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, true);
                    break;
                case 49:
                    zzmv.zzK(this.zzc[i5], (List) unsafe.getObject(obj, j), zzocVar, zzB(i5));
                    break;
                case 50:
                    zzN(zzocVar, i8, unsafe.getObject(obj, j), i5);
                    break;
                case 51:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzf(i8, zzm(obj, j));
                        break;
                    }
                    break;
                case 52:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzo(i8, zzn(obj, j));
                        break;
                    }
                    break;
                case 53:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzt(i8, zzz(obj, j));
                        break;
                    }
                    break;
                case 54:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzJ(i8, zzz(obj, j));
                        break;
                    }
                    break;
                case 55:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzr(i8, zzp(obj, j));
                        break;
                    }
                    break;
                case 56:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzm(i8, zzz(obj, j));
                        break;
                    }
                    break;
                case 57:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzk(i8, zzp(obj, j));
                        break;
                    }
                    break;
                case 58:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzb(i8, zzU(obj, j));
                        break;
                    }
                    break;
                case 59:
                    if (zzT(obj, i8, i5)) {
                        zzV(i8, unsafe.getObject(obj, j), zzocVar);
                        break;
                    }
                    break;
                case 60:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzv(i8, unsafe.getObject(obj, j), zzB(i5));
                        break;
                    }
                    break;
                case 61:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzd(i8, (zzka) unsafe.getObject(obj, j));
                        break;
                    }
                    break;
                case 62:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzH(i8, zzp(obj, j));
                        break;
                    }
                    break;
                case 63:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzi(i8, zzp(obj, j));
                        break;
                    }
                    break;
                case 64:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzw(i8, zzp(obj, j));
                        break;
                    }
                    break;
                case 65:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzy(i8, zzz(obj, j));
                        break;
                    }
                    break;
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzA(i8, zzp(obj, j));
                        break;
                    }
                    break;
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzC(i8, zzz(obj, j));
                        break;
                    }
                    break;
                case TypeReference.NEW /* 68 */:
                    if (zzT(obj, i8, i5)) {
                        zzocVar.zzq(i8, unsafe.getObject(obj, j), zzB(i5));
                        break;
                    }
                    break;
            }
            i5 += 3;
            i2 = 1048575;
        }
        zznk zznkVar2 = this.zzn;
        zznkVar2.zzi(zznkVar2.zzd(obj), zzocVar);
    }
}
