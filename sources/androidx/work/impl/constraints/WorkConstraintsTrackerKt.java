package androidx.work.impl.constraints;

import androidx.work.Logger;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class WorkConstraintsTrackerKt {
    private static final String TAG;

    static {
        String strTagWithPrefix = Logger.tagWithPrefix("WorkConstraintsTracker");
        Intrinsics.checkNotNullExpressionValue(strTagWithPrefix, "tagWithPrefix(\"WorkConstraintsTracker\")");
        TAG = strTagWithPrefix;
    }
}
