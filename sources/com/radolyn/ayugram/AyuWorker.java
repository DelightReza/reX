package com.radolyn.ayugram;

import com.radolyn.ayugram.controllers.AyuGhostController;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class AyuWorker {
    private static ScheduledFuture<?> scheduledTask;
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final ConcurrentHashMap<Integer, AtomicBoolean> needOffline = new ConcurrentHashMap<>();

    public static /* synthetic */ void $r8$lambda$KgWsHERdHqsKDkU_ehM6zGb60Cg(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    static {
        for (int i = 0; i < 16; i++) {
            needOffline.put(Integer.valueOf(i), new AtomicBoolean(false));
        }
    }

    public static synchronized void run() {
        try {
            ScheduledFuture<?> scheduledFuture = scheduledTask;
            if (scheduledFuture != null && !scheduledFuture.isDone()) {
                scheduledTask.cancel(false);
            }
            scheduledTask = scheduler.scheduleWithFixedDelay(new Runnable() { // from class: com.radolyn.ayugram.AyuWorker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AyuWorker.runOnce();
                }
            }, 1500L, 3000L, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void runOnce() {
        AtomicBoolean atomicBoolean;
        for (int i = 0; i < 16; i++) {
            if (UserConfig.getInstance(i).isClientActivated() && AyuGhostController.getInstance(i).isSendOfflinePacketAfterOnline() && (atomicBoolean = needOffline.get(Integer.valueOf(i))) != null && atomicBoolean.getAndSet(false)) {
                sendOffline(i);
            }
        }
    }

    private static void sendOffline(int i) {
        TL_account.updateStatus updatestatus = new TL_account.updateStatus();
        updatestatus.offline = true;
        ConnectionsManager.getInstance(i).sendRequest(updatestatus, new RequestDelegate() { // from class: com.radolyn.ayugram.AyuWorker$$ExternalSyntheticLambda1
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AyuWorker.$r8$lambda$KgWsHERdHqsKDkU_ehM6zGb60Cg(tLObject, tL_error);
            }
        });
    }

    public static synchronized void setOnline(int i, boolean z) {
        needOffline.get(Integer.valueOf(i)).set(z);
        run();
    }

    public static void shutdown() {
        scheduler.shutdown();
    }
}
