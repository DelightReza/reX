package kotlinx.serialization;

import kotlinx.serialization.descriptors.SerialDescriptor;

/* loaded from: classes4.dex */
public interface KSerializer extends DeserializationStrategy {
    @Override // kotlinx.serialization.DeserializationStrategy
    SerialDescriptor getDescriptor();
}
