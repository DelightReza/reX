package androidx.camera.video.internal.encoder;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public interface EncoderFactory {
    Encoder createEncoder(Executor executor, EncoderConfig encoderConfig, int i);
}
