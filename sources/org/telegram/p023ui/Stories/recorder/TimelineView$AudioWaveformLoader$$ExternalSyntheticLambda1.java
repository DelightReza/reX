package org.telegram.p023ui.Stories.recorder;

import android.media.MediaCodec;
import java.io.IOException;
import org.telegram.p023ui.Stories.recorder.TimelineView;

/* loaded from: classes6.dex */
public final /* synthetic */ class TimelineView$AudioWaveformLoader$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ TimelineView.AudioWaveformLoader f$0;

    public /* synthetic */ TimelineView$AudioWaveformLoader$$ExternalSyntheticLambda1(TimelineView.AudioWaveformLoader audioWaveformLoader) {
        this.f$0 = audioWaveformLoader;
    }

    @Override // java.lang.Runnable
    public final void run() throws MediaCodec.CryptoException, IOException {
        this.f$0.run();
    }
}
