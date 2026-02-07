package com.google.android.gms.internal.cast;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.mvel2.Operator;
import org.mvel2.asm.TypeReference;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class zzva implements zzvi {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzwj.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final zzux zze;
    private final boolean zzf;
    private final int[] zzg;
    private final int zzh;
    private final zzul zzi;
    private final zzvz zzj;
    private final zztf zzk;
    private final zzvc zzl;
    private final zzus zzm;

    private zzva(int[] iArr, Object[] objArr, int i, int i2, zzux zzuxVar, int i3, boolean z, int[] iArr2, int i4, int i5, zzvc zzvcVar, zzul zzulVar, zzvz zzvzVar, zztf zztfVar, zzus zzusVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        boolean z2 = false;
        if (zztfVar != null && zztfVar.zzc(zzuxVar)) {
            z2 = true;
        }
        this.zzf = z2;
        this.zzg = iArr2;
        this.zzh = i4;
        this.zzl = zzvcVar;
        this.zzi = zzulVar;
        this.zzj = zzvzVar;
        this.zzk = zztfVar;
        this.zze = zzuxVar;
        this.zzm = zzusVar;
    }

    private static boolean zzA(Object obj, int i, zzvi zzviVar) {
        return zzviVar.zzh(zzwj.zzf(obj, i & 1048575));
    }

    private static boolean zzB(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zztp) {
            return ((zztp) obj).zzK();
        }
        return true;
    }

    private final boolean zzC(Object obj, int i, int i2) {
        return zzwj.zzc(obj, (long) (zzm(i2) & 1048575)) == i;
    }

    private static boolean zzD(Object obj, long j) {
        return ((Boolean) zzwj.zzf(obj, j)).booleanValue();
    }

    private static final void zzE(int i, Object obj, zzwq zzwqVar) {
        if (obj instanceof String) {
            zzwqVar.zzD(i, (String) obj);
        } else {
            zzwqVar.zzd(i, (zzsu) obj);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x03a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.gms.internal.cast.zzva zzi(java.lang.Class r32, com.google.android.gms.internal.cast.zzuu r33, com.google.android.gms.internal.cast.zzvc r34, com.google.android.gms.internal.cast.zzul r35, com.google.android.gms.internal.cast.zzvz r36, com.google.android.gms.internal.cast.zztf r37, com.google.android.gms.internal.cast.zzus r38) {
        /*
            Method dump skipped, instructions count: 1054
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zzi(java.lang.Class, com.google.android.gms.internal.cast.zzuu, com.google.android.gms.internal.cast.zzvc, com.google.android.gms.internal.cast.zzul, com.google.android.gms.internal.cast.zzvz, com.google.android.gms.internal.cast.zztf, com.google.android.gms.internal.cast.zzus):com.google.android.gms.internal.cast.zzva");
    }

    private static double zzj(Object obj, long j) {
        return ((Double) zzwj.zzf(obj, j)).doubleValue();
    }

    private static float zzk(Object obj, long j) {
        return ((Float) zzwj.zzf(obj, j)).floatValue();
    }

    private static int zzl(Object obj, long j) {
        return ((Integer) zzwj.zzf(obj, j)).intValue();
    }

    private final int zzm(int i) {
        return this.zzc[i + 2];
    }

    private static int zzn(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzo(int i) {
        return this.zzc[i + 1];
    }

    private static long zzp(Object obj, long j) {
        return ((Long) zzwj.zzf(obj, j)).longValue();
    }

    private final zzvi zzq(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzvi zzviVar = (zzvi) objArr[i3];
        if (zzviVar != null) {
            return zzviVar;
        }
        zzvi zzviVarZzb = zzvf.zza().zzb((Class) objArr[i3 + 1]);
        this.zzd[i3] = zzviVarZzb;
        return zzviVarZzb;
    }

    private final Object zzr(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static Field zzs(Class cls, String str) {
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

    private final void zzt(Object obj, Object obj2, int i) {
        if (zzy(obj2, i)) {
            int iZzo = zzo(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzo;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzvi zzviVarZzq = zzq(i);
            if (!zzy(obj, i)) {
                if (zzB(object)) {
                    Object objZzc = zzviVarZzq.zzc();
                    zzviVarZzq.zze(objZzc, object);
                    unsafe.putObject(obj, j, objZzc);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzv(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzB(object2)) {
                Object objZzc2 = zzviVarZzq.zzc();
                zzviVarZzq.zze(objZzc2, object2);
                unsafe.putObject(obj, j, objZzc2);
                object2 = objZzc2;
            }
            zzviVarZzq.zze(object2, object);
        }
    }

    private final void zzu(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzC(obj2, i2, i)) {
            int iZzo = zzo(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzo;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzvi zzviVarZzq = zzq(i);
            if (!zzC(obj, i2, i)) {
                if (zzB(object)) {
                    Object objZzc = zzviVarZzq.zzc();
                    zzviVarZzq.zze(objZzc, object);
                    unsafe.putObject(obj, j, objZzc);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzw(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzB(object2)) {
                Object objZzc2 = zzviVarZzq.zzc();
                zzviVarZzq.zze(objZzc2, object2);
                unsafe.putObject(obj, j, objZzc2);
                object2 = objZzc2;
            }
            zzviVarZzq.zze(object2, object);
        }
    }

    private final void zzv(Object obj, int i) {
        int iZzm = zzm(i);
        long j = 1048575 & iZzm;
        if (j == 1048575) {
            return;
        }
        zzwj.zzq(obj, j, (1 << (iZzm >>> 20)) | zzwj.zzc(obj, j));
    }

    private final void zzw(Object obj, int i, int i2) {
        zzwj.zzq(obj, zzm(i2) & 1048575, i);
    }

    private final boolean zzx(Object obj, Object obj2, int i) {
        return zzy(obj, i) == zzy(obj2, i);
    }

    private final boolean zzy(Object obj, int i) {
        int iZzm = zzm(i);
        long j = iZzm & 1048575;
        if (j != 1048575) {
            return (zzwj.zzc(obj, j) & (1 << (iZzm >>> 20))) != 0;
        }
        int iZzo = zzo(i);
        long j2 = iZzo & 1048575;
        switch (zzn(iZzo)) {
            case 0:
                return Double.doubleToRawLongBits(zzwj.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzwj.zzb(obj, j2)) != 0;
            case 2:
                return zzwj.zzd(obj, j2) != 0;
            case 3:
                return zzwj.zzd(obj, j2) != 0;
            case 4:
                return zzwj.zzc(obj, j2) != 0;
            case 5:
                return zzwj.zzd(obj, j2) != 0;
            case 6:
                return zzwj.zzc(obj, j2) != 0;
            case 7:
                return zzwj.zzw(obj, j2);
            case 8:
                Object objZzf = zzwj.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzsu) {
                    return !zzsu.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzwj.zzf(obj, j2) != null;
            case 10:
                return !zzsu.zzb.equals(zzwj.zzf(obj, j2));
            case 11:
                return zzwj.zzc(obj, j2) != 0;
            case 12:
                return zzwj.zzc(obj, j2) != 0;
            case 13:
                return zzwj.zzc(obj, j2) != 0;
            case 14:
                return zzwj.zzd(obj, j2) != 0;
            case 15:
                return zzwj.zzc(obj, j2) != 0;
            case 16:
                return zzwj.zzd(obj, j2) != 0;
            case 17:
                return zzwj.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzz(Object obj, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzy(obj, i) : (i3 & i4) != 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:170:0x045a  */
    @Override // com.google.android.gms.internal.cast.zzvi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zza(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 1966
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zza(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final int zzb(Object obj) {
        int i;
        long jDoubleToLongBits;
        int i2;
        int iFloatToIntBits;
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < this.zzc.length; i5 += 3) {
            int iZzo = zzo(i5);
            int[] iArr = this.zzc;
            int i6 = 1048575 & iZzo;
            int iZzn = zzn(iZzo);
            int i7 = iArr[i5];
            long j = i6;
            int iHashCode = 37;
            switch (iZzn) {
                case 0:
                    i = i4 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzwj.zza(obj, j));
                    byte[] bArr = zzty.zzd;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 1:
                    i2 = i4 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzwj.zzb(obj, j));
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 2:
                    i = i4 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr2 = zzty.zzd;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 3:
                    i = i4 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr3 = zzty.zzd;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 4:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 5:
                    i = i4 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr4 = zzty.zzd;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 6:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 7:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzty.zza(zzwj.zzw(obj, j));
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 8:
                    i2 = i4 * 53;
                    iFloatToIntBits = ((String) zzwj.zzf(obj, j)).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 9:
                    i3 = i4 * 53;
                    Object objZzf = zzwj.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i4 = i3 + iHashCode;
                    break;
                case 10:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 11:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 12:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 13:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 14:
                    i = i4 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr5 = zzty.zzd;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 15:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 16:
                    i = i4 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr6 = zzty.zzd;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 17:
                    i3 = i4 * 53;
                    Object objZzf2 = zzwj.zzf(obj, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i4 = i3 + iHashCode;
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
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 50:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 51:
                    if (zzC(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzj(obj, j));
                        byte[] bArr7 = zzty.zzd;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzk(obj, j));
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzC(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr8 = zzty.zzd;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzC(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr9 = zzty.zzd;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzC(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr10 = zzty.zzd;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzty.zza(zzD(obj, j));
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = ((String) zzwj.zzf(obj, j)).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzC(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr11 = zzty.zzd;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzC(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr12 = zzty.zzd;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    if (zzC(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i4 * 53) + this.zzj.zzc(obj).hashCode();
        if (!this.zzf) {
            return iHashCode2;
        }
        this.zzk.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final Object zzc() {
        return ((zztp) this.zze).zzx();
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x007c, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0069  */
    @Override // com.google.android.gms.internal.cast.zzvi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzd(java.lang.Object r7) {
        /*
            r6 = this;
            boolean r0 = zzB(r7)
            if (r0 != 0) goto L8
            goto L8d
        L8:
            boolean r0 = r7 instanceof com.google.android.gms.internal.cast.zztp
            r1 = 0
            if (r0 == 0) goto L1b
            r0 = r7
            com.google.android.gms.internal.cast.zztp r0 = (com.google.android.gms.internal.cast.zztp) r0
            r2 = 2147483647(0x7fffffff, float:NaN)
            r0.zzI(r2)
            r0.zza = r1
            r0.zzG()
        L1b:
            int[] r0 = r6.zzc
        L1d:
            int r2 = r0.length
            if (r1 >= r2) goto L7f
            int r2 = r6.zzo(r1)
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r3 = r3 & r2
            int r2 = zzn(r2)
            long r3 = (long) r3
            r5 = 9
            if (r2 == r5) goto L69
            r5 = 60
            if (r2 == r5) goto L51
            r5 = 68
            if (r2 == r5) goto L51
            switch(r2) {
                case 17: goto L69;
                case 18: goto L4b;
                case 19: goto L4b;
                case 20: goto L4b;
                case 21: goto L4b;
                case 22: goto L4b;
                case 23: goto L4b;
                case 24: goto L4b;
                case 25: goto L4b;
                case 26: goto L4b;
                case 27: goto L4b;
                case 28: goto L4b;
                case 29: goto L4b;
                case 30: goto L4b;
                case 31: goto L4b;
                case 32: goto L4b;
                case 33: goto L4b;
                case 34: goto L4b;
                case 35: goto L4b;
                case 36: goto L4b;
                case 37: goto L4b;
                case 38: goto L4b;
                case 39: goto L4b;
                case 40: goto L4b;
                case 41: goto L4b;
                case 42: goto L4b;
                case 43: goto L4b;
                case 44: goto L4b;
                case 45: goto L4b;
                case 46: goto L4b;
                case 47: goto L4b;
                case 48: goto L4b;
                case 49: goto L4b;
                case 50: goto L3d;
                default: goto L3c;
            }
        L3c:
            goto L7c
        L3d:
            sun.misc.Unsafe r2 = com.google.android.gms.internal.cast.zzva.zzb
            java.lang.Object r2 = r2.getObject(r7, r3)
            if (r2 != 0) goto L46
            goto L7c
        L46:
            androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(r2)
            r7 = 0
            throw r7
        L4b:
            com.google.android.gms.internal.cast.zzul r2 = r6.zzi
            r2.zza(r7, r3)
            goto L7c
        L51:
            int[] r2 = r6.zzc
            r2 = r2[r1]
            boolean r2 = r6.zzC(r7, r2, r1)
            if (r2 == 0) goto L7c
            com.google.android.gms.internal.cast.zzvi r2 = r6.zzq(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.cast.zzva.zzb
            java.lang.Object r3 = r5.getObject(r7, r3)
            r2.zzd(r3)
            goto L7c
        L69:
            boolean r2 = r6.zzy(r7, r1)
            if (r2 == 0) goto L7c
            com.google.android.gms.internal.cast.zzvi r2 = r6.zzq(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.cast.zzva.zzb
            java.lang.Object r3 = r5.getObject(r7, r3)
            r2.zzd(r3)
        L7c:
            int r1 = r1 + 3
            goto L1d
        L7f:
            com.google.android.gms.internal.cast.zzvz r0 = r6.zzj
            r0.zze(r7)
            boolean r0 = r6.zzf
            if (r0 == 0) goto L8d
            com.google.android.gms.internal.cast.zztf r0 = r6.zzk
            r0.zzb(r7)
        L8d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zzd(java.lang.Object):void");
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final void zze(Object obj, Object obj2) {
        if (!zzB(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(obj)));
        }
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzo = zzo(i);
            int i2 = 1048575 & iZzo;
            int[] iArr = this.zzc;
            int iZzn = zzn(iZzo);
            int i3 = iArr[i];
            long j = i2;
            switch (iZzn) {
                case 0:
                    if (zzy(obj2, i)) {
                        zzwj.zzo(obj, j, zzwj.zza(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzy(obj2, i)) {
                        zzwj.zzp(obj, j, zzwj.zzb(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzy(obj2, i)) {
                        zzwj.zzm(obj, j, zzwj.zzw(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzy(obj2, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzt(obj, obj2, i);
                    break;
                case 10:
                    if (zzy(obj2, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzt(obj, obj2, i);
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
                    this.zzi.zzb(obj, obj2, j);
                    break;
                case 50:
                    int i4 = zzvk.$r8$clinit;
                    Object objZzf = zzwj.zzf(obj, j);
                    Object objZzf2 = zzwj.zzf(obj2, j);
                    WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(objZzf);
                    WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(objZzf2);
                    throw null;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzC(obj2, i3, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzw(obj, i3, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzu(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzC(obj2, i3, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzw(obj, i3, i);
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    zzu(obj, obj2, i);
                    break;
            }
        }
        zzvk.zzo(this.zzj, obj, obj2);
        if (this.zzf) {
            this.zzk.zza(obj2);
            throw null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.cast.zzvi
    public final void zzf(Object obj, zzwq zzwqVar) throws zzta {
        int i;
        zzva zzvaVar = this;
        if (zzvaVar.zzf) {
            zzvaVar.zzk.zza(obj);
            throw null;
        }
        int[] iArr = zzvaVar.zzc;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 0;
        int i4 = 1048575;
        int i5 = 0;
        while (i3 < iArr.length) {
            int iZzo = zzvaVar.zzo(i3);
            int[] iArr2 = zzvaVar.zzc;
            int iZzn = zzn(iZzo);
            int i6 = iArr2[i3];
            if (iZzn <= 17) {
                int i7 = iArr2[i3 + 2];
                int i8 = i7 & i2;
                if (i8 != i4) {
                    i5 = i8 == i2 ? 0 : unsafe.getInt(obj, i8);
                    i4 = i8;
                }
                i = 1 << (i7 >>> 20);
            } else {
                i = 0;
            }
            long j = iZzo & i2;
            switch (iZzn) {
                case 0:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzf(i6, zzwj.zza(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 1:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzn(i6, zzwj.zzb(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 2:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzs(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 3:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzH(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 4:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzq(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 5:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzl(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 6:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzj(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 7:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzb(i6, zzwj.zzw(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 8:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzE(i6, unsafe.getObject(obj, j), zzwqVar);
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 9:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzu(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i3));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 10:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzd(i6, (zzsu) unsafe.getObject(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 11:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzF(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 12:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzh(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 13:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzv(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 14:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzx(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 15:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzz(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 16:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzB(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i3 += 3;
                    i2 = 1048575;
                case 17:
                    if (zzvaVar.zzz(obj, i3, i4, i5, i)) {
                        zzwqVar.zzp(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i3));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 18:
                    zzvk.zzr(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 19:
                    zzvk.zzv(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 20:
                    zzvk.zzx(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 21:
                    zzvk.zzD(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 22:
                    zzvk.zzw(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 23:
                    zzvk.zzu(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 24:
                    zzvk.zzt(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 25:
                    zzvk.zzq(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 26:
                    int i9 = zzvaVar.zzc[i3];
                    List list = (List) unsafe.getObject(obj, j);
                    int i10 = zzvk.$r8$clinit;
                    if (list != null && !list.isEmpty()) {
                        zzwqVar.zzE(i9, list);
                    }
                    i3 += 3;
                    i2 = 1048575;
                    break;
                case 27:
                    int i11 = zzvaVar.zzc[i3];
                    List list2 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq = zzvaVar.zzq(i3);
                    int i12 = zzvk.$r8$clinit;
                    if (list2 != null && !list2.isEmpty()) {
                        for (int i13 = 0; i13 < list2.size(); i13++) {
                            ((zztd) zzwqVar).zzu(i11, list2.get(i13), zzviVarZzq);
                        }
                    }
                    i3 += 3;
                    i2 = 1048575;
                    break;
                case 28:
                    int i14 = zzvaVar.zzc[i3];
                    List list3 = (List) unsafe.getObject(obj, j);
                    int i15 = zzvk.$r8$clinit;
                    if (list3 != null && !list3.isEmpty()) {
                        zzwqVar.zze(i14, list3);
                    }
                    i3 += 3;
                    i2 = 1048575;
                    break;
                case 29:
                    zzvk.zzC(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 30:
                    zzvk.zzs(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 31:
                    zzvk.zzy(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 32:
                    zzvk.zzz(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 33:
                    zzvk.zzA(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case 34:
                    zzvk.zzB(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.PROJECTION /* 35 */:
                    zzvk.zzr(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.CONVERTABLE_TO /* 36 */:
                    zzvk.zzv(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.END_OF_STMT /* 37 */:
                    zzvk.zzx(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.FOREACH /* 38 */:
                    zzvk.zzD(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.f1408IF /* 39 */:
                    zzvk.zzw(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.ELSE /* 40 */:
                    zzvk.zzu(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.WHILE /* 41 */:
                    zzvk.zzt(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.UNTIL /* 42 */:
                    zzvk.zzq(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.FOR /* 43 */:
                    zzvk.zzC(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.SWITCH /* 44 */:
                    zzvk.zzs(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case Operator.f1407DO /* 45 */:
                    zzvk.zzy(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case 46:
                    zzvk.zzz(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case 47:
                    zzvk.zzA(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case 48:
                    zzvk.zzB(zzvaVar.zzc[i3], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i3 += 3;
                    i2 = 1048575;
                case 49:
                    int i16 = zzvaVar.zzc[i3];
                    List list4 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq2 = zzvaVar.zzq(i3);
                    int i17 = zzvk.$r8$clinit;
                    if (list4 != null && !list4.isEmpty()) {
                        for (int i18 = 0; i18 < list4.size(); i18++) {
                            ((zztd) zzwqVar).zzp(i16, list4.get(i18), zzviVarZzq2);
                        }
                    }
                    i3 += 3;
                    i2 = 1048575;
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzvaVar.zzr(i3));
                        throw null;
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 51:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzf(i6, zzj(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 52:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzn(i6, zzk(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 53:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzs(i6, zzp(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 54:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzH(i6, zzp(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 55:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzq(i6, zzl(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 56:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzl(i6, zzp(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 57:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzj(i6, zzl(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 58:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzb(i6, zzD(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 59:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzE(i6, unsafe.getObject(obj, j), zzwqVar);
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 60:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzu(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i3));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 61:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzd(i6, (zzsu) unsafe.getObject(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 62:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzF(i6, zzl(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 63:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzh(i6, zzl(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 64:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzv(i6, zzl(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case 65:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzx(i6, zzp(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzz(i6, zzl(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzB(i6, zzp(obj, j));
                    }
                    i3 += 3;
                    i2 = 1048575;
                case TypeReference.NEW /* 68 */:
                    if (zzvaVar.zzC(obj, i6, i3)) {
                        zzwqVar.zzp(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i3));
                    }
                    i3 += 3;
                    i2 = 1048575;
                default:
                    i3 += 3;
                    i2 = 1048575;
            }
        }
        zzvz zzvzVar = zzvaVar.zzj;
        zzvzVar.zzg(zzvzVar.zzc(obj), zzwqVar);
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final boolean zzg(Object obj, Object obj2) {
        boolean zZzE;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzo = zzo(i);
            long j = iZzo & 1048575;
            switch (zzn(iZzo)) {
                case 0:
                    if (!zzx(obj, obj2, i) || Double.doubleToLongBits(zzwj.zza(obj, j)) != Double.doubleToLongBits(zzwj.zza(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 1:
                    if (!zzx(obj, obj2, i) || Float.floatToIntBits(zzwj.zzb(obj, j)) != Float.floatToIntBits(zzwj.zzb(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 2:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 3:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 4:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 5:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 6:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 7:
                    if (!zzx(obj, obj2, i) || zzwj.zzw(obj, j) != zzwj.zzw(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 8:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 9:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 10:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 11:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 12:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 13:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 14:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 15:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 16:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 17:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
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
                    zZzE = zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j));
                    break;
                case 50:
                    zZzE = zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j));
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
                    long jZzm = zzm(i) & 1048575;
                    if (zzwj.zzc(obj, jZzm) != zzwj.zzc(obj2, jZzm) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                default:
            }
            if (!zZzE) {
                return false;
            }
        }
        if (!this.zzj.zzc(obj).equals(this.zzj.zzc(obj2))) {
            return false;
        }
        if (!this.zzf) {
            return true;
        }
        this.zzk.zza(obj);
        this.zzk.zza(obj2);
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    @Override // com.google.android.gms.internal.cast.zzvi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzh(java.lang.Object r16) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zzh(java.lang.Object):boolean");
    }
}
