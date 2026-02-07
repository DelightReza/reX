package androidx.camera.core;

import androidx.lifecycle.LiveData;

/* loaded from: classes3.dex */
public interface CameraInfo {
    int getLensFacing();

    int getSensorRotationDegrees();

    int getSensorRotationDegrees(int i);

    LiveData getZoomState();

    boolean hasFlashUnit();
}
