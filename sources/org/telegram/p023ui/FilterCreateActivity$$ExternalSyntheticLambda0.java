package org.telegram.p023ui;

import org.telegram.messenger.Utilities;
import org.telegram.tgnet.p022tl.TL_chatlists;

/* loaded from: classes5.dex */
public final /* synthetic */ class FilterCreateActivity$$ExternalSyntheticLambda0 implements Utilities.Callback {
    public final /* synthetic */ FilterCreateActivity f$0;

    public /* synthetic */ FilterCreateActivity$$ExternalSyntheticLambda0(FilterCreateActivity filterCreateActivity) {
        this.f$0 = filterCreateActivity;
    }

    @Override // org.telegram.messenger.Utilities.Callback
    public final void run(Object obj) {
        this.f$0.onEdit((TL_chatlists.TL_exportedChatlistInvite) obj);
    }
}
