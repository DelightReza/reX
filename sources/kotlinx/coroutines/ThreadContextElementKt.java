package kotlinx.coroutines;

import kotlinx.coroutines.internal.ThreadLocalElement;

/* loaded from: classes4.dex */
public abstract class ThreadContextElementKt {
    public static final ThreadContextElement asContextElement(ThreadLocal threadLocal, Object obj) {
        return new ThreadLocalElement(obj, threadLocal);
    }
}
