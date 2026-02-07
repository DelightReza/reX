package androidx.work.impl;

import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public abstract class WorkDatabaseKt {
    private static final long PRUNE_THRESHOLD_MILLIS = TimeUnit.DAYS.toMillis(1);
}
