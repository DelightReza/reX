package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes.dex */
public final class zzix extends zziu {
    public zzix() {
        super(4);
    }

    public final zzix zza(Object... objArr) {
        zzjd.zzb(objArr, 15);
        int i = this.zzb;
        int i2 = i + 15;
        Object[] objArr2 = this.zza;
        int length = objArr2.length;
        if (length < i2) {
            int i3 = length + (length >> 1) + 1;
            if (i3 < i2) {
                int iHighestOneBit = Integer.highestOneBit(i + 14);
                i3 = iHighestOneBit + iHighestOneBit;
            }
            if (i3 < 0) {
                i3 = ConnectionsManager.DEFAULT_DATACENTER_ID;
            }
            this.zza = Arrays.copyOf(objArr2, i3);
            this.zzc = false;
        } else if (this.zzc) {
            this.zza = (Object[]) objArr2.clone();
            this.zzc = false;
        }
        System.arraycopy(objArr, 0, this.zza, this.zzb, 15);
        this.zzb += 15;
        return this;
    }

    public final zzja zzb() {
        this.zzc = true;
        return zzja.zzg(this.zza, this.zzb);
    }
}
