package org.telegram.p023ui.Cells;

import org.telegram.p023ui.Components.RLottieDrawable;

/* loaded from: classes5.dex */
public final /* synthetic */ class ChatActionCell$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ RLottieDrawable f$0;

    public /* synthetic */ ChatActionCell$$ExternalSyntheticLambda9(RLottieDrawable rLottieDrawable) {
        this.f$0 = rLottieDrawable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.restart();
    }
}
