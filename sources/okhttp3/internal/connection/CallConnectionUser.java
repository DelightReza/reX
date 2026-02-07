package okhttp3.internal.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Route;
import okhttp3.internal.http.RealInterceptorChain;

/* loaded from: classes.dex */
public final class CallConnectionUser implements ConnectionUser {
    private final RealCall call;
    private final RealInterceptorChain chain;
    private final ConnectionListener poolConnectionListener;

    public CallConnectionUser(RealCall call, ConnectionListener poolConnectionListener, RealInterceptorChain chain) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(poolConnectionListener, "poolConnectionListener");
        Intrinsics.checkNotNullParameter(chain, "chain");
        this.call = call;
        this.poolConnectionListener = poolConnectionListener;
        this.chain = chain;
    }

    private final EventListener getEventListener() {
        return this.call.getEventListener$okhttp();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void addPlanToCancel(ConnectPlan connectPlan) {
        Intrinsics.checkNotNullParameter(connectPlan, "connectPlan");
        this.call.getPlansToCancel$okhttp().add(connectPlan);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void removePlanToCancel(ConnectPlan connectPlan) {
        Intrinsics.checkNotNullParameter(connectPlan, "connectPlan");
        this.call.getPlansToCancel$okhttp().remove(connectPlan);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void updateRouteDatabaseAfterSuccess(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
        this.call.getClient().getRouteDatabase$okhttp().connected(route);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectStart(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
        getEventListener().connectStart(this.call, route.socketAddress(), route.proxy());
        this.poolConnectionListener.connectStart(route, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectFailed(Route route, Protocol protocol, IOException e) {
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(e, "e");
        getEventListener().connectFailed(this.call, route.socketAddress(), route.proxy(), null, e);
        this.poolConnectionListener.connectFailed(route, this.call, e);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void secureConnectStart() {
        getEventListener().secureConnectStart(this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void secureConnectEnd(Handshake handshake) {
        getEventListener().secureConnectEnd(this.call, handshake);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void callConnectEnd(Route route, Protocol protocol) {
        Intrinsics.checkNotNullParameter(route, "route");
        getEventListener().connectEnd(this.call, route.socketAddress(), route.proxy(), protocol);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectEnd(Connection connection, Route route) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        Intrinsics.checkNotNullParameter(route, "route");
        this.poolConnectionListener.connectEnd(connection, route, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionAcquired(Connection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        getEventListener().connectionAcquired(this.call, connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void acquireConnectionNoEvents(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        this.call.acquireConnectionNoEvents(connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public Socket releaseConnectionNoEvents() {
        return this.call.releaseConnectionNoEvents$okhttp();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionReleased(Connection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        getEventListener().connectionReleased(this.call, connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionAcquired(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener$okhttp().connectionAcquired(connection, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionReleased(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener$okhttp().connectionReleased(connection, this.call);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void connectionConnectionClosed(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener$okhttp().connectionClosed(connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void noNewExchanges(RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        connection.getConnectionListener$okhttp().noNewExchanges(connection);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public boolean doExtensiveHealthChecks() {
        return !Intrinsics.areEqual(this.chain.getRequest$okhttp().method(), "GET");
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public boolean isCanceled() {
        return this.call.isCanceled();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public RealConnection candidateConnection() {
        return this.call.getConnection();
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void proxySelectStart(HttpUrl url) {
        Intrinsics.checkNotNullParameter(url, "url");
        getEventListener().proxySelectStart(this.call, url);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void proxySelectEnd(HttpUrl url, List proxies) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(proxies, "proxies");
        getEventListener().proxySelectEnd(this.call, url, proxies);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void dnsStart(String socketHost) {
        Intrinsics.checkNotNullParameter(socketHost, "socketHost");
        getEventListener().dnsStart(this.call, socketHost);
    }

    @Override // okhttp3.internal.connection.ConnectionUser
    public void dnsEnd(String socketHost, List result) {
        Intrinsics.checkNotNullParameter(socketHost, "socketHost");
        Intrinsics.checkNotNullParameter(result, "result");
        getEventListener().dnsEnd(this.call, socketHost, result);
    }
}
