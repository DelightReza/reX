package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class zzfd {
    final /* synthetic */ zzfi zza;
    private final String zzb;
    private final Bundle zzc;
    private Bundle zzd;

    public zzfd(zzfi zzfiVar, String str, Bundle bundle) {
        this.zza = zzfiVar;
        Preconditions.checkNotEmpty("default_event_parameters");
        this.zzb = "default_event_parameters";
        this.zzc = new Bundle();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0081 A[Catch: NumberFormatException | JSONException -> 0x0093, NumberFormatException | JSONException -> 0x0093, TRY_LEAVE, TryCatch #0 {NumberFormatException | JSONException -> 0x0093, blocks: (B:10:0x0026, B:20:0x0051, B:20:0x0051, B:29:0x0081, B:29:0x0081, B:24:0x0061, B:24:0x0061, B:28:0x0075, B:28:0x0075), top: B:41:0x0026, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.os.Bundle zza() {
        /*
            r9 = this;
            android.os.Bundle r0 = r9.zzd
            if (r0 == 0) goto L6
            goto Lc4
        L6:
            com.google.android.gms.measurement.internal.zzfi r0 = r9.zza
            android.content.SharedPreferences r0 = r0.zza()
            java.lang.String r1 = r9.zzb
            r2 = 0
            java.lang.String r0 = r0.getString(r1, r2)
            if (r0 == 0) goto Lbc
            android.os.Bundle r1 = new android.os.Bundle     // Catch: org.json.JSONException -> Lab
            r1.<init>()     // Catch: org.json.JSONException -> Lab
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch: org.json.JSONException -> Lab
            r2.<init>(r0)     // Catch: org.json.JSONException -> Lab
            r0 = 0
        L20:
            int r3 = r2.length()     // Catch: org.json.JSONException -> Lab
            if (r0 >= r3) goto La8
            org.json.JSONObject r3 = r2.getJSONObject(r0)     // Catch: java.lang.Throwable -> L93
            java.lang.String r4 = "n"
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> L93
            java.lang.String r5 = "t"
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> L93
            int r6 = r5.hashCode()     // Catch: java.lang.Throwable -> L93
            r7 = 100
            java.lang.String r8 = "v"
            if (r6 == r7) goto L6d
            r7 = 108(0x6c, float:1.51E-43)
            if (r6 == r7) goto L59
            r7 = 115(0x73, float:1.61E-43)
            if (r6 == r7) goto L49
            goto L81
        L49:
            java.lang.String r6 = "s"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L81
            java.lang.String r3 = r3.getString(r8)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            r1.putString(r4, r3)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            goto La4
        L59:
            java.lang.String r6 = "l"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L81
            java.lang.String r3 = r3.getString(r8)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            long r5 = java.lang.Long.parseLong(r3)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            r1.putLong(r4, r5)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            goto La4
        L6d:
            java.lang.String r6 = "d"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L81
            java.lang.String r3 = r3.getString(r8)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            double r5 = java.lang.Double.parseDouble(r3)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            r1.putDouble(r4, r5)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            goto La4
        L81:
            com.google.android.gms.measurement.internal.zzfi r3 = r9.zza     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            com.google.android.gms.measurement.internal.zzgd r3 = r3.zzt     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            com.google.android.gms.measurement.internal.zzet r3 = r3.zzaA()     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            com.google.android.gms.measurement.internal.zzer r3 = r3.zzd()     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            java.lang.String r4 = "Unrecognized persisted bundle type. Type"
            r3.zzb(r4, r5)     // Catch: java.lang.Throwable -> L93 java.lang.Throwable -> L93
            goto La4
        L93:
            com.google.android.gms.measurement.internal.zzfi r3 = r9.zza     // Catch: org.json.JSONException -> Lab
            com.google.android.gms.measurement.internal.zzgd r3 = r3.zzt     // Catch: org.json.JSONException -> Lab
            com.google.android.gms.measurement.internal.zzet r3 = r3.zzaA()     // Catch: org.json.JSONException -> Lab
            com.google.android.gms.measurement.internal.zzer r3 = r3.zzd()     // Catch: org.json.JSONException -> Lab
            java.lang.String r4 = "Error reading value from SharedPreferences. Value dropped"
            r3.zza(r4)     // Catch: org.json.JSONException -> Lab
        La4:
            int r0 = r0 + 1
            goto L20
        La8:
            r9.zzd = r1     // Catch: org.json.JSONException -> Lab
            goto Lbc
        Lab:
            com.google.android.gms.measurement.internal.zzfi r0 = r9.zza
            com.google.android.gms.measurement.internal.zzgd r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzet r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzer r0 = r0.zzd()
            java.lang.String r1 = "Error loading bundle from SharedPreferences. Values will be lost"
            r0.zza(r1)
        Lbc:
            android.os.Bundle r0 = r9.zzd
            if (r0 != 0) goto Lc4
            android.os.Bundle r0 = r9.zzc
            r9.zzd = r0
        Lc4:
            android.os.Bundle r0 = r9.zzd
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfd.zza():android.os.Bundle");
    }

    public final void zzb(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        SharedPreferences.Editor editorEdit = this.zza.zza().edit();
        if (bundle.size() == 0) {
            editorEdit.remove(this.zzb);
        } else {
            String str = this.zzb;
            JSONArray jSONArray = new JSONArray();
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj != null) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("n", str2);
                        jSONObject.put("v", obj.toString());
                        if (obj instanceof String) {
                            jSONObject.put("t", "s");
                        } else if (obj instanceof Long) {
                            jSONObject.put("t", "l");
                        } else if (obj instanceof Double) {
                            jSONObject.put("t", "d");
                        } else {
                            this.zza.zzt.zzaA().zzd().zzb("Cannot serialize bundle value to SharedPreferences. Type", obj.getClass());
                        }
                        jSONArray.put(jSONObject);
                    } catch (JSONException e) {
                        this.zza.zzt.zzaA().zzd().zzb("Cannot serialize bundle value to SharedPreferences", e);
                    }
                }
            }
            editorEdit.putString(str, jSONArray.toString());
        }
        editorEdit.apply();
        this.zzd = bundle;
    }
}
