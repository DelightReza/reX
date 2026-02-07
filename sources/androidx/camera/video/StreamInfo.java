package androidx.camera.video;

import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.ConstantObservable;
import androidx.camera.core.impl.Observable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import p017j$.util.DesugarCollections;

/* loaded from: classes3.dex */
public abstract class StreamInfo {
    static final StreamInfo STREAM_INFO_ANY_INACTIVE = m77of(0, StreamState.INACTIVE);
    static final Set NON_SURFACE_STREAM_ID = DesugarCollections.unmodifiableSet(new HashSet(Arrays.asList(0, -1)));
    static final Observable ALWAYS_ACTIVE_OBSERVABLE = ConstantObservable.withValue(m77of(0, StreamState.ACTIVE));

    enum StreamState {
        ACTIVE,
        INACTIVE
    }

    public abstract int getId();

    public abstract SurfaceRequest.TransformationInfo getInProgressTransformationInfo();

    public abstract StreamState getStreamState();

    StreamInfo() {
    }

    /* renamed from: of */
    static StreamInfo m77of(int i, StreamState streamState) {
        return new AutoValue_StreamInfo(i, streamState, null);
    }

    /* renamed from: of */
    static StreamInfo m78of(int i, StreamState streamState, SurfaceRequest.TransformationInfo transformationInfo) {
        return new AutoValue_StreamInfo(i, streamState, transformationInfo);
    }
}
