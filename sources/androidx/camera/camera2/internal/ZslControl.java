package androidx.camera.camera2.internal;

import androidx.camera.core.impl.SessionConfig;

/* loaded from: classes3.dex */
interface ZslControl {
    void addZslConfig(SessionConfig.Builder builder);

    void clearZslConfig();

    void setZslDisabledByFlashMode(boolean z);

    void setZslDisabledByUserCaseConfig(boolean z);
}
