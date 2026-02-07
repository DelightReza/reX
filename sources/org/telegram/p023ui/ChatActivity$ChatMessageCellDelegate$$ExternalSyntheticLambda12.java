package org.telegram.p023ui;

import org.telegram.messenger.browser.Browser;

/* loaded from: classes5.dex */
public final /* synthetic */ class ChatActivity$ChatMessageCellDelegate$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ Browser.Progress f$0;

    public /* synthetic */ ChatActivity$ChatMessageCellDelegate$$ExternalSyntheticLambda12(Browser.Progress progress) {
        this.f$0 = progress;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.end();
    }
}
