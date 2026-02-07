package kotlinx.serialization.descriptors;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public abstract class SerialDescriptorKt {
    public static final Iterable getElementDescriptors(SerialDescriptor serialDescriptor) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        return new SerialDescriptorKt$special$$inlined$Iterable$1(serialDescriptor);
    }
}
