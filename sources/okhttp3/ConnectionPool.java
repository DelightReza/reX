package okhttp3;

import de.robv.android.xposed.callbacks.XCallback;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.ConnectionListener;
import okhttp3.internal.connection.ConnectionUser;
import okhttp3.internal.connection.ExchangeFinder;
import okhttp3.internal.connection.FastFallbackExchangeFinder;
import okhttp3.internal.connection.ForceConnectRoutePlanner;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.connection.RealRoutePlanner;
import okhttp3.internal.connection.RouteDatabase;

/* loaded from: classes.dex */
public final class ConnectionPool {
    private final RealConnectionPool delegate;

    public ConnectionPool(RealConnectionPool delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    public final RealConnectionPool getDelegate$okhttp() {
        return this.delegate;
    }

    public /* synthetic */ ConnectionPool(int i, long j, TimeUnit timeUnit, TaskRunner taskRunner, ConnectionListener connectionListener, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, RouteDatabase routeDatabase, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this((i7 & 1) != 0 ? 5 : i, (i7 & 2) != 0 ? 5L : j, (i7 & 4) != 0 ? TimeUnit.MINUTES : timeUnit, (i7 & 8) != 0 ? TaskRunner.INSTANCE : taskRunner, (i7 & 16) != 0 ? ConnectionListener.Companion.getNONE() : connectionListener, (i7 & 32) != 0 ? XCallback.PRIORITY_HIGHEST : i2, (i7 & 64) != 0 ? XCallback.PRIORITY_HIGHEST : i3, (i7 & 128) != 0 ? XCallback.PRIORITY_HIGHEST : i4, (i7 & 256) != 0 ? XCallback.PRIORITY_HIGHEST : i5, (i7 & 512) == 0 ? i6 : XCallback.PRIORITY_HIGHEST, (i7 & 1024) != 0 ? true : z, (i7 & 2048) == 0 ? z2 : true, (i7 & 4096) != 0 ? new RouteDatabase() : routeDatabase);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ConnectionPool(int i, long j, TimeUnit timeUnit, final TaskRunner taskRunner, ConnectionListener connectionListener, final int i2, final int i3, final int i4, final int i5, final int i6, final boolean z, final boolean z2, final RouteDatabase routeDatabase) {
        this(new RealConnectionPool(taskRunner, i, j, timeUnit, connectionListener, new Function3() { // from class: okhttp3.ConnectionPool$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ConnectionPool._init_$lambda$0(taskRunner, i2, i3, i4, i5, i6, z, z2, routeDatabase, (RealConnectionPool) obj, (Address) obj2, (ConnectionUser) obj3);
            }
        }));
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
        Intrinsics.checkNotNullParameter(connectionListener, "connectionListener");
        Intrinsics.checkNotNullParameter(routeDatabase, "routeDatabase");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExchangeFinder _init_$lambda$0(TaskRunner taskRunner, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, RouteDatabase routeDatabase, RealConnectionPool pool, Address address, ConnectionUser user) {
        Intrinsics.checkNotNullParameter(pool, "pool");
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(user, "user");
        return new FastFallbackExchangeFinder(new ForceConnectRoutePlanner(new RealRoutePlanner(taskRunner, pool, i, i2, i3, i4, i5, z, z2, address, routeDatabase, user)), taskRunner);
    }
}
