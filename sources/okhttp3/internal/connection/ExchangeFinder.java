package okhttp3.internal.connection;

/* loaded from: classes.dex */
public interface ExchangeFinder {
    RealConnection find();

    RoutePlanner getRoutePlanner();
}
