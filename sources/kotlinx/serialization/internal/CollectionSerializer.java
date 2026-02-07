package kotlinx.serialization.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public abstract class CollectionSerializer extends CollectionLikeSerializer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectionSerializer(KSerializer element) {
        super(element, null);
        Intrinsics.checkNotNullParameter(element, "element");
    }
}
