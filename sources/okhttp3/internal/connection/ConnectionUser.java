package okhttp3.internal.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import okhttp3.Connection;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Route;

/* loaded from: classes.dex */
public interface ConnectionUser {
    void acquireConnectionNoEvents(RealConnection realConnection);

    void addPlanToCancel(ConnectPlan connectPlan);

    void callConnectEnd(Route route, Protocol protocol);

    RealConnection candidateConnection();

    void connectFailed(Route route, Protocol protocol, IOException iOException);

    void connectStart(Route route);

    void connectionAcquired(Connection connection);

    void connectionConnectEnd(Connection connection, Route route);

    void connectionConnectionAcquired(RealConnection realConnection);

    void connectionConnectionClosed(RealConnection realConnection);

    void connectionConnectionReleased(RealConnection realConnection);

    void connectionReleased(Connection connection);

    void dnsEnd(String str, List list);

    void dnsStart(String str);

    boolean doExtensiveHealthChecks();

    boolean isCanceled();

    void noNewExchanges(RealConnection realConnection);

    void proxySelectEnd(HttpUrl httpUrl, List list);

    void proxySelectStart(HttpUrl httpUrl);

    Socket releaseConnectionNoEvents();

    void removePlanToCancel(ConnectPlan connectPlan);

    void secureConnectEnd(Handshake handshake);

    void secureConnectStart();

    void updateRouteDatabaseAfterSuccess(Route route);
}
