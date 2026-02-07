package androidx.work.impl.utils.futures;

import com.google.common.util.concurrent.ListenableFuture;

/* loaded from: classes3.dex */
public final class SettableFuture extends AbstractFuture {
    public static SettableFuture create() {
        return new SettableFuture();
    }

    @Override // androidx.work.impl.utils.futures.AbstractFuture
    public boolean set(Object obj) {
        return super.set(obj);
    }

    @Override // androidx.work.impl.utils.futures.AbstractFuture
    public boolean setException(Throwable th) {
        return super.setException(th);
    }

    @Override // androidx.work.impl.utils.futures.AbstractFuture
    public boolean setFuture(ListenableFuture listenableFuture) {
        return super.setFuture(listenableFuture);
    }

    private SettableFuture() {
    }
}
