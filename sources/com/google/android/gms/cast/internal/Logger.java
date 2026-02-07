package com.google.android.gms.cast.internal;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Locale;

/* loaded from: classes4.dex */
public class Logger {
    protected final String zza;
    private final boolean zzb;
    private boolean zzc;
    private final String zzd;

    public Logger(String str) {
        this(str, null);
    }

    /* renamed from: d */
    public void m297d(String str, Object... objArr) {
        if (zzc()) {
            Log.d(this.zza, zza(str, objArr));
        }
    }

    /* renamed from: e */
    public void m299e(String str, Object... objArr) {
        Log.e(this.zza, zza(str, objArr));
    }

    /* renamed from: i */
    public void m301i(String str, Object... objArr) {
        Log.i(this.zza, zza(str, objArr));
    }

    /* renamed from: w */
    public void m302w(String str, Object... objArr) {
        Log.w(this.zza, zza(str, objArr));
    }

    protected final String zza(String str, Object... objArr) {
        if (objArr.length != 0) {
            str = String.format(Locale.ROOT, str, objArr);
        }
        if (TextUtils.isEmpty(this.zzd)) {
            return str;
        }
        String str2 = this.zzd;
        return String.valueOf(str2).concat(String.valueOf(str));
    }

    public final boolean zzc() {
        if (Build.TYPE.equals("user")) {
            return false;
        }
        if (this.zzc) {
            return true;
        }
        return this.zzb && Log.isLoggable(this.zza, 3);
    }

    protected Logger(String str, String str2) {
        Preconditions.checkNotEmpty(str, "The log tag cannot be null or empty.");
        this.zza = str;
        this.zzb = str.length() <= 23;
        this.zzc = false;
        this.zzd = TextUtils.isEmpty(str2) ? null : String.format("[%s] ", str2);
    }

    /* renamed from: e */
    public void m300e(Throwable th, String str, Object... objArr) {
        Log.e(this.zza, zza(str, objArr), th);
    }

    /* renamed from: w */
    public void m303w(Throwable th, String str, Object... objArr) {
        Log.w(this.zza, zza(str, objArr), th);
    }

    /* renamed from: d */
    public void m298d(Throwable th, String str, Object... objArr) {
        if (zzc()) {
            Log.d(this.zza, zza(str, objArr), th);
        }
    }
}
