package org.telegram.messenger.voip;

/* loaded from: classes5.dex */
public final /* synthetic */ class VoIPService$$ExternalSyntheticLambda78 implements Runnable {
    public final /* synthetic */ NativeInstance f$0;

    public /* synthetic */ VoIPService$$ExternalSyntheticLambda78(NativeInstance nativeInstance) {
        this.f$0 = nativeInstance;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.stopGroup();
    }
}
