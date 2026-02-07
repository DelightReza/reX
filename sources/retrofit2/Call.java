package retrofit2;

import okhttp3.Request;

/* loaded from: classes3.dex */
public interface Call<T> extends Cloneable {
    void cancel();

    /* renamed from: clone */
    Call mo19923clone();

    void enqueue(Callback callback);

    boolean isCanceled();

    Request request();
}
