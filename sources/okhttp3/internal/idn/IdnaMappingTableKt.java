package okhttp3.internal.idn;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class IdnaMappingTableKt {
    public static final int read14BitInt(String str, int i) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        char cCharAt = str.charAt(i);
        return (cCharAt << 7) + str.charAt(i + 1);
    }
}
