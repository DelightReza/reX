package org.telegram.p023ui;

import androidx.core.util.Consumer;
import java.util.List;
import org.telegram.p023ui.Components.ReactedUsersListView;

/* loaded from: classes.dex */
public final /* synthetic */ class ChatActivity$$ExternalSyntheticLambda423 implements Consumer {
    public final /* synthetic */ ReactedUsersListView f$0;

    public /* synthetic */ ChatActivity$$ExternalSyntheticLambda423(ReactedUsersListView reactedUsersListView) {
        this.f$0 = reactedUsersListView;
    }

    @Override // androidx.core.util.Consumer
    public final void accept(Object obj) {
        this.f$0.setSeenUsers((List) obj);
    }
}
