package org.telegram.messenger.voip;

import android.text.TextUtils;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes.dex */
class VLog {
    /* renamed from: d */
    public static native void m1217d(String str);

    /* renamed from: e */
    public static native void m1218e(String str);

    /* renamed from: i */
    public static native void m1221i(String str);

    /* renamed from: v */
    public static native void m1222v(String str);

    /* renamed from: w */
    public static native void m1223w(String str);

    VLog() {
    }

    /* renamed from: e */
    public static void m1220e(Throwable th) {
        m1219e(null, th);
    }

    /* renamed from: e */
    public static void m1219e(String str, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        if (!TextUtils.isEmpty(str)) {
            stringWriter.append((CharSequence) str);
            stringWriter.append((CharSequence) ": ");
        }
        th.printStackTrace(new PrintWriter(stringWriter));
        String[] strArrSplit = stringWriter.toString().split("\n");
        for (String str2 : strArrSplit) {
            m1218e(str2);
        }
    }
}
