package kotlinx.coroutines.sync;

import kotlin.coroutines.Continuation;

/* loaded from: classes4.dex */
public interface Semaphore {
    Object acquire(Continuation continuation);

    int getAvailablePermits();

    void release();
}
