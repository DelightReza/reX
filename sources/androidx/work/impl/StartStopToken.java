package androidx.work.impl;

import androidx.work.impl.model.WorkGenerationalId;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class StartStopToken {

    /* renamed from: id */
    private final WorkGenerationalId f50id;

    public StartStopToken(WorkGenerationalId id) {
        Intrinsics.checkNotNullParameter(id, "id");
        this.f50id = id;
    }

    public final WorkGenerationalId getId() {
        return this.f50id;
    }
}
