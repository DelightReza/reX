package androidx.camera.core.impl;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public interface Observable {

    public interface Observer {
        void onError(Throwable th);

        void onNewData(Object obj);
    }

    void addObserver(Executor executor, Observer observer);

    ListenableFuture fetchData();

    void removeObserver(Observer observer);
}
