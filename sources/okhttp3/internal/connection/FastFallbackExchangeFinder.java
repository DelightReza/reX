package okhttp3.internal.connection;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import kotlin.ExceptionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RoutePlanner;

/* loaded from: classes.dex */
public final class FastFallbackExchangeFinder implements ExchangeFinder {
    private final long connectDelayNanos;
    private final BlockingQueue connectResults;
    private long nextTcpConnectAtNanos;
    private final RoutePlanner routePlanner;
    private final TaskRunner taskRunner;
    private final CopyOnWriteArrayList tcpConnectsInFlight;

    public FastFallbackExchangeFinder(RoutePlanner routePlanner, TaskRunner taskRunner) {
        Intrinsics.checkNotNullParameter(routePlanner, "routePlanner");
        Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
        this.routePlanner = routePlanner;
        this.taskRunner = taskRunner;
        this.connectDelayNanos = TimeUnit.MILLISECONDS.toNanos(250L);
        this.nextTcpConnectAtNanos = Long.MIN_VALUE;
        this.tcpConnectsInFlight = new CopyOnWriteArrayList();
        this.connectResults = taskRunner.getBackend().decorate(new LinkedBlockingDeque());
    }

    @Override // okhttp3.internal.connection.ExchangeFinder
    public RoutePlanner getRoutePlanner() {
        return this.routePlanner;
    }

    @Override // okhttp3.internal.connection.ExchangeFinder
    public RealConnection find() throws IOException {
        RoutePlanner.ConnectResult connectResultLaunchTcpConnect;
        long j;
        IOException iOException = null;
        while (true) {
            try {
                if (!this.tcpConnectsInFlight.isEmpty() || RoutePlanner.CC.hasNext$default(getRoutePlanner(), null, 1, null)) {
                    if (getRoutePlanner().isCanceled()) {
                        throw new IOException("Canceled");
                    }
                    long jNanoTime = this.taskRunner.getBackend().nanoTime();
                    long j2 = this.nextTcpConnectAtNanos - jNanoTime;
                    if (this.tcpConnectsInFlight.isEmpty() || j2 <= 0) {
                        connectResultLaunchTcpConnect = launchTcpConnect();
                        j = this.connectDelayNanos;
                        this.nextTcpConnectAtNanos = jNanoTime + j;
                    } else {
                        j = j2;
                        connectResultLaunchTcpConnect = null;
                    }
                    if (connectResultLaunchTcpConnect != null || (connectResultLaunchTcpConnect = awaitTcpConnect(j, TimeUnit.NANOSECONDS)) != null) {
                        if (connectResultLaunchTcpConnect.isSuccess()) {
                            cancelInFlightConnects();
                            if (!connectResultLaunchTcpConnect.getPlan().isReady()) {
                                connectResultLaunchTcpConnect = connectResultLaunchTcpConnect.getPlan().mo3035connectTlsEtc();
                            }
                            if (connectResultLaunchTcpConnect.isSuccess()) {
                                return connectResultLaunchTcpConnect.getPlan().mo3031handleSuccess();
                            }
                        }
                        Throwable throwable = connectResultLaunchTcpConnect.getThrowable();
                        if (throwable != null) {
                            if (!(throwable instanceof IOException)) {
                                throw throwable;
                            }
                            if (iOException == null) {
                                iOException = (IOException) throwable;
                            } else {
                                ExceptionsKt.addSuppressed(iOException, throwable);
                            }
                        }
                        RoutePlanner.Plan nextPlan = connectResultLaunchTcpConnect.getNextPlan();
                        if (nextPlan != null) {
                            getRoutePlanner().getDeferredPlans().addFirst(nextPlan);
                        }
                    }
                } else {
                    cancelInFlightConnects();
                    Intrinsics.checkNotNull(iOException);
                    throw iOException;
                }
            } finally {
                cancelInFlightConnects();
            }
        }
    }

    private final RoutePlanner.ConnectResult launchTcpConnect() {
        final RoutePlanner.Plan failedPlan;
        if (RoutePlanner.CC.hasNext$default(getRoutePlanner(), null, 1, null)) {
            try {
                failedPlan = getRoutePlanner().plan();
            } catch (Throwable th) {
                failedPlan = new FailedPlan(th);
            }
            if (failedPlan.isReady()) {
                return new RoutePlanner.ConnectResult(failedPlan, null, null, 6, null);
            }
            if (failedPlan instanceof FailedPlan) {
                return ((FailedPlan) failedPlan).getResult();
            }
            this.tcpConnectsInFlight.add(failedPlan);
            TaskQueue.schedule$default(this.taskRunner.newQueue(), new Task(_UtilJvmKt.okHttpName + " connect " + getRoutePlanner().getAddress().url().redact()) { // from class: okhttp3.internal.connection.FastFallbackExchangeFinder.launchTcpConnect.1
                @Override // okhttp3.internal.concurrent.Task
                public long runOnce() throws InterruptedException {
                    RoutePlanner.ConnectResult connectResult;
                    try {
                        connectResult = failedPlan.mo3034connectTcp();
                    } catch (Throwable th2) {
                        connectResult = new RoutePlanner.ConnectResult(failedPlan, null, th2, 2, null);
                    }
                    if (!this.tcpConnectsInFlight.contains(failedPlan)) {
                        return -1L;
                    }
                    this.connectResults.put(connectResult);
                    return -1L;
                }
            }, 0L, 2, null);
        }
        return null;
    }

    private final RoutePlanner.ConnectResult awaitTcpConnect(long j, TimeUnit timeUnit) {
        RoutePlanner.ConnectResult connectResult;
        if (this.tcpConnectsInFlight.isEmpty() || (connectResult = (RoutePlanner.ConnectResult) this.connectResults.poll(j, timeUnit)) == null) {
            return null;
        }
        this.tcpConnectsInFlight.remove(connectResult.getPlan());
        return connectResult;
    }

    private final void cancelInFlightConnects() {
        Iterator it = this.tcpConnectsInFlight.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            RoutePlanner.Plan plan = (RoutePlanner.Plan) it.next();
            plan.mo3030cancel();
            RoutePlanner.Plan planMo3032retry = plan.mo3032retry();
            if (planMo3032retry != null) {
                getRoutePlanner().getDeferredPlans().addLast(planMo3032retry);
            }
        }
        this.tcpConnectsInFlight.clear();
    }
}
