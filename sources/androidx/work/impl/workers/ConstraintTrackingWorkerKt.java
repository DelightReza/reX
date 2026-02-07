package androidx.work.impl.workers;

import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.impl.utils.futures.SettableFuture;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class ConstraintTrackingWorkerKt {
    private static final String TAG;

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setFailed(SettableFuture settableFuture) {
        return settableFuture.set(ListenableWorker.Result.failure());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setRetry(SettableFuture settableFuture) {
        return settableFuture.set(ListenableWorker.Result.retry());
    }

    static {
        String strTagWithPrefix = Logger.tagWithPrefix("ConstraintTrkngWrkr");
        Intrinsics.checkNotNullExpressionValue(strTagWithPrefix, "tagWithPrefix(\"ConstraintTrkngWrkr\")");
        TAG = strTagWithPrefix;
    }
}
