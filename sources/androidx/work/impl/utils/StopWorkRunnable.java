package androidx.work.impl.utils;

import androidx.work.Logger;
import androidx.work.impl.StartStopToken;
import androidx.work.impl.WorkManagerImpl;

/* loaded from: classes3.dex */
public class StopWorkRunnable implements Runnable {
    private static final String TAG = Logger.tagWithPrefix("StopWorkRunnable");
    private final boolean mStopInForeground;
    private final StartStopToken mToken;
    private final WorkManagerImpl mWorkManagerImpl;

    public StopWorkRunnable(WorkManagerImpl workManagerImpl, StartStopToken startStopToken, boolean z) {
        this.mWorkManagerImpl = workManagerImpl;
        this.mToken = startStopToken;
        this.mStopInForeground = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zStopWork;
        if (this.mStopInForeground) {
            zStopWork = this.mWorkManagerImpl.getProcessor().stopForegroundWork(this.mToken);
        } else {
            zStopWork = this.mWorkManagerImpl.getProcessor().stopWork(this.mToken);
        }
        Logger.get().debug(TAG, "StopWorkRunnable for " + this.mToken.getId().getWorkSpecId() + "; Processor.stopWork = " + zStopWork);
    }
}
