package com.google.android.gms.internal.play_billing;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.mvel2.Operator;
import org.mvel2.asm.TypeReference;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class zzip implements zzix {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzjq.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzim zzg;
    private final boolean zzh = false;
    private final int[] zzi;
    private final int zzj;
    private final int zzk;
    private final zzjj zzl;
    private final zzgx zzm;

    private zzip(int[] iArr, Object[] objArr, int i, int i2, zzim zzimVar, boolean z, int[] iArr2, int i3, int i4, zzir zzirVar, zzhz zzhzVar, zzjj zzjjVar, zzgx zzgxVar, zzih zzihVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = iArr2;
        this.zzj = i3;
        this.zzk = i4;
        this.zzl = zzjjVar;
        this.zzm = zzgxVar;
        this.zzg = zzimVar;
    }

    private static void zzA(Object obj) {
        if (!zzL(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(obj)));
        }
    }

    private final void zzB(Object obj, Object obj2, int i) {
        if (zzI(obj2, i)) {
            int iZzs = zzs(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzs;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzix zzixVarZzv = zzv(i);
            if (!zzI(obj, i)) {
                if (zzL(object)) {
                    Object objZze = zzixVarZzv.zze();
                    zzixVarZzv.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzD(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzL(object2)) {
                Object objZze2 = zzixVarZzv.zze();
                zzixVarZzv.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzixVarZzv.zzg(object2, object);
        }
    }

    private final void zzC(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzM(obj2, i2, i)) {
            int iZzs = zzs(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzs;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzix zzixVarZzv = zzv(i);
            if (!zzM(obj, i2, i)) {
                if (zzL(object)) {
                    Object objZze = zzixVarZzv.zze();
                    zzixVarZzv.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzE(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzL(object2)) {
                Object objZze2 = zzixVarZzv.zze();
                zzixVarZzv.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzixVarZzv.zzg(object2, object);
        }
    }

    private final void zzD(Object obj, int i) {
        int iZzp = zzp(i);
        long j = 1048575 & iZzp;
        if (j == 1048575) {
            return;
        }
        zzjq.zzq(obj, j, (1 << (iZzp >>> 20)) | zzjq.zzc(obj, j));
    }

    private final void zzE(Object obj, int i, int i2) {
        zzjq.zzq(obj, zzp(i2) & 1048575, i);
    }

    private final boolean zzH(Object obj, Object obj2, int i) {
        return zzI(obj, i) == zzI(obj2, i);
    }

    private final boolean zzI(Object obj, int i) {
        int iZzp = zzp(i);
        long j = iZzp & 1048575;
        if (j != 1048575) {
            return (zzjq.zzc(obj, j) & (1 << (iZzp >>> 20))) != 0;
        }
        int iZzs = zzs(i);
        long j2 = iZzs & 1048575;
        switch (zzr(iZzs)) {
            case 0:
                return Double.doubleToRawLongBits(zzjq.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzjq.zzb(obj, j2)) != 0;
            case 2:
                return zzjq.zzd(obj, j2) != 0;
            case 3:
                return zzjq.zzd(obj, j2) != 0;
            case 4:
                return zzjq.zzc(obj, j2) != 0;
            case 5:
                return zzjq.zzd(obj, j2) != 0;
            case 6:
                return zzjq.zzc(obj, j2) != 0;
            case 7:
                return zzjq.zzw(obj, j2);
            case 8:
                Object objZzf = zzjq.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzgk) {
                    return !zzgk.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzjq.zzf(obj, j2) != null;
            case 10:
                return !zzgk.zzb.equals(zzjq.zzf(obj, j2));
            case 11:
                return zzjq.zzc(obj, j2) != 0;
            case 12:
                return zzjq.zzc(obj, j2) != 0;
            case 13:
                return zzjq.zzc(obj, j2) != 0;
            case 14:
                return zzjq.zzd(obj, j2) != 0;
            case 15:
                return zzjq.zzc(obj, j2) != 0;
            case 16:
                return zzjq.zzd(obj, j2) != 0;
            case 17:
                return zzjq.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzJ(Object obj, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzI(obj, i) : (i3 & i4) != 0;
    }

    private static boolean zzK(Object obj, int i, zzix zzixVar) {
        return zzixVar.zzk(zzjq.zzf(obj, i & 1048575));
    }

    private static boolean zzL(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzhk) {
            return ((zzhk) obj).zzA();
        }
        return true;
    }

    private final boolean zzM(Object obj, int i, int i2) {
        return zzjq.zzc(obj, (long) (zzp(i2) & 1048575)) == i;
    }

    private static boolean zzN(Object obj, long j) {
        return ((Boolean) zzjq.zzf(obj, j)).booleanValue();
    }

    private static final void zzO(int i, Object obj, zzjw zzjwVar) {
        if (obj instanceof String) {
            zzjwVar.zzG(i, (String) obj);
        } else {
            zzjwVar.zzd(i, (zzgk) obj);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x03a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.gms.internal.play_billing.zzip zzl(java.lang.Class r32, com.google.android.gms.internal.play_billing.zzij r33, com.google.android.gms.internal.play_billing.zzir r34, com.google.android.gms.internal.play_billing.zzhz r35, com.google.android.gms.internal.play_billing.zzjj r36, com.google.android.gms.internal.play_billing.zzgx r37, com.google.android.gms.internal.play_billing.zzih r38) {
        /*
            Method dump skipped, instructions count: 1045
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzip.zzl(java.lang.Class, com.google.android.gms.internal.play_billing.zzij, com.google.android.gms.internal.play_billing.zzir, com.google.android.gms.internal.play_billing.zzhz, com.google.android.gms.internal.play_billing.zzjj, com.google.android.gms.internal.play_billing.zzgx, com.google.android.gms.internal.play_billing.zzih):com.google.android.gms.internal.play_billing.zzip");
    }

    private static double zzm(Object obj, long j) {
        return ((Double) zzjq.zzf(obj, j)).doubleValue();
    }

    private static float zzn(Object obj, long j) {
        return ((Float) zzjq.zzf(obj, j)).floatValue();
    }

    private static int zzo(Object obj, long j) {
        return ((Integer) zzjq.zzf(obj, j)).intValue();
    }

    private final int zzp(int i) {
        return this.zzc[i + 2];
    }

    private static int zzr(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzs(int i) {
        return this.zzc[i + 1];
    }

    private static long zzt(Object obj, long j) {
        return ((Long) zzjq.zzf(obj, j)).longValue();
    }

    private final zzix zzv(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzix zzixVar = (zzix) objArr[i3];
        if (zzixVar != null) {
            return zzixVar;
        }
        zzix zzixVarZzb = zziu.zza().zzb((Class) objArr[i3 + 1]);
        this.zzd[i3] = zzixVarZzb;
        return zzixVarZzb;
    }

    private final Object zzw(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static Field zzz(Class cls, String str) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0457  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x06ef A[PHI: r0
      0x06ef: PHI (r0v2 com.google.android.gms.internal.play_billing.zzip) = 
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v39 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
      (r0v1 com.google.android.gms.internal.play_billing.zzip)
     binds: [B:18:0x0053, B:269:0x06e2, B:239:0x061d, B:216:0x058d, B:209:0x055a, B:133:0x0352, B:130:0x033a, B:127:0x0322, B:124:0x030a, B:121:0x02f2, B:118:0x02da, B:115:0x02c2, B:112:0x02aa, B:109:0x0291, B:106:0x027a, B:103:0x0263, B:100:0x024c, B:97:0x0235, B:92:0x0219, B:80:0x01cc, B:77:0x01be, B:74:0x01a8, B:71:0x0192, B:68:0x017b, B:65:0x016d, B:62:0x015f, B:59:0x014f, B:53:0x0124, B:50:0x0110, B:46:0x00f2, B:43:0x00dd, B:40:0x00c7, B:36:0x00b8, B:32:0x00a9, B:29:0x008f, B:25:0x0074, B:21:0x005c] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.play_billing.zzix
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zza(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 1950
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzip.zza(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.play_billing.zzix
    public final int zzb(Object obj) {
        int i;
        long jDoubleToLongBits;
        int i2;
        int iFloatToIntBits;
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < this.zzc.length; i5 += 3) {
            int iZzs = zzs(i5);
            int[] iArr = this.zzc;
            int i6 = 1048575 & iZzs;
            int iZzr = zzr(iZzs);
            int i7 = iArr[i5];
            long j = i6;
            int iHashCode = 37;
            switch (iZzr) {
                case 0:
                    i = i4 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzjq.zza(obj, j));
                    byte[] bArr = zzhp.zzb;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 1:
                    i2 = i4 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzjq.zzb(obj, j));
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 2:
                    i = i4 * 53;
                    jDoubleToLongBits = zzjq.zzd(obj, j);
                    byte[] bArr2 = zzhp.zzb;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 3:
                    i = i4 * 53;
                    jDoubleToLongBits = zzjq.zzd(obj, j);
                    byte[] bArr3 = zzhp.zzb;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 4:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 5:
                    i = i4 * 53;
                    jDoubleToLongBits = zzjq.zzd(obj, j);
                    byte[] bArr4 = zzhp.zzb;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 6:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 7:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzhp.zza(zzjq.zzw(obj, j));
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 8:
                    i2 = i4 * 53;
                    iFloatToIntBits = ((String) zzjq.zzf(obj, j)).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 9:
                    i3 = i4 * 53;
                    Object objZzf = zzjq.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i4 = i3 + iHashCode;
                    break;
                case 10:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzf(obj, j).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 11:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 12:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 13:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 14:
                    i = i4 * 53;
                    jDoubleToLongBits = zzjq.zzd(obj, j);
                    byte[] bArr5 = zzhp.zzb;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 15:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzc(obj, j);
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 16:
                    i = i4 * 53;
                    jDoubleToLongBits = zzjq.zzd(obj, j);
                    byte[] bArr6 = zzhp.zzb;
                    i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                    break;
                case 17:
                    i3 = i4 * 53;
                    Object objZzf2 = zzjq.zzf(obj, j);
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
                    iFloatToIntBits = zzjq.zzf(obj, j).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 50:
                    i2 = i4 * 53;
                    iFloatToIntBits = zzjq.zzf(obj, j).hashCode();
                    i4 = i2 + iFloatToIntBits;
                    break;
                case 51:
                    if (zzM(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzm(obj, j));
                        byte[] bArr7 = zzhp.zzb;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzn(obj, j));
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzM(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr8 = zzhp.zzb;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzM(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr9 = zzhp.zzb;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzM(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr10 = zzhp.zzb;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzhp.zza(zzN(obj, j));
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = ((String) zzjq.zzf(obj, j)).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzjq.zzf(obj, j).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzjq.zzf(obj, j).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzM(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr11 = zzhp.zzb;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzM(obj, i7, i5)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr12 = zzhp.zzb;
                        i4 = i + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    if (zzM(obj, i7, i5)) {
                        i2 = i4 * 53;
                        iFloatToIntBits = zzjq.zzf(obj, j).hashCode();
                        i4 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i4 * 53) + ((zzhk) obj).zzc.hashCode();
        if (!this.zzh) {
            return iHashCode2;
        }
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzix
    public final Object zze() {
        return ((zzhk) this.zzg).zzp();
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x006b  */
    @Override // com.google.android.gms.internal.play_billing.zzix
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzf(java.lang.Object r7) {
        /*
            r6 = this;
            boolean r0 = zzL(r7)
            if (r0 != 0) goto L8
            goto L8f
        L8:
            boolean r0 = r7 instanceof com.google.android.gms.internal.play_billing.zzhk
            r1 = 0
            if (r0 == 0) goto L1b
            r0 = r7
            com.google.android.gms.internal.play_billing.zzhk r0 = (com.google.android.gms.internal.play_billing.zzhk) r0
            r2 = 2147483647(0x7fffffff, float:NaN)
            r0.zzy(r2)
            r0.zza = r1
            r0.zzw()
        L1b:
            int[] r0 = r6.zzc
        L1d:
            int r2 = r0.length
            if (r1 >= r2) goto L81
            int r2 = r6.zzs(r1)
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r3 = r3 & r2
            int r2 = zzr(r2)
            long r3 = (long) r3
            r5 = 9
            if (r2 == r5) goto L6b
            r5 = 60
            if (r2 == r5) goto L53
            r5 = 68
            if (r2 == r5) goto L53
            r5 = 0
            switch(r2) {
                case 17: goto L6b;
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
                case 50: goto L3e;
                default: goto L3d;
            }
        L3d:
            goto L7e
        L3e:
            sun.misc.Unsafe r2 = com.google.android.gms.internal.play_billing.zzip.zzb
            java.lang.Object r2 = r2.getObject(r7, r3)
            if (r2 != 0) goto L47
            goto L7e
        L47:
            androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(r2)
            throw r5
        L4b:
            java.lang.Object r7 = com.google.android.gms.internal.play_billing.zzjq.zzf(r7, r3)
            androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(r7)
            throw r5
        L53:
            int[] r2 = r6.zzc
            r2 = r2[r1]
            boolean r2 = r6.zzM(r7, r2, r1)
            if (r2 == 0) goto L7e
            com.google.android.gms.internal.play_billing.zzix r2 = r6.zzv(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.play_billing.zzip.zzb
            java.lang.Object r3 = r5.getObject(r7, r3)
            r2.zzf(r3)
            goto L7e
        L6b:
            boolean r2 = r6.zzI(r7, r1)
            if (r2 == 0) goto L7e
            com.google.android.gms.internal.play_billing.zzix r2 = r6.zzv(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.play_billing.zzip.zzb
            java.lang.Object r3 = r5.getObject(r7, r3)
            r2.zzf(r3)
        L7e:
            int r1 = r1 + 3
            goto L1d
        L81:
            com.google.android.gms.internal.play_billing.zzjj r0 = r6.zzl
            r0.zza(r7)
            boolean r0 = r6.zzh
            if (r0 == 0) goto L8f
            com.google.android.gms.internal.play_billing.zzgx r0 = r6.zzm
            r0.zza(r7)
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzip.zzf(java.lang.Object):void");
    }

    @Override // com.google.android.gms.internal.play_billing.zzix
    public final void zzg(Object obj, Object obj2) {
        zzA(obj);
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzs = zzs(i);
            int i2 = 1048575 & iZzs;
            int[] iArr = this.zzc;
            int iZzr = zzr(iZzs);
            int i3 = iArr[i];
            long j = i2;
            switch (iZzr) {
                case 0:
                    if (zzI(obj2, i)) {
                        zzjq.zzo(obj, j, zzjq.zza(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzI(obj2, i)) {
                        zzjq.zzp(obj, j, zzjq.zzb(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzI(obj2, i)) {
                        zzjq.zzr(obj, j, zzjq.zzd(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzI(obj2, i)) {
                        zzjq.zzr(obj, j, zzjq.zzd(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzI(obj2, i)) {
                        zzjq.zzq(obj, j, zzjq.zzc(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzI(obj2, i)) {
                        zzjq.zzr(obj, j, zzjq.zzd(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzI(obj2, i)) {
                        zzjq.zzq(obj, j, zzjq.zzc(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzI(obj2, i)) {
                        zzjq.zzm(obj, j, zzjq.zzw(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzI(obj2, i)) {
                        zzjq.zzs(obj, j, zzjq.zzf(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzB(obj, obj2, i);
                    break;
                case 10:
                    if (zzI(obj2, i)) {
                        zzjq.zzs(obj, j, zzjq.zzf(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzI(obj2, i)) {
                        zzjq.zzq(obj, j, zzjq.zzc(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzI(obj2, i)) {
                        zzjq.zzq(obj, j, zzjq.zzc(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzI(obj2, i)) {
                        zzjq.zzq(obj, j, zzjq.zzc(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzI(obj2, i)) {
                        zzjq.zzr(obj, j, zzjq.zzd(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzI(obj2, i)) {
                        zzjq.zzq(obj, j, zzjq.zzc(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzI(obj2, i)) {
                        zzjq.zzr(obj, j, zzjq.zzd(obj2, j));
                        zzD(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzB(obj, obj2, i);
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
                    WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzjq.zzf(obj, j));
                    WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzjq.zzf(obj2, j));
                    throw null;
                case 50:
                    int i4 = zziz.$r8$clinit;
                    zzjq.zzs(obj, j, zzih.zza(zzjq.zzf(obj, j), zzjq.zzf(obj2, j)));
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
                    if (zzM(obj2, i3, i)) {
                        zzjq.zzs(obj, j, zzjq.zzf(obj2, j));
                        zzE(obj, i3, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzC(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzM(obj2, i3, i)) {
                        zzjq.zzs(obj, j, zzjq.zzf(obj2, j));
                        zzE(obj, i3, i);
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    zzC(obj, obj2, i);
                    break;
            }
        }
        zziz.zzp(this.zzl, obj, obj2);
        if (this.zzh) {
            zziz.zzo(this.zzm, obj, obj2);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzix
    public final void zzi(Object obj, zzjw zzjwVar) {
        int i;
        zzip zzipVar = this;
        if (zzipVar.zzh) {
            WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(obj);
            throw null;
        }
        int[] iArr = zzipVar.zzc;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 0;
        int i4 = 1048575;
        int i5 = 0;
        while (i3 < iArr.length) {
            int iZzs = zzipVar.zzs(i3);
            int[] iArr2 = zzipVar.zzc;
            int iZzr = zzr(iZzs);
            int i6 = iArr2[i3];
            if (iZzr <= 17) {
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
            long j = iZzs & i2;
            switch (iZzr) {
                case 0:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzf(i6, zzjq.zza(obj, j));
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 1:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzo(i6, zzjq.zzb(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 2:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzt(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 3:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzK(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 4:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzr(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 5:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzm(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 6:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzk(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 7:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzb(i6, zzjq.zzw(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 8:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzO(i6, unsafe.getObject(obj, j), zzjwVar);
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 9:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzv(i6, unsafe.getObject(obj, j), zzipVar.zzv(i3));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 10:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzd(i6, (zzgk) unsafe.getObject(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 11:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzI(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 12:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzi(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 13:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzx(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 14:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzz(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 15:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzB(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 16:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzD(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 17:
                    if (zzipVar.zzJ(obj, i3, i4, i5, i)) {
                        zzjwVar.zzq(i6, unsafe.getObject(obj, j), zzipVar.zzv(i3));
                    } else {
                        continue;
                    }
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 18:
                    zziz.zzr(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 19:
                    zziz.zzv(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 20:
                    zziz.zzx(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 21:
                    zziz.zzD(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 22:
                    zziz.zzw(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 23:
                    zziz.zzu(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 24:
                    zziz.zzt(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 25:
                    zziz.zzq(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 26:
                    int i9 = zzipVar.zzc[i3];
                    List list = (List) unsafe.getObject(obj, j);
                    int i10 = zziz.$r8$clinit;
                    if (list != null && !list.isEmpty()) {
                        zzjwVar.zzH(i9, list);
                        break;
                    }
                    break;
                case 27:
                    int i11 = zzipVar.zzc[i3];
                    List list2 = (List) unsafe.getObject(obj, j);
                    zzix zzixVarZzv = zzipVar.zzv(i3);
                    int i12 = zziz.$r8$clinit;
                    if (list2 != null && !list2.isEmpty()) {
                        for (int i13 = 0; i13 < list2.size(); i13++) {
                            ((zzgs) zzjwVar).zzv(i11, list2.get(i13), zzixVarZzv);
                        }
                        break;
                    }
                    break;
                case 28:
                    int i14 = zzipVar.zzc[i3];
                    List list3 = (List) unsafe.getObject(obj, j);
                    int i15 = zziz.$r8$clinit;
                    if (list3 != null && !list3.isEmpty()) {
                        zzjwVar.zze(i14, list3);
                        break;
                    }
                    break;
                case 29:
                    zziz.zzC(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 30:
                    zziz.zzs(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 31:
                    zziz.zzy(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 32:
                    zziz.zzz(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 33:
                    zziz.zzA(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case 34:
                    zziz.zzB(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, false);
                    continue;
                    i3 += 3;
                    i2 = 1048575;
                    zzipVar = this;
                case Operator.PROJECTION /* 35 */:
                    zziz.zzr(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.CONVERTABLE_TO /* 36 */:
                    zziz.zzv(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.END_OF_STMT /* 37 */:
                    zziz.zzx(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.FOREACH /* 38 */:
                    zziz.zzD(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.f1408IF /* 39 */:
                    zziz.zzw(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.ELSE /* 40 */:
                    zziz.zzu(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.WHILE /* 41 */:
                    zziz.zzt(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.UNTIL /* 42 */:
                    zziz.zzq(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.FOR /* 43 */:
                    zziz.zzC(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.SWITCH /* 44 */:
                    zziz.zzs(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case Operator.f1407DO /* 45 */:
                    zziz.zzy(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case 46:
                    zziz.zzz(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case 47:
                    zziz.zzA(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case 48:
                    zziz.zzB(zzipVar.zzc[i3], (List) unsafe.getObject(obj, j), zzjwVar, true);
                    break;
                case 49:
                    int i16 = zzipVar.zzc[i3];
                    List list4 = (List) unsafe.getObject(obj, j);
                    zzix zzixVarZzv2 = zzipVar.zzv(i3);
                    int i17 = zziz.$r8$clinit;
                    if (list4 != null && !list4.isEmpty()) {
                        for (int i18 = 0; i18 < list4.size(); i18++) {
                            ((zzgs) zzjwVar).zzq(i16, list4.get(i18), zzixVarZzv2);
                        }
                        break;
                    }
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzipVar.zzw(i3));
                        throw null;
                    }
                    break;
                case 51:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzf(i6, zzm(obj, j));
                        break;
                    }
                    break;
                case 52:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzo(i6, zzn(obj, j));
                        break;
                    }
                    break;
                case 53:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzt(i6, zzt(obj, j));
                        break;
                    }
                    break;
                case 54:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzK(i6, zzt(obj, j));
                        break;
                    }
                    break;
                case 55:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzr(i6, zzo(obj, j));
                        break;
                    }
                    break;
                case 56:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzm(i6, zzt(obj, j));
                        break;
                    }
                    break;
                case 57:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzk(i6, zzo(obj, j));
                        break;
                    }
                    break;
                case 58:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzb(i6, zzN(obj, j));
                        break;
                    }
                    break;
                case 59:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzO(i6, unsafe.getObject(obj, j), zzjwVar);
                        break;
                    }
                    break;
                case 60:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzv(i6, unsafe.getObject(obj, j), zzipVar.zzv(i3));
                        break;
                    }
                    break;
                case 61:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzd(i6, (zzgk) unsafe.getObject(obj, j));
                        break;
                    }
                    break;
                case 62:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzI(i6, zzo(obj, j));
                        break;
                    }
                    break;
                case 63:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzi(i6, zzo(obj, j));
                        break;
                    }
                    break;
                case 64:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzx(i6, zzo(obj, j));
                        break;
                    }
                    break;
                case 65:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzz(i6, zzt(obj, j));
                        break;
                    }
                    break;
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzB(i6, zzo(obj, j));
                        break;
                    }
                    break;
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzD(i6, zzt(obj, j));
                        break;
                    }
                    break;
                case TypeReference.NEW /* 68 */:
                    if (zzipVar.zzM(obj, i6, i3)) {
                        zzjwVar.zzq(i6, unsafe.getObject(obj, j), zzipVar.zzv(i3));
                        break;
                    }
                    break;
            }
            i3 += 3;
            i2 = 1048575;
            zzipVar = this;
        }
        ((zzhk) obj).zzc.zzl(zzjwVar);
    }

    @Override // com.google.android.gms.internal.play_billing.zzix
    public final boolean zzj(Object obj, Object obj2) {
        boolean zZzE;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzs = zzs(i);
            long j = iZzs & 1048575;
            switch (zzr(iZzs)) {
                case 0:
                    if (!zzH(obj, obj2, i) || Double.doubleToLongBits(zzjq.zza(obj, j)) != Double.doubleToLongBits(zzjq.zza(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 1:
                    if (!zzH(obj, obj2, i) || Float.floatToIntBits(zzjq.zzb(obj, j)) != Float.floatToIntBits(zzjq.zzb(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 2:
                    if (!zzH(obj, obj2, i) || zzjq.zzd(obj, j) != zzjq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 3:
                    if (!zzH(obj, obj2, i) || zzjq.zzd(obj, j) != zzjq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 4:
                    if (!zzH(obj, obj2, i) || zzjq.zzc(obj, j) != zzjq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 5:
                    if (!zzH(obj, obj2, i) || zzjq.zzd(obj, j) != zzjq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 6:
                    if (!zzH(obj, obj2, i) || zzjq.zzc(obj, j) != zzjq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 7:
                    if (!zzH(obj, obj2, i) || zzjq.zzw(obj, j) != zzjq.zzw(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 8:
                    if (!zzH(obj, obj2, i) || !zziz.zzE(zzjq.zzf(obj, j), zzjq.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 9:
                    if (!zzH(obj, obj2, i) || !zziz.zzE(zzjq.zzf(obj, j), zzjq.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 10:
                    if (!zzH(obj, obj2, i) || !zziz.zzE(zzjq.zzf(obj, j), zzjq.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                case 11:
                    if (!zzH(obj, obj2, i) || zzjq.zzc(obj, j) != zzjq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 12:
                    if (!zzH(obj, obj2, i) || zzjq.zzc(obj, j) != zzjq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 13:
                    if (!zzH(obj, obj2, i) || zzjq.zzc(obj, j) != zzjq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 14:
                    if (!zzH(obj, obj2, i) || zzjq.zzd(obj, j) != zzjq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 15:
                    if (!zzH(obj, obj2, i) || zzjq.zzc(obj, j) != zzjq.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 16:
                    if (!zzH(obj, obj2, i) || zzjq.zzd(obj, j) != zzjq.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                case 17:
                    if (!zzH(obj, obj2, i) || !zziz.zzE(zzjq.zzf(obj, j), zzjq.zzf(obj2, j))) {
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
                    zZzE = zziz.zzE(zzjq.zzf(obj, j), zzjq.zzf(obj2, j));
                    break;
                case 50:
                    zZzE = zziz.zzE(zzjq.zzf(obj, j), zzjq.zzf(obj2, j));
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
                    long jZzp = zzp(i) & 1048575;
                    if (zzjq.zzc(obj, jZzp) != zzjq.zzc(obj2, jZzp) || !zziz.zzE(zzjq.zzf(obj, j), zzjq.zzf(obj2, j))) {
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
        if (!((zzhk) obj).zzc.equals(((zzhk) obj2).zzc)) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(obj);
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    @Override // com.google.android.gms.internal.play_billing.zzix
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzk(java.lang.Object r16) {
        /*
            Method dump skipped, instructions count: 208
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzip.zzk(java.lang.Object):boolean");
    }
}
