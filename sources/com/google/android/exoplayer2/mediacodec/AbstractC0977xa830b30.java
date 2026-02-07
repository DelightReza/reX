package com.google.android.exoplayer2.mediacodec;

import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecBufferEnqueuer$$ExternalSyntheticBackportWithForwarding1 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0977xa830b30 {
    /* renamed from: m */
    public static /* synthetic */ boolean m273m(AtomicReference atomicReference, Object obj, Object obj2) {
        while (!atomicReference.compareAndSet(obj, obj2)) {
            if (atomicReference.get() != obj) {
                return false;
            }
        }
        return true;
    }
}
