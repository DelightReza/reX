package androidx.core.net;

import android.net.ConnectivityManager;

/* loaded from: classes3.dex */
public abstract class ConnectivityManagerCompat {
    public static boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) {
        return connectivityManager.isActiveNetworkMetered();
    }
}
