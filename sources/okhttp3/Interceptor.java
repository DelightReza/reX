package okhttp3;

/* loaded from: classes.dex */
public interface Interceptor {

    public interface Chain {
        Call call();

        Response proceed(Request request);

        Request request();
    }

    Response intercept(Chain chain);
}
