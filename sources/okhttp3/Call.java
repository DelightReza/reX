package okhttp3;

/* loaded from: classes.dex */
public interface Call extends Cloneable {

    public interface Factory {
        Call newCall(Request request);
    }

    void cancel();

    void enqueue(Callback callback);

    Response execute();

    boolean isCanceled();

    Request request();
}
