package androidx.camera.core;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class LegacySessionConfig extends SessionConfig {
    private final boolean isLegacy;

    public /* synthetic */ LegacySessionConfig(List list, ViewPort viewPort, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? null : viewPort, (i & 4) != 0 ? CollectionsKt.emptyList() : list2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacySessionConfig(List useCases, ViewPort viewPort, List effects) {
        super(useCases, viewPort, effects, null, null, null, 56, null);
        Intrinsics.checkNotNullParameter(useCases, "useCases");
        Intrinsics.checkNotNullParameter(effects, "effects");
        this.isLegacy = true;
    }

    @Override // androidx.camera.core.SessionConfig
    public boolean isLegacy() {
        return this.isLegacy;
    }
}
