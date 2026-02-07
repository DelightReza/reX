package org.telegram.p023ui.Stories.recorder;

import org.telegram.p023ui.Stories.recorder.TimelineView;

/* loaded from: classes6.dex */
public final /* synthetic */ class TimelineView$VideoThumbsLoader$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TimelineView.VideoThumbsLoader f$0;

    public /* synthetic */ TimelineView$VideoThumbsLoader$$ExternalSyntheticLambda0(TimelineView.VideoThumbsLoader videoThumbsLoader) {
        this.f$0 = videoThumbsLoader;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.retrieveFrame();
    }
}
