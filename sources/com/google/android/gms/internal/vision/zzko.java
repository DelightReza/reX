package com.google.android.gms.internal.vision;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mvel2.Operator;
import org.mvel2.asm.TypeReference;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class zzko implements zzlc {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzma.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzkk zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzks zzo;
    private final zzju zzp;
    private final zzlu zzq;
    private final zziq zzr;
    private final zzkh zzs;

    private zzko(int[] iArr, Object[] objArr, int i, int i2, zzkk zzkkVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzks zzksVar, zzju zzjuVar, zzlu zzluVar, zziq zziqVar, zzkh zzkhVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzkkVar instanceof zzjb;
        this.zzj = z;
        this.zzh = zziqVar != null && zziqVar.zza(zzkkVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzksVar;
        this.zzp = zzjuVar;
        this.zzq = zzluVar;
        this.zzr = zziqVar;
        this.zzg = zzkkVar;
        this.zzs = zzkhVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0384  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.gms.internal.vision.zzko zza(java.lang.Class r33, com.google.android.gms.internal.vision.zzki r34, com.google.android.gms.internal.vision.zzks r35, com.google.android.gms.internal.vision.zzju r36, com.google.android.gms.internal.vision.zzlu r37, com.google.android.gms.internal.vision.zziq r38, com.google.android.gms.internal.vision.zzkh r39) {
        /*
            Method dump skipped, instructions count: 1013
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Class, com.google.android.gms.internal.vision.zzki, com.google.android.gms.internal.vision.zzks, com.google.android.gms.internal.vision.zzju, com.google.android.gms.internal.vision.zzlu, com.google.android.gms.internal.vision.zziq, com.google.android.gms.internal.vision.zzkh):com.google.android.gms.internal.vision.zzko");
    }

    private static Field zza(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + name.length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final Object zza() {
        return this.zzo.zza(this.zzg);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    @Override // com.google.android.gms.internal.vision.zzlc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zza(java.lang.Object r10, java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 642
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final int zza(Object obj) {
        int i;
        int iZza;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzd = zzd(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzd;
            int iHashCode = 37;
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZza = zzjf.zza(Double.doubleToLongBits(zzma.zze(obj, j)));
                    i2 = i + iZza;
                    break;
                case 1:
                    i = i2 * 53;
                    iZza = Float.floatToIntBits(zzma.zzd(obj, j));
                    i2 = i + iZza;
                    break;
                case 2:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(obj, j));
                    i2 = i + iZza;
                    break;
                case 3:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(obj, j));
                    i2 = i + iZza;
                    break;
                case 4:
                    i = i2 * 53;
                    iZza = zzma.zza(obj, j);
                    i2 = i + iZza;
                    break;
                case 5:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(obj, j));
                    i2 = i + iZza;
                    break;
                case 6:
                    i = i2 * 53;
                    iZza = zzma.zza(obj, j);
                    i2 = i + iZza;
                    break;
                case 7:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzc(obj, j));
                    i2 = i + iZza;
                    break;
                case 8:
                    i = i2 * 53;
                    iZza = ((String) zzma.zzf(obj, j)).hashCode();
                    i2 = i + iZza;
                    break;
                case 9:
                    Object objZzf = zzma.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZza = zzma.zzf(obj, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 11:
                    i = i2 * 53;
                    iZza = zzma.zza(obj, j);
                    i2 = i + iZza;
                    break;
                case 12:
                    i = i2 * 53;
                    iZza = zzma.zza(obj, j);
                    i2 = i + iZza;
                    break;
                case 13:
                    i = i2 * 53;
                    iZza = zzma.zza(obj, j);
                    i2 = i + iZza;
                    break;
                case 14:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(obj, j));
                    i2 = i + iZza;
                    break;
                case 15:
                    i = i2 * 53;
                    iZza = zzma.zza(obj, j);
                    i2 = i + iZza;
                    break;
                case 16:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(obj, j));
                    i2 = i + iZza;
                    break;
                case 17:
                    Object objZzf2 = zzma.zzf(obj, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
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
                    i = i2 * 53;
                    iZza = zzma.zzf(obj, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 50:
                    i = i2 * 53;
                    iZza = zzma.zzf(obj, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 51:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(Double.doubleToLongBits(zzb(obj, j)));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = Float.floatToIntBits(zzc(obj, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(obj, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(obj, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(obj, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(obj, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(obj, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zzf(obj, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = ((String) zzma.zzf(obj, j)).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzma.zzf(obj, j).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzma.zzf(obj, j).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(obj, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(obj, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(obj, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(obj, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(obj, j);
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(obj, j));
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    if (zza(obj, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzma.zzf(obj, j).hashCode();
                        i2 = i + iZza;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzq.zzb(obj).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzr.zza(obj).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final void zzb(Object obj, Object obj2) {
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzd = zzd(i);
            long j = 1048575 & iZzd;
            int i2 = this.zzc[i];
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zze(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzd(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzb(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzb(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zza(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzb(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zza(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzc(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzf(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(obj, obj2, i);
                    break;
                case 10:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzf(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zza(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zza(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zza(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzb(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zza(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zza(obj2, i)) {
                        zzma.zza(obj, j, zzma.zzb(obj2, j));
                        zzb(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(obj, obj2, i);
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
                    this.zzp.zza(obj, obj2, j);
                    break;
                case 50:
                    zzle.zza(this.zzs, obj, obj2, j);
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
                    if (zza(obj2, i2, i)) {
                        zzma.zza(obj, j, zzma.zzf(obj2, j));
                        zzb(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zza(obj2, i2, i)) {
                        zzma.zza(obj, j, zzma.zzf(obj2, j));
                        zzb(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case TypeReference.NEW /* 68 */:
                    zzb(obj, obj2, i);
                    break;
            }
        }
        zzle.zza(this.zzq, obj, obj2);
        if (this.zzh) {
            zzle.zza(this.zzr, obj, obj2);
        }
    }

    private final void zza(Object obj, Object obj2, int i) {
        long jZzd = zzd(i) & 1048575;
        if (zza(obj2, i)) {
            Object objZzf = zzma.zzf(obj, jZzd);
            Object objZzf2 = zzma.zzf(obj2, jZzd);
            if (objZzf != null && objZzf2 != null) {
                zzma.zza(obj, jZzd, zzjf.zza(objZzf, objZzf2));
                zzb(obj, i);
            } else if (objZzf2 != null) {
                zzma.zza(obj, jZzd, objZzf2);
                zzb(obj, i);
            }
        }
    }

    private final void zzb(Object obj, Object obj2, int i) {
        int iZzd = zzd(i);
        int i2 = this.zzc[i];
        long j = iZzd & 1048575;
        if (zza(obj2, i2, i)) {
            Object objZzf = zza(obj, i2, i) ? zzma.zzf(obj, j) : null;
            Object objZzf2 = zzma.zzf(obj2, j);
            if (objZzf != null && objZzf2 != null) {
                zzma.zza(obj, j, zzjf.zza(objZzf, objZzf2));
                zzb(obj, i2, i);
            } else if (objZzf2 != null) {
                zzma.zza(obj, j, objZzf2);
                zzb(obj, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.vision.zzlc
    public final int zzb(Object obj) {
        int i;
        int iZzd;
        int iZzb;
        int iZzj;
        int iZzi;
        int iZze;
        int iZzg;
        int iZzb2;
        int iZzi2;
        int iZze2;
        int iZzg2;
        int i2 = 267386880;
        if (this.zzj) {
            Unsafe unsafe = zzb;
            int i3 = 0;
            int i4 = 0;
            while (i3 < this.zzc.length) {
                int iZzd2 = zzd(i3);
                int i5 = (iZzd2 & i2) >>> 20;
                int i6 = this.zzc[i3];
                long j = iZzd2 & 1048575;
                if (i5 >= zziv.zza.zza() && i5 <= zziv.zzb.zza()) {
                    int i7 = this.zzc[i3 + 2];
                }
                switch (i5) {
                    case 0:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzb(i6, 0.0d);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzb(i6, 0.0f);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzd(i6, zzma.zzb(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zze(i6, zzma.zzb(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzf(i6, zzma.zza(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzg(i6, 0L);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzi(i6, 0);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzb(i6, true);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (zza(obj, i3)) {
                            Object objZzf = zzma.zzf(obj, j);
                            if (objZzf instanceof zzht) {
                                iZzb2 = zzii.zzc(i6, (zzht) objZzf);
                            } else {
                                iZzb2 = zzii.zzb(i6, (String) objZzf);
                            }
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (zza(obj, i3)) {
                            iZzb2 = zzle.zza(i6, zzma.zzf(obj, j), zza(i3));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzc(i6, (zzht) zzma.zzf(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzg(i6, zzma.zza(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzk(i6, zzma.zza(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzj(i6, 0);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzh(i6, 0L);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzh(i6, zzma.zza(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzf(i6, zzma.zzb(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (zza(obj, i3)) {
                            iZzb2 = zzii.zzc(i6, (zzkk) zzma.zzf(obj, j), zza(i3));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        iZzb2 = zzle.zzi(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 19:
                        iZzb2 = zzle.zzh(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 20:
                        iZzb2 = zzle.zza(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 21:
                        iZzb2 = zzle.zzb(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 22:
                        iZzb2 = zzle.zze(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 23:
                        iZzb2 = zzle.zzi(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 24:
                        iZzb2 = zzle.zzh(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 25:
                        iZzb2 = zzle.zzj(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 26:
                        iZzb2 = zzle.zza(i6, zza(obj, j));
                        i4 += iZzb2;
                        break;
                    case 27:
                        iZzb2 = zzle.zza(i6, zza(obj, j), zza(i3));
                        i4 += iZzb2;
                        break;
                    case 28:
                        iZzb2 = zzle.zzb(i6, zza(obj, j));
                        i4 += iZzb2;
                        break;
                    case 29:
                        iZzb2 = zzle.zzf(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 30:
                        iZzb2 = zzle.zzd(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 31:
                        iZzb2 = zzle.zzh(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 32:
                        iZzb2 = zzle.zzi(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 33:
                        iZzb2 = zzle.zzg(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case 34:
                        iZzb2 = zzle.zzc(i6, zza(obj, j), false);
                        i4 += iZzb2;
                        break;
                    case Operator.PROJECTION /* 35 */:
                        iZzi2 = zzle.zzi((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.CONVERTABLE_TO /* 36 */:
                        iZzi2 = zzle.zzh((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.END_OF_STMT /* 37 */:
                        iZzi2 = zzle.zza((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.FOREACH /* 38 */:
                        iZzi2 = zzle.zzb((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.f1408IF /* 39 */:
                        iZzi2 = zzle.zze((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.ELSE /* 40 */:
                        iZzi2 = zzle.zzi((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.WHILE /* 41 */:
                        iZzi2 = zzle.zzh((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.UNTIL /* 42 */:
                        iZzi2 = zzle.zzj((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.FOR /* 43 */:
                        iZzi2 = zzle.zzf((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.SWITCH /* 44 */:
                        iZzi2 = zzle.zzd((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case Operator.f1407DO /* 45 */:
                        iZzi2 = zzle.zzh((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 46:
                        iZzi2 = zzle.zzi((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 47:
                        iZzi2 = zzle.zzg((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 48:
                        iZzi2 = zzle.zzc((List) unsafe.getObject(obj, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i6);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 49:
                        iZzb2 = zzle.zzb(i6, zza(obj, j), zza(i3));
                        i4 += iZzb2;
                        break;
                    case 50:
                        iZzb2 = this.zzs.zza(i6, zzma.zzf(obj, j), zzb(i3));
                        i4 += iZzb2;
                        break;
                    case 51:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzb(i6, 0.0d);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzb(i6, 0.0f);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzd(i6, zze(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zze(i6, zze(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzf(i6, zzd(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzg(i6, 0L);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzi(i6, 0);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzb(i6, true);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (zza(obj, i6, i3)) {
                            Object objZzf2 = zzma.zzf(obj, j);
                            if (objZzf2 instanceof zzht) {
                                iZzb2 = zzii.zzc(i6, (zzht) objZzf2);
                            } else {
                                iZzb2 = zzii.zzb(i6, (String) objZzf2);
                            }
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzle.zza(i6, zzma.zzf(obj, j), zza(i3));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzc(i6, (zzht) zzma.zzf(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzg(i6, zzd(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzk(i6, zzd(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzj(i6, 0);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzh(i6, 0L);
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzh(i6, zzd(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case TypeReference.INSTANCEOF /* 67 */:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzf(i6, zze(obj, j));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                    case TypeReference.NEW /* 68 */:
                        if (zza(obj, i6, i3)) {
                            iZzb2 = zzii.zzc(i6, (zzkk) zzma.zzf(obj, j), zza(i3));
                            i4 += iZzb2;
                            break;
                        } else {
                            break;
                        }
                }
                i3 += 3;
                i2 = 267386880;
            }
            return i4 + zza(this.zzq, obj);
        }
        Unsafe unsafe2 = zzb;
        int iZzb3 = 0;
        int i8 = 1048575;
        int i9 = 0;
        for (int i10 = 0; i10 < this.zzc.length; i10 += 3) {
            int iZzd3 = zzd(i10);
            int[] iArr = this.zzc;
            int i11 = iArr[i10];
            int i12 = (iZzd3 & 267386880) >>> 20;
            if (i12 <= 17) {
                int i13 = iArr[i10 + 2];
                int i14 = i13 & 1048575;
                i = 1 << (i13 >>> 20);
                if (i14 != i8) {
                    i9 = unsafe2.getInt(obj, i14);
                    i8 = i14;
                }
            } else {
                i = 0;
            }
            long j2 = iZzd3 & 1048575;
            switch (i12) {
                case 0:
                    if ((i & i9) != 0) {
                        iZzb3 += zzii.zzb(i11, 0.0d);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if ((i & i9) != 0) {
                        iZzb3 += zzii.zzb(i11, 0.0f);
                    }
                    break;
                case 2:
                    if ((i & i9) != 0) {
                        iZzd = zzii.zzd(i11, unsafe2.getLong(obj, j2));
                        iZzb3 += iZzd;
                    }
                    break;
                case 3:
                    if ((i & i9) != 0) {
                        iZzd = zzii.zze(i11, unsafe2.getLong(obj, j2));
                        iZzb3 += iZzd;
                    }
                    break;
                case 4:
                    if ((i & i9) != 0) {
                        iZzd = zzii.zzf(i11, unsafe2.getInt(obj, j2));
                        iZzb3 += iZzd;
                    }
                    break;
                case 5:
                    if ((i & i9) != 0) {
                        iZzd = zzii.zzg(i11, 0L);
                        iZzb3 += iZzd;
                    }
                    break;
                case 6:
                    if ((i & i9) != 0) {
                        iZzd = zzii.zzi(i11, 0);
                        iZzb3 += iZzd;
                        break;
                    }
                case 7:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzb(i11, true);
                        iZzb3 += iZzb;
                    }
                    break;
                case 8:
                    if ((i & i9) != 0) {
                        Object object = unsafe2.getObject(obj, j2);
                        if (object instanceof zzht) {
                            iZzb = zzii.zzc(i11, (zzht) object);
                        } else {
                            iZzb = zzii.zzb(i11, (String) object);
                        }
                        iZzb3 += iZzb;
                    }
                    break;
                case 9:
                    if ((i & i9) != 0) {
                        iZzb = zzle.zza(i11, unsafe2.getObject(obj, j2), zza(i10));
                        iZzb3 += iZzb;
                    }
                    break;
                case 10:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzc(i11, (zzht) unsafe2.getObject(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 11:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzg(i11, unsafe2.getInt(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 12:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzk(i11, unsafe2.getInt(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 13:
                    if ((i & i9) != 0) {
                        iZzj = zzii.zzj(i11, 0);
                        iZzb3 += iZzj;
                    }
                    break;
                case 14:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzh(i11, 0L);
                        iZzb3 += iZzb;
                    }
                    break;
                case 15:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzh(i11, unsafe2.getInt(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 16:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzf(i11, unsafe2.getLong(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 17:
                    if ((i & i9) != 0) {
                        iZzb = zzii.zzc(i11, (zzkk) unsafe2.getObject(obj, j2), zza(i10));
                        iZzb3 += iZzb;
                    }
                    break;
                case 18:
                    iZzb = zzle.zzi(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzb;
                    break;
                case 19:
                    iZzd = zzle.zzh(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 20:
                    iZzd = zzle.zza(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 21:
                    iZzd = zzle.zzb(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 22:
                    iZzd = zzle.zze(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 23:
                    iZzd = zzle.zzi(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 24:
                    iZzd = zzle.zzh(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 25:
                    iZzd = zzle.zzj(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 26:
                    iZzb = zzle.zza(i11, (List) unsafe2.getObject(obj, j2));
                    iZzb3 += iZzb;
                    break;
                case 27:
                    iZzb = zzle.zza(i11, (List) unsafe2.getObject(obj, j2), zza(i10));
                    iZzb3 += iZzb;
                    break;
                case 28:
                    iZzb = zzle.zzb(i11, (List) unsafe2.getObject(obj, j2));
                    iZzb3 += iZzb;
                    break;
                case 29:
                    iZzb = zzle.zzf(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzb;
                    break;
                case 30:
                    iZzd = zzle.zzd(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 31:
                    iZzd = zzle.zzh(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 32:
                    iZzd = zzle.zzi(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 33:
                    iZzd = zzle.zzg(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case 34:
                    iZzd = zzle.zzc(i11, (List) unsafe2.getObject(obj, j2), false);
                    iZzb3 += iZzd;
                    break;
                case Operator.PROJECTION /* 35 */:
                    iZzi = zzle.zzi((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.CONVERTABLE_TO /* 36 */:
                    iZzi = zzle.zzh((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.END_OF_STMT /* 37 */:
                    iZzi = zzle.zza((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.FOREACH /* 38 */:
                    iZzi = zzle.zzb((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.f1408IF /* 39 */:
                    iZzi = zzle.zze((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.ELSE /* 40 */:
                    iZzi = zzle.zzi((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.WHILE /* 41 */:
                    iZzi = zzle.zzh((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.UNTIL /* 42 */:
                    iZzi = zzle.zzj((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.FOR /* 43 */:
                    iZzi = zzle.zzf((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.SWITCH /* 44 */:
                    iZzi = zzle.zzd((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case Operator.f1407DO /* 45 */:
                    iZzi = zzle.zzh((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case 46:
                    iZzi = zzle.zzi((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case 47:
                    iZzi = zzle.zzg((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case 48:
                    iZzi = zzle.zzc((List) unsafe2.getObject(obj, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i11);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    break;
                case 49:
                    iZzb = zzle.zzb(i11, (List) unsafe2.getObject(obj, j2), zza(i10));
                    iZzb3 += iZzb;
                    break;
                case 50:
                    iZzb = this.zzs.zza(i11, unsafe2.getObject(obj, j2), zzb(i10));
                    iZzb3 += iZzb;
                    break;
                case 51:
                    if (zza(obj, i11, i10)) {
                        iZzb3 += zzii.zzb(i11, 0.0d);
                    }
                    break;
                case 52:
                    if (zza(obj, i11, i10)) {
                        iZzj = zzii.zzb(i11, 0.0f);
                        iZzb3 += iZzj;
                    }
                    break;
                case 53:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzd(i11, zze(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 54:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zze(i11, zze(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 55:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzf(i11, zzd(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 56:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzg(i11, 0L);
                        iZzb3 += iZzb;
                    }
                    break;
                case 57:
                    if (zza(obj, i11, i10)) {
                        iZzj = zzii.zzi(i11, 0);
                        iZzb3 += iZzj;
                    }
                    break;
                case 58:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzb(i11, true);
                        iZzb3 += iZzb;
                    }
                    break;
                case 59:
                    if (zza(obj, i11, i10)) {
                        Object object2 = unsafe2.getObject(obj, j2);
                        if (object2 instanceof zzht) {
                            iZzb = zzii.zzc(i11, (zzht) object2);
                        } else {
                            iZzb = zzii.zzb(i11, (String) object2);
                        }
                        iZzb3 += iZzb;
                    }
                    break;
                case 60:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzle.zza(i11, unsafe2.getObject(obj, j2), zza(i10));
                        iZzb3 += iZzb;
                    }
                    break;
                case 61:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzc(i11, (zzht) unsafe2.getObject(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 62:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzg(i11, zzd(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 63:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzk(i11, zzd(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case 64:
                    if (zza(obj, i11, i10)) {
                        iZzj = zzii.zzj(i11, 0);
                        iZzb3 += iZzj;
                    }
                    break;
                case 65:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzh(i11, 0L);
                        iZzb3 += iZzb;
                    }
                    break;
                case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzh(i11, zzd(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case TypeReference.INSTANCEOF /* 67 */:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzf(i11, zze(obj, j2));
                        iZzb3 += iZzb;
                    }
                    break;
                case TypeReference.NEW /* 68 */:
                    if (zza(obj, i11, i10)) {
                        iZzb = zzii.zzc(i11, (zzkk) unsafe2.getObject(obj, j2), zza(i10));
                        iZzb3 += iZzb;
                    }
                    break;
            }
        }
        int iZzc = 0;
        int iZza = iZzb3 + zza(this.zzq, obj);
        if (!this.zzh) {
            return iZza;
        }
        zziu zziuVarZza = this.zzr.zza(obj);
        for (int i15 = 0; i15 < zziuVarZza.zza.zzc(); i15++) {
            Map.Entry entryZzb = zziuVarZza.zza.zzb(i15);
            WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(entryZzb.getKey());
            iZzc += zziu.zzc(null, entryZzb.getValue());
        }
        for (Map.Entry entry : zziuVarZza.zza.zzd()) {
            WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(entry.getKey());
            iZzc += zziu.zzc(null, entry.getValue());
        }
        return iZza + iZzc;
    }

    private static int zza(zzlu zzluVar, Object obj) {
        return zzluVar.zzf(zzluVar.zzb(obj));
    }

    private static List zza(Object obj, long j) {
        return (List) zzma.zzf(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.vision.zzlc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(java.lang.Object r14, com.google.android.gms.internal.vision.zzmr r15) {
        /*
            Method dump skipped, instructions count: 2916
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, com.google.android.gms.internal.vision.zzmr):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzb(java.lang.Object r18, com.google.android.gms.internal.vision.zzmr r19) {
        /*
            Method dump skipped, instructions count: 1342
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zzb(java.lang.Object, com.google.android.gms.internal.vision.zzmr):void");
    }

    private final void zza(zzmr zzmrVar, int i, Object obj, int i2) {
        if (obj != null) {
            this.zzs.zzb(zzb(i2));
            zzmrVar.zza(i, (zzkf) null, this.zzs.zzc(obj));
        }
    }

    private static void zza(zzlu zzluVar, Object obj, zzmr zzmrVar) {
        zzluVar.zza(zzluVar.zzb(obj), zzmrVar);
    }

    private static zzlx zze(Object obj) {
        zzjb zzjbVar = (zzjb) obj;
        zzlx zzlxVar = zzjbVar.zzb;
        if (zzlxVar != zzlx.zza()) {
            return zzlxVar;
        }
        zzlx zzlxVarZzb = zzlx.zzb();
        zzjbVar.zzb = zzlxVarZzb;
        return zzlxVarZzb;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final int zza(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzhn zzhnVar) throws zzjk {
        int iZza;
        Unsafe unsafe = zzb;
        zzjl zzjlVarZza = (zzjl) unsafe.getObject(obj, j2);
        if (!zzjlVarZza.zza()) {
            int size = zzjlVarZza.size();
            zzjlVarZza = zzjlVarZza.zza(size == 0 ? 10 : size << 1);
            unsafe.putObject(obj, j2, zzjlVarZza);
        }
        zzjl zzjlVar = zzjlVarZza;
        switch (i7) {
            case 18:
            case Operator.PROJECTION /* 35 */:
                if (i5 != 2) {
                    if (i5 == 1) {
                        zzhl.zzc(bArr, i);
                        throw null;
                    }
                    return i;
                }
                int iZza2 = zzhl.zza(bArr, i, zzhnVar);
                int i8 = zzhnVar.zza + iZza2;
                if (iZza2 < i8) {
                    zzhl.zzc(bArr, iZza2);
                    throw null;
                }
                if (iZza2 == i8) {
                    return iZza2;
                }
                throw zzjk.zza();
            case 19:
            case Operator.CONVERTABLE_TO /* 36 */:
                if (i5 != 2) {
                    if (i5 == 5) {
                        zzhl.zzd(bArr, i);
                        throw null;
                    }
                    return i;
                }
                int iZza3 = zzhl.zza(bArr, i, zzhnVar);
                int i9 = zzhnVar.zza + iZza3;
                if (iZza3 < i9) {
                    zzhl.zzd(bArr, iZza3);
                    throw null;
                }
                if (iZza3 == i9) {
                    return iZza3;
                }
                throw zzjk.zza();
            case 20:
            case 21:
            case Operator.END_OF_STMT /* 37 */:
            case Operator.FOREACH /* 38 */:
                if (i5 != 2) {
                    if (i5 == 0) {
                        zzhl.zzb(bArr, i, zzhnVar);
                        long j3 = zzhnVar.zzb;
                        throw null;
                    }
                    return i;
                }
                int iZza4 = zzhl.zza(bArr, i, zzhnVar);
                int i10 = zzhnVar.zza + iZza4;
                if (iZza4 < i10) {
                    zzhl.zzb(bArr, iZza4, zzhnVar);
                    throw null;
                }
                if (iZza4 == i10) {
                    return iZza4;
                }
                throw zzjk.zza();
            case 22:
            case 29:
            case Operator.f1408IF /* 39 */:
            case Operator.FOR /* 43 */:
                if (i5 == 2) {
                    return zzhl.zza(bArr, i, zzjlVar, zzhnVar);
                }
                if (i5 == 0) {
                    return zzhl.zza(i3, bArr, i, i2, zzjlVar, zzhnVar);
                }
                return i;
            case 23:
            case 32:
            case Operator.ELSE /* 40 */:
            case 46:
                if (i5 != 2) {
                    if (i5 == 1) {
                        zzhl.zzb(bArr, i);
                        throw null;
                    }
                    return i;
                }
                int iZza5 = zzhl.zza(bArr, i, zzhnVar);
                int i11 = zzhnVar.zza + iZza5;
                if (iZza5 < i11) {
                    zzhl.zzb(bArr, iZza5);
                    throw null;
                }
                if (iZza5 == i11) {
                    return iZza5;
                }
                throw zzjk.zza();
            case 24:
            case 31:
            case Operator.WHILE /* 41 */:
            case Operator.f1407DO /* 45 */:
                if (i5 == 2) {
                    zzjd zzjdVar = (zzjd) zzjlVar;
                    int iZza6 = zzhl.zza(bArr, i, zzhnVar);
                    int i12 = zzhnVar.zza + iZza6;
                    while (iZza6 < i12) {
                        zzjdVar.zzc(zzhl.zza(bArr, iZza6));
                        iZza6 += 4;
                    }
                    if (iZza6 == i12) {
                        return iZza6;
                    }
                    throw zzjk.zza();
                }
                if (i5 == 5) {
                    zzjd zzjdVar2 = (zzjd) zzjlVar;
                    zzjdVar2.zzc(zzhl.zza(bArr, i));
                    int i13 = i + 4;
                    while (i13 < i2) {
                        int iZza7 = zzhl.zza(bArr, i13, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return i13;
                        }
                        zzjdVar2.zzc(zzhl.zza(bArr, iZza7));
                        i13 = iZza7 + 4;
                    }
                    return i13;
                }
                return i;
            case 25:
            case Operator.UNTIL /* 42 */:
                if (i5 != 2) {
                    if (i5 == 0) {
                        zzhl.zzb(bArr, i, zzhnVar);
                        long j4 = zzhnVar.zzb;
                        throw null;
                    }
                    return i;
                }
                int iZza8 = zzhl.zza(bArr, i, zzhnVar);
                int i14 = zzhnVar.zza + iZza8;
                if (iZza8 < i14) {
                    zzhl.zzb(bArr, iZza8, zzhnVar);
                    throw null;
                }
                if (iZza8 == i14) {
                    return iZza8;
                }
                throw zzjk.zza();
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iZza9 = zzhl.zza(bArr, i, zzhnVar);
                        int i15 = zzhnVar.zza;
                        if (i15 < 0) {
                            throw zzjk.zzb();
                        }
                        if (i15 == 0) {
                            zzjlVar.add("");
                        } else {
                            zzjlVar.add(new String(bArr, iZza9, i15, zzjf.zza));
                            iZza9 += i15;
                        }
                        while (iZza9 < i2) {
                            int iZza10 = zzhl.zza(bArr, iZza9, zzhnVar);
                            if (i3 != zzhnVar.zza) {
                                return iZza9;
                            }
                            iZza9 = zzhl.zza(bArr, iZza10, zzhnVar);
                            int i16 = zzhnVar.zza;
                            if (i16 < 0) {
                                throw zzjk.zzb();
                            }
                            if (i16 == 0) {
                                zzjlVar.add("");
                            } else {
                                zzjlVar.add(new String(bArr, iZza9, i16, zzjf.zza));
                                iZza9 += i16;
                            }
                        }
                        return iZza9;
                    }
                    int iZza11 = zzhl.zza(bArr, i, zzhnVar);
                    int i17 = zzhnVar.zza;
                    if (i17 < 0) {
                        throw zzjk.zzb();
                    }
                    if (i17 == 0) {
                        zzjlVar.add("");
                    } else {
                        int i18 = iZza11 + i17;
                        if (!zzmd.zza(bArr, iZza11, i18)) {
                            throw zzjk.zzh();
                        }
                        zzjlVar.add(new String(bArr, iZza11, i17, zzjf.zza));
                        iZza11 = i18;
                    }
                    while (iZza11 < i2) {
                        int iZza12 = zzhl.zza(bArr, iZza11, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return iZza11;
                        }
                        iZza11 = zzhl.zza(bArr, iZza12, zzhnVar);
                        int i19 = zzhnVar.zza;
                        if (i19 < 0) {
                            throw zzjk.zzb();
                        }
                        if (i19 == 0) {
                            zzjlVar.add("");
                        } else {
                            int i20 = iZza11 + i19;
                            if (!zzmd.zza(bArr, iZza11, i20)) {
                                throw zzjk.zzh();
                            }
                            zzjlVar.add(new String(bArr, iZza11, i19, zzjf.zza));
                            iZza11 = i20;
                        }
                    }
                    return iZza11;
                }
                return i;
            case 27:
                if (i5 == 2) {
                    return zzhl.zza(zza(i6), i3, bArr, i, i2, zzjlVar, zzhnVar);
                }
                return i;
            case 28:
                if (i5 == 2) {
                    int iZza13 = zzhl.zza(bArr, i, zzhnVar);
                    int i21 = zzhnVar.zza;
                    if (i21 < 0) {
                        throw zzjk.zzb();
                    }
                    if (i21 > bArr.length - iZza13) {
                        throw zzjk.zza();
                    }
                    if (i21 == 0) {
                        zzjlVar.add(zzht.zza);
                    } else {
                        zzjlVar.add(zzht.zza(bArr, iZza13, i21));
                        iZza13 += i21;
                    }
                    while (iZza13 < i2) {
                        int iZza14 = zzhl.zza(bArr, iZza13, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return iZza13;
                        }
                        iZza13 = zzhl.zza(bArr, iZza14, zzhnVar);
                        int i22 = zzhnVar.zza;
                        if (i22 < 0) {
                            throw zzjk.zzb();
                        }
                        if (i22 > bArr.length - iZza13) {
                            throw zzjk.zza();
                        }
                        if (i22 == 0) {
                            zzjlVar.add(zzht.zza);
                        } else {
                            zzjlVar.add(zzht.zza(bArr, iZza13, i22));
                            iZza13 += i22;
                        }
                    }
                    return iZza13;
                }
                return i;
            case 30:
            case Operator.SWITCH /* 44 */:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZza = zzhl.zza(i3, bArr, i, i2, zzjlVar, zzhnVar);
                    }
                    return i;
                }
                iZza = zzhl.zza(bArr, i, zzjlVar, zzhnVar);
                zzjb zzjbVar = (zzjb) obj;
                zzlx zzlxVar = zzjbVar.zzb;
                zzlx zzlxVar2 = (zzlx) zzle.zza(i4, zzjlVar, zzc(i6), zzlxVar != zzlx.zza() ? zzlxVar : null, this.zzq);
                if (zzlxVar2 != null) {
                    zzjbVar.zzb = zzlxVar2;
                }
                return iZza;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzjd zzjdVar3 = (zzjd) zzjlVar;
                    int iZza15 = zzhl.zza(bArr, i, zzhnVar);
                    int i23 = zzhnVar.zza + iZza15;
                    while (iZza15 < i23) {
                        iZza15 = zzhl.zza(bArr, iZza15, zzhnVar);
                        zzjdVar3.zzc(zzif.zze(zzhnVar.zza));
                    }
                    if (iZza15 == i23) {
                        return iZza15;
                    }
                    throw zzjk.zza();
                }
                if (i5 == 0) {
                    zzjd zzjdVar4 = (zzjd) zzjlVar;
                    int iZza16 = zzhl.zza(bArr, i, zzhnVar);
                    zzjdVar4.zzc(zzif.zze(zzhnVar.zza));
                    while (iZza16 < i2) {
                        int iZza17 = zzhl.zza(bArr, iZza16, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return iZza16;
                        }
                        iZza16 = zzhl.zza(bArr, iZza17, zzhnVar);
                        zzjdVar4.zzc(zzif.zze(zzhnVar.zza));
                    }
                    return iZza16;
                }
                return i;
            case 34:
            case 48:
                if (i5 != 2) {
                    if (i5 == 0) {
                        zzhl.zzb(bArr, i, zzhnVar);
                        zzif.zza(zzhnVar.zzb);
                        throw null;
                    }
                    return i;
                }
                int iZza18 = zzhl.zza(bArr, i, zzhnVar);
                int i24 = zzhnVar.zza + iZza18;
                if (iZza18 >= i24) {
                    if (iZza18 == i24) {
                        return iZza18;
                    }
                    throw zzjk.zza();
                }
                zzhl.zzb(bArr, iZza18, zzhnVar);
                zzif.zza(zzhnVar.zzb);
                throw null;
            case 49:
                if (i5 == 3) {
                    zzlc zzlcVarZza = zza(i6);
                    int i25 = (i3 & (-8)) | 4;
                    int iZza19 = zzhl.zza(zzlcVarZza, bArr, i, i2, i25, zzhnVar);
                    int i26 = i25;
                    zzhn zzhnVar2 = zzhnVar;
                    zzjlVar.add(zzhnVar2.zzc);
                    while (iZza19 < i2) {
                        int iZza20 = zzhl.zza(bArr, iZza19, zzhnVar2);
                        if (i3 != zzhnVar2.zza) {
                            return iZza19;
                        }
                        int i27 = i26;
                        zzhn zzhnVar3 = zzhnVar2;
                        iZza19 = zzhl.zza(zzlcVarZza, bArr, iZza20, i2, i27, zzhnVar3);
                        zzjlVar.add(zzhnVar3.zzc);
                        i26 = i27;
                        zzhnVar2 = zzhnVar3;
                    }
                    return iZza19;
                }
                return i;
            default:
                return i;
        }
    }

    private final int zza(Object obj, byte[] bArr, int i, int i2, int i3, long j, zzhn zzhnVar) throws zzjk {
        Unsafe unsafe = zzb;
        Object objZzb = zzb(i3);
        Object object = unsafe.getObject(obj, j);
        if (this.zzs.zzd(object)) {
            Object objZzf = this.zzs.zzf(objZzb);
            this.zzs.zza(objZzf, object);
            unsafe.putObject(obj, j, objZzf);
            object = objZzf;
        }
        this.zzs.zzb(objZzb);
        this.zzs.zza(object);
        int iZza = zzhl.zza(bArr, i, zzhnVar);
        int i4 = zzhnVar.zza;
        if (i4 < 0 || i4 > i2 - iZza) {
            throw zzjk.zza();
        }
        throw null;
    }

    private final int zza(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzhn zzhnVar) throws zzjk {
        int i9;
        int i10;
        int iZzb;
        Object object;
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                unsafe.putObject(obj, j, Double.valueOf(zzhl.zzc(bArr, i)));
                iZzb = i9 + 8;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 52:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                unsafe.putObject(obj, j, Float.valueOf(zzhl.zzd(bArr, i)));
                iZzb = i10 + 4;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zzb(bArr, i, zzhnVar);
                unsafe.putObject(obj, j, Long.valueOf(zzhnVar.zzb));
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zza(bArr, i, zzhnVar);
                unsafe.putObject(obj, j, Integer.valueOf(zzhnVar.zza));
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 56:
            case 65:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                unsafe.putObject(obj, j, Long.valueOf(zzhl.zzb(bArr, i)));
                iZzb = i9 + 8;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 57:
            case 64:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                unsafe.putObject(obj, j, Integer.valueOf(zzhl.zza(bArr, i)));
                iZzb = i10 + 4;
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zzb(bArr, i, zzhnVar);
                unsafe.putObject(obj, j, Boolean.valueOf(zzhnVar.zzb != 0));
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzhl.zza(bArr, i, zzhnVar);
                int i11 = zzhnVar.zza;
                if (i11 == 0) {
                    unsafe.putObject(obj, j, "");
                } else {
                    if ((i6 & 536870912) != 0 && !zzmd.zza(bArr, iZza, iZza + i11)) {
                        throw zzjk.zzh();
                    }
                    unsafe.putObject(obj, j, new String(bArr, iZza, i11, zzjf.zza));
                    iZza += i11;
                }
                unsafe.putInt(obj, j2, i4);
                return iZza;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZza2 = zzhl.zza(zza(i8), bArr, i, i2, zzhnVar);
                object = unsafe.getInt(obj, j2) == i4 ? unsafe.getObject(obj, j) : null;
                if (object == null) {
                    unsafe.putObject(obj, j, zzhnVar.zzc);
                } else {
                    unsafe.putObject(obj, j, zzjf.zza(object, zzhnVar.zzc));
                }
                unsafe.putInt(obj, j2, i4);
                return iZza2;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                iZzb = zzhl.zze(bArr, i, zzhnVar);
                unsafe.putObject(obj, j, zzhnVar.zzc);
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZza3 = zzhl.zza(bArr, i, zzhnVar);
                int i12 = zzhnVar.zza;
                zzjg zzjgVarZzc = zzc(i8);
                if (zzjgVarZzc == null || zzjgVarZzc.zza(i12)) {
                    unsafe.putObject(obj, j, Integer.valueOf(i12));
                    iZzb = iZza3;
                    unsafe.putInt(obj, j2, i4);
                    return iZzb;
                }
                zze(obj).zza(i3, Long.valueOf(i12));
                return iZza3;
            case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zza(bArr, i, zzhnVar);
                unsafe.putObject(obj, j, Integer.valueOf(zzif.zze(zzhnVar.zza)));
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case TypeReference.INSTANCEOF /* 67 */:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zzb(bArr, i, zzhnVar);
                unsafe.putObject(obj, j, Long.valueOf(zzif.zza(zzhnVar.zzb)));
                unsafe.putInt(obj, j2, i4);
                return iZzb;
            case TypeReference.NEW /* 68 */:
                if (i5 == 3) {
                    iZzb = zzhl.zza(zza(i8), bArr, i, i2, (i3 & (-8)) | 4, zzhnVar);
                    object = unsafe.getInt(obj, j2) == i4 ? unsafe.getObject(obj, j) : null;
                    if (object == null) {
                        unsafe.putObject(obj, j, zzhnVar.zzc);
                    } else {
                        unsafe.putObject(obj, j, zzjf.zza(object, zzhnVar.zzc));
                    }
                    unsafe.putInt(obj, j2, i4);
                    return iZzb;
                }
            default:
                return i;
        }
    }

    private final zzlc zza(int i) {
        int i2 = (i / 3) << 1;
        zzlc zzlcVar = (zzlc) this.zzd[i2];
        if (zzlcVar != null) {
            return zzlcVar;
        }
        zzlc zzlcVarZza = zzky.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zzlcVarZza;
        return zzlcVarZza;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    private final zzjg zzc(int i) {
        return (zzjg) this.zzd[((i / 3) << 1) + 1];
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:22:0x008b. Please report as an issue. */
    final int zza(Object obj, byte[] bArr, int i, int i2, int i3, zzhn zzhnVar) throws zzjk {
        int i4;
        zzko zzkoVar;
        Object obj2;
        Unsafe unsafe;
        int i5;
        int iZzg;
        int i6;
        int i7;
        zzhn zzhnVar2;
        Unsafe unsafe2;
        int i8;
        int i9;
        byte[] bArr2;
        Unsafe unsafe3;
        byte[] bArr3;
        int i10;
        int i11;
        Unsafe unsafe4;
        int iZzd;
        byte[] bArr4;
        int i12;
        int i13;
        int iZza;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        zzko zzkoVar2 = this;
        Object obj3 = obj;
        byte[] bArr5 = bArr;
        int i19 = i2;
        zzhn zzhnVar3 = zzhnVar;
        Unsafe unsafe5 = zzb;
        int i20 = -1;
        int i21 = i;
        int i22 = -1;
        int i23 = 0;
        int i24 = 1048575;
        int i25 = 0;
        int i26 = 0;
        while (true) {
            if (i21 < i19) {
                int iZza2 = i21 + 1;
                int i27 = bArr5[i21];
                if (i27 < 0) {
                    iZza2 = zzhl.zza(i27, bArr5, iZza2, zzhnVar3);
                    i27 = zzhnVar3.zza;
                }
                int i28 = iZza2;
                i26 = i27;
                int i29 = i26 >>> 3;
                int i30 = i23;
                int i31 = i26 & 7;
                if (i29 > i22) {
                    iZzg = zzkoVar2.zza(i29, i30 / 3);
                } else {
                    iZzg = zzkoVar2.zzg(i29);
                }
                if (iZzg == i20) {
                    zzkoVar = zzkoVar2;
                    obj2 = obj3;
                    i6 = i28;
                    unsafe = unsafe5;
                    i22 = i29;
                    i23 = 0;
                    i5 = 1048575;
                    i4 = i3;
                } else {
                    int[] iArr = zzkoVar2.zzc;
                    int i32 = iArr[iZzg + 1];
                    int i33 = (i32 & 267386880) >>> 20;
                    long j = i32 & 1048575;
                    if (i33 <= 17) {
                        int i34 = iArr[iZzg + 2];
                        int i35 = 1 << (i34 >>> 20);
                        int i36 = i34 & 1048575;
                        if (i36 != i24) {
                            if (i24 != 1048575) {
                                unsafe5.putInt(obj3, i24, i25);
                            }
                            i24 = i36;
                            i25 = unsafe5.getInt(obj3, i36);
                        }
                        switch (i33) {
                            case 0:
                                i7 = iZzg;
                                unsafe2 = unsafe5;
                                i8 = i28;
                                i9 = i2;
                                bArr2 = bArr;
                                zzhnVar2 = zzhnVar3;
                                if (i31 != 1) {
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    zzma.zza(obj3, j, zzhl.zzc(bArr2, i8));
                                    i21 = i8 + 8;
                                    i25 |= i35;
                                    int i37 = i9;
                                    unsafe5 = unsafe2;
                                    i19 = i37;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    bArr5 = bArr2;
                                    i22 = i29;
                                    i20 = -1;
                                }
                            case 1:
                                i7 = iZzg;
                                unsafe2 = unsafe5;
                                i8 = i28;
                                i9 = i2;
                                bArr2 = bArr;
                                zzhnVar2 = zzhnVar3;
                                if (i31 != 5) {
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    zzma.zza(obj3, j, zzhl.zzd(bArr2, i8));
                                    i21 = i8 + 4;
                                    i25 |= i35;
                                    int i372 = i9;
                                    unsafe5 = unsafe2;
                                    i19 = i372;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    bArr5 = bArr2;
                                    i22 = i29;
                                    i20 = -1;
                                }
                            case 2:
                            case 3:
                                i7 = iZzg;
                                unsafe3 = unsafe5;
                                i8 = i28;
                                bArr2 = bArr;
                                zzhnVar2 = zzhnVar3;
                                if (i31 != 0) {
                                    unsafe2 = unsafe3;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    int iZzb = zzhl.zzb(bArr2, i8, zzhnVar2);
                                    unsafe3.putLong(obj, j, zzhnVar2.zzb);
                                    obj3 = obj;
                                    i25 |= i35;
                                    unsafe5 = unsafe3;
                                    i19 = i2;
                                    i21 = iZzb;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    bArr5 = bArr2;
                                    i22 = i29;
                                    i20 = -1;
                                }
                            case 4:
                            case 11:
                                i7 = iZzg;
                                unsafe3 = unsafe5;
                                i8 = i28;
                                bArr3 = bArr;
                                i10 = i2;
                                zzhnVar2 = zzhnVar3;
                                if (i31 != 0) {
                                    unsafe2 = unsafe3;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    int iZza3 = zzhl.zza(bArr3, i8, zzhnVar2);
                                    unsafe3.putInt(obj3, j, zzhnVar2.zza);
                                    i25 |= i35;
                                    i21 = iZza3;
                                    i19 = i10;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    bArr5 = bArr3;
                                    i20 = -1;
                                    unsafe5 = unsafe3;
                                    i22 = i29;
                                }
                            case 5:
                            case 14:
                                zzhnVar2 = zzhnVar3;
                                bArr3 = bArr;
                                i7 = iZzg;
                                unsafe3 = unsafe5;
                                i11 = i28;
                                i10 = i2;
                                if (i31 != 1) {
                                    i8 = i11;
                                    unsafe2 = unsafe3;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    unsafe3.putLong(obj, j, zzhl.zzb(bArr3, i11));
                                    unsafe3 = unsafe3;
                                    obj3 = obj;
                                    i21 = i11 + 8;
                                    i25 |= i35;
                                    i19 = i10;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    bArr5 = bArr3;
                                    i20 = -1;
                                    unsafe5 = unsafe3;
                                    i22 = i29;
                                }
                            case 6:
                            case 13:
                                zzhnVar2 = zzhnVar3;
                                bArr3 = bArr;
                                i7 = iZzg;
                                unsafe3 = unsafe5;
                                i11 = i28;
                                i10 = i2;
                                if (i31 != 5) {
                                    i8 = i11;
                                    unsafe2 = unsafe3;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    unsafe3.putInt(obj3, j, zzhl.zza(bArr3, i11));
                                    i21 = i11 + 4;
                                    i25 |= i35;
                                    i19 = i10;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    bArr5 = bArr3;
                                    i20 = -1;
                                    unsafe5 = unsafe3;
                                    i22 = i29;
                                }
                            case 7:
                                zzhnVar2 = zzhnVar3;
                                bArr3 = bArr;
                                i7 = iZzg;
                                unsafe3 = unsafe5;
                                i11 = i28;
                                i10 = i2;
                                if (i31 != 0) {
                                    i8 = i11;
                                    unsafe2 = unsafe3;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    int iZzb2 = zzhl.zzb(bArr3, i11, zzhnVar2);
                                    zzma.zza(obj3, j, zzhnVar2.zzb != 0);
                                    i25 |= i35;
                                    i21 = iZzb2;
                                    i19 = i10;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    bArr5 = bArr3;
                                    i20 = -1;
                                    unsafe5 = unsafe3;
                                    i22 = i29;
                                }
                            case 8:
                                i7 = iZzg;
                                zzhnVar2 = zzhnVar3;
                                unsafe4 = unsafe5;
                                if (i31 != 2) {
                                    i8 = i28;
                                    unsafe2 = unsafe4;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    if ((536870912 & i32) == 0) {
                                        iZzd = zzhl.zzc(bArr, i28, zzhnVar2);
                                    } else {
                                        iZzd = zzhl.zzd(bArr, i28, zzhnVar2);
                                    }
                                    unsafe4.putObject(obj3, j, zzhnVar2.zzc);
                                    i25 |= i35;
                                    i21 = iZzd;
                                    i19 = i2;
                                    i23 = i7;
                                    bArr5 = bArr;
                                    i22 = i29;
                                    i20 = -1;
                                    unsafe5 = unsafe4;
                                    zzhnVar3 = zzhnVar2;
                                }
                            case 9:
                                bArr4 = bArr;
                                i7 = iZzg;
                                i12 = i28;
                                zzhnVar2 = zzhnVar3;
                                unsafe4 = unsafe5;
                                i13 = i2;
                                if (i31 != 2) {
                                    unsafe2 = unsafe4;
                                    i8 = i12;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    iZza = zzhl.zza(zzkoVar2.zza(i7), bArr4, i12, i13, zzhnVar2);
                                    if ((i25 & i35) == 0) {
                                        unsafe4.putObject(obj3, j, zzhnVar2.zzc);
                                    } else {
                                        unsafe4.putObject(obj3, j, zzjf.zza(unsafe4.getObject(obj3, j), zzhnVar2.zzc));
                                    }
                                    i25 |= i35;
                                    i21 = iZza;
                                    i19 = i13;
                                    bArr5 = bArr4;
                                    i23 = i7;
                                    i22 = i29;
                                    i20 = -1;
                                    unsafe5 = unsafe4;
                                    zzhnVar3 = zzhnVar2;
                                }
                            case 10:
                                bArr4 = bArr;
                                i7 = iZzg;
                                i12 = i28;
                                zzhnVar2 = zzhnVar3;
                                unsafe4 = unsafe5;
                                i13 = i2;
                                if (i31 != 2) {
                                    unsafe2 = unsafe4;
                                    i8 = i12;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    iZza = zzhl.zze(bArr4, i12, zzhnVar2);
                                    unsafe4.putObject(obj3, j, zzhnVar2.zzc);
                                    i25 |= i35;
                                    i21 = iZza;
                                    i19 = i13;
                                    bArr5 = bArr4;
                                    i23 = i7;
                                    i22 = i29;
                                    i20 = -1;
                                    unsafe5 = unsafe4;
                                    zzhnVar3 = zzhnVar2;
                                }
                            case 12:
                                bArr4 = bArr;
                                i7 = iZzg;
                                i12 = i28;
                                zzhnVar2 = zzhnVar3;
                                unsafe4 = unsafe5;
                                i13 = i2;
                                if (i31 != 0) {
                                    unsafe2 = unsafe4;
                                    i8 = i12;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    iZza = zzhl.zza(bArr4, i12, zzhnVar2);
                                    int i38 = zzhnVar2.zza;
                                    zzjg zzjgVarZzc = zzkoVar2.zzc(i7);
                                    if (zzjgVarZzc == null || zzjgVarZzc.zza(i38)) {
                                        unsafe4.putInt(obj3, j, i38);
                                        i25 |= i35;
                                        i21 = iZza;
                                        i19 = i13;
                                        bArr5 = bArr4;
                                        i23 = i7;
                                        i22 = i29;
                                        i20 = -1;
                                        unsafe5 = unsafe4;
                                        zzhnVar3 = zzhnVar2;
                                    } else {
                                        zze(obj3).zza(i26, Long.valueOf(i38));
                                        i21 = iZza;
                                        i19 = i13;
                                        bArr5 = bArr4;
                                        i23 = i7;
                                        i22 = i29;
                                        i20 = -1;
                                        unsafe5 = unsafe4;
                                        zzhnVar3 = zzhnVar2;
                                    }
                                }
                                break;
                            case 15:
                                bArr4 = bArr;
                                i7 = iZzg;
                                i12 = i28;
                                zzhnVar2 = zzhnVar3;
                                unsafe4 = unsafe5;
                                i13 = i2;
                                if (i31 != 0) {
                                    unsafe2 = unsafe4;
                                    i8 = i12;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    iZza = zzhl.zza(bArr4, i12, zzhnVar2);
                                    unsafe4.putInt(obj3, j, zzif.zze(zzhnVar2.zza));
                                    i25 |= i35;
                                    i21 = iZza;
                                    i19 = i13;
                                    bArr5 = bArr4;
                                    i23 = i7;
                                    i22 = i29;
                                    i20 = -1;
                                    unsafe5 = unsafe4;
                                    zzhnVar3 = zzhnVar2;
                                }
                            case 16:
                                bArr4 = bArr;
                                zzhn zzhnVar4 = zzhnVar3;
                                i7 = iZzg;
                                i12 = i28;
                                if (i31 != 0) {
                                    zzhnVar2 = zzhnVar4;
                                    unsafe4 = unsafe5;
                                    unsafe2 = unsafe4;
                                    i8 = i12;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    int iZzb3 = zzhl.zzb(bArr4, i12, zzhnVar4);
                                    zzhnVar2 = zzhnVar4;
                                    Object obj4 = obj3;
                                    Unsafe unsafe6 = unsafe5;
                                    i13 = i2;
                                    unsafe6.putLong(obj4, j, zzif.zza(zzhnVar4.zzb));
                                    unsafe4 = unsafe6;
                                    obj3 = obj4;
                                    i25 |= i35;
                                    i21 = iZzb3;
                                    i19 = i13;
                                    bArr5 = bArr4;
                                    i23 = i7;
                                    i22 = i29;
                                    i20 = -1;
                                    unsafe5 = unsafe4;
                                    zzhnVar3 = zzhnVar2;
                                }
                            case 17:
                                if (i31 != 3) {
                                    i7 = iZzg;
                                    zzhnVar2 = zzhnVar3;
                                    unsafe2 = unsafe5;
                                    i8 = i28;
                                    unsafe = unsafe2;
                                    i6 = i8;
                                    i23 = i7;
                                    zzhnVar3 = zzhnVar2;
                                    i22 = i29;
                                    i5 = 1048575;
                                    i4 = i3;
                                    zzkoVar = zzkoVar2;
                                    obj2 = obj3;
                                    break;
                                } else {
                                    zzhn zzhnVar5 = zzhnVar3;
                                    int i39 = iZzg;
                                    int iZza4 = zzhl.zza(zzkoVar2.zza(iZzg), bArr, i28, i2, (i29 << 3) | 4, zzhnVar5);
                                    if ((i25 & i35) == 0) {
                                        unsafe5.putObject(obj3, j, zzhnVar5.zzc);
                                    } else {
                                        unsafe5.putObject(obj3, j, zzjf.zza(unsafe5.getObject(obj3, j), zzhnVar5.zzc));
                                    }
                                    i25 |= i35;
                                    i19 = i2;
                                    zzhnVar3 = zzhnVar5;
                                    i23 = i39;
                                    i22 = i29;
                                    i20 = -1;
                                    i21 = iZza4;
                                    bArr5 = bArr;
                                }
                            default:
                                zzhnVar2 = zzhnVar3;
                                i7 = iZzg;
                                unsafe2 = unsafe5;
                                i8 = i28;
                                unsafe = unsafe2;
                                i6 = i8;
                                i23 = i7;
                                zzhnVar3 = zzhnVar2;
                                i22 = i29;
                                i5 = 1048575;
                                i4 = i3;
                                zzkoVar = zzkoVar2;
                                obj2 = obj3;
                                break;
                        }
                    } else {
                        int i40 = iZzg;
                        Unsafe unsafe7 = unsafe5;
                        zzhn zzhnVar6 = zzhnVar3;
                        if (i33 != 27) {
                            i14 = i28;
                            if (i33 <= 49) {
                                i15 = i40;
                                i16 = i24;
                                i17 = i25;
                                unsafe = unsafe7;
                                i5 = 1048575;
                                int iZza5 = zzkoVar2.zza(obj, bArr, i14, i2, i26, i29, i31, i15, i32, i33, j, zzhnVar);
                                i26 = i26;
                                i18 = i29;
                                if (iZza5 == i14) {
                                    zzkoVar = this;
                                    i4 = i3;
                                    zzhnVar3 = zzhnVar;
                                    i6 = iZza5;
                                    i23 = i15;
                                    i22 = i18;
                                    i24 = i16;
                                    i25 = i17;
                                    obj2 = obj;
                                } else {
                                    zzkoVar2 = this;
                                    obj3 = obj;
                                    bArr5 = bArr;
                                    i19 = i2;
                                    zzhnVar3 = zzhnVar;
                                    i21 = iZza5;
                                    i23 = i15;
                                    i22 = i18;
                                    i24 = i16;
                                    i25 = i17;
                                    unsafe5 = unsafe;
                                    i20 = -1;
                                }
                            } else {
                                i15 = i40;
                                i16 = i24;
                                i17 = i25;
                                unsafe = unsafe7;
                                i18 = i29;
                                i5 = 1048575;
                                if (i33 != 50) {
                                    i22 = i18;
                                    int iZza6 = zza(obj, bArr, i14, i2, i26, i22, i31, i32, i33, j, i15, zzhnVar);
                                    i26 = i26;
                                    zzhnVar3 = zzhnVar;
                                    zzkoVar = this;
                                    obj2 = obj;
                                    i4 = i3;
                                    if (iZza6 == i14) {
                                        i6 = iZza6;
                                        i23 = i15;
                                        i24 = i16;
                                        i25 = i17;
                                    } else {
                                        bArr5 = bArr;
                                        i19 = i2;
                                        zzhnVar3 = zzhnVar;
                                        i21 = iZza6;
                                        i23 = i15;
                                        zzkoVar2 = zzkoVar;
                                        obj3 = obj2;
                                        i24 = i16;
                                        i25 = i17;
                                        unsafe5 = unsafe;
                                        i20 = -1;
                                    }
                                } else if (i31 == 2) {
                                    int iZza7 = zza(obj, bArr, i14, i2, i15, j, zzhnVar);
                                    i15 = i15;
                                    if (iZza7 == i14) {
                                        zzkoVar = this;
                                        i4 = i3;
                                        zzhnVar3 = zzhnVar;
                                        i6 = iZza7;
                                        i23 = i15;
                                        i22 = i18;
                                        i24 = i16;
                                        i25 = i17;
                                        obj2 = obj;
                                    } else {
                                        zzkoVar2 = this;
                                        obj3 = obj;
                                        bArr5 = bArr;
                                        i19 = i2;
                                        zzhnVar3 = zzhnVar;
                                        i21 = iZza7;
                                        i23 = i15;
                                        i22 = i18;
                                        i24 = i16;
                                        i25 = i17;
                                        unsafe5 = unsafe;
                                        i20 = -1;
                                    }
                                }
                            }
                        } else if (i31 == 2) {
                            zzjl zzjlVarZza = (zzjl) unsafe7.getObject(obj3, j);
                            if (!zzjlVarZza.zza()) {
                                int size = zzjlVarZza.size();
                                zzjlVarZza = zzjlVarZza.zza(size == 0 ? 10 : size << 1);
                                unsafe7.putObject(obj3, j, zzjlVarZza);
                            }
                            int iZza8 = zzhl.zza(zzkoVar2.zza(i40), i26, bArr, i28, i2, zzjlVarZza, zzhnVar6);
                            bArr5 = bArr;
                            i19 = i2;
                            zzhnVar3 = zzhnVar;
                            i21 = iZza8;
                            i23 = i40;
                            unsafe5 = unsafe7;
                            i22 = i29;
                            i20 = -1;
                            obj3 = obj;
                        } else {
                            i15 = i40;
                            i16 = i24;
                            i17 = i25;
                            i14 = i28;
                            i18 = i29;
                            i5 = 1048575;
                            unsafe = unsafe7;
                        }
                        zzkoVar = this;
                        i4 = i3;
                        zzhnVar3 = zzhnVar;
                        i6 = i14;
                        i23 = i15;
                        i22 = i18;
                        i24 = i16;
                        i25 = i17;
                        obj2 = obj;
                    }
                }
                if (i26 != i4 || i4 == 0) {
                    if (zzkoVar.zzh && zzhnVar3.zzd != zzio.zzb()) {
                        zzhnVar3.zzd.zza(zzkoVar.zzg, i22);
                        int iZza9 = zzhl.zza(i26, bArr, i6, i2, zze(obj2), zzhnVar3);
                        i19 = i2;
                        zzhnVar3 = zzhnVar;
                        i21 = iZza9;
                        zzkoVar2 = zzkoVar;
                        obj3 = obj2;
                        unsafe5 = unsafe;
                        i20 = -1;
                    } else {
                        zzhnVar3 = zzhnVar;
                        int iZza10 = zzhl.zza(i26, bArr, i6, i2, zze(obj2), zzhnVar3);
                        i19 = i2;
                        zzkoVar2 = zzkoVar;
                        obj3 = obj2;
                        unsafe5 = unsafe;
                        i20 = -1;
                        i21 = iZza10;
                    }
                    bArr5 = bArr;
                } else {
                    i19 = i2;
                    i21 = i6;
                }
            } else {
                i4 = i3;
                zzkoVar = zzkoVar2;
                obj2 = obj3;
                unsafe = unsafe5;
                i5 = 1048575;
            }
        }
        if (i24 != i5) {
            unsafe.putInt(obj2, i24, i25);
        }
        zzlx zzlxVar = null;
        for (int i41 = zzkoVar.zzm; i41 < zzkoVar.zzn; i41++) {
            zzlxVar = (zzlx) zzkoVar.zza(obj2, zzkoVar.zzl[i41], zzlxVar, zzkoVar.zzq);
        }
        if (zzlxVar != null) {
            zzkoVar.zzq.zzb(obj2, zzlxVar);
        }
        if (i4 == 0) {
            if (i21 != i19) {
                throw zzjk.zzg();
            }
        } else if (i21 > i19 || i26 != i4) {
            throw zzjk.zzg();
        }
        return i21;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0086. Please report as an issue. */
    @Override // com.google.android.gms.internal.vision.zzlc
    public final void zza(Object obj, byte[] bArr, int i, int i2, zzhn zzhnVar) throws zzjk {
        int iZzg;
        Object obj2;
        Unsafe unsafe;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        zzko zzkoVar = this;
        Object obj3 = obj;
        byte[] bArr2 = bArr;
        int i12 = i2;
        zzhn zzhnVar2 = zzhnVar;
        if (zzkoVar.zzj) {
            Unsafe unsafe2 = zzb;
            int i13 = -1;
            int iZzb = i;
            int i14 = -1;
            int i15 = 0;
            int i16 = 1048575;
            int i17 = 0;
            while (iZzb < i12) {
                int iZza = iZzb + 1;
                int i18 = bArr2[iZzb];
                if (i18 < 0) {
                    iZza = zzhl.zza(i18, bArr2, iZza, zzhnVar2);
                    i18 = zzhnVar2.zza;
                }
                int i19 = iZza;
                int i20 = i18 >>> 3;
                int i21 = i18 & 7;
                if (i20 > i14) {
                    iZzg = zzkoVar.zza(i20, i15 / 3);
                } else {
                    iZzg = zzkoVar.zzg(i20);
                }
                if (iZzg == i13) {
                    obj2 = obj3;
                    unsafe = unsafe2;
                    i3 = i18;
                    i4 = i20;
                    i5 = 0;
                } else {
                    int[] iArr = zzkoVar.zzc;
                    int i22 = iArr[iZzg + 1];
                    int i23 = (i22 & 267386880) >>> 20;
                    int i24 = i18;
                    int i25 = iZzg;
                    long j = i22 & 1048575;
                    if (i23 <= 17) {
                        int i26 = iArr[i25 + 2];
                        int i27 = 1 << (i26 >>> 20);
                        int i28 = i26 & 1048575;
                        int i29 = 1048575;
                        if (i28 != i16) {
                            if (i16 != 1048575) {
                                unsafe2.putInt(obj3, i16, i17);
                                i29 = 1048575;
                            }
                            if (i28 != i29) {
                                i17 = unsafe2.getInt(obj3, i28);
                            }
                            i16 = i28;
                        }
                        switch (i23) {
                            case 0:
                                if (i21 != 1) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    zzma.zza(obj3, j, zzhl.zzc(bArr2, i19));
                                    iZzb = i19 + 8;
                                    i17 |= i27;
                                    i12 = i2;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    break;
                                }
                            case 1:
                                if (i21 != 5) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    zzma.zza(obj3, j, zzhl.zzd(bArr2, i19));
                                    iZzb = i19 + 4;
                                    i17 |= i27;
                                    i12 = i2;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    break;
                                }
                            case 2:
                            case 3:
                                if (i21 != 0) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    int iZzb2 = zzhl.zzb(bArr2, i19, zzhnVar2);
                                    Unsafe unsafe3 = unsafe2;
                                    Object obj4 = obj3;
                                    unsafe3.putLong(obj4, j, zzhnVar2.zzb);
                                    unsafe2 = unsafe3;
                                    obj3 = obj4;
                                    i17 |= i27;
                                    iZzb = iZzb2;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    i12 = i2;
                                    break;
                                }
                            case 4:
                            case 11:
                                if (i21 != 0) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    int iZza2 = zzhl.zza(bArr2, i19, zzhnVar2);
                                    unsafe2.putInt(obj3, j, zzhnVar2.zza);
                                    i17 |= i27;
                                    i12 = i2;
                                    iZzb = iZza2;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    break;
                                }
                            case 5:
                            case 14:
                                if (i21 != 1) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    Unsafe unsafe4 = unsafe2;
                                    Object obj5 = obj3;
                                    unsafe4.putLong(obj5, j, zzhl.zzb(bArr2, i19));
                                    unsafe2 = unsafe4;
                                    obj3 = obj5;
                                    iZzb = i19 + 8;
                                    i17 |= i27;
                                    i12 = i2;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    break;
                                }
                            case 6:
                            case 13:
                                if (i21 != 5) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    unsafe2.putInt(obj3, j, zzhl.zza(bArr2, i19));
                                    iZzb = i19 + 4;
                                    i17 |= i27;
                                    i12 = i2;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    break;
                                }
                            case 7:
                                if (i21 != 0) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    iZzb = zzhl.zzb(bArr2, i19, zzhnVar2);
                                    zzma.zza(obj3, j, zzhnVar2.zzb != 0);
                                    i17 |= i27;
                                    i12 = i2;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    break;
                                }
                            case 8:
                                if (i21 != 2) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    if ((536870912 & i22) == 0) {
                                        iZzb = zzhl.zzc(bArr2, i19, zzhnVar2);
                                    } else {
                                        iZzb = zzhl.zzd(bArr2, i19, zzhnVar2);
                                    }
                                    unsafe2.putObject(obj3, j, zzhnVar2.zzc);
                                    i17 |= i27;
                                    i14 = i20;
                                    i15 = i25;
                                    i13 = -1;
                                    break;
                                }
                            case 9:
                                i11 = i25;
                                if (i21 != 2) {
                                    i25 = i11;
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    iZzb = zzhl.zza(zzkoVar.zza(i11), bArr2, i19, i12, zzhnVar2);
                                    Object object = unsafe2.getObject(obj3, j);
                                    if (object == null) {
                                        unsafe2.putObject(obj3, j, zzhnVar2.zzc);
                                    } else {
                                        unsafe2.putObject(obj3, j, zzjf.zza(object, zzhnVar2.zzc));
                                    }
                                    i17 |= i27;
                                    i14 = i20;
                                    i15 = i11;
                                    i13 = -1;
                                    break;
                                }
                            case 10:
                                i11 = i25;
                                if (i21 != 2) {
                                    i25 = i11;
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    iZzb = zzhl.zze(bArr2, i19, zzhnVar2);
                                    unsafe2.putObject(obj3, j, zzhnVar2.zzc);
                                    i17 |= i27;
                                    i14 = i20;
                                    i15 = i11;
                                    i13 = -1;
                                    break;
                                }
                            case 12:
                                i11 = i25;
                                if (i21 != 0) {
                                    i25 = i11;
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    iZzb = zzhl.zza(bArr2, i19, zzhnVar2);
                                    unsafe2.putInt(obj3, j, zzhnVar2.zza);
                                    i17 |= i27;
                                    i14 = i20;
                                    i15 = i11;
                                    i13 = -1;
                                    break;
                                }
                            case 15:
                                i11 = i25;
                                if (i21 != 0) {
                                    i25 = i11;
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    iZzb = zzhl.zza(bArr2, i19, zzhnVar2);
                                    unsafe2.putInt(obj3, j, zzif.zze(zzhnVar2.zza));
                                    i17 |= i27;
                                    i14 = i20;
                                    i15 = i11;
                                    i13 = -1;
                                    break;
                                }
                            case 16:
                                if (i21 != 0) {
                                    obj2 = obj3;
                                    unsafe = unsafe2;
                                    i4 = i20;
                                    i5 = i25;
                                    i3 = i24;
                                    break;
                                } else {
                                    int iZzb3 = zzhl.zzb(bArr2, i19, zzhnVar2);
                                    Unsafe unsafe5 = unsafe2;
                                    Object obj6 = obj3;
                                    i11 = i25;
                                    unsafe5.putLong(obj6, j, zzif.zza(zzhnVar2.zzb));
                                    unsafe2 = unsafe5;
                                    obj3 = obj6;
                                    i17 |= i27;
                                    iZzb = iZzb3;
                                    i14 = i20;
                                    i15 = i11;
                                    i13 = -1;
                                    break;
                                }
                            default:
                                obj2 = obj3;
                                unsafe = unsafe2;
                                i4 = i20;
                                i5 = i25;
                                i3 = i24;
                                break;
                        }
                    } else {
                        i5 = i25;
                        if (i23 != 27) {
                            i6 = i19;
                            Unsafe unsafe6 = unsafe2;
                            if (i23 <= 49) {
                                int i30 = i16;
                                i7 = i17;
                                unsafe = unsafe6;
                                int iZza3 = zzkoVar.zza(obj, bArr, i6, i2, i24, i20, i21, i5, i22, i23, j, zzhnVar);
                                if (iZza3 == i6) {
                                    i19 = iZza3;
                                    i4 = i20;
                                    i3 = i24;
                                    i17 = i7;
                                    obj2 = obj;
                                    i16 = i30;
                                } else {
                                    zzkoVar = this;
                                    obj3 = obj;
                                    i16 = i30;
                                    zzhnVar2 = zzhnVar;
                                    iZzb = iZza3;
                                    i15 = i5;
                                    i14 = i20;
                                    i17 = i7;
                                    unsafe2 = unsafe;
                                    i13 = -1;
                                    bArr2 = bArr;
                                    i12 = i2;
                                }
                            } else {
                                i7 = i17;
                                unsafe = unsafe6;
                                i8 = i20;
                                i9 = i16;
                                i10 = i24;
                                if (i23 != 50) {
                                    i4 = i8;
                                    int iZza4 = zza(obj, bArr, i6, i2, i10, i4, i21, i22, i23, j, i5, zzhnVar);
                                    obj2 = obj;
                                    i3 = i10;
                                    i5 = i5;
                                    if (iZza4 == i6) {
                                        i19 = iZza4;
                                        i16 = i9;
                                        i17 = i7;
                                    } else {
                                        zzkoVar = this;
                                        zzhnVar2 = zzhnVar;
                                        i14 = i4;
                                        iZzb = iZza4;
                                        i15 = i5;
                                        obj3 = obj2;
                                        i16 = i9;
                                        i17 = i7;
                                        unsafe2 = unsafe;
                                        i13 = -1;
                                        bArr2 = bArr;
                                        i12 = i2;
                                    }
                                } else if (i21 == 2) {
                                    int iZza5 = zza(obj, bArr, i6, i2, i5, j, zzhnVar);
                                    i5 = i5;
                                    if (iZza5 == i6) {
                                        i19 = iZza5;
                                        i4 = i8;
                                        i3 = i10;
                                        i16 = i9;
                                        i17 = i7;
                                        obj2 = obj;
                                    } else {
                                        zzkoVar = this;
                                        obj3 = obj;
                                        bArr2 = bArr;
                                        zzhnVar2 = zzhnVar;
                                        iZzb = iZza5;
                                        i15 = i5;
                                        i14 = i8;
                                        i16 = i9;
                                        i17 = i7;
                                        unsafe2 = unsafe;
                                        i13 = -1;
                                        i12 = i2;
                                    }
                                } else {
                                    i5 = i5;
                                    i19 = i6;
                                    i4 = i8;
                                    i3 = i10;
                                    i16 = i9;
                                    i17 = i7;
                                    obj2 = obj;
                                }
                            }
                        } else if (i21 == 2) {
                            zzjl zzjlVarZza = (zzjl) unsafe2.getObject(obj3, j);
                            if (!zzjlVarZza.zza()) {
                                int size = zzjlVarZza.size();
                                zzjlVarZza = zzjlVarZza.zza(size == 0 ? 10 : size << 1);
                                unsafe2.putObject(obj3, j, zzjlVarZza);
                            }
                            int iZza6 = zzhl.zza(zzkoVar.zza(i5), i24, bArr2, i19, i2, zzjlVarZza, zzhnVar2);
                            bArr2 = bArr;
                            zzhnVar2 = zzhnVar;
                            iZzb = iZza6;
                            i15 = i5;
                            unsafe2 = unsafe2;
                            i14 = i20;
                            i13 = -1;
                            obj3 = obj;
                            i12 = i2;
                        } else {
                            i6 = i19;
                            i7 = i17;
                            unsafe = unsafe2;
                            i8 = i20;
                            i9 = i16;
                            i10 = i24;
                            i19 = i6;
                            i4 = i8;
                            i3 = i10;
                            i16 = i9;
                            i17 = i7;
                            obj2 = obj;
                        }
                    }
                }
                int iZza7 = zzhl.zza(i3, bArr, i19, i2, zze(obj2), zzhnVar);
                bArr2 = bArr;
                zzhnVar2 = zzhnVar;
                i14 = i4;
                i15 = i5;
                obj3 = obj2;
                unsafe2 = unsafe;
                i13 = -1;
                i12 = i2;
                iZzb = iZza7;
                zzkoVar = this;
            }
            Object obj7 = obj3;
            Unsafe unsafe7 = unsafe2;
            int i31 = i12;
            int i32 = i16;
            int i33 = i17;
            if (i32 != 1048575) {
                unsafe7.putInt(obj7, i32, i33);
            }
            if (iZzb != i31) {
                throw zzjk.zzg();
            }
            return;
        }
        zza(obj3, bArr, i, i12, 0, zzhnVar);
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final void zzc(Object obj) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long jZzd = zzd(this.zzl[i2]) & 1048575;
            Object objZzf = zzma.zzf(obj, jZzd);
            if (objZzf != null) {
                zzma.zza(obj, jZzd, this.zzs.zze(objZzf));
            }
            i2++;
        }
        int length = this.zzl.length;
        while (i < length) {
            this.zzp.zzb(obj, this.zzl[i]);
            i++;
        }
        this.zzq.zzd(obj);
        if (this.zzh) {
            this.zzr.zzc(obj);
        }
    }

    private final Object zza(Object obj, int i, Object obj2, zzlu zzluVar) {
        zzjg zzjgVarZzc;
        int i2 = this.zzc[i];
        Object objZzf = zzma.zzf(obj, zzd(i) & 1048575);
        return (objZzf == null || (zzjgVarZzc = zzc(i)) == null) ? obj2 : zza(i, i2, this.zzs.zza(objZzf), zzjgVarZzc, obj2, zzluVar);
    }

    private final Object zza(int i, int i2, Map map, zzjg zzjgVar, Object obj, zzlu zzluVar) {
        this.zzs.zzb(zzb(i));
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!zzjgVar.zza(((Integer) entry.getValue()).intValue())) {
                if (obj == null) {
                    obj = zzluVar.zza();
                }
                zzib zzibVarZzc = zzht.zzc(zzkc.zza(null, entry.getKey(), entry.getValue()));
                try {
                    zzkc.zza(zzibVarZzc.zzb(), null, entry.getKey(), entry.getValue());
                    zzluVar.zza(obj, i2, zzibVarZzc.zza());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0098  */
    @Override // com.google.android.gms.internal.vision.zzlc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzd(java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zzd(java.lang.Object):boolean");
    }

    private static boolean zza(Object obj, int i, zzlc zzlcVar) {
        return zzlcVar.zzd(zzma.zzf(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzmr zzmrVar) {
        if (obj instanceof String) {
            zzmrVar.zza(i, (String) obj);
        } else {
            zzmrVar.zza(i, (zzht) obj);
        }
    }

    private final int zzd(int i) {
        return this.zzc[i + 1];
    }

    private final int zze(int i) {
        return this.zzc[i + 2];
    }

    private static double zzb(Object obj, long j) {
        return ((Double) zzma.zzf(obj, j)).doubleValue();
    }

    private static float zzc(Object obj, long j) {
        return ((Float) zzma.zzf(obj, j)).floatValue();
    }

    private static int zzd(Object obj, long j) {
        return ((Integer) zzma.zzf(obj, j)).intValue();
    }

    private static long zze(Object obj, long j) {
        return ((Long) zzma.zzf(obj, j)).longValue();
    }

    private static boolean zzf(Object obj, long j) {
        return ((Boolean) zzma.zzf(obj, j)).booleanValue();
    }

    private final boolean zzc(Object obj, Object obj2, int i) {
        return zza(obj, i) == zza(obj2, i);
    }

    private final boolean zza(Object obj, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza(obj, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(Object obj, int i) {
        int iZze = zze(i);
        long j = iZze & 1048575;
        if (j != 1048575) {
            return (zzma.zza(obj, j) & (1 << (iZze >>> 20))) != 0;
        }
        int iZzd = zzd(i);
        long j2 = iZzd & 1048575;
        switch ((iZzd & 267386880) >>> 20) {
            case 0:
                return zzma.zze(obj, j2) != 0.0d;
            case 1:
                return zzma.zzd(obj, j2) != 0.0f;
            case 2:
                return zzma.zzb(obj, j2) != 0;
            case 3:
                return zzma.zzb(obj, j2) != 0;
            case 4:
                return zzma.zza(obj, j2) != 0;
            case 5:
                return zzma.zzb(obj, j2) != 0;
            case 6:
                return zzma.zza(obj, j2) != 0;
            case 7:
                return zzma.zzc(obj, j2);
            case 8:
                Object objZzf = zzma.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzht) {
                    return !zzht.zza.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzma.zzf(obj, j2) != null;
            case 10:
                return !zzht.zza.equals(zzma.zzf(obj, j2));
            case 11:
                return zzma.zza(obj, j2) != 0;
            case 12:
                return zzma.zza(obj, j2) != 0;
            case 13:
                return zzma.zza(obj, j2) != 0;
            case 14:
                return zzma.zzb(obj, j2) != 0;
            case 15:
                return zzma.zza(obj, j2) != 0;
            case 16:
                return zzma.zzb(obj, j2) != 0;
            case 17:
                return zzma.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzb(Object obj, int i) {
        int iZze = zze(i);
        long j = 1048575 & iZze;
        if (j == 1048575) {
            return;
        }
        zzma.zza(obj, j, (1 << (iZze >>> 20)) | zzma.zza(obj, j));
    }

    private final boolean zza(Object obj, int i, int i2) {
        return zzma.zza(obj, (long) (zze(i2) & 1048575)) == i;
    }

    private final void zzb(Object obj, int i, int i2) {
        zzma.zza(obj, zze(i2) & 1048575, i);
    }

    private final int zzg(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, 0);
    }

    private final int zza(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, i2);
    }

    private final int zzb(int i, int i2) {
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
}
