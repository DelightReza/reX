package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* loaded from: classes4.dex */
public final class VideoInfo extends AbstractSafeParcelable {
    private final int zzb;
    private final int zzc;
    private final int zzd;
    private static final Logger zza = new Logger("VideoInfo");
    public static final Parcelable.Creator<VideoInfo> CREATOR = new zzdu();

    VideoInfo(int i, int i2, int i3) {
        this.zzb = i;
        this.zzc = i2;
        this.zzd = i3;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x004b A[Catch: JSONException -> 0x0069, TRY_ENTER, TryCatch #0 {JSONException -> 0x0069, blocks: (B:6:0x0005, B:29:0x0057, B:28:0x004b), top: B:34:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.android.gms.cast.VideoInfo zza(org.json.JSONObject r6) throws org.json.JSONException {
        /*
            r0 = 0
            if (r6 != 0) goto L4
            return r0
        L4:
            r1 = 0
            java.lang.String r2 = "hdrType"
            java.lang.String r2 = r6.getString(r2)     // Catch: org.json.JSONException -> L69
            int r3 = r2.hashCode()     // Catch: org.json.JSONException -> L69
            r4 = 3218(0xc92, float:4.51E-42)
            r5 = 1
            if (r3 == r4) goto L41
            r4 = 103158(0x192f6, float:1.44555E-40)
            if (r3 == r4) goto L37
            r4 = 113729(0x1bc41, float:1.59368E-40)
            if (r3 == r4) goto L2e
            r4 = 99136405(0x5e8b395, float:2.1883143E-35)
            if (r3 == r4) goto L24
            goto L4b
        L24:
            java.lang.String r3 = "hdr10"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L4b
            r5 = 2
            goto L57
        L2e:
            java.lang.String r3 = "sdr"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L4b
            goto L57
        L37:
            java.lang.String r3 = "hdr"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L4b
            r5 = 4
            goto L57
        L41:
            java.lang.String r3 = "dv"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L4b
            r5 = 3
            goto L57
        L4b:
            com.google.android.gms.cast.internal.Logger r3 = com.google.android.gms.cast.VideoInfo.zza     // Catch: org.json.JSONException -> L69
            java.lang.String r4 = "Unknown HDR type: %s"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: org.json.JSONException -> L69
            r5[r1] = r2     // Catch: org.json.JSONException -> L69
            r3.m297d(r4, r5)     // Catch: org.json.JSONException -> L69
            r5 = 0
        L57:
            com.google.android.gms.cast.VideoInfo r2 = new com.google.android.gms.cast.VideoInfo     // Catch: org.json.JSONException -> L69
            java.lang.String r3 = "width"
            int r3 = r6.getInt(r3)     // Catch: org.json.JSONException -> L69
            java.lang.String r4 = "height"
            int r6 = r6.getInt(r4)     // Catch: org.json.JSONException -> L69
            r2.<init>(r3, r6, r5)     // Catch: org.json.JSONException -> L69
            return r2
        L69:
            r6 = move-exception
            com.google.android.gms.cast.internal.Logger r2 = com.google.android.gms.cast.VideoInfo.zza
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r3 = "Error while creating a VideoInfo instance from JSON"
            r2.m298d(r6, r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.VideoInfo.zza(org.json.JSONObject):com.google.android.gms.cast.VideoInfo");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VideoInfo)) {
            return false;
        }
        VideoInfo videoInfo = (VideoInfo) obj;
        return this.zzc == videoInfo.getHeight() && this.zzb == videoInfo.getWidth() && this.zzd == videoInfo.getHdrType();
    }

    public int getHdrType() {
        return this.zzd;
    }

    public int getHeight() {
        return this.zzc;
    }

    public int getWidth() {
        return this.zzb;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzc), Integer.valueOf(this.zzb), Integer.valueOf(this.zzd));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, getWidth());
        SafeParcelWriter.writeInt(parcel, 3, getHeight());
        SafeParcelWriter.writeInt(parcel, 4, getHdrType());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
