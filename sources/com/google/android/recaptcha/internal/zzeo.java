package com.google.android.recaptcha.internal;

import android.content.Context;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes4.dex */
public final class zzeo implements zzen {
    private final Context zzb;
    private final Map zzc = MapsKt.mapOf(TuplesKt.m1122to(2, "activity"), TuplesKt.m1122to(3, "phone"), TuplesKt.m1122to(4, "input_method"), TuplesKt.m1122to(5, MediaStreamTrack.AUDIO_TRACK_KIND));

    public zzeo(Context context) {
        this.zzb = context;
    }

    @Override // com.google.android.recaptcha.internal.zzen
    /* renamed from: cs */
    public final /* synthetic */ Object mo426cs(Object[] objArr) {
        return zzel.zza(this, objArr);
    }

    @Override // com.google.android.recaptcha.internal.zzen
    public final Object zza(Object... objArr) throws zzae {
        Object obj = objArr[0];
        if (true != (obj instanceof Integer)) {
            obj = null;
        }
        Integer num = (Integer) obj;
        if (num == null) {
            throw new zzae(4, 5, null);
        }
        Object obj2 = this.zzc.get(Integer.valueOf(num.intValue()));
        if (obj2 != null) {
            return this.zzb.getSystemService((String) obj2);
        }
        throw new zzae(4, 4, null);
    }
}
