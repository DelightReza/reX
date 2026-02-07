package androidx.camera.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.params.SessionConfiguration;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public abstract /* synthetic */ class FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline4 {
    /* renamed from: m */
    public static /* synthetic */ SessionConfiguration m11m(int i, List list, Executor executor, CameraCaptureSession.StateCallback stateCallback) {
        return new SessionConfiguration(i, list, executor, stateCallback);
    }
}
